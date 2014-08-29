package es.fjtorres.pruebas.vaadin.view.common;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_SINGLETON;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import org.reflections.Reflections;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.navigator.View;

import es.fjtorres.pruebas.vaadin.common.ConstantsApp;

@Component
@Scope(SCOPE_SINGLETON)
public class ViewProvider implements com.vaadin.navigator.ViewProvider,
		ApplicationContextAware {

	private static final long serialVersionUID = 1671121171323606041L;

	private static final Class<es.fjtorres.pruebas.vaadin.view.common.View> viewAnnotationClass = es.fjtorres.pruebas.vaadin.view.common.View.class;

	private final HashMap<String, ViewConfig> routes = new HashMap<String, ViewConfig>();

	private ApplicationContext applicationContext;

	public ViewProvider() {
		final Reflections reflections = new Reflections(
				ConstantsApp.PROJECT_PACKAGE);

		final Set<Class<? extends View>> viewTypes = reflections
				.getSubTypesOf(View.class);

		final Iterator<Class<? extends View>> it = viewTypes.iterator();
		while (it.hasNext()) {
			final Class<? extends View> clazz = it.next();
			if (clazz != null && clazz.isAnnotationPresent(viewAnnotationClass)) {
				es.fjtorres.pruebas.vaadin.view.common.View viewAnnotation = clazz
						.getAnnotation(viewAnnotationClass);
				routes.put(
						viewAnnotation.path(),
						new ViewConfig(viewAnnotation.name(), viewAnnotation
								.path(), clazz));
			}
		}
	}

	@Override
	public String getViewName(final String viewAndParameters) {
		if (viewAndParameters == null) {
			return null;
		}

		final ViewConfig config = findViewByPath(viewAndParameters);
		if (config == null) {
			return null;
		} else {
			return config.getViewPath();
		}
	}

	@Override
	public View getView(final String viewName) {
		View view = null;
		final ViewConfig config = findViewByPath(viewName);
		if (config != null) {
			try {
				view = applicationContext.getBean(config.getViewClass());
			} catch (final NoSuchBeanDefinitionException e) {
				try {
					view = config.getViewClass().newInstance();
				} catch (final InstantiationException ie) {
					throw new RuntimeException(ie);
				} catch (final IllegalAccessException iae) {
					throw new RuntimeException(iae);
				}
			}
		}
		return view;
	}

	private ViewConfig findViewByName(final String viewName) {
		ViewConfig config = null;
		final Set<Entry<String, ViewConfig>> entrySet = routes.entrySet();
		for (Entry<String, ViewConfig> entry : entrySet) {
			if (viewName.equals(entry.getValue().getViewName())) {
				config = entry.getValue();
				break;
			}
		}
		return config;
	}

	private ViewConfig findViewByPath(final String viewPath) {
		ViewConfig config = null;
		if (routes.containsKey(viewPath)) {
			config = routes.get(viewPath);
		}
		return config;
	}

	private static class ViewConfig implements Serializable {

		private static final long serialVersionUID = -2367366665696760743L;

		private final String viewName;

		private final String viewPath;

		private final Class<? extends View> viewClass;

		public ViewConfig(final String viewName, final String viewPath,
				final Class<? extends View> viewClass) {
			this.viewName = viewName;
			this.viewPath = viewPath;
			this.viewClass = viewClass;
		}

		public String getViewName() {
			return viewName;
		}

		public String getViewPath() {
			return viewPath;
		}

		public Class<? extends View> getViewClass() {
			return viewClass;
		}

	}

	@Override
	public void setApplicationContext(
			final ApplicationContext pApplicationContext) throws BeansException {
		this.applicationContext = pApplicationContext;
	}
}
