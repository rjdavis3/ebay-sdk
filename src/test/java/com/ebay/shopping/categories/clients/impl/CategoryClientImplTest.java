package com.ebay.shopping.categories.clients.impl;

import static com.github.restdriver.clientdriver.RestClientDriver.giveResponse;
import static com.github.restdriver.clientdriver.RestClientDriver.onRequestTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.net.URI;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import com.ebay.exceptions.EbayErrorResponseException;
import com.ebay.models.Marketplace;
import com.ebay.shopping.categories.clients.CategoryClient;
import com.ebay.shopping.categories.models.CategoryType;
import com.github.restdriver.clientdriver.ClientDriverRequest.Method;
import com.github.restdriver.clientdriver.ClientDriverRule;

public class CategoryClientImplTest {

	private static final String SOME_CLIENT_ID = "some-client-id";
	private static final Marketplace SOME_MARKETPLACE = Marketplace.UNITED_KINGDOM;

	private static final String CONSUMER_ELECTRONICS_CATEGORY_PARENT_ID = "-1";
	private static final String CONSUMER_ELECTRONICS_CATEGORY_ID = "293";
	private static final Integer CONSUMER_ELECTRONICS_CATEGORY_LEVEL = 1;
	private static final String CONSUMER_ELECTRONICS_CATEGORY_NAME = "Consumer Electronics";
	private static final Boolean CONSUMER_ELECTRONICS_LEAF_CATEGORY = false;
	private static final String CONSUMER_ELECTRONICS_XML_RESPONSE_BODY = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<GetCategoryInfoResponse xmlns=\"urn:ebay:apis:eBLBaseComponents\">\r\n    <Timestamp>2017-02-07T19:31:25.973Z</Timestamp>\r\n    <Ack>Success</Ack>\r\n    <Build>E981_CORE_APILW_18070859_R1</Build>\r\n    <Version>981</Version>\r\n    <CategoryArray>\r\n        <Category>\r\n            <CategoryID>"
			+ CONSUMER_ELECTRONICS_CATEGORY_ID + "</CategoryID>\r\n            <CategoryLevel>"
			+ CONSUMER_ELECTRONICS_CATEGORY_LEVEL + "</CategoryLevel>\r\n            <CategoryName>"
			+ CONSUMER_ELECTRONICS_CATEGORY_NAME + "</CategoryName>\r\n            <CategoryParentID>"
			+ CONSUMER_ELECTRONICS_CATEGORY_PARENT_ID + "</CategoryParentID>\r\n            <CategoryNamePath>"
			+ CONSUMER_ELECTRONICS_CATEGORY_NAME + "</CategoryNamePath>\r\n            <CategoryIDPath>"
			+ CONSUMER_ELECTRONICS_CATEGORY_ID + "</CategoryIDPath>\r\n            <LeafCategory>"
			+ CONSUMER_ELECTRONICS_LEAF_CATEGORY
			+ "</LeafCategory>\r\n        </Category>\r\n    </CategoryArray>\r\n    <CategoryCount>1</CategoryCount>\r\n    <UpdateTime>2016-08-31T23:33:02.000Z</UpdateTime>\r\n    <CategoryVersion>115</CategoryVersion>\r\n</GetCategoryInfoResponse>";
	private static final String INVALID_CATEGORY_ON_CURRENT_SITE_XML_RESPONSE_BODY = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<GetCategoryInfoResponse xmlns=\"urn:ebay:apis:eBLBaseComponents\">\r\n    <Timestamp>2017-02-07T20:08:27.105Z</Timestamp>\r\n    <Ack>Failure</Ack>\r\n    <Errors>\r\n        <ShortMessage>Category is invalid on current site.</ShortMessage>\r\n        <LongMessage>Category is invalid on current site.</LongMessage>\r\n        <ErrorCode>10.54</ErrorCode>\r\n        <SeverityCode>Error</SeverityCode>\r\n        <ErrorClassification>RequestError</ErrorClassification>\r\n    </Errors>\r\n    <Build>E981_CORE_APILW_18070859_R1</Build>\r\n    <Version>981</Version>\r\n</GetCategoryInfoResponse>";
	private static final String CONSUMER_ELECTRONICS_WITH_CHILDREN_XML_RESPONSE_BODY = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<GetCategoryInfoResponse xmlns=\"urn:ebay:apis:eBLBaseComponents\">\r\n    <Timestamp>2017-02-07T20:53:28.886Z</Timestamp>\r\n    <Ack>Success</Ack>\r\n    <Build>E981_CORE_APILW_18070859_R1</Build>\r\n    <Version>981</Version>\r\n    <CategoryArray>\r\n        <Category>\r\n            <CategoryID>293</CategoryID>\r\n            <CategoryLevel>1</CategoryLevel>\r\n            <CategoryName>Consumer Electronics</CategoryName>\r\n            <CategoryParentID>-1</CategoryParentID>\r\n            <CategoryNamePath>Consumer Electronics</CategoryNamePath>\r\n            <CategoryIDPath>293</CategoryIDPath>\r\n            <LeafCategory>false</LeafCategory>\r\n        </Category>\r\n        <Category>\r\n            <CategoryID>183067</CategoryID>\r\n            <CategoryLevel>2</CategoryLevel>\r\n            <CategoryName>Virtual Reality</CategoryName>\r\n            <CategoryParentID>293</CategoryParentID>\r\n            <CategoryNamePath>Consumer Electronics:Virtual Reality</CategoryNamePath>\r\n            <CategoryIDPath>293:183067</CategoryIDPath>\r\n            <LeafCategory>false</LeafCategory>\r\n        </Category>\r\n        <Category>\r\n            <CategoryID>15052</CategoryID>\r\n            <CategoryLevel>2</CategoryLevel>\r\n            <CategoryName>Portable Audio &amp; Headphones</CategoryName>\r\n            <CategoryParentID>293</CategoryParentID>\r\n            <CategoryNamePath>Consumer Electronics:Portable Audio &amp; Headphones</CategoryNamePath>\r\n            <CategoryIDPath>293:15052</CategoryIDPath>\r\n            <LeafCategory>false</LeafCategory>\r\n        </Category>\r\n        <Category>\r\n            <CategoryID>32852</CategoryID>\r\n            <CategoryLevel>2</CategoryLevel>\r\n            <CategoryName>TV, Video &amp; Home Audio</CategoryName>\r\n            <CategoryParentID>293</CategoryParentID>\r\n            <CategoryNamePath>Consumer Electronics:TV, Video &amp; Home Audio</CategoryNamePath>\r\n            <CategoryIDPath>293:32852</CategoryIDPath>\r\n            <LeafCategory>false</LeafCategory>\r\n        </Category>\r\n        <Category>\r\n            <CategoryID>3270</CategoryID>\r\n            <CategoryLevel>2</CategoryLevel>\r\n            <CategoryName>Vehicle Electronics &amp; GPS</CategoryName>\r\n            <CategoryParentID>293</CategoryParentID>\r\n            <CategoryNamePath>Consumer Electronics:Vehicle Electronics &amp; GPS</CategoryNamePath>\r\n            <CategoryIDPath>293:3270</CategoryIDPath>\r\n            <LeafCategory>false</LeafCategory>\r\n        </Category>\r\n        <Category>\r\n            <CategoryID>50582</CategoryID>\r\n            <CategoryLevel>2</CategoryLevel>\r\n            <CategoryName>Home Automation</CategoryName>\r\n            <CategoryParentID>293</CategoryParentID>\r\n            <CategoryNamePath>Consumer Electronics:Home Automation</CategoryNamePath>\r\n            <CategoryIDPath>293:50582</CategoryIDPath>\r\n            <LeafCategory>false</LeafCategory>\r\n        </Category>\r\n        <Category>\r\n            <CategoryID>48633</CategoryID>\r\n            <CategoryLevel>2</CategoryLevel>\r\n            <CategoryName>Home Surveillance</CategoryName>\r\n            <CategoryParentID>293</CategoryParentID>\r\n            <CategoryNamePath>Consumer Electronics:Home Surveillance</CategoryNamePath>\r\n            <CategoryIDPath>293:48633</CategoryIDPath>\r\n            <LeafCategory>false</LeafCategory>\r\n        </Category>\r\n        <Category>\r\n            <CategoryID>3286</CategoryID>\r\n            <CategoryLevel>2</CategoryLevel>\r\n            <CategoryName>Home Telephones &amp; Accessories</CategoryName>\r\n            <CategoryParentID>293</CategoryParentID>\r\n            <CategoryNamePath>Consumer Electronics:Home Telephones &amp; Accessories</CategoryNamePath>\r\n            <CategoryIDPath>293:3286</CategoryIDPath>\r\n            <LeafCategory>false</LeafCategory>\r\n        </Category>\r\n        <Category>\r\n            <CategoryID>48446</CategoryID>\r\n            <CategoryLevel>2</CategoryLevel>\r\n            <CategoryName>Multipurpose Batteries &amp; Power</CategoryName>\r\n            <CategoryParentID>293</CategoryParentID>\r\n            <CategoryNamePath>Consumer Electronics:Multipurpose Batteries &amp; Power</CategoryNamePath>\r\n            <CategoryIDPath>293:48446</CategoryIDPath>\r\n            <LeafCategory>false</LeafCategory>\r\n        </Category>\r\n        <Category>\r\n            <CategoryID>1500</CategoryID>\r\n            <CategoryLevel>2</CategoryLevel>\r\n            <CategoryName>Radio Communication</CategoryName>\r\n            <CategoryParentID>293</CategoryParentID>\r\n            <CategoryNamePath>Consumer Electronics:Radio Communication</CategoryNamePath>\r\n            <CategoryIDPath>293:1500</CategoryIDPath>\r\n            <LeafCategory>false</LeafCategory>\r\n        </Category>\r\n        <Category>\r\n            <CategoryID>14948</CategoryID>\r\n            <CategoryLevel>2</CategoryLevel>\r\n            <CategoryName>Gadgets &amp; Other Electronics</CategoryName>\r\n            <CategoryParentID>293</CategoryParentID>\r\n            <CategoryNamePath>Consumer Electronics:Gadgets &amp; Other Electronics</CategoryNamePath>\r\n            <CategoryIDPath>293:14948</CategoryIDPath>\r\n            <LeafCategory>false</LeafCategory>\r\n        </Category>\r\n        <Category>\r\n            <CategoryID>183077</CategoryID>\r\n            <CategoryLevel>2</CategoryLevel>\r\n            <CategoryName>Vintage Electronics</CategoryName>\r\n            <CategoryParentID>293</CategoryParentID>\r\n            <CategoryNamePath>Consumer Electronics:Vintage Electronics</CategoryNamePath>\r\n            <CategoryIDPath>293:183077</CategoryIDPath>\r\n            <LeafCategory>false</LeafCategory>\r\n        </Category>\r\n        <Category>\r\n            <CategoryID>61494</CategoryID>\r\n            <CategoryLevel>2</CategoryLevel>\r\n            <CategoryName>Wholesale Lots</CategoryName>\r\n            <CategoryParentID>293</CategoryParentID>\r\n            <CategoryNamePath>Consumer Electronics:Wholesale Lots</CategoryNamePath>\r\n            <CategoryIDPath>293:61494</CategoryIDPath>\r\n            <LeafCategory>false</LeafCategory>\r\n        </Category>\r\n    </CategoryArray>\r\n    <CategoryCount>13</CategoryCount>\r\n    <UpdateTime>2016-08-31T23:33:02.000Z</UpdateTime>\r\n    <CategoryVersion>115</CategoryVersion>\r\n</GetCategoryInfoResponse>";
	private static final String INVALID_APPLICATION_ID_XML_RESPONSE_BODY = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<GetCategoryInfoResponse xmlns=\"urn:ebay:apis:eBLBaseComponents\">\r\n    <Timestamp>2017-02-07T21:00:23.261Z</Timestamp>\r\n    <Ack>Failure</Ack>\r\n    <Errors>\r\n        <ShortMessage>Application ID invalid.</ShortMessage>\r\n        <LongMessage>Application ID invalid.</LongMessage>\r\n        <ErrorCode>1.20</ErrorCode>\r\n        <SeverityCode>Error</SeverityCode>\r\n        <ErrorClassification>RequestError</ErrorClassification>\r\n    </Errors>\r\n    <Build>E981_CORE_APILW_18070859_R1</Build>\r\n    <Version>981</Version>\r\n</GetCategoryInfoResponse>";

	private CategoryClient categoryClient;

	@Rule
	public ClientDriverRule driver = new ClientDriverRule();

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		final URI baseUri = URI.create(driver.getBaseUrl());
		categoryClient = new CategoryClientImpl(SOME_CLIENT_ID, baseUri);
	}

	@Test
	public void giveSomeMarketplaceAnd293CategoryIdWhenRetrievingCategoryThenReturnConsumerElectronicsCategory() {
		final String expectedCategoryId = CONSUMER_ELECTRONICS_CATEGORY_ID;
		final String expectedResponseBody = CONSUMER_ELECTRONICS_XML_RESPONSE_BODY;
		getSingleCategory(expectedCategoryId, expectedResponseBody);

		final CategoryType actualCategory = categoryClient.getCategory(SOME_MARKETPLACE, expectedCategoryId);

		assertEquals(CONSUMER_ELECTRONICS_CATEGORY_ID, actualCategory.getCategoryID());
		assertEquals(CONSUMER_ELECTRONICS_CATEGORY_LEVEL, actualCategory.getCategoryLevel());
		assertEquals(CONSUMER_ELECTRONICS_CATEGORY_PARENT_ID, actualCategory.getCategoryParentID());
		assertEquals(CONSUMER_ELECTRONICS_CATEGORY_NAME, actualCategory.getCategoryName());
		assertEquals(CONSUMER_ELECTRONICS_CATEGORY_ID, actualCategory.getCategoryIDPath());
		assertEquals(CONSUMER_ELECTRONICS_CATEGORY_NAME, actualCategory.getCategoryNamePath());
		assertFalse(actualCategory.isLeafCategory());
	}

	@Test
	public void giveSomeMarketplaceAnd9001CategoryIdWhenRetrievingCategoryThenReturnNullCategory() {
		final String expectedCategoryId = "9001";
		final String expectedResponseBody = INVALID_CATEGORY_ON_CURRENT_SITE_XML_RESPONSE_BODY;
		getSingleCategory(expectedCategoryId, expectedResponseBody);

		final CategoryType actualCategory = categoryClient.getCategory(SOME_MARKETPLACE, expectedCategoryId);

		assertNull(actualCategory);
	}

	@Test(expected = EbayErrorResponseException.class)
	public void giveSomeMarketplaceAnd293CategoryIdAndInvalidApplicationIdWhenRetrievingCategoryThenThrowNewEbayErrorResponseException() {
		final String expectedCategoryId = CONSUMER_ELECTRONICS_CATEGORY_ID;
		final String expectedResponseBody = INVALID_APPLICATION_ID_XML_RESPONSE_BODY;
		getSingleCategory(expectedCategoryId, expectedResponseBody);

		categoryClient.getCategory(SOME_MARKETPLACE, expectedCategoryId);
	}

	@Test
	public void giveSomeMarketplaceAnd293CategoryIdWhenRetrievingCategoryChildrenThenReturnConsumerElectronicsCategoryAndAllOfItsChildrenCategories() {
		final String expectedCategoryId = CONSUMER_ELECTRONICS_CATEGORY_ID;
		final String expectedResponseBody = CONSUMER_ELECTRONICS_WITH_CHILDREN_XML_RESPONSE_BODY;
		getCategoryChildren(expectedCategoryId, expectedResponseBody);

		final List<CategoryType> actualChildren = categoryClient.getCategoryWithChildren(SOME_MARKETPLACE,
				expectedCategoryId);

		assertEquals(13, actualChildren.size());
	}

	@Test
	public void giveSomeMarketplaceAnd9001CategoryIdWhenRetrievingCategoryChildrenThenReturnEmptyCategoryList() {
		final String expectedCategoryId = "9001";
		final String expectedResponseBody = INVALID_CATEGORY_ON_CURRENT_SITE_XML_RESPONSE_BODY;
		getCategoryChildren(expectedCategoryId, expectedResponseBody);

		final List<CategoryType> actualChildren = categoryClient.getCategoryWithChildren(SOME_MARKETPLACE,
				expectedCategoryId);

		assertEquals(Collections.emptyList(), actualChildren);
	}

	@Test(expected = EbayErrorResponseException.class)
	public void giveSomeMarketplaceAnd293CategoryIdAndInvalidApplicationIdWhenRetrievingCategoryChildrenThenThrowNewEbayErrorResponseException() {
		final String expectedCategoryId = CONSUMER_ELECTRONICS_CATEGORY_ID;
		final String expectedResponseBody = INVALID_APPLICATION_ID_XML_RESPONSE_BODY;
		getCategoryChildren(expectedCategoryId, expectedResponseBody);

		categoryClient.getCategoryWithChildren(SOME_MARKETPLACE, expectedCategoryId);
	}

	private void getSingleCategory(final String expectedCategoryId, final String expectedResponseBody) {
		driver.addExpectation(onRequestTo("/")
				.withParam(CategoryClientImpl.CALL_NAME_QUERY_PARAMETER, CategoryClientImpl.GET_CATEGORY_INFO)
				.withParam(CategoryClientImpl.APP_ID_QUERY_PARAMETER, SOME_CLIENT_ID)
				.withParam(CategoryClientImpl.VERSION_QUERY_PARAMETER, CategoryClientImpl.SHOPPING_API_VERSION)
				.withParam(CategoryClientImpl.SITE_ID_QUERY_PARAMETER, SOME_MARKETPLACE.getShoppingSiteId())
				.withParam(CategoryClientImpl.CATEGORY_ID_QUERY_PARAMETER, expectedCategoryId).withMethod(Method.GET),
				giveResponse(expectedResponseBody, "text/xml;charset=utf-8"));
	}

	private void getCategoryChildren(final String expectedCategoryId, final String expectedResponseBody) {
		driver.addExpectation(onRequestTo("/")
				.withParam(CategoryClientImpl.CALL_NAME_QUERY_PARAMETER, CategoryClientImpl.GET_CATEGORY_INFO)
				.withParam(CategoryClientImpl.INCLUDE_SELECTOR_QUERY_PARAMETER,
						CategoryClientImpl.CHILD_CATEGORIES_SELECTOR)
				.withParam(CategoryClientImpl.APP_ID_QUERY_PARAMETER, SOME_CLIENT_ID)
				.withParam(CategoryClientImpl.VERSION_QUERY_PARAMETER, CategoryClientImpl.SHOPPING_API_VERSION)
				.withParam(CategoryClientImpl.SITE_ID_QUERY_PARAMETER, SOME_MARKETPLACE.getShoppingSiteId())
				.withParam(CategoryClientImpl.CATEGORY_ID_QUERY_PARAMETER, expectedCategoryId).withMethod(Method.GET),
				giveResponse(expectedResponseBody, "text/xml;charset=utf-8"));
	}

}
