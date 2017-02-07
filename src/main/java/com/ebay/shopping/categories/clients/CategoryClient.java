package com.ebay.shopping.categories.clients;

import com.ebay.shopping.categories.models.CategoryType;

public interface CategoryClient {

	public CategoryType get(final String categoryId);

}
