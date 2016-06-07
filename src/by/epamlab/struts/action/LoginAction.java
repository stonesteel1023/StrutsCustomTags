package by.epamlab.struts.action;

import by.epamlab.constants.Constants;
import by.epamlab.struts.form.LoginForm;
import by.epamlab.ifaces.IUserDAO;
import by.epamlab.model.beans.user.User;
import by.epamlab.model.exceptions.DaoException;
import by.epamlab.model.factories.UserFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginAction extends Action {
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        LoginForm loginForm = (LoginForm) form;
        String target = "success";

        try {
            String login = loginForm.getUserName();
            String password = loginForm.getPassword();
            request.getSession().removeAttribute(Constants.KEY_USER);

            IUserDAO userDAO = UserFactory.getImplFromFactory();
            try {
                User user = userDAO.getUser(login, password);
                request.getSession().setAttribute(Constants.KEY_USER, user);
                return mapping.findForward(target);
            } catch (DaoException e) {
                e.printStackTrace();
            }

        }catch (IllegalArgumentException | NullPointerException e) {
            e.printStackTrace();
        }

        target = "failure";
        ActionErrors actionErrors = new ActionErrors();
        actionErrors.add("nouser", new ActionMessage("error.nouser.null"));
        saveErrors(request, actionErrors);
        return mapping.findForward(target);
    }



}