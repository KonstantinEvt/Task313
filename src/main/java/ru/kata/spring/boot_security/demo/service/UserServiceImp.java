package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.Dto.DtoUser;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.security.UserSecurityDetails;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public void addUser(DtoUser dtoUser) {
        if (userDao.findUserByUsername(dtoUser.getUsername()) == null) {
            userDao.addUser(convertDtoToEntity(dtoUser));
        } else {
            throw new IllegalArgumentException("This user is already exist");
        }
    }

    private User convertDtoToEntity(DtoUser dtoUser) {
        User userNew = new User();
        userNew.setFirstName(dtoUser.getFirstName());
        userNew.setLastName(dtoUser.getLastName());
        userNew.setEmail(dtoUser.getEmail());
        userNew.setUsername(dtoUser.getUsername());
        Set<Role> userRole = new HashSet<>();
        for (String role : dtoUser.getRoles()) {
            userRole.add(roleDao.getRole(Long.parseLong(role)));
        }
        userNew.setRoles(userRole);
        userNew.setPassword(encoder.encode(dtoUser.getPassword()));
        return userNew;
    }

    @Transactional(readOnly = true)
    @Override
    public User getUser(Long id) {
        return userDao.getUser(id);
    }

    @Transactional
    @Override
    public void removeUser(Long id) {
        userDao.removeUser(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> getListUsers() {
        return userDao.getListUsers();
    }

    @Transactional
    @Override
    public void updateUser(DtoUser dtoUser, Long id) {
        userDao.updateUser(convertDtoToEntity(dtoUser), id);
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (userDao.findUserByUsername(username) == null) {
            throw new UsernameNotFoundException(String.format("User %s not found", username));
        }
        UserSecurityDetails userSecurityDetails = new UserSecurityDetails(userDao.findUserByUsername(username));
        return new org.springframework.security.core.userdetails.User(userSecurityDetails.getUsername(), userSecurityDetails.getPassword(), userSecurityDetails.getAuthorities());
    }

    @Transactional(readOnly = true)
    public User findUserByUsername(String username) {
        return userDao.findUserByUsername(username);
    }
}
