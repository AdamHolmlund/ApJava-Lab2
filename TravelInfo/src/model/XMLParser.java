package model;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.w3c.dom.*;

import javax.swing.SwingUtilities;
import javax.xml.parsers.*;

public class XMLParser extends Thread {

	private String searchPath = "http://www.fritidsresor.se/Blandade-Sidor/feeds/tradera/";
	public TravelTableModel tablemodel;

	/**
	 * Constructs an XMLParser associated with the program's Controller
	 * 
	 * @param controller
	 *            is the parser's Controller
	 */
	public XMLParser(TravelTableModel model) {
		this.tablemodel = model;
	}

	/**
	 * Creates a Document object from an online XML.
	 * 
	 * @param docString
	 *            the address which creates the URL
	 * @return the created Document containing the nodes from the XML
	 */
	private Document getDocument(String docString) {
		try {
			URL url = new URL(docString);
			URLConnection conn = url.openConnection();

			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			factory.setIgnoringComments(true);
			factory.setIgnoringElementContentWhitespace(true);

			DocumentBuilder builder = factory.newDocumentBuilder();
			return builder.parse(conn.getInputStream());
			// return builder.parse(new InputSource(path));
		} catch (Exception e) {
			System.out.println("XML could not be found");
		}
		return null;
	}

	/**
	 * Parses the XML specified by the search path given into a matrix of Offer
	 * objects
	 * 
	 * @param searchPath
	 *            is where the XML is found
	 */
	public void parseXML(String searchPath) {
		Document xmlDoc = getDocument(searchPath);

		NodeList off = xmlDoc.getDocumentElement().getChildNodes();
		ArrayList<Offer> offlist = new ArrayList<Offer>();
		for (int i = 0; i < off.getLength(); i++) {
			// Create an array of Strings with one line in each
			String lines[] = off.item(i).getTextContent().split("\\r?\\n");
			String[] lines2 = new String[lines.length - 1];

			// Move all entries into a new array so they are arranged properly
			// (????)
			for (int j = 1; j < lines.length; j++) {
				lines2[j - 1] = lines[j];
				if (lines2[j - 1] == "") {
					lines2[j - 1] = "N/A";
				}
			}

			Offer o = new Offer(lines2);
			offlist.add(o);
		}
		final Offer[] offerArray = new Offer[offlist.size()];
		offlist.toArray(offerArray);
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				tablemodel.setOfferList(offerArray);
				System.out.println("XML parsed");
			}
		});
	}

	@Override
	public void run() {
		parseXML(searchPath);
	}

}
