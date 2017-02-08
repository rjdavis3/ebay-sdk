package com.ebay.models;

import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;

/**
 * Contains Marketplace IDs common to both the Shopping API and new REST API.
 * Shopping Site IDs are needed for category ID retrieval while Marketplace ID
 * and language codes are needed for new eBay SELL APIs.
 * 
 * 
 * @see http://developer.ebay.com/devzone/rest/api-ref/inventory/types/
 *      MarketplaceEnum.html
 * @see https://developer.ebay.com/devzone/shopping/docs/CallRef/types/
 *      SiteCodeType.html
 * 
 * @author rjdavis
 *
 */
public enum Marketplace {

	AUSTRALIA("EBAY_AU", "15", "en-AU"),
	AUSTRIA("EBAY_AT", "16", "de-AT"),
	BELGIUM_DUTCH("EBAY_BE_NL", "123", "nl-BE"),
	BELGIUM_FRENCH("EBAY_BE_FR", "23", "fr-BE"),
	CANADA_ENGLISH("EBAY_CA", "2", "en-CA"),
	CANADA_FRENCH("EBAY_CA_FR", "210", "fr-CA"),
	CHINA("EBAY_CN", "223", "zh-HK"),
	FRANCE("EBAY_FR", "71", "fr-FR"),
	GERMANY("EBAY_DE", "77", "de-DE"),
	HONG_KONG("EBAY_HK", "201", "zh-HK"),
	INDIA("EBAY_IN", "203", "en-IN"),
	IRELAND("EBAY_IE", "205", "en-IE"),
	ITALY("EBAY_IT", "101", "it-IT"),
	MALAYSIA("EBAY_MY", "207", "en-MY"),
	NETHERLANDS("EBAY_NL", "146", "nl-NL"),
	PHILIPPINES("EBAY_PH", "211", "en-PH"),
	POLAND("EBAY_PL", "212", "pl-PL"),
	RUSSIA("EBAY_RU", "215", "ru-RU"),
	SINGAPORE("EBAY_SG", "216", "en-SG"),
	SPAIN("EBAY_ES", "186", "es-es"),
	SWEDEN("EBAY_SE", "218", "sv-SE"),
	SWITZERLAND("EBAY_CH", "193", "de-CH"),
	TAIWAN("EBAY_TW", "196", "zh-TW"),
	UNITED_KINGDOM("EBAY_GB", "3", "en-GB"),
	UNITED_STATES("EBAY_US", "0", "en-US"),
	MOTORS("EBAY_MOTORS", "100", "en-US");

	static final String NO_MATCHING_ENUMS_ERROR_MESSAGE = "No matching enum found for %s";

	private final String id;
	private final String shoppingSiteId;
	private final Locale supportedLocale;

	private Marketplace(final String id, final String shoppingSiteId, final String supportedLanguageTag) {
		this.id = id;
		this.shoppingSiteId = shoppingSiteId;
		this.supportedLocale = Locale.forLanguageTag(supportedLanguageTag);
	}

	public String getId() {
		return id;
	}

	public String getShoppingSiteId() {
		return shoppingSiteId;
	}

	public Locale getSupportedLocale() {
		return supportedLocale;
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
