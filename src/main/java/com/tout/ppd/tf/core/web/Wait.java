package com.tout.ppd.tf.core.web;

import org.openqa.selenium.TimeoutException;

public class Wait<T extends Component<T>> {
	
	private static final int DEFAULT_TIMEOUT_IN_MILLIS = 15000;
	private static final int DEFAULT_RETRY_DELAY_IN_MILLIS = 500;
	private int timeoutInMillis;
	private int retryDelayInMillis;
	private T component;
	
	public Wait(){
		this.timeoutInMillis = DEFAULT_TIMEOUT_IN_MILLIS;
		this.retryDelayInMillis = DEFAULT_RETRY_DELAY_IN_MILLIS;
	}
	
	public Wait<T> withTimeout(int timeoutInMillis){
		this.timeoutInMillis = timeoutInMillis;
		return this;
	}
	
	public Wait<T> withRetryDelay(int retryDealyInMillis) {
		this.retryDelayInMillis = retryDealyInMillis;
		return this;
	}
	
	public Wait<T> forComponent(T component){
		this.component = component;
		return this;
	}
	
	public T toBeAvailable(){
		int timePassed = 0;
		while (timePassed < timeoutInMillis) {
			if (this.component.isAvailable()){
				return this.component;
			}
			timePassed = timePassed + delay();
		}
		if (!this.component.isAvailable()){
			throw new TimeoutException("Timed out after " + timeoutInMillis + " ms. waiting for " + this.component.getClass().getSimpleName() + " component to be available.");
		}
		return this.component;
	}
	
	private int delay() {
		try {
			Thread.sleep(retryDelayInMillis);
			return retryDelayInMillis;
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	
}
