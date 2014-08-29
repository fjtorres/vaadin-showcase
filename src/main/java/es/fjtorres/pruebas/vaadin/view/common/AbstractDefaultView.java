package es.fjtorres.pruebas.vaadin.view.common;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.VerticalLayout;

public abstract class AbstractDefaultView  extends VerticalLayout implements View {

	private static final long serialVersionUID = 120324147660583067L;

	public AbstractDefaultView () {
		setSizeFull();
		setMargin(true);
	}
	
	@Override
	public void enter(final ViewChangeEvent event) {

	}

}
