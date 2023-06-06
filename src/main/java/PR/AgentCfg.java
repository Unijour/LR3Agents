package PR;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "ConfigPR")
@XmlAccessorType(XmlAccessType.FIELD)
public class AgentCfg {

    @XmlElementWrapper(name = "neighbour")
    @XmlElement(name = "neighbours")
    private List<Neighbour> neighbours;
    @XmlElement(name = "startpoint")
    private boolean startpoint;
    @XmlElement(name = "endpoint")
    private boolean endpoint;

    @XmlElement(name = "destination")
    private String destination;

    @XmlElement(name = "battery")
    private double battery;

    @XmlElement(name = "consumption")
    private double consumption;

    @XmlElement(name = "type")
    private String type;

    @XmlElement(name = "owner")
    private String owner;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getBattery() {
        return battery;
    }

    public double getConsumption() {
        return consumption;
    }

    public void setConsumption(double consumption) {
        this.consumption = consumption;
    }

    public void setBattery(double battery) {
        this.battery = battery;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public boolean isStartpoint() {
        return startpoint;
    }

    public void setStartpoint(boolean startpoint) {
        this.startpoint = startpoint;
    }

    public boolean isEndpoint() {
        return endpoint;
    }

    public void setEndpoint(boolean endpoint) {
        this.endpoint = endpoint;
    }



    public List<Neighbour> getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(List<Neighbour> neighbours) {
        this.neighbours = neighbours;
    }
}


