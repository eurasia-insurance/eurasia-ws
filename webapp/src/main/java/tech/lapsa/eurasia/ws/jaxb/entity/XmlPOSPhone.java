package tech.lapsa.eurasia.ws.jaxb.entity;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class XmlPOSPhone implements Serializable {
    private static final long serialVersionUID = -9045502827763919007L;

    protected String type;

    protected String fullNumber;

    @Override
    public String toString() {
	return ToStringBuilder.reflectionToString(this, Constants.DEFAULT_TO_STRING_STYLE);
    }

    public String getType() {
	return type;
    }

    public void setType(final String type) {
	this.type = type;
    }

    public String getFullNumber() {
	return fullNumber;
    }

    public void setFullNumber(final String fullNumber) {
	this.fullNumber = fullNumber;
    }

}
