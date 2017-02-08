# eBay SDK

Java SDK for eBay REST APIs

| Service   | Develop | Master |
|-----------|---------|--------|
| CI Status | [![Build Status](https://travis-ci.org/rjdavis3/ebay-sdk.svg?branch=develop)](https://travis-ci.org/rjdavis3/ebay-sdk) | [![Build Status](https://travis-ci.org/rjdavis3/ebay-sdk.svg?branch=master)](https://travis-ci.org/rjdavis3/ebay-sdk) |

## Maven
```xml
	<dependency>
	    <groupId>com.github.rjdavis3</groupId>
	    <artifactId>ebay-sdk</artifactId>
	    <version>1.1.0</version>
	</dependency>
```

## Quickstart
Creating SDK with refresh token then making a sample call:

```java
	final RequestRetryConfiguration requestRetryConfiguration = RequestRetryConfiguration.newBuilder()
			.withMininumWait(5, TimeUnit.SECONDS).withTimeout(2, TimeUnit.MINUTES).build();
	final EbaySdk ebaySdk = EbaySdk.newBuilder().withClientId(clientId).withClientSecret(clientSecret)
			.withMarketplace(Marketplace.UNITED_STATES).withRefreshToken(refreshToken)
			.withRequestRetryConfiguration(requestRetryConfiguration).withSandbox(false)
			.build();
	final InventoryItem inventoryItem = ebaySdk.getInventoryItem(sku);
```

Creating SDK with RuName and temporary authorization code then making a sample call:

```java
	final RequestRetryConfiguration requestRetryConfiguration = RequestRetryConfiguration.newBuilder()
			.withMininumWait(5, TimeUnit.SECONDS).withTimeout(2, TimeUnit.MINUTES).build();
	final EbaySdk ebaySdk = EbaySdk.newBuilder().withClientId(clientId).withClientSecret(clientSecret)
			.withMarketplace(Marketplace.UNITED_STATES).withRuName(ruName).withCode(authorizationCode)
			.withRequestRetryConfiguration(requestRetryConfiguration).withSandbox(false)
			.build();
	final InventoryItem inventoryItem = ebaySdk.getInventoryItem(sku);
```

## Building from source

	1. Install Maven
	2. Install JDK 8
	3. Clone the repository.
	3. Navigate to repository directory and run `mvn install`

