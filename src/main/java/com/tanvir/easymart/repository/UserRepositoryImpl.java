package com.tanvir.easymart.repository;

import com.tanvir.easymart.domain.User;
import com.tanvir.easymart.service.UserService;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class UserRepositoryImpl implements UserRepository{

    private static final Set<User> USERS = new CopyOnWriteArraySet<>();
    @Override
    public void save(User user) {
        USERS.add(user);
    }
}
