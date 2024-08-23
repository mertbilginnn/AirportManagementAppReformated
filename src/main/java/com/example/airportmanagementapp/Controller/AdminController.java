package com.example.airportmanagementapp.Controller;

import com.example.airportmanagementapp.Service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/manageUsers")
public class AdminController {

    private UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getManageUsersPage(Model model) {
        model.addAttribute("users", userService.findAll());

        return "manageUsers";
    }

    @PostMapping("/addUser")
    public String addUser(@RequestParam String username, @RequestParam String password) {
        userService.saveUser(username, password);

        return "redirect:/manageUsers";
    }

    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam Long id) {
        userService.deleteUser(id);
        return "redirect:/manageUsers";
    }


}
