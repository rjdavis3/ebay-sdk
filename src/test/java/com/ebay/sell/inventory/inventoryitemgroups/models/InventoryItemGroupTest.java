package com.ebay.sell.inventory.inventoryitemgroups.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;

public class InventoryItemGroupTest {

	@Test
	public void givenSameInstanceWhenTestingEqualityThenReturnTrue() {
		final InventoryItemGroup leftHandInventoryItemGroup = new InventoryItemGroup();
		leftHandInventoryItemGroup.setInventoryItemGroupKey("9212cbda-d9f9-4b7c-8461-63ec09f3b7bf");
		leftHandInventoryItemGroup.setTitle("Clif Bar");
		leftHandInventoryItemGroup.setSubtitle("CB");

		final InventoryItemGroup rightHandInventoryItemGroup = leftHandInventoryItemGroup;

		assertTrue(leftHandInventoryItemGroup.equals(rightHandInventoryItemGroup));
		assertEquals(leftHandInventoryItemGroup.hashCode(), rightHandInventoryItemGroup.hashCode());
	}

	@Test
	public void givenSomeStringWhenTestingEqualityThenReturnFalse() {
		final InventoryItemGroup leftHandInventoryItemGroup = new InventoryItemGroup();

		final String rightHandInventoryItemGroup = "test123";

		assertFalse(leftHandInventoryItemGroup.equals(rightHandInventoryItemGroup));
		assertFalse(leftHandInventoryItemGroup.hashCode() == rightHandInventoryItemGroup.hashCode());
	}

	@Test
	public void givenDifferentVariantSKUsWhenTestingEqualityThenReturnFalse() {
		final InventoryItemGroup leftHandInventoryItemGroup = new InventoryItemGroup();
		leftHandInventoryItemGroup.setInventoryItemGroupKey("9212cbda-d9f9-4b7c-8461-63ec09f3b7bf");
		leftHandInventoryItemGroup.setTitle("Clif Bar");
		leftHandInventoryItemGroup.setSubtitle("CB");
		leftHandInventoryItemGroup.setDescription("Some description");
		leftHandInventoryItemGroup.setImageUrls(Arrays.asList("image-1", "image-2"));
		leftHandInventoryItemGroup.setVariantSKUs(new HashSet<>(Arrays.asList("ABC-123", "ABC-032")));
		final VariesBy leftHandVariesBy = new VariesBy();
		leftHandVariesBy.setAspectsImageVariesBy(Arrays.asList("flavor", "smell"));
		leftHandVariesBy.setSpecifications(Arrays.asList(new Specification(), new Specification()));
		leftHandInventoryItemGroup.setVariesBy(leftHandVariesBy);

		final InventoryItemGroup rightHandInventoryItemGroup = new InventoryItemGroup();
		rightHandInventoryItemGroup.setInventoryItemGroupKey("9212cbda-d9f9-4b7c-8461-63ec09f3b7bf");
		rightHandInventoryItemGroup.setTitle("clif Bar");
		rightHandInventoryItemGroup.setSubtitle("CB");
		rightHandInventoryItemGroup.setDescription("Some description");
		rightHandInventoryItemGroup.setImageUrls(Arrays.asList("image-1", "image-2"));
		rightHandInventoryItemGroup.setVariantSKUs(new HashSet<>(Arrays.asList("ABC-123", "ABC-032", "DEF-33")));
		final VariesBy rightHandVariesBy = new VariesBy();
		rightHandVariesBy.setAspectsImageVariesBy(Arrays.asList("flavor", "smell"));
		rightHandVariesBy.setSpecifications(Arrays.asList(new Specification(), new Specification()));
		rightHandInventoryItemGroup.setVariesBy(rightHandVariesBy);

		assertFalse(leftHandInventoryItemGroup.equals(rightHandInventoryItemGroup));
		assertFalse(leftHandInventoryItemGroup.hashCode() == rightHandInventoryItemGroup.hashCode());
	}

	@Test
	public void givenSameFieldsWhenTestingEqualityThenReturnTrue() {
		final InventoryItemGroup leftHandInventoryItemGroup = new InventoryItemGroup();
		leftHandInventoryItemGroup.setInventoryItemGroupKey("9212cbda-d9f9-4b7c-8461-63ec09f3b7bf");
		leftHandInventoryItemGroup.setTitle("Clif Bar");
		leftHandInventoryItemGroup.setSubtitle("CB");
		leftHandInventoryItemGroup.setDescription("Some description");
		leftHandInventoryItemGroup.setImageUrls(Arrays.asList("image-1", "image-2"));
		leftHandInventoryItemGroup.setVariantSKUs(new HashSet<>(Arrays.asList("ABC-123", "ABC-032")));
		final VariesBy leftHandVariesBy = new VariesBy();
		leftHandVariesBy.setAspectsImageVariesBy(Arrays.asList("flavor", "smell"));
		leftHandVariesBy.setSpecifications(Arrays.asList(new Specification(), new Specification()));
		leftHandInventoryItemGroup.setVariesBy(leftHandVariesBy);

		final InventoryItemGroup rightHandInventoryItemGroup = new InventoryItemGroup();
		rightHandInventoryItemGroup.setInventoryItemGroupKey("9212cbda-d9f9-4b7c-8461-63ec09f3b7bf");
		rightHandInventoryItemGroup.setTitle("Clif Bar");
		rightHandInventoryItemGroup.setSubtitle("CB");
		rightHandInventoryItemGroup.setDescription("Some description");
		rightHandInventoryItemGroup.setImageUrls(Arrays.asList("image-1", "image-2"));
		rightHandInventoryItemGroup.setVariantSKUs(new HashSet<>(Arrays.asList("ABC-123", "ABC-032")));
		final VariesBy rightHandVariesBy = new VariesBy();
		rightHandVariesBy.setAspectsImageVariesBy(Arrays.asList("flavor", "smell"));
		rightHandVariesBy.setSpecifications(Arrays.asList(new Specification(), new Specification()));
		rightHandInventoryItemGroup.setVariesBy(rightHandVariesBy);

		assertTrue(leftHandInventoryItemGroup.equals(rightHandInventoryItemGroup));
		assertEquals(leftHandInventoryItemGroup.hashCode(), rightHandInventoryItemGroup.hashCode());
	}

}
