package by.epamlab.model.utils.xml.handlers;

import by.epamlab.model.beans.reservation.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.List;

public class ReservationHandler extends DefaultHandler {
	private static enum TagEnum {
		RESERVATION,
		RESCOMPONENT,
		CUSTOMER,
		FAREFAMILY,
		ANCILLARYAIRCOMPONENT,
		EMAIL,
		PAYMENT,
		PHONE;
	}

	private ObjectFactory objectFactory = new ObjectFactory();
	private ReservationType reservation;
	private List<CustomerType> customers;
	private CustomerType customer;
	private List<FareFamilyType> fareFamilys;
	private FareFamilyType fareFamily;
	private List<ResComponentType> resComponents;
	private ResComponentType resComponent;
	private List<AncillaryAirComponentType> ancillaryAirComponents;
	private AncillaryAirComponentType ancillaryAirComponent;
	private EmailType email;
	private PhoneType phone;
	private List<PaymentType> payments;
	private PaymentType payment;
	private TagEnum currentEnum;

	public ReservationType getReservation() {
		if(reservation == null) {
			reservation = objectFactory.createReservationType();
		}
		return reservation;
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attrs) throws SAXException {
		final String CODE = "Code";
		final String DESCRIPTION = "Description";
		final String CUSTOMER_DOC_ID = "CustomerDocID";
		final String FIRST_NAME = "FirstName";
		final String LAST_NAME = "LastName";
		final String DEFAULT = "Default";
		final String EMAIL_ADDRESS = "EmailAddress";
		final String EMAIL_TYPE = "EmailType";
		final String SEQUENCE = "Sequence";
		final String SYNC_STATUS = "SyncStatus";
		final String PHONE_NUMBER = "PhoneNumber";
		final String PHONE_TYPE = "PhoneType";
		final String FARE_FAMILY_CODE = "FareFamilyCode";
		final String ANCILLARY_AIR_COMPONENT_CODE = "AncillaryAirComponentCode";
		final String COMPONENT_TYPE_CODE = "ComponentTypeCode";
		final String CREATE_DATE_TIME = "CreateDateTime";
		final String INTERNAL_STATUS = "InternalStatus";
		final String AMOUNT_PAID = "AmountPaid";
		final String CURRENCY_CODE = "CurrencyCode";
		final String FORM_OF_PAYMENT_TYPE_CODE = "FormOfPaymentTypeCode";

		try {
			currentEnum = TagEnum.valueOf(localName.toUpperCase());
		} catch (Exception err) {
			//No need case other tags
			return;
		}
		switch (currentEnum) {
			case RESERVATION:
				reservation = objectFactory.createReservationType(attrs.getValue(CODE), attrs.getValue(DESCRIPTION));
				resComponents = reservation.getResComponent();
				customers = reservation.getCustomer();
				fareFamilys = reservation.getFareFamily();
				break;
			case CUSTOMER:
				customer = objectFactory.createCustomerType(attrs.getValue(CUSTOMER_DOC_ID), attrs.getValue(FIRST_NAME),
						attrs.getValue(LAST_NAME), attrs.getValue(SEQUENCE));
				payments = customer.getPayment();
				break;
			case EMAIL:
				email = objectFactory.createEmailType(attrs.getValue(DEFAULT), attrs.getValue(EMAIL_ADDRESS), attrs.getValue(EMAIL_TYPE),
						attrs.getValue(SEQUENCE), attrs.getValue(SYNC_STATUS));
				break;
			case PHONE:
				phone = objectFactory.createPhoneType(attrs.getValue(DEFAULT), attrs.getValue(PHONE_NUMBER), attrs.getValue(PHONE_TYPE),
						attrs.getValue(SEQUENCE), attrs.getValue(SYNC_STATUS));
				break;
			case PAYMENT:
				payment = objectFactory.createPaymentType(attrs.getValue(AMOUNT_PAID), attrs.getValue(CURRENCY_CODE), attrs.getValue(FORM_OF_PAYMENT_TYPE_CODE));
				break;
			case FAREFAMILY:
				fareFamily = objectFactory.createFareFamilyType(attrs.getValue(FARE_FAMILY_CODE));
				ancillaryAirComponents = fareFamily.getAncillaryAirComponent();
				break;
			case ANCILLARYAIRCOMPONENT:
				ancillaryAirComponent = objectFactory.createAncillaryAirComponentType(ANCILLARY_AIR_COMPONENT_CODE);
				break;
			case RESCOMPONENT:
				resComponent = objectFactory.createResComponentType(attrs.getValue(COMPONENT_TYPE_CODE),
						attrs.getValue(CREATE_DATE_TIME), attrs.getValue(INTERNAL_STATUS), attrs.getValue(SEQUENCE));
				break;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		try {
			currentEnum = TagEnum.valueOf(localName.toUpperCase());
		}catch (Exception err) {
			//No need case other tags
			return;
		}
		switch (currentEnum) {
			case CUSTOMER:
				customers.add(customer);
				break;
			case EMAIL:
				customer.setEmail(email);
				break;
			case PHONE:
				customer.setPhone(phone);
				break;
			case PAYMENT:
				payments.add(payment);
				break;
			case FAREFAMILY:
				fareFamilys.add(fareFamily);
				break;
			case ANCILLARYAIRCOMPONENT:
				ancillaryAirComponents.add(ancillaryAirComponent);
				break;
			case RESCOMPONENT:
				resComponents.add(resComponent);
				break;
		}
	}
}
