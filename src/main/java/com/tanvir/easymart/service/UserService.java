package com.tanvir.easymart.service; // business layer
import com.tanvir.easymart.dto.UserDTO;
public interface UserService {
    void saveUser(UserDTO userDTO);
    boolean inNotUniqueUserName(UserDTO user);
}
