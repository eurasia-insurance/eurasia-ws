package tech.lapsa.eurasia.ws.rs.app;

import static tech.lapsa.javax.rs.utility.RESTUtils.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.lapsa.kz.country.KZCity;

import tech.lapsa.eurasia.domain.CompanyContactEmail;
import tech.lapsa.eurasia.domain.CompanyContactPhone;
import tech.lapsa.eurasia.domain.CompanyPointOfSale;
import tech.lapsa.eurasia.facade.CompanyPointOfSaleFacade.CompanyPointOfSaleFacadeRemote;
import tech.lapsa.eurasia.ws.jaxb.entity.XmlPOS;
import tech.lapsa.eurasia.ws.jaxb.entity.XmlPOSCity;
import tech.lapsa.eurasia.ws.jaxb.entity.XmlPOSEmail;
import tech.lapsa.eurasia.ws.jaxb.entity.XmlPOSPhone;

@Path("/pos")
@Produces({ MediaType.APPLICATION_JSON })
@PermitAll
public class POSWS {

    @EJB
    private CompanyPointOfSaleFacadeRemote posFacade;

    @GET
    @Path("/all")
    public Response allGET(@Context HttpHeaders headers) {
	final Locale locale = headers.getAcceptableLanguages().isEmpty()
		? Locale.getDefault()
		: headers.getAcceptableLanguages().get(0);
	return responseOk(_getAll(locale), locale);
    }

    private List<XmlPOSCity> _getAll(final Locale locale) {

	final List<CompanyPointOfSale> poses = posFacade.findAllAvailable();

	final List<KZCity> order = new ArrayList<>();
	final Map<KZCity, List<CompanyPointOfSale>> cityMap = new HashMap<>();
	for (final CompanyPointOfSale pos : poses) {
	    if (!cityMap.containsKey(pos.getAddress().getCity())) {
		cityMap.put(pos.getAddress().getCity(), new ArrayList<CompanyPointOfSale>());
		order.add(pos.getAddress().getCity());
	    }
	    cityMap.get(pos.getAddress().getCity()).add(pos);
	}

	final List<XmlPOSCity> result = new ArrayList<>();
	for (final KZCity kzc : order) {
	    final XmlPOSCity city = new XmlPOSCity();
	    result.add(city);
	    city.setName(kzc.few(locale));

	    {
		final List<XmlPOS> list1 = new ArrayList<>();
		for (final CompanyPointOfSale cpos : cityMap.get(kzc)) {
		    final XmlPOS pos = new XmlPOS();
		    list1.add(pos);
		    pos.setId(cpos.getId());
		    pos.setName(cpos.few(locale));
		    pos.setAddress(cpos.getAddress().few(locale));

		    {
			final List<XmlPOSPhone> list2 = new ArrayList<>();
			for (final CompanyContactPhone ccp : cpos.getPhones()) {
			    final XmlPOSPhone phone = new XmlPOSPhone();
			    list2.add(phone);
			    phone.setType(ccp.getPhoneType().regular(locale));
			    phone.setFullNumber(ccp.getPhone().getFormatted());
			}
			pos.setPhones(list2.toArray(new XmlPOSPhone[0]));
		    }

		    {
			final List<XmlPOSEmail> list2 = new ArrayList<>();
			for (final CompanyContactEmail cce : cpos.getEmailAddresses()) {
			    final XmlPOSEmail xmlPOSEmail = new XmlPOSEmail();
			    list2.add(xmlPOSEmail);
			    xmlPOSEmail.setAddress(cce.getAddress());
			}
			pos.setEmails(list2.toArray(new XmlPOSEmail[0]));
		    }

		}
		city.setPoses(list1.toArray(new XmlPOS[0]));
	    }
	}

	return result;
    }
}
