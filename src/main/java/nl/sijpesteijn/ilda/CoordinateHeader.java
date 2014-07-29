package nl.sijpesteijn.ilda;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * User: gijs
 * Date: 03/02/14 20:53
 */
@XmlRootElement
public class CoordinateHeader {
    private String protocol;
    private boolean threeD;
    private String frameName;
    private String companyName;
    private int totalPoints;
    private int frameNumber;
    private int totalFrames;
    private int scannerHead;
    @XmlElement
    private List<CoordinateData> coordinateDatas = new ArrayList<CoordinateData>();

    @XmlElement
    public void setProtocol(final String protocol) {
        this.protocol = protocol;
    }

    @XmlElement
    public void setThreeD(final boolean threeD) {
        this.threeD = threeD;
    }

    @XmlElement
    public void setFrameName(final String frameName) {
        this.frameName = frameName;
    }

    @XmlElement
    public void setCompanyName(final String companyName) {
        this.companyName = companyName;
    }

    @XmlElement
    public void setTotalPoints(final int totalPoints) {
        this.totalPoints = totalPoints;
    }

    @XmlElement
    public void setFrameNumber(final int frameNumber) {
        this.frameNumber = frameNumber;
    }

    @XmlElement
    public void setTotalFrames(final int totalFrames) {
        this.totalFrames = totalFrames;
    }

    @XmlElement
    public void setScannerHead(final int scannerHead) {
        this.scannerHead = scannerHead;
    }

    public void setCoordinateDatas(List<CoordinateData> coordinateDatas) {
        this.coordinateDatas = coordinateDatas;
    }

    public boolean isThreeD() {
        return threeD;
    }

    public String getFrameName() {
        return frameName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public int getFrameNumber() {
        return frameNumber;
    }

    public int getTotalFrames() {
        return totalFrames;
    }

    public int getScannerHead() {
        return scannerHead;
    }

    public String getProtocol() {
        return protocol;
    }

    public List<CoordinateData> getCoordinateData() {
        return coordinateDatas;
    }

    @Override
    public String toString() {
        return "Protocol: "+ protocol + ", 3D: " + threeD + ", frameName: " + frameName + ", " +
                "companyName: " + companyName + ", totalPoints: " + totalPoints + ", frameNumber: " + frameNumber +
                ", totalFrames: " + totalFrames + ", scannerHead: " + scannerHead + "\n";
    }
}
