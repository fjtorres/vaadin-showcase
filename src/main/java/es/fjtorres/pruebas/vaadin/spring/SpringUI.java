package es.fjtorres.pruebas.vaadin.spring;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.DefaultErrorHandler;
import com.vaadin.server.ErrorHandler;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import es.fjtorres.pruebas.vaadin.common.NavigationUI;
import es.fjtorres.pruebas.vaadin.composite.MainPage;
import es.fjtorres.pruebas.vaadin.service.IUserService;
import es.fjtorres.pruebas.vaadin.view.common.MenuProvider;
import es.fjtorres.pruebas.vaadin.view.common.ViewProvider;
import es.fjtorres.pruebas.vaadin.view.login.LoginForm;

@Title(SpringUI.DEFAULT_TITLE)
@Theme("mytheme")
@Component(SpringUI.BEAN_NAME)
@Scope(SCOPE_PROTOTYPE)
public class SpringUI extends UI implements ErrorHandler, NavigationUI {

	private static final long serialVersionUID = 242293252225457635L;

	public static final String BEAN_NAME = "spring-ui";

	public static final String DEFAULT_TITLE = "Vaadin showcase";

	@Autowired
	private transient ViewProvider navigationProvider;

	@Autowired
	private transient MenuProvider menuProvider;

	@Autowired
	private transient IUserService userService;

	@Autowired
	private MainPage mainPage;

	@Autowired
	private LoginForm loginForm;

	private VerticalLayout root = new VerticalLayout();

	private Navigator nav;

	@Override
	public void error(final com.vaadin.server.ErrorEvent event) {
		DefaultErrorHandler.doDefault(event);
	}

	@Override
	protected void init(final VaadinRequest request) {
		setContent(root);
		root.setSizeFull();
		VaadinSession.getCurrent().setErrorHandler(this);

		if (!isLoggedIn()) {
			showLogin();
		} else {
			navigateToFirst();
		}
	}

	private boolean isLoggedIn() {
		return SecurityContextHolder.getContext().getAuthentication() != null
				&& SecurityContextHolder.getContext().getAuthentication()
						.isAuthenticated();
	}

	private void showLogin () {
		root.removeAllComponents();
		root.addComponent(loginForm);
		loginForm.clear();
		loginForm.getUsername().focus();
	}
	
	@Override
	public void navigateToFirst() {
		root.removeAllComponents();
		mainPage.setSizeFull();
		root.addComponent(mainPage);

		nav = new Navigator(this, mainPage.getContent());

		nav.addViewChangeListener(new ViewChangeListener() {

			@Override
			public boolean beforeViewChange(final ViewChangeEvent event) {
				boolean viewChange = true;
				if (isLoggedIn()) {
					viewChange = true;
				} else {
					showLogin();
				}
				return viewChange;
			}

			@Override
			public void afterViewChange(final ViewChangeEvent event) {

			}
		});

		nav.addProvider(navigationProvider);

		mainPage.initMenu(menuProvider.getOptionsMenu());
		naviateTo("/", null);

		setNavigator(nav);
	}

	@Override
	public void naviateTo(final String path, final String title) {
		String auxPath = path;
		if (path != null && path.startsWith("!")) {
			auxPath = path.substring(1);
		}
		if (auxPath == null || auxPath.equals("") || auxPath.equals("/")) {
			nav.navigateTo("/");
		} else {
			nav.navigateTo(auxPath);
		}

		if (title == null || title.length() == 0) {
			Page.getCurrent().setTitle(DEFAULT_TITLE);
		} else {
			Page.getCurrent().setTitle(DEFAULT_TITLE + " - " + title);
		}
	}
}
