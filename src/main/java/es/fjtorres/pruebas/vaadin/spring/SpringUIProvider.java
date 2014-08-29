package es.fjtorres.pruebas.vaadin.spring;

import org.springframework.context.ApplicationContext;

import com.vaadin.server.UIClassSelectionEvent;
import com.vaadin.server.UICreateEvent;
import com.vaadin.server.UIProvider;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

public class SpringUIProvider extends UIProvider {

	public static final String DEFAULT_UI_BEAN_NAME = "spring-ui";

	public static final String UI_BEAN_NAME = "beanName";

	private static final long serialVersionUID = 6526048916439245307L;

	private final ApplicationContext applicationContext;

	public SpringUIProvider(final ApplicationContext pApplicationContext) {
		this.applicationContext = pApplicationContext;
	}

	@Override
	public Class<? extends UI> getUIClass(final UIClassSelectionEvent event) {
		return applicationContext.getType(getUIBeanName(event.getRequest()))
				.asSubclass(UI.class);
	}

	@Override
	public UI createInstance(final UICreateEvent event) {
		return (UI) applicationContext
				.getBean(getUIBeanName(event.getRequest()));
	}

	@Override
	public boolean isPreservedOnRefresh(final UICreateEvent event) {
		boolean preserved = false;
		if (applicationContext.isPrototype(getUIBeanName(event.getRequest()))) {
			preserved = super.isPreservedOnRefresh(event);
		}
		return preserved;
	}

	private String getUIBeanName(final VaadinRequest request) {
		return request
				.getService()
				.getDeploymentConfiguration()
				.getApplicationOrSystemProperty(UI_BEAN_NAME,
						DEFAULT_UI_BEAN_NAME);
	}
}
