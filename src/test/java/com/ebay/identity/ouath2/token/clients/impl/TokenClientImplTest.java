package com.ebay.identity.ouath2.token.clients.impl;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.ebay.identity.oauth2.token.models.Token;
import com.github.restdriver.clientdriver.ClientDriverRule;

public class TokenClientImplTest {

	@InjectMocks
	private TokenClientImpl tokenClientImpl;

	@Rule
	public ClientDriverRule driver = new ClientDriverRule();

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	@Ignore
	public void givenValidRuNameAndValidAuthorizationCodeWhenGeneratingAccessTokenThenReturnToken() {
		final String ruName = "some-RuName";
		final String code = "some-authorization-code";
		final Token actualToken = tokenClientImpl.getAccessToken(ruName, code);

		assertNotNull(actualToken.getAccessToken());
		assertNotNull(actualToken.getRefreshToken());
	}

	@Test
	@Ignore
	public void givenValidRefreshTokenWhenRefreshingAccessTokenThenReturnToken() {
		final String refreshToken = "some-refresh-token";
		final Token actualToken = tokenClientImpl.refreshAccessToken(refreshToken);

		assertNotNull(actualToken.getAccessToken());
	}

}
