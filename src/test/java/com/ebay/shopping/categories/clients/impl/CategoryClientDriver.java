package com.ebay.shopping.categories.clients.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import com.ebay.shopping.categories.clients.CategoryClient;
import com.ebay.shopping.categories.models.CategoryType;

public class CategoryClientDriver {

	private static final String CLIENT_ID = System.getenv("EBAY_CLIENT_ID");

	private final CategoryClient categoryClient = new CategoryClientImpl(CLIENT_ID, true);

	@Test
	public void given293CategoryIdWhenRetrievingCategoryThenReturnConsumerElectronicsCategory() {
		final String categoryId = "293";
		final CategoryType actualCategory = categoryClient.get(categoryId);

		assertEquals(categoryId, actualCategory.getCategoryID());
		assertEquals(1, actualCategory.getCategoryLevel().intValue());
		assertEquals("-1", actualCategory.getCategoryParentID());
		assertEquals("Consumer Electronics", actualCategory.getCategoryName());
		assertEquals(categoryId, actualCategory.getCategoryIDPath());
		assertEquals("Consumer Electronics", actualCategory.getCategoryNamePath());
		assertFalse(actualCategory.isLeafCategory());
	}

}
