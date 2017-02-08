package com.ebay.shopping.categories.clients;

import java.util.List;

import com.ebay.shopping.categories.models.CategoryType;

public interface CategoryClient {

	public CategoryType get(final String categoryId);

	public List<CategoryType> getChildren(final String categoryId);

}
