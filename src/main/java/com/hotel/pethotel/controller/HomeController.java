package com.hotel.pethotel.controller;

import com.hotel.pethotel.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private UserService userService;

    @GetMapping("/")
    public String greeting(Model model) {
        model.addAttribute("userRoles", SecurityContextHolder
                .getContext().getAuthentication().getAuthorities().toString());
            model.addAttribute("isAuthenticated",SecurityContextHolder.getContext().getAuthentication() != null &&
                    SecurityContextHolder.getContext().getAuthentication().isAuthenticated());
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @GetMapping("/adminpanel")
    public String adminPanel() {
        return "adminpanel";
    }


    @GetMapping("/clientpanel")
    public String userPanel(Model model) {
        //musimy to rzutowac na obiekt User z UserDetails aby dostac sie do Username
        model.addAttribute("email", ((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        return "clientpanel";
    }




    @GetMapping("/defaultpanel")
    public String defaultPanel() {
        return "defaultpanel";
    }
//    @GetMapping("/admin")
//    public String admin() {
//        return "admin";
//    }
//
//    @GetMapping("/client")
//    public String client() {
//        return "client";
//    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }
}