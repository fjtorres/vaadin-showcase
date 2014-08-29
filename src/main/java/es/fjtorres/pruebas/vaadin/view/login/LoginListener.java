package es.fjtorres.pruebas.vaadin.view.login;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

import es.fjtorres.pruebas.vaadin.common.NavigationUI;

public class LoginListener implements Button.ClickListener {

	private static final long serialVersionUID = 3066257740418365500L;

	private final AuthenticationManager authenticationManager;

	private final LoginForm form;

	public LoginListener(final AuthenticationManager pAuthenticationManager,
			final LoginForm pForm) {
		this.authenticationManager = pAuthenticationManager;
		this.form = pForm;
	}

	@Override
	public void buttonClick(final ClickEvent pEvent) {
		if (authenticationManager == null || form == null) {
			return;
		}

		if (form.validate()) {
			try {
				final Authentication auth = authenticationManager
						.authenticate(new UsernamePasswordAuthenticationToken(
								form.getUsername().getValue(), form
										.getPassword().getValue()));
				if (auth != null && auth.isAuthenticated()) {
					SecurityContextHolder.getContext().setAuthentication(auth);
					((NavigationUI) UI.getCurrent()).navigateToFirst();
				}
			} catch (final AuthenticationException ex) {
				// FIXME ERROR
				Notification.show(ex.getLocalizedMessage());
			}
		} else {
			Notification.show("The credentials are required");
		}

	}
}
