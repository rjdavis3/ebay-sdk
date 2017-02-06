package com.ebay.shopping.categories.clients;

import com.ebay.shopping.categories.models.Category;

public interface CategoryClient {

	public Category get(final String categoryId);

}
