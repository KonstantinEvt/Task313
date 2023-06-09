package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.DtoUser;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("")
    public String getAllUsers(ModelMap model) {
        model.addAttribute("usersList", userService.getListUsers());
        return "userData";
    }

    @GetMapping("/add")
    public String getViewToAddUser(@ModelAttribute("useradd") User user, ModelMap model) {
        model.addAttribute("userRoles", roleService.getListRoles());
        return "userAdd";
    }

    @PostMapping("/persist")
    public String addUser(DtoUser dtoUser) {
        userService.addUser(dtoUser);
        return "redirect:/admin";
    }

    @GetMapping("/update/{id}")
    public String getViewToUpdateUser(ModelMap model, @PathVariable("id") Long id) {
        model.addAttribute("updateUser", userService.getUser(id));
        model.addAttribute("userRoles", roleService.getListRoles());
        return "userUpdate";
    }

    @PatchMapping("/{id}")
    public String updateUser(DtoUser dtoUser, @PathVariable("id") Long id) {
        userService.updateUser(dtoUser, id);
        return "redirect:/admin";
    }

    @DeleteMapping("/remove/{id}")
    public String removeUser(@PathVariable("id") Long id) {
        userService.removeUser(id);
        return "redirect:/admin";
    }
}
