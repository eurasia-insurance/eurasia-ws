package tech.lapsa.eurasia.ws.jaxb.entity;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.ToStringBuilder;

@XmlRootElement(name = "geo")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlGeo implements Serializable {
    private static final long serialVersionUID = 6143722379851972817L;

    @XmlAttribute
    protected double lat;

    @XmlAttribute
    protected double lng;

    @Override
    public String toString() {
	return ToStringBuilder.reflectionToString(this, Constants.DEFAULT_TO_STRING_STYLE);
    }

    public double getLat() {
	return lat;
    }

    public void setLat(final double lat) {
	this.lat = lat;
    }

    public double getLng() {
	return lng;
    }

    public void setLng(final double lng) {
	this.lng = lng;
    }

}
