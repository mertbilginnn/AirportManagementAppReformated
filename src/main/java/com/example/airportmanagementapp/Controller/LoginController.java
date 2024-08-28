package com.example.airportmanagementapp.Controller;


import com.example.airportmanagementapp.Entity.User;
import com.example.airportmanagementapp.Service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {


    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/showMyLoginPage")
    public String showMyLoginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        userService.addUser(user); // Kullanıcıyı veritabanına kaydet
        return "redirect:/showMyLoginPage"; // Kayıttan sonra login sayfasına yönlendir
    }


}
