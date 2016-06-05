package by.epamlab.filters;

import by.epamlab.model.beans.user.Role;
import by.epamlab.model.beans.user.User;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by rdv on 04.06.2016.
 */
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpSession httpSession = httpRequest.getSession();
        String path = httpRequest.getServletPath();
        User user = (User) httpSession.getAttribute("user");

        if(path.equals("/reservation.do") || path.equals("/customer.do") || path.equals("/farefamily.do")) {
            if (user.getRole() == Role.VISITOR) {
                servletRequest.getRequestDispatcher("/index.do").forward(servletRequest, servletResponse);
                return;
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
