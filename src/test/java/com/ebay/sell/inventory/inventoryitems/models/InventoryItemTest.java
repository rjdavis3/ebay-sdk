package com.ebay.sell.inventory.inventoryitems.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class InventoryItemTest {

	@Test
	public void givenSameInstanceWhenTestingEqualityThenReturnTrue() {
		final InventoryItem leftHandInventoryItem = buildInventoryItem();

		final InventoryItem rightHandInventoryItem = leftHandInventoryItem;

		assertTrue(leftHandInventoryItem.equals(rightHandInventoryItem));
		assertEquals(leftHandInventoryItem.hashCode(), rightHandInventoryItem.hashCode());
	}

	@Test
	public void givenSomeStringWhenTestingEqualityThenReturnFalse() {
		final InventoryItem leftHandInventoryItem = buildInventoryItem();

		final String rightHandInventoryItem = "test123";

		assertFalse(leftHandInventoryItem.equals(rightHandInventoryItem));
		assertFalse(leftHandInventoryItem.hashCode() == rightHandInventoryItem.hashCode());
	}

	@Test
	public void givenDifferentProductWhenTestingEqualityThenReturnFalse() {
		final InventoryItem leftHandInventoryItem = buildInventoryItem();

		final InventoryItem rightHandInventoryItem = buildInventoryItem();
		rightHandInventoryItem.getProduct().setBrand("another brand");

		assertFalse(leftHandInventoryItem.equals(rightHandInventoryItem));
		assertFalse(leftHandInventoryItem.hashCode() == rightHandInventoryItem.hashCode());
	}

	@Test
	public void givenSameFieldsWhenTestingEqualityThenReturnTrue() {
		final InventoryItem leftHandInventoryItem = buildInventoryItem();

		final InventoryItem rightHandInventoryItem = buildInventoryItem();

		assertTrue(leftHandInventoryItem.equals(rightHandInventoryItem));
		assertEquals(leftHandInventoryItem.hashCode(), rightHandInventoryItem.hashCode());
	}

	private InventoryItem buildInventoryItem() {
		final InventoryItem inventoryItem = new InventoryItem();
		inventoryItem.setSku("4000514");
		inventoryItem.setCondition("NEW");
		inventoryItem.setConditionDescription("Brand spanking new");
		final Availability availability = new Availability();
		final ShipToLocationAvailability shipToLocationAvailability = new ShipToLocationAvailability();
		shipToLocationAvailability.setQuantity(20);
		inventoryItem.setAvailability(availability);
		final PackageWeightAndSize packageWeightAndSize = new PackageWeightAndSize();
		final Dimension dimension = new Dimension();
		dimension.setHeight(new BigDecimal("10"));
		dimension.setLength(new BigDecimal("4"));
		dimension.setWidth(new BigDecimal("6"));
		dimension.setUnit("INCH");
		packageWeightAndSize.setDimensions(dimension);
		packageWeightAndSize.setPackageType("MAILING_BOX");
		inventoryItem.setPackageWeightAndSize(packageWeightAndSize);
		final Product product = new Product();
		product.setBrand("Clif");
		product.setDescription("Some description");
		product.setEan(Arrays.asList("40404"));
		product.setImageUrls(Arrays.asList("image-1", "image-2"));
		product.setIsbn(Arrays.asList("4040204242"));
		product.setMpn("40405");
		product.setSubtitle("CB");
		product.setTitle("Clif Bar");
		product.setUpc(Arrays.asList("631312100104"));
		final Map<String, List<String>> Aspects = new HashMap<>();
		Aspects.put("Size", Arrays.asList("Small", "Spherical"));
		Aspects.put("Flavor", Arrays.asList("Strawberry", "Grainy"));
		product.setAspects(Aspects);
		inventoryItem.setProduct(product);
		return inventoryItem;
	}
}
