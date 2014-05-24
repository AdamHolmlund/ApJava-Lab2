package model;

public class Offer {

	private String[] offerData;

	/**
	 * Constructs an Offer object containing the attributes connected to the
	 * offer
	 * 
	 * @param properties
	 *            is an array of strings representing all the information about
	 *            the offer
	 */
	public Offer(String[] properties) {
		this.offerData = properties;
	}

	/**
	 * @param i
	 *            the place in the offer array that should be returned
	 * @return the information at the specified place in the offer array
	 */
	public String getAttribute(int i) {
		return offerData[i];
	}

	/**
	 * @return the offer destination as a string
	 */
	public String getDestination() {
		return offerData[3];
	}

	/**
	 * @return the departure place as a string
	 */
	public String getDepartureFrom() {
		return offerData[1];
	}

	/**
	 * @return the offer departure date as a string
	 */
	public String getDepartureDate() {
		return offerData[2];
	}

	/**
	 * @return the offer's current price as a string
	 */
	public String getCurrentPrice() {
		return offerData[8];
	}

	/**
	 * @return the offer's original price as a string
	 */
	public String getOriginalPrice() {
		return offerData[9];
	}

	/**
	 * @return the offer name as a string
	 */
	public String getCampaignName() {
		return offerData[0];
	}

	/**
	 * @return the number of remaining seats as a string
	 */
	public String getRemainingSeats() {
		return offerData[14];
	}

	/**
	 * @return the picture URL of the offer as a string
	 */
	public String getPictureURL() {
		return offerData[12];
	}

	/**
	 * @return the booking URL of the offer as a string
	 */
	public String getBookingURL() {
		return offerData[17];
	}

}
