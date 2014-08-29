package es.fjtorres.pruebas.vaadin;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

import es.fjtorres.pruebas.vaadin.composite.MainPage;
import es.fjtorres.pruebas.vaadin.view.common.MenuProvider;
import es.fjtorres.pruebas.vaadin.view.common.ViewProvider;

@Title(MyVaadinUI.DEFAULT_TITLE)
@Theme("mytheme")
@SuppressWarnings("serial")
public class MyVaadinUI extends UI {

	public static final String DEFAULT_TITLE = "Vaadin showcase";

	private static ViewProvider navigationProvider;

	private static MenuProvider menuProvider;

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = MyVaadinUI.class)
	public static class Servlet extends VaadinServlet {

		@Override
		public void init(ServletConfig servletConfig) throws ServletException {
			super.init(servletConfig);

			if (navigationProvider == null) {
				navigationProvider = new ViewProvider();
			}

			if (menuProvider == null) {
				menuProvider = new MenuProvider();
			}
		}

	}

	private Navigator nav;

	@Override
	protected void init(final VaadinRequest request) {
		final MainPage mainPage = new MainPage();
		mainPage.setSizeFull();
		mainPage.initMenu(menuProvider.getOptionsMenu());
		setContent(mainPage);

		nav = new Navigator(this, mainPage.getContent());

		nav.addViewChangeListener(new ViewChangeListener() {

			@Override
			public boolean beforeViewChange(ViewChangeEvent event) {
				// Aqui podemos controlar los permisos para ver una ventana
				return true;
			}

			@Override
			public void afterViewChange(ViewChangeEvent event) {

			}
		});

		nav.addProvider(navigationProvider);

		naviateTo(Page.getCurrent().getUriFragment(), null);
	}

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
