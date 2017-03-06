package com.ebay.sell.inventory.inventoryitems.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class ProductTest {

	@Test
	public void givenSameInstanceWhenTestingEqualityThenReturnTrue() {
		final Product leftHandProduct = new Product();
		leftHandProduct.setBrand("Clif");
		leftHandProduct.setDescription("Some description");
		leftHandProduct.setSubtitle("CB");
		leftHandProduct.setTitle("Clif Bar");

		final Product rightHandProduct = leftHandProduct;

		assertTrue(leftHandProduct.equals(rightHandProduct));
		assertEquals(leftHandProduct.hashCode(), rightHandProduct.hashCode());
	}

	@Test
	public void givenSomeStringWhenTestingEqualityThenReturnFalse() {
		final Product leftHandProduct = new Product();

		final String rightHandProduct = "test123";

		assertFalse(leftHandProduct.equals(rightHandProduct));
		assertFalse(leftHandProduct.hashCode() == rightHandProduct.hashCode());
	}

	@Test
	public void givenDifferentAspectsWhenTestingEqualityThenReturnFalse() {
		final Product leftHandProduct = new Product();
		leftHandProduct.setBrand("Clif");
		leftHandProduct.setDescription("Some description");
		leftHandProduct.setEan(Arrays.asList("40404"));
		leftHandProduct.setImageUrls(Arrays.asList("image-1", "image-2"));
		leftHandProduct.setIsbn(Arrays.asList("4040204242"));
		leftHandProduct.setMpn("40405");
		leftHandProduct.setSubtitle("CB");
		leftHandProduct.setTitle("Clif Bar");
		leftHandProduct.setUpc(Arrays.asList("631312100104"));
		final Map<String, List<String>> leftHandAspects = new HashMap<>();
		leftHandAspects.put("Size", Arrays.asList("Small", "Spherical"));
		leftHandAspects.put("Flavor", Arrays.asList("Strawberry", "Grainy"));
		leftHandProduct.setAspects(leftHandAspects);

		final Product rightHandProduct = new Product();
		rightHandProduct.setBrand("Clif");
		rightHandProduct.setDescription("Some description");
		rightHandProduct.setEan(Arrays.asList("40404"));
		rightHandProduct.setImageUrls(Arrays.asList("image-1", "image-2"));
		rightHandProduct.setIsbn(Arrays.asList("4040204242"));
		rightHandProduct.setMpn("40405");
		rightHandProduct.setSubtitle("CB");
		rightHandProduct.setTitle("Clif Bar");
		rightHandProduct.setUpc(Arrays.asList("631312100104"));
		final Map<String, List<String>> rightHandAspects = new HashMap<>();
		rightHandAspects.put("Size", Arrays.asList("Small", "Spherical"));
		rightHandAspects.put("Flavor", Arrays.asList("Orange", "Grainy"));
		rightHandProduct.setAspects(rightHandAspects);

		assertFalse(leftHandProduct.equals(rightHandProduct));
		assertFalse(leftHandProduct.hashCode() == rightHandProduct.hashCode());
	}

	@Test
	public void givenSameFieldsWhenTestingEqualityThenReturnTrue() {
		final Product leftHandProduct = new Product();
		leftHandProduct.setBrand("Clif");
		leftHandProduct.setDescription("Some description");
		leftHandProduct.setEan(Arrays.asList("40404"));
		leftHandProduct.setImageUrls(Arrays.asList("image-1", "image-2"));
		leftHandProduct.setIsbn(Arrays.asList("4040204242"));
		leftHandProduct.setMpn("40405");
		leftHandProduct.setSubtitle("CB");
		leftHandProduct.setTitle("Clif Bar");
		leftHandProduct.setUpc(Arrays.asList("631312100104"));
		final Map<String, List<String>> leftHandAspects = new HashMap<>();
		leftHandAspects.put("Size", Arrays.asList("Small", "Spherical"));
		leftHandAspects.put("Flavor", Arrays.asList("Strawberry", "Grainy"));
		leftHandProduct.setAspects(leftHandAspects);

		final Product rightHandProduct = new Product();
		rightHandProduct.setBrand("Clif");
		rightHandProduct.setDescription("Some description");
		rightHandProduct.setEan(Arrays.asList("40404"));
		rightHandProduct.setImageUrls(Arrays.asList("image-1", "image-2"));
		rightHandProduct.setIsbn(Arrays.asList("4040204242"));
		rightHandProduct.setMpn("40405");
		rightHandProduct.setSubtitle("CB");
		rightHandProduct.setTitle("Clif Bar");
		rightHandProduct.setUpc(Arrays.asList("631312100104"));
		final Map<String, List<String>> rightHandAspects = new HashMap<>();
		rightHandAspects.put("Size", Arrays.asList("Small", "Spherical"));
		rightHandAspects.put("Flavor", Arrays.asList("Strawberry", "Grainy"));
		rightHandProduct.setAspects(rightHandAspects);

		assertTrue(leftHandProduct.equals(rightHandProduct));
		assertEquals(leftHandProduct.hashCode(), rightHandProduct.hashCode());
	}

}
