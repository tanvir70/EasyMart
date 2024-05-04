package com.tanvir.easymart.service;

import com.tanvir.easymart.domain.User;
import com.tanvir.easymart.dto.LoginDTO;
import com.tanvir.easymart.dto.UserDTO;
import com.tanvir.easymart.exceptions.UserNotFoundException;
import com.tanvir.easymart.repository.UserRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

    @Override
    public User verifyUser(LoginDTO loginDTO) {
        var user = userRepository.findByUsername(loginDTO.getUserName())
                .orElseThrow(() -> new UserNotFoundException("User not found" + loginDTO.getUserName()));
        var encrypted = encryptPassword(loginDTO.getPassword());
        if (user.getPassword().equals(encrypted)){
           return user;
        }else {
            throw new UserNotFoundException("Invalid username or password");
        }
    }

    private String encryptPassword(String password) {
        try{
            var digest = MessageDigest.getInstance("SHA-256");
            var bytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(bytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Unable to encrypt password",e);
        }

    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b: bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1){
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
