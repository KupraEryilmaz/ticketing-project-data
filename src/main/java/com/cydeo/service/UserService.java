package com.cydeo.service;

import com.cydeo.dto.UserDTO;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

public interface UserService {

    List<UserDTO> listAllUsers();
    UserDTO findByUserName(String username);
    void save(UserDTO user);
    void deleteByUserName(String username);
    UserDTO update(UserDTO user);//security kisminda return type a ihtiyacimiz oldugu icin void yerine UserDTO kullandik
    void delete(String username);
    List<UserDTO> listAllByRole(String role);
}
