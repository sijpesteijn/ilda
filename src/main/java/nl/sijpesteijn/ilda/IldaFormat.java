package nl.sijpesteijn.ilda;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * User: gijs
 * Date: 03/02/14 21:43
 */
@XmlRootElement
public class IldaFormat {
    public static final int COORDINATE_HEADER_SIZE = 32;
    public static final int COORDINATE_DATA_SIZE = 8;
    public static final int COLOR_HEADER_SIZE = 32;
    public static final int COLOR_DATA_SIZE = 6;
    public static final double MAX_WIDTH = 65535;
    public static final double MAX_HEIGHT = 65535;

    @XmlElement
    private List<CoordinateHeader> coordinateHeaders = new ArrayList<CoordinateHeader>();
    private ColorHeader colorHeader;
    @XmlElement
    private List<ColorData> colorDatas = new ArrayList<ColorData>();
    private boolean dirty;
    @XmlElement
    private int maxWidth;
    @XmlElement
    private int minWidth;
    @XmlElement
    private int maxHeight;
    @XmlElement
    private int minHeight;
    @XmlElement
    private int minDepth;
    @XmlElement
    private int maxDepth;

    @XmlElement
    public void setColorHeader(final ColorHeader colorHeader) {
        this.colorHeader = colorHeader;
    }

    public List<CoordinateHeader> getCoordinateHeaders() {
        return coordinateHeaders;
    }

    public void setColorData(final List<ColorData> colorDatas) {
        this.colorDatas = colorDatas;
    }
    public List<ColorData> getColorData() {
        return colorDatas;
    }

    public ColorHeader getColorHeader() {
        return colorHeader;
    }

    public void addCoordinateHeader(final CoordinateHeader coordinateHeader) {
        coordinateHeaders.add(coordinateHeader);
    }

    public boolean isDirty() {
        return dirty;
    }

    @XmlElement
    public void setDirty(final boolean dirty) {
        this.dirty = dirty;
    }

    public double getMaxWidth() {
        return maxWidth;
    }

    public void setMaxWidth(final int maxWidth) {
        this.maxWidth = maxWidth;
    }

    public double getMinWidth() {
        return minWidth;
    }

    public void setMinWidth(final int minWidth) {
        this.minWidth = minWidth;
    }

    public double getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(final int maxHeight) {
        this.maxHeight = maxHeight;
    }

    public double getMinHeight() {
        return minHeight;
    }

    public void setMinHeight(final int minHeight) {
        this.minHeight = minHeight;
    }

    public double getMinDepth() {
        return minDepth;
    }

    public void setMinDepth(final int minDepth) {
        this.minDepth = minDepth;
    }

    public double getMaxDepth() {
        return maxDepth;
    }

    public void setMaxDepth(final int maxDepth) {
        this.maxDepth = maxDepth;
    }

    @Override
    public String toString() {
        return "IldaFormat{" +
                "coordinateHeaders=" + coordinateHeaders +
                ", colorHeader=" + colorHeader +
                ", colorDatas=" + colorDatas +
                ", dirty=" + dirty +
                ", maxWidth=" + maxWidth +
                ", minWidth=" + minWidth +
                ", maxHeight=" + maxHeight +
                ", minHeight=" + minHeight +
                ", minDepth=" + minDepth +
                ", maxDepth=" + maxDepth +
                '}';
    }
}
