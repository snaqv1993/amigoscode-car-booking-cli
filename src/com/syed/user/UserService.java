package com.syed.user;

import java.util.UUID;

public class UserService {

    private UserDAO userDAO = new UserDAO();

    public User[] getAllUsers(){
        return userDAO.getAllUsers();
    }

    public User getUserById(UUID userID){
        return userDAO.getUserById(userID);
    }
}
