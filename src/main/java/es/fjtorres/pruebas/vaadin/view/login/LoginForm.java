package es.fjtorres.pruebas.vaadin.view.login;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Component;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.util.PropertysetItem;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@Component
@Scope(SCOPE_PROTOTYPE)
public class LoginForm extends VerticalLayout {

	private static final long serialVersionUID = 3834862712226668708L;

	@Autowired(required = true)
	private AuthenticationManager authenticationManager;

	private FieldGroup binder;

	private TextField username;

	private PasswordField password;

	public LoginForm() {

	}

	@PostConstruct
	public void init() {
		setSizeFull();
		
		final FormLayout layout = new FormLayout();
		layout.setMargin(new MarginInfo(true, true, false, true));
		layout.setSpacing(true);

		username = new TextField("User");
		username.setWidth(98F, Unit.PERCENTAGE);
		username.setRequired(true);
		layout.addComponent(username);

		password = new PasswordField("Password");
		password.setWidth(98F, Unit.PERCENTAGE);
		password.setRequired(true);
		layout.addComponent(password);

		final PropertysetItem item = new PropertysetItem();
		item.addItemProperty("username", new ObjectProperty<String>(""));
		item.addItemProperty("password", new ObjectProperty<String>(""));

		binder = new FieldGroup(item);
		binder.bind(username, "username");
		binder.bind(password, "password");
		binder.setBuffered(true);

		final Button loginButton = new Button("Access");
		loginButton.setStyleName("primary");

		final Button clearButton = new Button("Clear");
		clearButton.setStyleName("primary");

		loginButton.addClickListener(new LoginListener(authenticationManager,
				this));

		clearButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(final ClickEvent pEvent) {
				clear();
			}
		});

		HorizontalLayout buttons = new HorizontalLayout(loginButton,
				clearButton);
		buttons.setSpacing(true);
		layout.addComponent(buttons);
		
		final Panel loginPanel = new Panel("Application access", layout);
		loginPanel.setWidth(350F, Unit.PIXELS);
		loginPanel.setHeight(200F, Unit.PIXELS);
		
		addComponent(loginPanel);
		setComponentAlignment(loginPanel, Alignment.MIDDLE_CENTER);
	}

	public boolean validate() {
		return binder.isValid();
	}

	public void clear() {
		binder.discard();
	}

	public TextField getUsername() {
		return username;
	}

	public PasswordField getPassword() {
		return password;
	}
}
