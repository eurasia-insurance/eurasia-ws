package tech.lapsa.eurasia.ws.jaxb.entity;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class XmlPOS implements Serializable {
    private static final long serialVersionUID = -3174271326978667664L;

    protected int id;

    protected String name;

    protected String address;

    protected XmlPOSPhone[] phones;

    protected XmlPOSEmail[] emails;

    @Override
    public String toString() {
	return ToStringBuilder.reflectionToString(this, Constants.DEFAULT_TO_STRING_STYLE);
    }

    public int getId() {
	return id;
    }

    public void setId(final int id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(final String name) {
	this.name = name;
    }

    public String getAddress() {
	return address;
    }

    public void setAddress(final String address) {
	this.address = address;
    }

    public XmlPOSPhone[] getPhones() {
	return phones;
    }

    public void setPhones(final XmlPOSPhone[] phones) {
	this.phones = phones;
    }

    public XmlPOSEmail[] getEmails() {
	return emails;
    }

    public void setEmails(final XmlPOSEmail[] emails) {
	this.emails = emails;
    }
}
