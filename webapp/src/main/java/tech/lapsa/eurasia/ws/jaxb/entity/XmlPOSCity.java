package tech.lapsa.eurasia.ws.jaxb.entity;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class XmlPOSCity implements Serializable {
    private static final long serialVersionUID = -476894803974611269L;

    protected String name;

    protected XmlPOS[] poses;

    @Override
    public String toString() {
	return ToStringBuilder.reflectionToString(this, Constants.DEFAULT_TO_STRING_STYLE);
    }

    public String getName() {
	return name;
    }

    public void setName(final String name) {
	this.name = name;
    }

    public XmlPOS[] getPoses() {
	return poses;
    }

    public void setPoses(final XmlPOS[] poses) {
	this.poses = poses;
    }
}
