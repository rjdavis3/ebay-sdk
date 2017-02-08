package com.ebay.shopping.categories.clients.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.List;

import org.junit.Test;

import com.ebay.EbaySdk;
import com.ebay.models.Marketplace;
import com.ebay.shopping.categories.clients.CategoryClient;
import com.ebay.shopping.categories.models.CategoryType;

public class CategoryClientDriver {

	private static final String CLIENT_ID = System.getenv("EBAY_CLIENT_ID");

	private final CategoryClient categoryClient = new CategoryClientImpl(CLIENT_ID, Marketplace.UNITED_STATES,
			EbaySdk.SHOPPING_SANDBOX_URI);

	@Test
	public void given293CategoryIdWhenRetrievingEbayUsCategoryThenReturnConsumerElectronicsCategory() {
		final String categoryId = "293";
		final CategoryType actualCategory = categoryClient.getCategory(categoryId);

		assertEquals(categoryId, actualCategory.getCategoryID());
		assertEquals(1, actualCategory.getCategoryLevel().intValue());
		assertEquals("-1", actualCategory.getCategoryParentID());
		assertEquals("Consumer Electronics", actualCategory.getCategoryName());
		assertEquals(categoryId, actualCategory.getCategoryIDPath());
		assertEquals("Consumer Electronics", actualCategory.getCategoryNamePath());
		assertFalse(actualCategory.isLeafCategory());
	}

	@Test
	public void givenNegativeOneCategoryIdWhenRetrievingEbayUsChildrenCategoriesThenReturnThirtyFiveCategories() {
		final String categoryId = "-1";
		final List<CategoryType> categories = categoryClient.getCategoryWithChildren(categoryId);

		assertEquals(35, categories.size());
	}

}
