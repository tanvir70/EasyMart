package com.tanvir.easymart.service; // business layer
import com.tanvir.easymart.domain.User;
import com.tanvir.easymart.dto.LoginDTO;
import com.tanvir.easymart.dto.UserDTO;
public interface UserService {
    void saveUser(UserDTO userDTO);
    boolean inNotUniqueUserName(UserDTO user);
    User verifyUser(LoginDTO loginDTO);
}
