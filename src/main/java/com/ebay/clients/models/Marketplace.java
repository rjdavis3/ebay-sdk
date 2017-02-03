package com.ebay.clients.models;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

public enum Marketplace {

	AUSTRALIA("EBAY-AU", "en-AU"),
	AUSTRIA("EBAY-AT", "de-AT"),
	BELGIUM("EBAY-BE", "nl-BE", "fr-BE"),
	CANADA("EBAY-CA", "en-CA", "fr-CA"),
	CHINA("EBAY-CN", "zh-HK"),
	FRANCE("EBAY-FR", "fr-FR"),
	GERMANY("EBAY-DE", "de-DE"),
	HONG_KONG("EBAY-HK", "zh-HK"),
	INDIA("EBAY-IN", "en-IN"),
	IRELAND("EBAY-IE", "en-IE"),
	ITALY("EBAY-IT", "it-IT"),
	MALAYSIA("EBAY-MY", "en-MY"),
	NETHERLANDS("EBAY-NL", "nl-NL"),
	PHILIPPINES("EBAY-PH", "en-PH"),
	POLAND("EBAY-PL", "pl-PL"),
	SINGAPORE("EBAY-SG", "en-SG"),
	SPAIN("EBAY-ES", "es-es"),
	SWITZERLAND("EBAY-CH", "de-CH"),
	UNITED_KINGDOM("EBAY-GB", "en-GB"),
	UNITED_STATES("EBAY-US", "en-US", "pt-BR", "ru-RU", "es-CO"),
	UNITED_STATES_MOTORS("EBAY-US.MOTORS", "en-US");

	static final String NO_MATCHING_ENUMS_ERROR_MESSAGE = "No matching enum found for %s";

	private final String id;
	private final List<Locale> supportedLocales;

	private Marketplace(final String id, final String... supportedLanguageTags) {
		this.id = id;
		this.supportedLocales = Arrays.asList(supportedLanguageTags).stream().map(Locale::forLanguageTag)
				.collect(Collectors.toList());
	}

	public String getId() {
		return id;
	}

	public List<Locale> getSupportedLocales() {
		return supportedLocales;
	}

	public static Marketplace toEnum(final String value) {
		final Optional<Marketplace> optionalMarketplace = Arrays.asList(values()).stream()
				.filter(marketplace -> marketplace.getId().equalsIgnoreCase(value)).findFirst();
		if (optionalMarketplace.isPresent()) {
			return optionalMarketplace.get();
		}
		throw new IllegalArgumentException(String.format(NO_MATCHING_ENUMS_ERROR_MESSAGE, value));
	}

}
