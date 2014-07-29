package nl.sijpesteijn.ilda;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * User: gijs
 * Date: 09/02/14 18:08
 */
public class IldaWriter {

    public static void main(String args[]) throws Exception {
        IldaWriter writer = new IldaWriter();
        IldaReader reader = new IldaReader();
        final String fileName = "src/main/resources/examples/test.ild";
        final IldaFormat ildaFormat = reader.read(new File("src/main/resources/examples/astroid.ild"));
        writer.write(ildaFormat, fileName);
    }

    public void write(final IldaFormat ildaFormat, final String fileName) throws IOException {
        final Path path = Paths.get(fileName);
        final int totalByteSize = getTotalByteSize(ildaFormat);
        final byte[] bytes = new byte[totalByteSize];
        fillBytesWithCoordinate(bytes, ildaFormat.getCoordinateHeaders());
        Files.write(path, bytes);
    }

    private void fillBytesWithCoordinate(final byte[] bytes, final List<CoordinateHeader> coordinateHeaders) {
        int index = 0;
        for(CoordinateHeader coordinateHeader : coordinateHeaders) {
            // Ilda protocol
            bytes[index++] = (byte) coordinateHeader.getProtocol().charAt(0);
            bytes[index++] = (byte) coordinateHeader.getProtocol().charAt(1);
            bytes[index++] = (byte) coordinateHeader.getProtocol().charAt(2);
            bytes[index++] = (byte) coordinateHeader.getProtocol().charAt(3);
            // three unused 0 bytes
            bytes[index++] = 0;
            bytes[index++] = 0;
            bytes[index++] = 0;
            // Format code. 3D = 0, 2D = 1
            bytes[index++] = (byte) (coordinateHeader.isThreeD() ? 0 : 1);
            // Frame name 8 characters
            bytes[index++] = (byte) coordinateHeader.getFrameName().charAt(0);
            bytes[index++] = (byte) coordinateHeader.getFrameName().charAt(1);
            bytes[index++] = (byte) coordinateHeader.getFrameName().charAt(2);
            bytes[index++] = (byte) coordinateHeader.getFrameName().charAt(3);
            bytes[index++] = (byte) coordinateHeader.getFrameName().charAt(4);
            bytes[index++] = (byte) coordinateHeader.getFrameName().charAt(5);
            bytes[index++] = (byte) coordinateHeader.getFrameName().charAt(6);
            bytes[index++] = (byte) coordinateHeader.getFrameName().charAt(7);
            // Company name 8 characters
            bytes[index++] = (byte) coordinateHeader.getCompanyName().charAt(0);
            bytes[index++] = (byte) coordinateHeader.getCompanyName().charAt(1);
            bytes[index++] = (byte) coordinateHeader.getCompanyName().charAt(2);
            bytes[index++] = (byte) coordinateHeader.getCompanyName().charAt(3);
            bytes[index++] = (byte) coordinateHeader.getCompanyName().charAt(4);
            bytes[index++] = (byte) coordinateHeader.getCompanyName().charAt(5);
            bytes[index++] = (byte) coordinateHeader.getCompanyName().charAt(6);
            bytes[index++] = (byte) coordinateHeader.getCompanyName().charAt(7);
            // Total points
            bytes[index++] = getHighByte(coordinateHeader.getTotalPoints());
            bytes[index++] = getLowByte(coordinateHeader.getTotalPoints());
            // Frame number
            bytes[index++] = getHighByte(coordinateHeader.getFrameNumber());
            bytes[index++] = getLowByte(coordinateHeader.getFrameNumber());
            // Total frames
            bytes[index++] = getHighByte(coordinateHeader.getTotalFrames());
            bytes[index++] = getLowByte(coordinateHeader.getTotalFrames());
            // Scanner head
            bytes[index++] = getHighByte(coordinateHeader.getScannerHead());
            // Reserved future use. Must be 0
            bytes[index++] = 0;

            for(CoordinateData coordinateData : coordinateHeader.getCoordinateData()) {
                // X
                bytes[index++] = getHighByte(coordinateData.getX());
                bytes[index++] = getLowByte(coordinateData.getX());
                // Y
                bytes[index++] = getHighByte(coordinateData.getY());
                bytes[index++] = getLowByte(coordinateData.getY());
                // Z
                bytes[index++] = getHighByte(coordinateData.getZ());
                bytes[index++] = getLowByte(coordinateData.getZ());

                // Status. Blanked and end image data.

                if (coordinateData.isBlanked() || coordinateData.isEndImageData()) {
                    if (coordinateData.isBlanked() && coordinateData.isEndImageData()) {
                        bytes[index++] = (byte)-64;
                    } else if (coordinateData.isBlanked()) {
                        bytes[index++] = (byte)64;
                    } else if (coordinateData.isEndImageData()) {
//                        don't know. bytes[index++] = ;
                    }
                } else {
                    bytes[index++] = 0;
                }
                // Color code
                bytes[index++] = getLowByte(coordinateData.getColorData().getCode());
            }
        }
    }

    private byte getLowByte(final int number) {
        return (byte) (number & 0xFF);
    }

    private byte getHighByte(final int number) {
        return (byte) ((number >> 8) & 0xFF);
    }

    private int getTotalByteSize(final IldaFormat ildaFormat) {
        int size = 0;
        for(CoordinateHeader coordinateHeader: ildaFormat.getCoordinateHeaders()) {
            size += IldaFormat.COORDINATE_HEADER_SIZE + (coordinateHeader.getTotalPoints() * IldaFormat
                    .COORDINATE_DATA_SIZE);
        }
        return size;
    }
}
