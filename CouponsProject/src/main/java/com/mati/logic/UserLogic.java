package com.mati.logic;

import com.mati.beans.User;
import com.mati.dao.UserDao;
import com.mati.enums.ErrorTypes;
import com.mati.enums.UserTypes;
import com.mati.exceptions.ApplicationException;
import com.mati.interfaces.daointerfaces.IUserDao;

public class UserLogic {
	private IUserDao userDao = new UserDao();
	
	public void removeUser(User user) throws ApplicationException{
		if(user == null){
			throw new ApplicationException(ErrorTypes.FALSE_USER_INFORMATION, "User is null");
		}
		
		userDao.removeUser(user);
	}
	
	public void checkUser(User user) throws ApplicationException {
		if(user == null){
			throw new ApplicationException(ErrorTypes.FALSE_USER_INFORMATION, "User is null");
		}
		if(isAdmin(user)){
			return;
		}
		if(!userDao.isValidUser(user)){
			throw new ApplicationException(ErrorTypes.INVALID_USER_NAME_OR_PASSWORD, "Login filed");
		}
	}
	
	public long getClientIdByUser(User user) throws ApplicationException {
		if(user == null){
			throw new ApplicationException(ErrorTypes.FALSE_USER_INFORMATION, "User is null");
		}	
		long clientId = userDao.getClientIdByUser(user);
		return clientId;
	}

	public UserTypes getClientType(User user) throws ApplicationException {
		UserTypes type = userDao.getClientType(user);
		return type;
	}

	public boolean isAdmin(User user) {
		if(user.getUserName().equals("Admin") && user.getPassword().equals("Admin")){
			return true;
		}
		return false;
	}

}
