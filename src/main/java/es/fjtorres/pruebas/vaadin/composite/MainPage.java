package es.fjtorres.pruebas.vaadin.composite;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

import java.util.ArrayList;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Runo;

import es.fjtorres.pruebas.vaadin.view.common.MenuItem;
import es.fjtorres.pruebas.vaadin.view.login.LogoutListener;

@Component
@Scope(SCOPE_PROTOTYPE)
public class MainPage extends CustomComponent {

	@AutoGenerated
	private VerticalLayout mainLayout;

	@AutoGenerated
	private HorizontalSplitPanel contentSplit;

	@AutoGenerated
	private VerticalLayout header;

	@AutoGenerated
	private VerticalLayout headerContent;

	@AutoGenerated
	private Label appTitle;

	private static final long serialVersionUID = -4287162214192887607L;

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	private MainMenu menu;

	private VerticalLayout content;

	/**
	 * The constructor should first build the main layout, set the composition
	 * root and then do any custom initialization.
	 * 
	 * The constructor will not be automatically regenerated by the visual
	 * editor.
	 */
	public MainPage() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// TODO add user code here
		initCustomComponents();
	}

	public void initCustomComponents() {
		menu = new MainMenu();
		contentSplit.setFirstComponent(menu);
		contentSplit.setSplitPosition(20, Sizeable.Unit.PERCENTAGE);

		content = new VerticalLayout();
		content.setSizeFull();
		contentSplit.setSecondComponent(content);
	}

	public void initMenu(final ArrayList<MenuItem> menuItems) {
		menu.init(menuItems);
	}

	public VerticalLayout getContent() {
		return content;
	}

	@AutoGenerated
	private VerticalLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new VerticalLayout();
		mainLayout.setImmediate(false);
		mainLayout.setSizeFull();
		mainLayout.setMargin(false);
		mainLayout.setStyleName("app");

		// header
		header = buildHeader();
		mainLayout.addComponent(header);
		mainLayout.setExpandRatio(header, 0F);

		// contentSplit
		contentSplit = new HorizontalSplitPanel();
		contentSplit.setStyleName("app-content");
		contentSplit.setImmediate(false);
		contentSplit.addStyleName(Runo.SPLITPANEL_SMALL);
		mainLayout.addComponent(contentSplit);
		mainLayout.setExpandRatio(contentSplit, 1.0F);

		return mainLayout;
	}

	@AutoGenerated
	private VerticalLayout buildHeader() {
		// common part: create layout
		header = new VerticalLayout();
		header.setStyleName("app-header");
		header.setImmediate(false);
		header.setWidth("100.0%");
		header.setHeight("75px");
		header.setMargin(false);

		// headerContent
		headerContent = buildHeaderContent();
		header.addComponent(headerContent);

		return header;
	}

	@AutoGenerated
	private VerticalLayout buildHeaderContent() {
		// common part: create layout
		headerContent = new VerticalLayout();
		headerContent.setImmediate(false);
		headerContent.setWidth("-1px");
		headerContent.setHeight("-1px");
		headerContent.setMargin(true);

		// appTitle
		appTitle = new Label();
		appTitle.setImmediate(false);
		appTitle.setWidth("-1px");
		appTitle.setHeight("-1px");
		appTitle.setValue("Vaadin showcase");
		headerContent.addComponent(appTitle);

		// FIXME Añadir opciones a la cabecera.
		HorizontalLayout headerMenu = new HorizontalLayout();
		headerMenu.addComponent(new Button("Logout", new LogoutListener()));
		headerContent.addComponent(headerMenu);
		
		return headerContent;
	}
}