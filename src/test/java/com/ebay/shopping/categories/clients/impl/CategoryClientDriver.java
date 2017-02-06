package com.ebay.shopping.categories.clients.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import com.ebay.shopping.categories.clients.CategoryClient;
import com.ebay.shopping.categories.models.Category;

public class CategoryClientDriver {

	private static final String CLIENT_ID = System.getenv("EBAY_CLIENT_ID");

	private final CategoryClient categoryClient = new CategoryClientImpl(CLIENT_ID, true);

	@Test
	public void given293CategoryIdWhenRetrievingCategoryThenReturnConsumerElectronicsCategory() {
		final String categoryId = "293";
		final Category actualCategory = categoryClient.get(categoryId);

		assertEquals(categoryId, actualCategory.getCategoryId());
		assertEquals(1, actualCategory.getCategoryLevel());
		assertEquals("-1", actualCategory.getCategoryParentId());
		assertEquals("Consumer Electronics", actualCategory.getCategoryName());
		assertEquals(categoryId, actualCategory.getCategoryIdPath());
		assertEquals("Consumer Electronics", actualCategory.getCategoryNamePath());
		assertFalse(actualCategory.isLeafCategory());
	}

}
