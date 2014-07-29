package nl.sijpesteijn.ilda;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * User: gijs
 * Date: 03/02/14 21:06
 */
@XmlRootElement
public class CoordinateData {
    private int x;
    private int y;
    private int z;
    private ColorData colorData;
    private boolean blanked;
    private boolean endImageData;

    @XmlElement
    public void setX(final int x) {
        this.x = x;
    }

    @XmlElement
    public void setY(final int y) {
        this.y = y;
    }

    @XmlElement
    public void setZ(final int z) {
        this.z = z;
    }

    @XmlElement
    public void setColorData(ColorData colorData) {
        this.colorData = colorData;
    }

    @XmlElement
    public void setBlanked(boolean blanked) {
        this.blanked = blanked;
    }

    @XmlElement
    public void setEndImageData(final boolean endImageData) {
        this.endImageData = endImageData;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public ColorData getColorData() {
        return colorData;
    }

    public boolean isBlanked() {
        return this.blanked;
    }

    public boolean isEndImageData() {
        return endImageData;
    }

    @Override
    public String toString() {
        return "CoordinateData{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", blanked=" + blanked +
                ", end image data=" + endImageData +
                ", color=" + colorData.toString() +
                "}\n";
    }
}
