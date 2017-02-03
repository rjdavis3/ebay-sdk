package com.ebay.clients.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Locale;

import org.junit.Test;

public class MarketplaceTest {

	@Test
	public void givenEbayAuIdWhenRetrievingMarketplaceThenReturnAustraliaMarketplace() {
		assertEquals(Marketplace.AUSTRALIA, Marketplace.toEnum("ebay-au"));
	}

	@Test
	public void givenEbayAtIdWhenRetrievingMarketplaceThenReturnAustriaMarketplace() {
		assertEquals(Marketplace.AUSTRIA, Marketplace.toEnum("ebay-At"));
	}

	@Test
	public void givenEbayBeIdWhenRetrievingMarketplaceThenReturnBelgiumMarketplace() {
		assertEquals(Marketplace.BELGIUM, Marketplace.toEnum("ebay-be"));
	}

	@Test
	public void givenEbayCaIdWhenRetrievingMarketplaceThenReturnCanadaMarketplace() {
		assertEquals(Marketplace.CANADA, Marketplace.toEnum("ebay-ca"));
	}

	@Test
	public void givenEbayCnIdWhenRetrievingMarketplaceThenReturnChinaMarketplace() {
		assertEquals(Marketplace.CHINA, Marketplace.toEnum("ebay-cn"));
	}

	@Test
	public void givenEbayFrIdWhenRetrievingMarketplaceThenReturnFranceMarketplace() {
		assertEquals(Marketplace.FRANCE, Marketplace.toEnum("ebay-fr"));
	}

	@Test
	public void givenEbayDeIdWhenRetrievingMarketplaceThenReturnGermanyMarketplace() {
		assertEquals(Marketplace.GERMANY, Marketplace.toEnum("ebay-de"));
	}

	@Test
	public void givenEbayHkIdWhenRetrievingMarketplaceThenReturnHongKongMarketplace() {
		assertEquals(Marketplace.HONG_KONG, Marketplace.toEnum("ebay-HK"));
	}

	@Test
	public void givenEbayInIdWhenRetrievingMarketplaceThenReturnIndiaMarketplace() {
		assertEquals(Marketplace.INDIA, Marketplace.toEnum("ebay-iN"));
	}

	@Test
	public void givenEbayIeIdWhenRetrievingMarketplaceThenReturnIrelandMarketplace() {
		assertEquals(Marketplace.IRELAND, Marketplace.toEnum("ebay-ie"));
	}

	@Test
	public void givenEbayItIdWhenRetrievingMarketplaceThenReturnItalyMarketplace() {
		assertEquals(Marketplace.ITALY, Marketplace.toEnum("ebay-it"));
	}

	@Test
	public void givenEbayMyIdWhenRetrievingMarketplaceThenReturnMalaysiaMarketplace() {
		assertEquals(Marketplace.MALAYSIA, Marketplace.toEnum("ebay-my"));
	}

	@Test
	public void givenEbayNlIdWhenRetrievingMarketplaceThenReturnNetherlandsMarketplace() {
		assertEquals(Marketplace.NETHERLANDS, Marketplace.toEnum("ebay-nl"));
	}

	@Test
	public void givenEbayPhIdWhenRetrievingMarketplaceThenReturnPhilippinesMarketplace() {
		assertEquals(Marketplace.PHILIPPINES, Marketplace.toEnum("ebay-ph"));
	}

	@Test
	public void givenEbayPlIdWhenRetrievingMarketplaceThenReturnPolandMarketplace() {
		assertEquals(Marketplace.POLAND, Marketplace.toEnum("ebay-pl"));
	}

	@Test
	public void givenEbaySgIdWhenRetrievingMarketplaceThenReturnSingaporeMarketplace() {
		assertEquals(Marketplace.SINGAPORE, Marketplace.toEnum("ebay-sg"));
	}

	@Test
	public void givenEbayEsIdWhenRetrievingMarketplaceThenReturnSpainMarketplace() {
		assertEquals(Marketplace.SPAIN, Marketplace.toEnum("ebay-es"));
	}

	@Test
	public void givenEbayChIdWhenRetrievingMarketplaceThenReturnSwitzerlandMarketplace() {
		assertEquals(Marketplace.SWITZERLAND, Marketplace.toEnum("ebay-ch"));
	}

	@Test
	public void givenEbayGbIdWhenRetrievingMarketplaceThenReturnUnitedKingdomMarketplace() {
		assertEquals(Marketplace.UNITED_KINGDOM, Marketplace.toEnum("ebay-gb"));
	}

	@Test
	public void givenEbayUsIdWhenRetrievingMarketplaceThenReturnUnitedStatesMarketplace() {
		assertEquals(Marketplace.UNITED_STATES, Marketplace.toEnum("ebay-us"));
	}

	@Test
	public void givenEbayUsMotorsIdWhenRetrievingMarketplaceThenReturnUnitedStatesMotorsMarketplace() {
		assertEquals(Marketplace.UNITED_STATES_MOTORS, Marketplace.toEnum("EBAY-US.MOTORS"));
	}

	@Test
	public void givenEbayNopeIdWhenRetrievingMarketplaceThenThowNewIllegalArgumentExcpetionWithCorrectMessage() {
		final String value = "ebay-nope";
		try {
			Marketplace.toEnum(value);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(String.format(Marketplace.NO_MATCHING_ENUMS_ERROR_MESSAGE, value), e.getMessage());
		}
	}

	@Test
	public void givenUnitedStatesMarketplaceWhenRetrievingSupportedLocalesThenReturnEnUsAndPtBrAndRuRuAndEsCo() {
		assertEquals(Marketplace.UNITED_STATES.getSupportedLocales(), Arrays.asList(Locale.forLanguageTag("en-US"),
				Locale.forLanguageTag("pt-BR"), Locale.forLanguageTag("ru-RU"), Locale.forLanguageTag("es-CO")));
	}

}
