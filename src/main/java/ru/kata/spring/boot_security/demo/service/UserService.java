package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.kata.spring.boot_security.demo.model.UserCrud;

import java.util.List;

public interface UserService extends UserDetailsService {
    void addUser(UserCrud userCrud);

    UserCrud getUser(Long id);

    void removeUser(Long id);

    List<UserCrud> getListUsers();

    void updateUser(UserCrud userCrud, Long id);

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    public UserCrud findUserByUsername(String username);
}
