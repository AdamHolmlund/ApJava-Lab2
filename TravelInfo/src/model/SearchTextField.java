package model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SearchTextField extends JTextField implements FocusListener {

	private static final long serialVersionUID = -3253139061685637697L;
	private static Color searchStartingText = new Color(100, 100, 100);
	private static Color searchTextColor = new Color(0, 0, 0);

	private String startingText;

	/**
	 * Creates a text field with the startingText set as empty
	 */
	public SearchTextField() {
		this.startingText = "";
	}

	/**
	 * Creates a text field with the starting text defined
	 * 
	 * @param text
	 *            the starting text
	 */
	public SearchTextField(String text) {
		this.setForeground(searchStartingText);
		this.setText(text);
		startingText = text;
		this.addFocusListener(this);
	}

	/**
	 * @return the starting text in the field
	 */
	public String getStartingText() {
		return this.startingText;
	}

	/**
	 * @param startingText
	 *            the starting text in the field
	 */
	public void setStartingText(String hintText) {
		this.startingText = hintText;
	}

	/**
	 * Removes the starting text when box is selected
	 * 
	 * @param e
	 *            event
	 */
	@Override
	public void focusGained(FocusEvent e) {
		if (this.getText().equals(startingText)) {
			this.setForeground(searchTextColor);
			this.setText("");
		}
	}

	/**
	 * Adds starting text when box is deselected, but only if the box was empty
	 * 
	 * @param e
	 *            event
	 */
	@Override
	public void focusLost(FocusEvent e) {
		if (!this.getText().equals(startingText)
				&& this.getText().length() == 0) {
			this.setForeground(searchStartingText);
			this.setText(startingText);

		}
	}
}