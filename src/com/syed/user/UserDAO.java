package com.syed.user;

import java.util.UUID;

public interface UserDAO {
    User[] getAllUsers();
    User getUserById(UUID userId);
}
