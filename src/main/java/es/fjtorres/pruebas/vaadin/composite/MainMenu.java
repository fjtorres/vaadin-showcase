package es.fjtorres.pruebas.vaadin.composite;

import java.util.ArrayList;
import java.util.HashMap;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.Item;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.shared.MouseEventDetails.MouseButton;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Tree;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import es.fjtorres.pruebas.vaadin.common.NavigationUI;
import es.fjtorres.pruebas.vaadin.common.TreeItemStyleGenerator;
import es.fjtorres.pruebas.vaadin.view.common.MenuItem;

public class MainMenu extends CustomComponent {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private VerticalLayout mainLayout;

	@AutoGenerated
	private Tree menu;

	private HashMap<Item, MenuItem> componentMenuItems;

	private static final long serialVersionUID = -8340258722141956378L;

	public MainMenu() {
		buildMainLayout();
		setCompositionRoot(mainLayout);
	}

	@AutoGenerated
	private VerticalLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new VerticalLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("100%");
		mainLayout.setMargin(false);

		// top-level component properties
		setWidth("100.0%");
		setHeight("100.0%");

		// menu
		menu = new Tree();
		menu.setImmediate(false);
		menu.setWidth("-1px");
		menu.setHeight("-1px");
		menu.setItemStyleGenerator(new TreeItemStyleGenerator());
		mainLayout.addComponent(menu);

		return mainLayout;
	}

	public void init(final ArrayList<MenuItem> menuItems) {

		if (menuItems != null && !menuItems.isEmpty()) {

			componentMenuItems = new HashMap<Item, MenuItem>();
			for (final MenuItem menuItem : menuItems) {
				final Item item = menu.addItem(menuItem.getText());
				componentMenuItems.put(item, menuItem);
			}

			menu.addItemClickListener(new ItemClickListener() {
				@Override
				public void itemClick(final ItemClickEvent event) {
					if (event.getButton() == MouseButton.LEFT) {
						MenuItem menu = componentMenuItems.get(event.getItem());
						((NavigationUI) UI.getCurrent()).naviateTo(
								menu.getPath(), menu.getText());
					}
				}
			});
		}
	}
}