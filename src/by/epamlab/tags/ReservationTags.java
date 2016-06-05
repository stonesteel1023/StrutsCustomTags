package by.epamlab.tags;

import by.epamlab.ifaces.IReservationDAO;
import by.epamlab.model.beans.reservation.ObjectElement;
import by.epamlab.model.exceptions.DaoException;
import by.epamlab.model.factories.ReservationFactory;

import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TryCatchFinally;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReservationTags extends BodyTagSupport implements TryCatchFinally {
    private String element;
    private String[] attributes;
    private int numTab = -1;

    public void setElement(String element) {
        this.element = element;
    }

    public void setAttributes(String... attributes) {
        this.attributes = attributes;
    }

    public int doAfterBody() throws JspException {
        StringBuffer result = new StringBuffer();
        String params = bodyContent.getString();
        bodyContent.clearBody();

        IReservationDAO reservationDAO = ReservationFactory.getImplFromFactory();
        try {
            List<ObjectElement> objectElementList = reservationDAO.getElement(element, params);
            result.append("<div class=\"container\">");
            for(ObjectElement objectElement: objectElementList) {
                printObject(result, objectElement);
            }
            result.append("</div>");
        }catch (DaoException e) {
            e.printStackTrace();
        }

        try {
            if (result.length() > 0) {
                bodyContent.println(result.toString());
            }
            bodyContent.writeOut(bodyContent.getEnclosingWriter());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return SKIP_BODY;
    }

    private void printObject(StringBuffer result, ObjectElement objectElement) {
        numTab++;
        result.append("<dl>");
//        String ab = getTabulation();
        result.append(objectElement.getNameElement());
        numTab++;
//        ab = getTabulation();
        HashMap<String, List<ObjectElement>> objectElementList = objectElement.getContent();
        HashMap<String, String> attributes = objectElement.getAttributes();

        if(attributes != null) {
            for (Map.Entry<String, String> value: attributes.entrySet()){
                result.append("<dt>" + value.getKey() + "</dt>");
                result.append("<dd>" + value.getValue() + "</dd>");
            }
        }
        if(objectElementList != null) {
            for (Map.Entry<String, List<ObjectElement>> object : objectElementList.entrySet()) {
                if (object.getValue() != null) {
                    for(ObjectElement element : object.getValue()) {
                        printObject(result, element);
                    }
                }
            }
        }
        numTab--;
//        ab = getTabulation();
        result.append("</dl>");
        numTab--;
    }

    private String getTabulation() {
        String tab = "	";
        String ab = "";
        for(int i = 0; i<numTab; i++) {
            ab += tab;
        }
        return ab;
    }

    public void doCatch(Throwable t) {
        System.out.println("An error occurred with the message"
                + t.getMessage());
    }

    public void doFinally() {
    }

    public void release() {

    }
}
