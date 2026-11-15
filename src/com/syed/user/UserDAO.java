package com.syed.user;

import java.util.List;
import java.util.UUID;

public interface UserDAO {
    List<User> getAllUsers();
    User getUserById(UUID userId);
}
