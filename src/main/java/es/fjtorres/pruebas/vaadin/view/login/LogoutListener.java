package es.fjtorres.pruebas.vaadin.view.login;

import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.UI;

import es.fjtorres.pruebas.vaadin.common.NavigationUI;

public class LogoutListener implements Button.ClickListener {

	private static final long serialVersionUID = -9181611261411124159L;

	@Override
	public void buttonClick(final ClickEvent pEvent) {
		SecurityContextHolder.clearContext();
		((NavigationUI) UI.getCurrent()).naviateTo("/", null);
	}

}
