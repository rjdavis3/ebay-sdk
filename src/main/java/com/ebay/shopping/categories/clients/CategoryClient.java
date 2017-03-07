package com.ebay.shopping.categories.clients;

import java.util.List;

import com.ebay.models.Marketplace;
import com.ebay.shopping.categories.models.CategoryType;

public interface CategoryClient {

	public CategoryType getCategory(final Marketplace marketplace, final String categoryId);

	public List<CategoryType> getCategoryWithChildren(final Marketplace marketplace, final String categoryId);

}
