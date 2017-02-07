package com.ebay.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Locale;

import org.junit.Test;

import com.ebay.models.Marketplace;

public class MarketplaceTest {

	@Test
	public void givenEbayAuIdWhenRetrievingMarketplaceThenReturnAustraliaMarketplace() {
		assertEquals(Marketplace.AUSTRALIA, Marketplace.toEnum("ebay_au"));
	}

	@Test
	public void givenEbayAtIdWhenRetrievingMarketplaceThenReturnAustriaMarketplace() {
		assertEquals(Marketplace.AUSTRIA, Marketplace.toEnum("ebay_At"));
	}

	@Test
	public void givenEbayBeNlIdWhenRetrievingMarketplaceThenReturnBelgiumDutchMarketplace() {
		assertEquals(Marketplace.BELGIUM_DUTCH, Marketplace.toEnum("EBAY_BE_NL"));
	}

	@Test
	public void givenEbayBeFreIdWhenRetrievingMarketplaceThenReturnBelgiumFrenchMarketplace() {
		assertEquals(Marketplace.BELGIUM_FRENCH, Marketplace.toEnum("EBAY_BE_fr"));
	}

	@Test
	public void givenEbayCaIdWhenRetrievingMarketplaceThenReturnCanadaEnglishMarketplace() {
		assertEquals(Marketplace.CANADA_ENGLISH, Marketplace.toEnum("ebay_ca"));
	}

	@Test
	public void givenEbayCaFrIdWhenRetrievingMarketplaceThenReturnCanadaFrenchMarketplace() {
		assertEquals(Marketplace.CANADA_FRENCH, Marketplace.toEnum("ebay_ca_fr"));
	}

	@Test
	public void givenEbayCnIdWhenRetrievingMarketplaceThenReturnChinaMarketplace() {
		assertEquals(Marketplace.CHINA, Marketplace.toEnum("ebay_cn"));
	}

	@Test
	public void givenEbayFrIdWhenRetrievingMarketplaceThenReturnFranceMarketplace() {
		assertEquals(Marketplace.FRANCE, Marketplace.toEnum("ebay_fr"));
	}

	@Test
	public void givenEbayDeIdWhenRetrievingMarketplaceThenReturnGermanyMarketplace() {
		assertEquals(Marketplace.GERMANY, Marketplace.toEnum("ebay_de"));
	}

	@Test
	public void givenEbayHkIdWhenRetrievingMarketplaceThenReturnHongKongMarketplace() {
		assertEquals(Marketplace.HONG_KONG, Marketplace.toEnum("ebay_HK"));
	}

	@Test
	public void givenEbayInIdWhenRetrievingMarketplaceThenReturnIndiaMarketplace() {
		assertEquals(Marketplace.INDIA, Marketplace.toEnum("ebay_iN"));
	}

	@Test
	public void givenEbayIeIdWhenRetrievingMarketplaceThenReturnIrelandMarketplace() {
		assertEquals(Marketplace.IRELAND, Marketplace.toEnum("ebay_ie"));
	}

	@Test
	public void givenEbayItIdWhenRetrievingMarketplaceThenReturnItalyMarketplace() {
		assertEquals(Marketplace.ITALY, Marketplace.toEnum("ebay_it"));
	}

	@Test
	public void givenEbayMyIdWhenRetrievingMarketplaceThenReturnMalaysiaMarketplace() {
		assertEquals(Marketplace.MALAYSIA, Marketplace.toEnum("ebay_my"));
	}

	@Test
	public void givenEbayNlIdWhenRetrievingMarketplaceThenReturnNetherlandsMarketplace() {
		assertEquals(Marketplace.NETHERLANDS, Marketplace.toEnum("ebay_nl"));
	}

	@Test
	public void givenEbayPhIdWhenRetrievingMarketplaceThenReturnPhilippinesMarketplace() {
		assertEquals(Marketplace.PHILIPPINES, Marketplace.toEnum("ebay_ph"));
	}

	@Test
	public void givenEbayPlIdWhenRetrievingMarketplaceThenReturnPolandMarketplace() {
		assertEquals(Marketplace.POLAND, Marketplace.toEnum("ebay_pl"));
	}

	@Test
	public void givenEbaySgIdWhenRetrievingMarketplaceThenReturnSingaporeMarketplace() {
		assertEquals(Marketplace.SINGAPORE, Marketplace.toEnum("ebay_sg"));
	}

	@Test
	public void givenEbayEsIdWhenRetrievingMarketplaceThenReturnSpainMarketplace() {
		assertEquals(Marketplace.SPAIN, Marketplace.toEnum("ebay_es"));
	}

	@Test
	public void givenEbayChIdWhenRetrievingMarketplaceThenReturnSwitzerlandMarketplace() {
		assertEquals(Marketplace.SWITZERLAND, Marketplace.toEnum("ebay_ch"));
	}

	@Test
	public void givenEbayGbIdWhenRetrievingMarketplaceThenReturnUnitedKingdomMarketplace() {
		assertEquals(Marketplace.UNITED_KINGDOM, Marketplace.toEnum("ebay_gb"));
	}

	@Test
	public void givenEbayUsIdWhenRetrievingMarketplaceThenReturnUnitedStatesMarketplace() {
		assertEquals(Marketplace.UNITED_STATES, Marketplace.toEnum("ebay_us"));
	}

	@Test
	public void givenEbayMotorsIdWhenRetrievingMarketplaceThenReturnUnitedStatesMotorsMarketplace() {
		assertEquals(Marketplace.MOTORS, Marketplace.toEnum("EBAY_MOTORS"));
	}

	@Test
	public void givenEbayNopeIdWhenRetrievingMarketplaceThenThowNewIllegalArgumentExcpetionWithCorrectMessage() {
		final String value = "ebay_nope";
		try {
			Marketplace.toEnum(value);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(String.format(Marketplace.NO_MATCHING_ENUMS_ERROR_MESSAGE, value), e.getMessage());
		}
	}

	@Test
	public void givenUnitedStatesMarketplaceWhenRetrievingSupportedLocaleThenReturnEnUs() {
		assertEquals(Marketplace.UNITED_STATES.getSupportedLocale(), Locale.forLanguageTag("en-US"));
	}

	@Test
	public void givenAustraliaMarketPlaceWhenRetrievingShoppingSiteIdThenReturn15() {
		assertEquals("15", Marketplace.AUSTRALIA.getShoppingSiteId());
	}

}
