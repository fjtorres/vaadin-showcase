package es.fjtorres.pruebas.vaadin.view.common;

import java.io.Serializable;

public class MenuItem implements Serializable {

	private static final long serialVersionUID = -4492026492732400734L;

	private final String text;

	private final String path;

	public MenuItem(String text, String path) {
		super();
		this.text = text;
		this.path = path;
	}

	public String getText() {
		return text;
	}

	public String getPath() {
		return path;
	}

}
