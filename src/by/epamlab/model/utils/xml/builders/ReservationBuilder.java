package by.epamlab.model.utils.xml.builders;

import by.epamlab.model.beans.reservation.ReservationType;
import by.epamlab.model.utils.xml.handlers.ReservationHandler;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ReservationBuilder {
	private ReservationType reservation;
	private ReservationHandler reservationHandler;
	private XMLReader xmlReader;
	
	public ReservationBuilder() {
		reservationHandler = new ReservationHandler();
		try {
			xmlReader = XMLReaderFactory.createXMLReader();
			xmlReader.setContentHandler(reservationHandler);
		} catch (SAXException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	public void buildListResults(String fileName) {
		try {
			xmlReader.parse(fileName);
		} catch (FileNotFoundException e) {
			System.out.println("File \"" + fileName + "\" not found");
		} catch (IOException | SAXException e) {
			throw new IllegalArgumentException(e);
		} 
		
		reservation = reservationHandler.getReservation();
	}
	
	public ReservationType getReservation() {
		return reservation;
	}
}
