package nl.sijpesteijn.ilda;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * User: gijs
 * Date: 03/02/14 22:01
 */
@XmlRootElement
public class ColorHeader {
    private int scannerHead;
    private int paletteNumber;
    private int totalColors;
    private String companyName;
    private String paletteName;
    private int formatCode;
    private String protocol;

    @XmlElement
    public void setScannerHead(final int scannerHead) {
        this.scannerHead = scannerHead;
    }

    @XmlElement
    public void setPaletteNumber(final int paletteNumber) {
        this.paletteNumber = paletteNumber;
    }

    @XmlElement
    public void setTotalColors(final int totalColors) {
        this.totalColors = totalColors;
    }

    @XmlElement
    public void setCompanyName(final String companyName) {
        this.companyName = companyName;
    }

    @XmlElement
    public void setPaletteName(final String paletteName) {
        this.paletteName = paletteName;
    }

    @XmlElement
    public void setFormatCode(final int formatCode) {
        this.formatCode = formatCode;
    }

    @XmlElement
    public void setProtocol(final String protocol) {
        this.protocol = protocol;
    }

    public int getScannerHead() {
        return scannerHead;
    }

    public int getPaletteNumber() {
        return paletteNumber;
    }

    public int getTotalColors() {
        return totalColors;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getPaletteName() {
        return paletteName;
    }

    public int getFormatCode() {
        return formatCode;
    }

    public String getProtocol() {
        return protocol;
    }

    @Override
    public String toString() {
        return "ColorHeader{" +
                "scannerHead=" + scannerHead +
                ", paletteNumber=" + paletteNumber +
                ", totalColors=" + totalColors +
                ", companyName='" + companyName + '\'' +
                ", paletteName='" + paletteName + '\'' +
                ", formatCode=" + formatCode +
                ", protocol='" + protocol + '\'' +
                "}\n";
    }
}
