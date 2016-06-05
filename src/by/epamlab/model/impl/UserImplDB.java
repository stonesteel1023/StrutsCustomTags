package by.epamlab.model.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import by.epamlab.constants.Constants;
import by.epamlab.constants.ConstantsDB;
import by.epamlab.ifaces.IUserDAO;
import by.epamlab.model.beans.user.Role;
import by.epamlab.model.beans.user.User;
import by.epamlab.model.exceptions.DBException;
import by.epamlab.model.exceptions.DaoException;
import by.epamlab.model.utils.db.ServiceDB;

public class UserImplDB implements IUserDAO {
	@Override
	public User getUser(String login, String password) throws DaoException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			final int IND_LOGIN = 1;
			connection = ServiceDB.getConnection();
			preparedStatement = connection.prepareStatement(ConstantsDB.SELECT_SQL);
			preparedStatement.setString(IND_LOGIN, login);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				try {
					String userLogin = resultSet.getString(ConstantsDB.USER_COLUMN_LOGIN);
					String userRole = resultSet.getString(ConstantsDB.USER_COLUMN_ROLE);
					String userPass = new String(resultSet.getBytes(ConstantsDB.USER_COLUMN_PASS));
					if(userPass != null && userPass.equals(password)) {
						User user = new User(userLogin, Role.valueOf(userRole));
						return user;
					}
					throw new DaoException(Constants.ERROR_USER_PASSWORD);
				} catch (IllegalArgumentException e) {
					throw new DaoException(Constants.ERROR_LOAD_USER + e.getMessage());
				}
			} 
			throw new DaoException(Constants.ERROR_USER);
		} catch (SQLException e) {
            throw new DaoException(ConstantsDB.QUERY_STRING + ConstantsDB.SELECT_SQL+ ConstantsDB.END_STRING + e);
		}  catch (DBException e) {
            throw new RuntimeException(e.getMessage());
		} finally {
			try {
				ServiceDB.closeConnection(connection, preparedStatement, resultSet);
			} catch (DBException e) {
				new DaoException(e.getMessage());
			}
		}
	}

	@Override
	public User setUser(String login, String password) throws DaoException {
		final int IND_LOGIN = 1;
		final int IND_PASS = 2;
		final int IND_ROLE = 3;
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		synchronized (UserImplDB.class) {
			try {
				connection = ServiceDB.getConnection();
				preparedStatement = connection.prepareStatement(ConstantsDB.SELECT_USER_SQL);
				preparedStatement.setString(IND_LOGIN, login);
				
				resultSet = preparedStatement.executeQuery();
				if(resultSet.next()) {
					throw new DaoException(Constants.ERROR_USER_EXIST);
				} 
				
				preparedStatement = connection.prepareStatement(ConstantsDB.INSERT_USER_SQL);
				preparedStatement.setString(IND_LOGIN, login);
				preparedStatement.setString(IND_PASS, password);
				preparedStatement.setString(IND_ROLE, Role.USER.toString());
				preparedStatement.executeUpdate();
				
				return new User(login, Role.USER);
				
			} catch (SQLException e) {
	            throw new DaoException(ConstantsDB.QUERY_STRING + ConstantsDB.SELECT_SQL+ ConstantsDB.END_STRING + e);
			}  catch (DBException e) {
	            throw new RuntimeException(e.getMessage());
			} finally {
				try {
					ServiceDB.closeConnection(connection, preparedStatement, resultSet);
				} catch (DBException e) {
					new DaoException(e.getMessage());
				}
			}
		}
	}
}
