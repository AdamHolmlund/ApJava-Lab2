package model;

import javax.swing.table.AbstractTableModel;

public class TravelTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -6999751185907569381L;
	private Offer[] offers = new Offer[] {};

	/**
	 * Returns the number of rows. That is, the number of offers in the offer
	 * array.
	 */
	@Override
	public int getRowCount() {
		return offers.length;
	}

	/**
	 * Returns the number of columns, which here is always 3
	 */
	@Override
	public int getColumnCount() {
		return 3;
	}

	/**
	 * Returns the table value in rowIndex, columnIndex
	 */
	@Override
	public String getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return offers[rowIndex].getDestination();
		case 1:
			return offers[rowIndex].getDepartureDate();
		case 2:
			return offers[rowIndex].getCurrentPrice();
		}
		return null;
	}

	/**
	 * Returns the names of the respective columns
	 */
	@Override
	public String getColumnName(int column) {
		switch (column) {
		case 0:
			return "Destination";
		case 1:
			return "Departure date";
		case 2:
			return "Price";
		default:
			break;
		}
		return null;
	}

	/**
	 * Sets the array of offer objects which is used to build the table.
	 * 
	 * @param offers
	 *            the new offer array
	 */
	public void setOfferList(Offer[] offers) {
		synchronized (this.offers) {
			this.offers = offers;
		}
		fireTableDataChanged();
	}

	public Offer[] getOfferList() {
		return offers;
	}

}
