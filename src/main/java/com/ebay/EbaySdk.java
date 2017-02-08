package com.ebay;

import java.net.URI;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.ebay.identity.oauth2.token.clients.TokenClient;
import com.ebay.identity.oauth2.token.models.Token;
import com.ebay.identity.oauth2.token.models.UserToken;
import com.ebay.identity.ouath2.token.clients.impl.TokenClientImpl;
import com.ebay.models.Marketplace;
import com.ebay.models.RequestRetryConfiguration;
import com.ebay.sell.inventory.inventoryitemgroups.clients.InventoryItemGroupClient;
import com.ebay.sell.inventory.inventoryitemgroups.clients.impl.InventoryItemGroupClientImpl;
import com.ebay.sell.inventory.inventoryitemgroups.models.InventoryItemGroup;
import com.ebay.sell.inventory.inventoryitems.clients.InventoryItemClient;
import com.ebay.sell.inventory.inventoryitems.clients.impl.InventoryItemClientImpl;
import com.ebay.sell.inventory.inventoryitems.models.InventoryItem;
import com.ebay.sell.inventory.inventoryitems.models.InventoryItems;
import com.ebay.sell.inventory.offers.clients.OfferClient;
import com.ebay.sell.inventory.offers.clients.impl.OfferClientImpl;
import com.ebay.sell.inventory.offers.models.Offer;
import com.ebay.shopping.categories.clients.CategoryClient;
import com.ebay.shopping.categories.clients.impl.CategoryClientImpl;
import com.ebay.shopping.categories.models.CategoryType;

public class EbaySdk implements InventoryItemGroupClient, InventoryItemClient, OfferClient, CategoryClient {

	public static final URI SANDBOX_URI = URI.create("https://api.sandbox.ebay.com");
	public static final URI PRODUCTION_URI = URI.create("https://api.ebay.com");
	public static final URI SHOPPING_SANDBOX_URI = URI.create("http://open.api.sandbox.ebay.com/Shopping");
	public static final URI SHOPPING_PRODUCTION_URI = URI.create("http://open.api.ebay.com/Shopping");

	private final Marketplace marketplace;
	private final String refreshToken;

	private final InventoryItemClient inventoryItemClient;
	private final InventoryItemGroupClient inventoryItemGroupClient;
	private final OfferClient offerClient;
	private final CategoryClient categoryClient;

	public static interface ClientIdStep {
		ClientSecretStep withClientId(final String clientId);
	}

	public static interface ClientSecretStep {
		MarketplaceStep withClientSecret(final String clientSecret);
	}

	public static interface MarketplaceStep {
		CredentialsStep withMarketplace(final Marketplace marketplace);
	}

	public static interface CredentialsStep {
		RequestRetryConfigurationStep withRefreshToken(final String refreshToken);

		CodeStep withRuName(final String ruName);
	}

	public static interface CodeStep {
		RequestRetryConfigurationStep withCode(final String code);
	}

	public static interface RequestRetryConfigurationStep {
		SandboxStep withRequestRetryConfiguration(final RequestRetryConfiguration requestRetryConfiguration);
	}

	public static interface SandboxStep {
		BuildStep withSandbox(final boolean sandbox);

		ShoppingUriStep withBaseUri(final URI baseUri);
	}

	public static interface ShoppingUriStep {
		BuildStep withShoppingUri(final URI shoppingUri);
	}

	public static interface BuildStep {
		EbaySdk build();
	}

	public static ClientIdStep newBuilder() {
		return new Steps();
	}

	public Marketplace getMarketplace() {
		return marketplace;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	@Override
	public Offer getOffer(final String offerId) {
		return offerClient.getOffer(offerId);
	}

	@Override
	public void createOffer(final Offer offer) {
		offerClient.createOffer(offer);
	}

	@Override
	public void updateOffer(final Offer offer) {
		offerClient.updateOffer(offer);
	}

	@Override
	public Offer getOfferBySku(final String sku) {
		return offerClient.getOfferBySku(sku);
	}

	@Override
	public String publishOffer(final String offerId) {
		return offerClient.publishOffer(offerId);
	}

	@Override
	public InventoryItem getInventoryItem(final String sku) {
		return inventoryItemClient.getInventoryItem(sku);
	}

	@Override
	public void updateInventoryItem(final InventoryItem inventoryItem) {
		inventoryItemClient.updateInventoryItem(inventoryItem);
	}

	@Override
	public void deleteInventoryItem(final String sku) {
		inventoryItemClient.deleteInventoryItem(sku);
	}

	@Override
	public InventoryItems getInventoryItems(final int offset, final int limit) {
		return inventoryItemClient.getInventoryItems(offset, limit);
	}

	@Override
	public InventoryItemGroup getInventoryItemGroup(final String inventoryItemGroupKey) {
		return inventoryItemGroupClient.getInventoryItemGroup(inventoryItemGroupKey);
	}

	@Override
	public void deleteInventoryItemGroup(final String inventoryItemGroupKey) {
		inventoryItemGroupClient.deleteInventoryItemGroup(inventoryItemGroupKey);
	}

	@Override
	public void updateInventoryItemGroup(final InventoryItemGroup inventoryItemGroup) {
		inventoryItemGroupClient.updateInventoryItemGroup(inventoryItemGroup);
	}

	@Override
	public CategoryType get(final String categoryId) {
		return categoryClient.get(categoryId);
	}

	@Override
	public List<CategoryType> getChildren(final String categoryId) {
		return categoryClient.getChildren(categoryId);
	}

	private EbaySdk(final Steps steps) {
		this.marketplace = steps.marketplace;
		this.refreshToken = steps.refreshToken;

		this.inventoryItemClient = steps.inventoryItemClient;
		this.inventoryItemGroupClient = steps.inventoryItemGroupClient;
		this.offerClient = steps.offerClient;
		this.categoryClient = steps.categoryClient;
	}

	private static class Steps implements ClientIdStep, ClientSecretStep, MarketplaceStep, CredentialsStep, CodeStep,
			SandboxStep, ShoppingUriStep, RequestRetryConfigurationStep, BuildStep {

		private Marketplace marketplace;
		private String refreshToken;

		private InventoryItemClient inventoryItemClient;
		private InventoryItemGroupClient inventoryItemGroupClient;
		private OfferClient offerClient;
		private CategoryClient categoryClient;

		private String clientId;
		private String clientSecret;
		private URI baseUri;
		private URI shoppingUri;
		private String ruName;
		private String code;
		private RequestRetryConfiguration requestRetryConfiguration;

		@Override
		public EbaySdk build() {
			final TokenClient tokenClient = new TokenClientImpl(baseUri, clientId, clientSecret);

			if (StringUtils.isBlank(refreshToken)) {
				final Token token = tokenClient.getAccessToken(ruName, code);
				refreshToken = token.getRefreshToken();
			}

			final UserToken userToken = new UserToken(tokenClient, refreshToken);

			inventoryItemClient = new InventoryItemClientImpl(baseUri, userToken, requestRetryConfiguration);
			inventoryItemGroupClient = new InventoryItemGroupClientImpl(baseUri, userToken, requestRetryConfiguration);
			offerClient = new OfferClientImpl(baseUri, userToken, requestRetryConfiguration);
			categoryClient = new CategoryClientImpl(clientId, marketplace, shoppingUri);

			return new EbaySdk(this);
		}

		@Override
		public BuildStep withSandbox(final boolean sandbox) {
			if (sandbox) {
				this.baseUri = SANDBOX_URI;
				this.shoppingUri = SHOPPING_SANDBOX_URI;
			} else {
				this.baseUri = PRODUCTION_URI;
				this.shoppingUri = SHOPPING_PRODUCTION_URI;
			}
			return this;
		}

		@Override
		public RequestRetryConfigurationStep withCode(final String code) {
			this.code = code;
			return this;
		}

		@Override
		public RequestRetryConfigurationStep withRefreshToken(final String refreshToken) {
			this.refreshToken = refreshToken;
			return this;
		}

		@Override
		public CodeStep withRuName(final String ruName) {
			this.ruName = ruName;
			return this;
		}

		@Override
		public MarketplaceStep withClientSecret(final String clientSecret) {
			this.clientSecret = clientSecret;
			return this;
		}

		@Override
		public ClientSecretStep withClientId(final String clientId) {
			this.clientId = clientId;
			return this;
		}

		@Override
		public ShoppingUriStep withBaseUri(final URI baseUri) {
			this.baseUri = baseUri;
			return this;
		}

		@Override
		public SandboxStep withRequestRetryConfiguration(final RequestRetryConfiguration requestRetryConfiguration) {
			this.requestRetryConfiguration = requestRetryConfiguration;
			return this;
		}

		@Override
		public BuildStep withShoppingUri(final URI shoppingUri) {
			this.shoppingUri = shoppingUri;
			return this;
		}

		@Override
		public CredentialsStep withMarketplace(final Marketplace marketplace) {
			this.marketplace = marketplace;
			return this;
		}

	}

}
