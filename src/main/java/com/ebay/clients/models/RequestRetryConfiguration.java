package com.ebay.clients.models;

import java.util.concurrent.TimeUnit;

public class RequestRetryConfiguration {

	private final long mininumWaitDuration;
	private final TimeUnit mininumWaitUnit;
	private final long timeoutDuration;
	private final TimeUnit timeoutUnit;

	public static interface MininumWaitStep {
		TimeoutStep withMininumWait(final long mininumWaitDuration, final TimeUnit mininumWaitUnit);
	}

	public static interface TimeoutStep {
		BuildStep withTimeout(final long timeoutDuration, final TimeUnit timeoutUnit);
	}

	public static interface BuildStep {
		RequestRetryConfiguration build();
	}

	public static MininumWaitStep newBuilder() {
		return new Steps();
	}

	public long getMininumWaitDuration() {
		return mininumWaitDuration;
	}

	public TimeUnit getMininumWaitUnit() {
		return mininumWaitUnit;
	}

	public long getTimeoutDuration() {
		return timeoutDuration;
	}

	public TimeUnit getTimeoutUnit() {
		return timeoutUnit;
	}

	private RequestRetryConfiguration(final Steps steps) {
		this.mininumWaitDuration = steps.mininumWaitDuration;
		this.mininumWaitUnit = steps.mininumWaitUnit;
		this.timeoutDuration = steps.timeoutDuration;
		this.timeoutUnit = steps.timeoutUnit;
	}

	private static class Steps implements MininumWaitStep, TimeoutStep, BuildStep {

		private long mininumWaitDuration;
		private TimeUnit mininumWaitUnit;
		private long timeoutDuration;
		private TimeUnit timeoutUnit;

		@Override
		public RequestRetryConfiguration build() {
			return new RequestRetryConfiguration(this);
		}

		@Override
		public BuildStep withTimeout(final long timeoutDuration, final TimeUnit timeoutUnit) {
			this.timeoutUnit = timeoutUnit;
			this.timeoutDuration = timeoutDuration;
			return this;
		}

		@Override
		public TimeoutStep withMininumWait(final long mininumWaitDuration, final TimeUnit mininumWaitUnit) {
			this.mininumWaitDuration = mininumWaitDuration;
			this.mininumWaitUnit = mininumWaitUnit;
			return this;
		}

	}

}
