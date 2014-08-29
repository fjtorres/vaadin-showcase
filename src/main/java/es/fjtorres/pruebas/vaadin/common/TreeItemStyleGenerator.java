package es.fjtorres.pruebas.vaadin.common;

import com.vaadin.ui.Tree;
import com.vaadin.ui.Tree.ItemStyleGenerator;

public class TreeItemStyleGenerator implements ItemStyleGenerator {
	
	private static final long serialVersionUID = 5821598255362217210L;

	protected static final String TREE_NODE_WITHOUT_CHILDREN_STYLE = "no-children";

	@Override
	public String getStyle(Tree source, Object itemId) {
		if (source.getChildren(itemId) == null
				|| source.getChildren(itemId).size() == 0) {
			return TREE_NODE_WITHOUT_CHILDREN_STYLE;
		} else {
			return null;
		}
	}

}
