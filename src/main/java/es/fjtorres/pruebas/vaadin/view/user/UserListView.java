package es.fjtorres.pruebas.vaadin.view.user;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.data.util.BeanContainer;
import com.vaadin.event.Action;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

import es.fjtorres.pruebas.vaadin.model.User;
import es.fjtorres.pruebas.vaadin.view.common.AbstractListView;
import es.fjtorres.pruebas.vaadin.view.common.OptionMenu;
import es.fjtorres.pruebas.vaadin.view.common.View;

@View(name = "usersList", path = UserListView.VIEW_PATH)
@OptionMenu(path = UserListView.VIEW_PATH, text = "User")
@Component
@Scope(SCOPE_PROTOTYPE)
public class UserListView extends AbstractListView {

	private static final String PROPERTY_ID = "id";

	private static final String PROPERTY_LAST_NAME = "lastName";

	private static final String PROPERTY_FIRST_NAME = "firstName";

	private static final String PROPERTY_USERNAME = "username";

	private static final long serialVersionUID = 359178421593644799L;

	public static final String VIEW_PATH = "/users/list";

	private Table grid;

	private BeanContainer<Long, User> gridDatasource;

	private List<User> markedRows = new ArrayList<User>();

	@PostConstruct
	public void init() {
		gridDatasource = new BeanContainer<Long, User>(User.class);
		gridDatasource.setBeanIdProperty(PROPERTY_ID);
		gridDatasource.addAll(getGridData());

		grid = new Table();
		grid.setSizeFull();
		grid.setContainerDataSource(gridDatasource);
		grid.setPageLength(10);
		grid.setVisibleColumns(getGridColumns());
		grid.setColumnHeaders(getColumnsHeaders());
		grid.setColumnCollapsingAllowed(true);
		grid.setSelectable(isSelectableGrid());

		if (grid.isSelectable()) {
			final Action actionMark = new Action("Mark");
			final Action actionUnmark = new Action("Unmark");

			grid.addActionHandler(new Action.Handler() {

				private static final long serialVersionUID = 1710561522313231026L;

				@Override
				public Action[] getActions(final Object target,
						final Object sender) {
					if (markedRows.contains(target)) {
						return new Action[] { actionUnmark };
					} else {
						return new Action[] { actionMark };
					}
				}

				@Override
				public void handleAction(final Action action,
						final Object sender, final Object target) {
					if (actionMark == action) {
						markedRows.add((User) target);
					} else if (actionUnmark == action) {
						markedRows.remove(target);
					}
					grid.markAsDirtyRecursive();
				}

			});
		}

		configureColumns();

		com.vaadin.ui.Component controls = createControls();
		addComponent(controls);
		setExpandRatio(controls, 0F);
		final VerticalLayout gridContainer = new VerticalLayout(grid);
		gridContainer.setMargin(true);
		gridContainer.setMargin(new MarginInfo(true, false, false, false));
		gridContainer.setSizeFull();
		addComponent(gridContainer);
		setExpandRatio(gridContainer, 1F);
	}

	private com.vaadin.ui.Component createControls() {
		HorizontalLayout controls = new HorizontalLayout();
		controls.addComponents(new Button(), new Button(), new Button());
		return controls;
	}

	private User getSelection() {
		User selected = null;
		if (isSelectableGrid() && !markedRows.isEmpty()) {
			selected = markedRows.get(0);
		}
		return selected;
	}

	private boolean isSelectableGrid() {
		return true;
	}

	private String[] getColumnsHeaders() {
		return new String[] { PROPERTY_USERNAME, PROPERTY_FIRST_NAME,
				PROPERTY_LAST_NAME };
	}

	private Object[] getGridColumns() {
		return new Object[] { PROPERTY_USERNAME, PROPERTY_FIRST_NAME,
				PROPERTY_LAST_NAME };
	}

	private void configureColumns() {
		grid.setColumnExpandRatio(PROPERTY_USERNAME, 10);
		grid.setColumnExpandRatio(PROPERTY_FIRST_NAME, 40);
		grid.setColumnExpandRatio(PROPERTY_LAST_NAME, 40);
	}

	private List<User> getGridData() {
		final List<User> data = new ArrayList<User>();
		for (int i = 0; i < 20; i++) {
			final User item = new User();
			item.setId(new Long(i));
			item.setFirstName("First name " + i);
			item.setLastName("Last name " + i);
			item.setUsername("username " + i);
			data.add(item);
		}
		return data;
	}
}
