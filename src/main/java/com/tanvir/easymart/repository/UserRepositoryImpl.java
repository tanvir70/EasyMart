package com.tanvir.easymart.repository;

import com.tanvir.easymart.domain.User;
import com.tanvir.easymart.service.UserService;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class UserRepositoryImpl implements UserRepository{

    private static final Set<User> USERS = new CopyOnWriteArraySet<>();
    @Override
    public void save(User user) {
        USERS.add(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        var environment = System.getenv();
        if (environment.get("DEV") != null && environment.get("DEV").equals("true")){
            return Optional.of(new User("tanvir","bd94dcda26fccb4e68d6a31f9b5aac0b571ae266d822620e901ef7ebe3a11d4f","tanvir@gmail.com","tanvir","Rifat"));
        }
        return USERS.stream().filter(user -> Objects.equals(user.getUsername(),username)).findFirst();
    }
}
