package com.tanvir.easymart.repository;

import com.tanvir.easymart.domain.User;

import java.util.Optional;

public interface UserRepository {
    void save(User user);
    Optional<User>findByUsername(String username);
}
