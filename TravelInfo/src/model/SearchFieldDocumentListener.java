package model;

import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.BadLocationException;

public class SearchFieldDocumentListener implements DocumentListener {
	private String searchBoxHint = "Search";
	private TableRowSorter<TableModel> rowSorter;
	private JTextField filterText;

	public SearchFieldDocumentListener(TableRowSorter<TableModel> rowSorter,
			JTextField filterText) {
		super();
		this.rowSorter = rowSorter;
		this.filterText = filterText;
	}

	/**
	 * At any changes in the text field, the filter for the TableRowSorter is
	 * updated
	 */
	@Override
	public void insertUpdate(DocumentEvent e) {
		try {
			if (!(e.getDocument().getText(0, e.getLength())
					.equals(searchBoxHint))) {
				newFilter(rowSorter, filterText);
			}
		} catch (BadLocationException e1) {
			System.out.println(e1.getMessage());
		}
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		newFilter(rowSorter, filterText);

	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		newFilter(rowSorter, filterText);

	}

	/**
	 * Sets the filter text for the TableRowSorter
	 * 
	 * @param rowSorter
	 *            the sorter to be modified
	 * @param filterText
	 *            the new filter text
	 */
	private void newFilter(TableRowSorter<TableModel> rowSorter,
			JTextField filterText) {
		RowFilter<TableModel, Object> rf = null;
		try {
			rf = RowFilter.regexFilter(filterText.getText(), 0);
		} catch (java.util.regex.PatternSyntaxException e) {
			return;
		}
		rowSorter.setRowFilter(rf);
	}

}
