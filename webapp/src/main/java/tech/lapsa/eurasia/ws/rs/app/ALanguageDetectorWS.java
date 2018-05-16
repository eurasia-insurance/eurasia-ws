package tech.lapsa.eurasia.ws.rs.app;

import java.util.Locale;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import com.lapsa.international.localization.LocalizationLanguage;

import tech.lapsa.eurasia.ws.rs.entity.LocalizationLanguageWrapped;

public abstract class ALanguageDetectorWS {

    @Context
    private HttpHeaders headers;

    protected LocalizationLanguage getLanguageOrDefault(final LocalizationLanguageWrapped langWrapped) {
	if (langWrapped != null)
	    return langWrapped.getLanguage();
	return getLanguageOrDefault();
    }

    protected LocalizationLanguage getLanguageOrDefault() {
	return LocalizationLanguage.byLocalePriorityListOrDefault(headers.getAcceptableLanguages());
    }

    protected Locale getLocaleOrDefault() {
	return getLanguageOrDefault().getLocale();
    }

    protected Locale getLocaleOrDefault(final LocalizationLanguageWrapped langWrapped) {
	if (langWrapped != null)
	    return langWrapped.getLanguage().getLocale();
	return getLocaleOrDefault();
    }
}
