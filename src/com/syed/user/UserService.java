package com.syed.user;

import java.util.UUID;

public class UserService {

    private UserDAO userDAO;

    public UserService(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    public User[] getAllUsers(){
        return userDAO.getAllUsers();
    }

    public User getUserById(UUID userID){
        return userDAO.getUserById(userID);
    }
}
