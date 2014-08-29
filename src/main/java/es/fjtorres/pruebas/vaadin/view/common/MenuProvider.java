package es.fjtorres.pruebas.vaadin.view.common;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_SINGLETON;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import org.reflections.Reflections;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import es.fjtorres.pruebas.vaadin.common.ConstantsApp;

@Component
@Scope(SCOPE_SINGLETON)
public class MenuProvider {

	private final ArrayList<MenuItem> optionsMenu = new ArrayList<MenuItem>();

	public MenuProvider() {
		init();
	}

	private void init() {
		final Reflections reflections = new Reflections(
				ConstantsApp.PROJECT_PACKAGE);

		final Set<Class<?>> types = reflections
				.getTypesAnnotatedWith(OptionMenu.class);

		final Iterator<Class<?>> it = types.iterator();
		while (it.hasNext()) {
			final Class<?> clazz = it.next();
			if (clazz != null) {
				final OptionMenu annotation = clazz
						.getAnnotation(OptionMenu.class);
				String text = annotation.path();
				if (annotation.text() != null
						&& annotation.text().length() != 0) {
					text = annotation.text();
				}
				optionsMenu.add(new MenuItem(text, annotation.path()));
			}
		}
	}

	public ArrayList<MenuItem> getOptionsMenu() {
		return optionsMenu;
	}
}
