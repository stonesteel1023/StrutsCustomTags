package by.epamlab.ifaces;

import by.epamlab.model.beans.user.User;
import by.epamlab.model.exceptions.DaoException;

public interface IUserDAO {
	public User getUser(String name, String password) throws DaoException;
	
	public User setUser(String name, String password) throws DaoException;
}
