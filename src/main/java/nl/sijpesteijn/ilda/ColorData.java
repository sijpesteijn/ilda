package nl.sijpesteijn.ilda;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * User: gijs
 * Date: 03/02/14 22:11
 */
@XmlRootElement
public class ColorData {
    private int red1;
    private int red2;
    private int green1;
    private int green2;
    private int blue1;
    private int blue2;
    private int code;

    @XmlElement
    public void setRed1(final int red1) {
        this.red1 = red1;
    }

    @XmlElement
    public void setRed2(final int red2) {
        this.red2 = red2;
    }

    @XmlElement
    public void setGreen1(final int green1) {
        this.green1 = green1;
    }

    @XmlElement
    public void setGreen2(final int green2) {
        this.green2 = green2;
    }

    @XmlElement
    public void setBlue1(final int blue1) {
        this.blue1 = blue1;
    }

    @XmlElement
    public void setBlue2(final int blue2) {
        this.blue2 = blue2;
    }

    @XmlElement
    public void setCode(final int code) {
        this.code = code;
    }

    public int getRed1() {
        return red1;
    }

    public int getGreen1() {
        return green1;
    }

    public int getBlue1() {
        return blue1;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "ColorData{" +
                "red1=" + red1 +
                ", red2=" + red2 +
                ", green1=" + green1 +
                ", green2=" + green2 +
                ", blue1=" + blue1 +
                ", blue2=" + blue2 +
                ", code=" + code +
                "}\n";
    }

}
