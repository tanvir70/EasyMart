package com.tanvir.easymart.service;

import com.tanvir.easymart.domain.User;
import com.tanvir.easymart.dto.UserDTO;
import com.tanvir.easymart.repository.UserRepository;

public class UserServiceImpl implements UserService{
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public void saveUser(UserDTO userDTO) {
        String encrypted = encryptPassword(userDTO.getPassword());

        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(encrypted);
        user.setUsername(userDTO.getUsername());

        userRepository.save(user);
    }

    @Override
    public boolean inNotUniqueUserName(UserDTO user) {
        return userRepository.findByUsername(user.getUsername()).isPresent();
    }

    private String encryptPassword(String password) {
        return password;
    }
}
