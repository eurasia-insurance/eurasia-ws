package tech.lapsa.eurasia.ws.rs.app;

import static tech.lapsa.javax.rs.utility.RESTUtils.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.lapsa.international.localization.LocalizationLanguage;
import com.lapsa.kz.country.KZCity;

import tech.lapsa.eurasia.domain.CompanyContactEmail;
import tech.lapsa.eurasia.domain.CompanyContactPhone;
import tech.lapsa.eurasia.domain.CompanyPointOfSale;
import tech.lapsa.eurasia.facade.CompanyPointOfSaleFacade.CompanyPointOfSaleFacadeRemote;
import tech.lapsa.eurasia.ws.jaxb.entity.XmlGeo;
import tech.lapsa.eurasia.ws.jaxb.entity.XmlPOS;
import tech.lapsa.eurasia.ws.jaxb.entity.XmlPOSCity;
import tech.lapsa.eurasia.ws.jaxb.entity.XmlPOSEmail;
import tech.lapsa.eurasia.ws.jaxb.entity.XmlPOSPhone;
import tech.lapsa.eurasia.ws.rs.entity.LocalizationLanguageWrapped;
import tech.lapsa.javax.validation.NotNullValue;

@Path("/pos")
@Produces({ MediaType.APPLICATION_JSON })
@PermitAll
public class POSWS extends ALanguageDetectorWS {

    @Context
    private UriInfo uriInfo;

    @EJB
    private CompanyPointOfSaleFacadeRemote posFacade;

    @GET
    @Path("/all/{lang}")
    public Response getAllOwnAvailableLangPath(
	    @PathParam("lang") @NotNullValue final LocalizationLanguageWrapped queryLangWrapped) {
	final LocalizationLanguage lang = getLanguageOrDefault(queryLangWrapped);

	return responseOk(all(lang), lang.getLocale());
    }

    @GET
    @Path("/all")
    public Response getAllOwnAvailableLangDefault(
	    @QueryParam("lang") final LocalizationLanguageWrapped queryLangWrapped) {
	final LocalizationLanguage lang = getLanguageOrDefault(queryLangWrapped);
	return responseOk(all(lang), lang.getLocale());
    }

    private Object all(final LocalizationLanguage language) {

	final List<CompanyPointOfSale> poses = posFacade.findAllOwnOffices();

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
	    city.setName(kzc.few(language.getLocale()));

	    {
		final List<XmlPOS> list1 = new ArrayList<>();
		for (final CompanyPointOfSale cpos : cityMap.get(kzc)) {
		    final XmlPOS pos = new XmlPOS();
		    list1.add(pos);
		    pos.setId(cpos.getId());
		    pos.setName(cpos.few(language.getLocale()));
		    pos.setAddress(cpos.getAddress().few(language.getLocale()));
		    pos.setDeliveryServiceEnable(cpos.isDeliveryServicesAvailable());
		    pos.setPickupServiceAvailable(cpos.isPickupAvailable());

		    if (cpos.getGeoPoint() != null) {
			pos.setGeoPoint(new XmlGeo());
			pos.getGeoPoint().setLat(cpos.getGeoPoint().getLatitude());
			pos.getGeoPoint().setLng(cpos.getGeoPoint().getLongitude());
		    }

		    {
			final List<XmlPOSPhone> list2 = new ArrayList<>();
			for (final CompanyContactPhone ccp : cpos.getPhones()) {
			    final XmlPOSPhone phone = new XmlPOSPhone();
			    list2.add(phone);
			    phone.setType(ccp.getPhoneType().regular(language.getLocale()));
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
