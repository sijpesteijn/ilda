package nl.sijpesteijn.ilda;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class IldaReader {
    public IldaFormat ildaFormat = new IldaFormat();
    private IldaColorTable colorTable = new IldaColorTable();
    private int max_height = 0;
    private int min_height = 0;
    private int max_width = 0;
    private int min_width = 0;
    private int max_depth = 0;
    private int min_depth = 0;

    public static void main(String args[]) throws Exception {
        IldaReader reader = new IldaReader();
        IldaFormat content = reader.read(new File("src/main/resources/examples/test.ild"));
        List<CoordinateHeader> coordinateHeaders = content.getCoordinateHeaders();
        for (CoordinateHeader coordinateHeader : coordinateHeaders) {
            int index = 0;
            List<CoordinateData> data = coordinateHeader.getCoordinateData();
            for (CoordinateData dat : data) {
                System.out.println("Frame nr: " + coordinateHeader.getFrameNumber() + ", point : " + index++
                        + ": x= " + dat.getX() + ", y= " + dat.getY() + ", z= " + dat.getZ() + ", color: " + dat.getColorData());
            }
        }
        if (content.getColorHeader() != null) {
            System.out.println(content.getColorHeader().toString());
        }
        assert (content.getCoordinateHeaders() != null);
        assert (content.getColorHeader().getTotalColors() == content.getColorData().size());
    }

    public IldaFormat read(File fileName) throws IOException, URISyntaxException {
        Path file = Paths.get(fileName.getAbsolutePath());
        byte[] fileArray = Files.readAllBytes(file);
        int offset = 0;

        CoordinateHeader coordinateHeader = getCoordinateHeader(fileArray, offset);
        ildaFormat.addCoordinateHeader(coordinateHeader);
        while (coordinateHeader.getTotalPoints() > 0) {
            List<CoordinateData> coordinateDatas = new ArrayList<CoordinateData>();
            for (int i = 0; i < coordinateHeader.getTotalPoints(); i++) {
                byte[] data = Arrays.copyOfRange(fileArray, offset + IldaFormat.COORDINATE_HEADER_SIZE + i *
                        IldaFormat.COORDINATE_DATA_SIZE,
                        offset + IldaFormat.COORDINATE_HEADER_SIZE + (i + 1) * IldaFormat.COORDINATE_DATA_SIZE);
                CoordinateData coordinateData = getCoordinateData(data);
                coordinateDatas.add(coordinateData);
            }
            coordinateHeader.setCoordinateDatas(coordinateDatas);
            offset += IldaFormat.COORDINATE_HEADER_SIZE + (IldaFormat.COORDINATE_DATA_SIZE * coordinateHeader.getTotalPoints());
            coordinateHeader = getCoordinateHeader(fileArray, offset);
            ildaFormat.addCoordinateHeader(coordinateHeader);
        }
        ildaFormat.setMaxWidth(max_width);
        ildaFormat.setMinWidth(min_width);
        ildaFormat.setMaxHeight(max_height);
        ildaFormat.setMinHeight(min_height);
        ildaFormat.setMaxDepth(max_depth);
        ildaFormat.setMinDepth(min_depth);

        int start_color_header = offset + IldaFormat.COORDINATE_HEADER_SIZE + (coordinateHeader.getTotalPoints() *
                IldaFormat.COORDINATE_DATA_SIZE);
        if (start_color_header < fileArray.length) {
            byte[] data = Arrays.copyOfRange(fileArray, start_color_header, start_color_header + IldaFormat.COLOR_HEADER_SIZE);
            ColorHeader colorHeader = getColorHeader(data);
            ildaFormat.setColorHeader(colorHeader);

            int start_color_data = start_color_header + IldaFormat.COLOR_HEADER_SIZE;
            List<ColorData> colorDatas = new ArrayList<ColorData>();
            for (int i = 0; i < colorHeader.getTotalColors(); i++) {
                data = Arrays.copyOfRange(fileArray, start_color_data + i * IldaFormat.COLOR_DATA_SIZE,
                        start_color_data + (i + 1) * IldaFormat.COLOR_DATA_SIZE);
                ColorData colorData = getColorData(data);
                colorDatas.add(colorData);
            }
            ildaFormat.setColorData(colorDatas);
        }
        return ildaFormat;
    }

    private ColorData getColorData(final byte[] data) {
        ColorData colorData = new ColorData();
        int red1 = getSmallInteger(data[0]);
        int green1 = getSmallInteger(data[1]);
        int blue1 = getSmallInteger(data[2]);
        int red2 = getSmallInteger(data[3]);
        int green2 = getSmallInteger(data[4]);
        int blue2 = getSmallInteger(data[5]);

        colorData.setRed1(red1);
        colorData.setRed2(red2);
        colorData.setGreen1(green1);
        colorData.setGreen2(green2);
        colorData.setBlue1(blue1);
        colorData.setBlue2(blue2);
        return colorData;
    }

    private ColorHeader getColorHeader(final byte[] data) {
        ColorHeader colorHeader = new ColorHeader();
        String protocol = getProtocol(data[0], data[1], data[2], data[3]);
        int formatCode = getSmallInteger(data[7]);
        String paletteName = getString(data, 8, 15);
        String companyName = getString(data, 16, 23);
        int totalColors = getInteger(data[24], data[25]);
        int paletteNumber = getInteger(data[26], data[27]);
        int scannerHead = getSmallInteger(data[30]);
        colorHeader.setProtocol(protocol);
        colorHeader.setFormatCode(formatCode);
        colorHeader.setPaletteName(paletteName);
        colorHeader.setCompanyName(companyName);
        colorHeader.setTotalColors(totalColors);
        colorHeader.setPaletteNumber(paletteNumber);
        colorHeader.setScannerHead(scannerHead);
        return colorHeader;
    }

    private CoordinateData getCoordinateData(final byte[] data) {
        final int x = getCoordinate(data[0], data[1]);
        final int y = -1 * getCoordinate(data[2], data[3]);
        final int z = getCoordinate(data[4], data[5]);
        final boolean blanked = isBitSet(data[6], 6);
        if (!blanked) {
            setMaxMinValue(x,y,z);
        }
        final boolean endImageData = isBitSet(data[6],7);
        final int colorCode = getSmallInteger(data[7]);

        CoordinateData coordinateData = new CoordinateData();
        coordinateData.setX(x);
        coordinateData.setY(y);
        coordinateData.setZ(z);
        coordinateData.setBlanked(blanked);
        coordinateData.setEndImageData(endImageData);
        ColorData color = colorTable.getColor(colorCode);
        assert(color != null);
        coordinateData.setColorData(color);
        return coordinateData;
    }

    private void setMaxMinValue(final int x, final int y, final int z) {
        if (x > max_width) {
            max_width = x;
        }
        if (x < min_width) {
            min_width = x;
        }
        if (y > max_height) {
            max_height = y;
        }
        if (y < min_height) {
            min_height = y;
        }
        if (z > max_depth) {
            max_depth = z;
        }
        if (z < min_depth) {
            min_depth = z;
        }
    }

    private CoordinateHeader getCoordinateHeader(final byte[] fileArray, final int offset) {
        String protocol = getProtocol(fileArray[0 + offset], fileArray[1 + offset], fileArray[2 + offset], fileArray[3 + offset]);
        boolean threeD = get3D(fileArray[7 + offset]);
        String frameName = getString(fileArray, 8 + offset, 15 + offset);
        String companyName = getString(fileArray, 16 + offset, 23 + offset);
        int totalPoints = getInteger(fileArray[24 + offset], fileArray[25 + offset]);
        int frameNumber = getInteger(fileArray[26 + offset], fileArray[27 + offset]);
        int totalFrames = getInteger(fileArray[28 + offset], fileArray[29 + offset]);
        int scannerHead = getSmallInteger(fileArray[30 + offset]);

        final CoordinateHeader coordinateHeader = new CoordinateHeader();
        coordinateHeader.setProtocol(protocol);
        coordinateHeader.setThreeD(threeD);
        coordinateHeader.setFrameName(frameName);
        coordinateHeader.setCompanyName(companyName);
        coordinateHeader.setTotalPoints(totalPoints);
        coordinateHeader.setFrameNumber(frameNumber);
        coordinateHeader.setTotalFrames(totalFrames);
        coordinateHeader.setScannerHead(scannerHead);
        return coordinateHeader;
    }

    private int getCoordinate(final byte b, final byte b1) {
        return (short) (((b & 0xFF) << 8) | b1 & 0xFF);
    }

    private int getSmallInteger(final byte b) {
        return  (int) b & 0xff;
    }

    private static Boolean isBitSet(byte b, int bit) {
        return (b & (1 << bit)) != 0;
    }

    private int getInteger(final byte b, final byte b1) {
        int high = b & 0xff;
        int low = b1 & 0xff;
        return (high << 8 | low);
    }

    private String getString(final byte[] fileArray, final int start, final int stop) {
        String name = "";
        for (int i = start; i <= stop; i++) {
            name += (char) fileArray[i];
        }
        return name;
    }

    private boolean get3D(final byte b) {
        return b == 0;
    }

    private String getProtocol(final byte b, final byte b1, final byte b2, final byte b3) {
        return "" + (char) b + (char) b1 + (char) b2 + (char) b3;
    }
}
