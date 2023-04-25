package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.UserCrud;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImp implements UserService {

    private UserDao userDao;
    private RoleDao roleDao;
    private BCryptPasswordEncoder encoder;

    public UserServiceImp(UserDao userDao, RoleDao roleDao, BCryptPasswordEncoder encoder) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.encoder = encoder;
    }

    @Transactional
    @Override
    public void addUser(UserCrud userCrud) {
        UserCrud userNew = userDao.findUserByUsername(userCrud.getUsername());
        if (userNew == null) {
            userCrud.setPassword(encoder.encode(userCrud.getPassword()));
            userCrud.setRoles(this.getCheckedRoles(userCrud));
            userDao.addUser(userCrud);
        } else {
            throw new IllegalArgumentException("This user is already exist");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public UserCrud getUser(Long id) {
        return userDao.getUser(id);
    }

    @Transactional
    @Override
    public void removeUser(Long id) {
        userDao.removeUser(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserCrud> getListUsers() {
        return userDao.getListUsers();
    }

    @Transactional
    @Override
    public void updateUser(UserCrud userCrud, Long id) {
        userCrud.setRoles(this.getCheckedRoles(userCrud));
        userDao.updateUser(userCrud, id);
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserCrud userCrud = userDao.findUserByUsername(username);
        if (userCrud == null) {
            throw new UsernameNotFoundException(String.format("User %s not found", username));
        }
        return new User(userCrud.getUsername(), userCrud.getPassword(), userCrud.getAuthorities());
    }

    @Transactional(readOnly = true)
    public UserCrud findUserByUsername(String username) {
        return userDao.findUserByUsername(username);
    }

    @Transactional(readOnly = true)
    public List<Role> getCheckedRoles(UserCrud userCrud) {
        List<Role> userRole = new ArrayList<>();
        for (Role role : userCrud.getRoles()) {
            userRole.add(roleDao.getRoleByName(role.getRole()));
        }
        if (userRole.isEmpty()) {
            userRole.add(roleDao.getRoleByName("ROLE_USER"));
        }
        return userRole;
    }
}
