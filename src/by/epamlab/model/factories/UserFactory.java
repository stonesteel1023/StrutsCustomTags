package by.epamlab.model.factories;

import by.epamlab.ifaces.IUserDAO;
import by.epamlab.model.impl.UserImplDB;
import by.epamlab.model.impl.UserImplMem;

public class UserFactory {
	public static IUserDAO getImplFromFactory() {
		return new UserImplMem();
//		return new UserImplDB();
	}

}
