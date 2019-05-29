package xyz.hyunto.spring5.master.springbootexample.bean;

public class WelcomeBean {
	private String message;

	public WelcomeBean(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
