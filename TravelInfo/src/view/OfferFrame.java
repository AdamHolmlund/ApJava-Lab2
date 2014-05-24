package view;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import javax.swing.*;

import model.Offer;

public class OfferFrame extends JFrame {

	private static final long serialVersionUID = 8786030101140530585L;
	private Offer offer;
	private JPanel mainpanel;
	private URL bookURL;
	private JButton bookButton;

	public OfferFrame(Offer offer) {
		this.offer = offer;
		initialize();
	}

	/**
	 * Initializes the frame. Sets its properties and adds all the panels.
	 */
	private void initialize() {
		setBookingURL();
		Dimension dim = new Dimension(540, 390);
		mainpanel = new JPanel();
		mainpanel.setPreferredSize(dim);
		mainpanel.setLayout(new BorderLayout());

		mainpanel.add(buildTopPanel(), BorderLayout.NORTH);
		mainpanel.add(buildPicturePanel(), BorderLayout.CENTER);
		mainpanel.add(buildButtonPanel(), BorderLayout.SOUTH);

		add(mainpanel);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
		pack();

	}

	/**
	 * Builds the bottom panel which holds the "book" button
	 * 
	 * @return the bottom panel as a JPanel
	 */
	private JPanel buildButtonPanel() {
		System.out.println("Building bottom panel");
		bookButton = new JButton("Book");
		bookButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					openWebPage(bookURL.toURI());
				} catch (URISyntaxException e1) {
					e1.printStackTrace();
				}
			}

			/**
			 * Opens a web page with the given URI
			 * 
			 * @param uri
			 *            the page to be opened
			 */
			private void openWebPage(URI uri) {
				Desktop desktop = Desktop.isDesktopSupported() ? Desktop
						.getDesktop() : null;
				if (desktop != null
						&& desktop.isSupported(Desktop.Action.BROWSE)) {
					try {
						desktop.browse(uri);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}
		});
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout());
		buttonPanel.add(bookButton, BorderLayout.CENTER);
		return buttonPanel;
	}

	/**
	 * Constructs the top panel which contains the information about the offer
	 * 
	 * @return the top panel as a JPanel
	 */
	private JPanel buildTopPanel() {
		System.out.println("Building top frame of new window...");
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(6, 1));
		JLabel destLabel = new JLabel("Destination:   "
				+ offer.getDestination());
		JLabel currentPriceLabel = new JLabel("Price:   "
				+ offer.getCurrentPrice() + " (from "
				+ offer.getOriginalPrice() + ")");
		JLabel seatLabel = new JLabel("Remaining seats:   "
				+ offer.getRemainingSeats());
		JLabel departureFromLabel = new JLabel("Departs from:  "
				+ offer.getDepartureFrom());
		JLabel departureDateLabel = new JLabel("Departure date:   "
				+ offer.getDepartureDate());
		JLabel nameLabel = new JLabel("Campaign name:   "
				+ offer.getCampaignName());

		topPanel.add(nameLabel);
		topPanel.add(destLabel);
		topPanel.add(currentPriceLabel);
		topPanel.add(seatLabel);
		topPanel.add(departureFromLabel);
		topPanel.add(departureDateLabel);
		return topPanel;
	}

	/**
	 * Builds an ImagePanel from the offer URL
	 * 
	 * @return the ImagePanel
	 */
	private JPanel buildPicturePanel() {
		System.out.println("Building picture frame of new window");
		URL url;
		try {
			url = new URL(offer.getPictureURL());
			ImagePanel imagePanel = new ImagePanel(url);
			return imagePanel;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return new JPanel();
	}

	/**
	 * Sets the URL for booking a trip
	 */
	private void setBookingURL() {
		try {
			bookURL = new URL(offer.getBookingURL());
		} catch (MalformedURLException e) {
			System.out.println("URL object could not be created");
		}
	}
}
