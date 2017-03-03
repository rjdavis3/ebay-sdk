package com.ebay.sell.inventory.inventoryitemgroups.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

public class VariesByTest {

	@Test
	public void givenSameInstanceWhenTestingEqualityThenReturnTrue() {
		final VariesBy leftHandVariesBy = new VariesBy();
		leftHandVariesBy.setAspectsImageVariesBy(Arrays.asList("flavor", "color"));
		final Specification firstLefthandSpecification = new Specification();
		firstLefthandSpecification.setName("color");
		firstLefthandSpecification.setValues(Arrays.asList("yella", "bluueh", "red"));
		final Specification secondLefthandSpecification = new Specification();
		secondLefthandSpecification.setName("flavor");
		secondLefthandSpecification.setValues(Arrays.asList("tartar sauce", "nutmeg"));
		leftHandVariesBy.setSpecifications(Arrays.asList(firstLefthandSpecification, secondLefthandSpecification));
		final VariesBy rightHandVariesBy = leftHandVariesBy;

		assertTrue(leftHandVariesBy.equals(rightHandVariesBy));
		assertEquals(leftHandVariesBy.hashCode(), rightHandVariesBy.hashCode());
	}

	@Test
	public void givenSomeStringWhenTestingEqualityThenReturnFalse() {
		final VariesBy leftHandVariesBy = new VariesBy();
		leftHandVariesBy.setAspectsImageVariesBy(Arrays.asList("flavor", "color"));
		final Specification firstLefthandSpecification = new Specification();
		firstLefthandSpecification.setName("color");
		firstLefthandSpecification.setValues(Arrays.asList("yella", "bluueh", "red"));
		final Specification secondLefthandSpecification = new Specification();
		secondLefthandSpecification.setName("flavor");
		secondLefthandSpecification.setValues(Arrays.asList("tartar sauce", "nutmeg"));
		leftHandVariesBy.setSpecifications(Arrays.asList(firstLefthandSpecification, secondLefthandSpecification));

		final String rightHandVariesBy = "test123";

		assertFalse(leftHandVariesBy.equals(rightHandVariesBy));
		assertFalse(leftHandVariesBy.hashCode() == rightHandVariesBy.hashCode());
	}

	@Test
	public void givenSomeVariesByWithDifferentAspectsImageVariesByAndSameSpecificationsWhenTestingEqualityThenReturnFalse() {
		final VariesBy leftHandVariesBy = new VariesBy();
		leftHandVariesBy.setAspectsImageVariesBy(Arrays.asList("flavor", "color"));
		final Specification firstLefthandSpecification = new Specification();
		firstLefthandSpecification.setName("color");
		firstLefthandSpecification.setValues(Arrays.asList("yella", "bluueh", "red"));
		final Specification secondLefthandSpecification = new Specification();
		secondLefthandSpecification.setName("flavor");
		secondLefthandSpecification.setValues(Arrays.asList("tartar sauce", "nutmeg"));
		leftHandVariesBy.setSpecifications(Arrays.asList(firstLefthandSpecification, secondLefthandSpecification));

		final VariesBy rightHandVariesBy = new VariesBy();
		rightHandVariesBy.setAspectsImageVariesBy(Arrays.asList("flavor", "colour"));
		final Specification firstRighthandSpecification = new Specification();
		firstRighthandSpecification.setName("color");
		firstRighthandSpecification.setValues(Arrays.asList("yella", "bluueh", "red"));
		final Specification secondRighthandSpecification = new Specification();
		secondRighthandSpecification.setName("flavor");
		secondRighthandSpecification.setValues(Arrays.asList("tartar sauce", "nutmeg"));
		rightHandVariesBy.setSpecifications(Arrays.asList(firstRighthandSpecification, secondRighthandSpecification));

		assertFalse(leftHandVariesBy.equals(rightHandVariesBy));
		assertFalse(leftHandVariesBy.hashCode() == rightHandVariesBy.hashCode());
	}

	@Test
	public void givenSomeVariesBySameAspectsImageVariesByAndDifferentSpecificationsWhenTestingEqualityThenReturnFalse() {
		final VariesBy leftHandVariesBy = new VariesBy();
		leftHandVariesBy.setAspectsImageVariesBy(Arrays.asList("flavor", "color"));
		final Specification firstLefthandSpecification = new Specification();
		firstLefthandSpecification.setName("color");
		firstLefthandSpecification.setValues(Arrays.asList("yella", "bluueh", "red"));
		final Specification secondLefthandSpecification = new Specification();
		secondLefthandSpecification.setName("flavor");
		secondLefthandSpecification.setValues(Arrays.asList("tartar sauce", "nutmeg"));
		leftHandVariesBy.setSpecifications(Arrays.asList(firstLefthandSpecification, secondLefthandSpecification));

		final VariesBy rightHandVariesBy = new VariesBy();
		rightHandVariesBy.setAspectsImageVariesBy(Arrays.asList("flavor", "color"));
		final Specification firstRighthandSpecification = new Specification();
		firstRighthandSpecification.setName("color");
		firstRighthandSpecification.setValues(Arrays.asList("yella", "bluueh", "red"));
		final Specification secondRighthandSpecification = new Specification();
		secondRighthandSpecification.setName("flavor");
		secondRighthandSpecification.setValues(Arrays.asList("tartar sauce", "nutmegs"));
		rightHandVariesBy.setSpecifications(Arrays.asList(firstRighthandSpecification, secondRighthandSpecification));

		assertFalse(leftHandVariesBy.equals(rightHandVariesBy));
		assertFalse(leftHandVariesBy.hashCode() == rightHandVariesBy.hashCode());
	}

	@Test
	public void givenVariesByWithSameAspectsImageVariesByAndSpecificationsInDifferentOrderWhenTestingEqualityThenReturnFalse() {
		final VariesBy leftHandVariesBy = new VariesBy();
		leftHandVariesBy.setAspectsImageVariesBy(Arrays.asList("flavor", "color"));
		final Specification firstLefthandSpecification = new Specification();
		firstLefthandSpecification.setName("color");
		firstLefthandSpecification.setValues(Arrays.asList("yella", "bluueh", "red"));
		final Specification secondLefthandSpecification = new Specification();
		secondLefthandSpecification.setName("flavor");
		secondLefthandSpecification.setValues(Arrays.asList("tartar sauce", "nutmeg"));
		leftHandVariesBy.setSpecifications(Arrays.asList(firstLefthandSpecification, secondLefthandSpecification));

		final VariesBy rightHandVariesBy = new VariesBy();
		rightHandVariesBy.setAspectsImageVariesBy(Arrays.asList("flavor", "color"));
		final Specification firstRighthandSpecification = new Specification();
		firstRighthandSpecification.setName("color");
		firstRighthandSpecification.setValues(Arrays.asList("yella", "red", "bluueh"));
		final Specification secondRighthandSpecification = new Specification();
		secondRighthandSpecification.setName("flavor");
		secondRighthandSpecification.setValues(Arrays.asList("tartar sauce", "nutmeg"));
		rightHandVariesBy.setSpecifications(Arrays.asList(firstRighthandSpecification, secondRighthandSpecification));

		assertFalse(leftHandVariesBy.equals(rightHandVariesBy));
		assertFalse(leftHandVariesBy.hashCode() == rightHandVariesBy.hashCode());
	}

	@Test
	public void givenVariesByWithSameAspectsImageVariesByAndSameSpecificationsWhenTestingEqualityThenReturnTrue() {
		final VariesBy leftHandVariesBy = new VariesBy();
		leftHandVariesBy.setAspectsImageVariesBy(Arrays.asList("flavor", "color"));
		final Specification firstLefthandSpecification = new Specification();
		firstLefthandSpecification.setName("color");
		firstLefthandSpecification.setValues(Arrays.asList("yella", "bluueh", "red"));
		final Specification secondLefthandSpecification = new Specification();
		secondLefthandSpecification.setName("flavor");
		secondLefthandSpecification.setValues(Arrays.asList("tartar sauce", "nutmeg"));
		leftHandVariesBy.setSpecifications(Arrays.asList(firstLefthandSpecification, secondLefthandSpecification));

		final VariesBy rightHandVariesBy = new VariesBy();
		rightHandVariesBy.setAspectsImageVariesBy(Arrays.asList("flavor", "color"));
		final Specification firstRighthandSpecification = new Specification();
		firstRighthandSpecification.setName("color");
		firstRighthandSpecification.setValues(Arrays.asList("yella", "bluueh", "red"));
		final Specification secondRighthandSpecification = new Specification();
		secondRighthandSpecification.setName("flavor");
		secondRighthandSpecification.setValues(Arrays.asList("tartar sauce", "nutmeg"));
		rightHandVariesBy.setSpecifications(Arrays.asList(firstRighthandSpecification, secondRighthandSpecification));

		assertTrue(leftHandVariesBy.equals(rightHandVariesBy));
		assertEquals(leftHandVariesBy.hashCode(), rightHandVariesBy.hashCode());
	}

}
