package com.graduationproject.vinoeshop.web;


import com.graduationproject.vinoeshop.model.Manufacturer;
import com.graduationproject.vinoeshop.model.User;
import com.graduationproject.vinoeshop.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UsersController {

    private final UserService userService;


    public UsersController(UserService userService) {
        this.userService = userService;
    }


    /**
     *                      *
     *          GET         *
     *                      *
     *                      **/
    @GetMapping
    public String getUsersPage(@RequestParam(required = false)String error, Model model){
        if(error!= null && !error.isEmpty()){
            model.addAttribute("hasError",error);
            model.addAttribute("error",error);
        }
        List<User> users = this.userService.getAllUsers();
        model.addAttribute("users",users);
        return "users";
    }

    @GetMapping("/add-form")
    public String addAdministratorPage(){
        return "add-admin";
    }


    /**
     *                      *
     *          POST        *
     *                      *
     *                      **/
    @PostMapping("/add")
    public String addAdministrator(@RequestParam String email,
                                   @RequestParam String password,
                                   @RequestParam String name,
                                   @RequestParam String surname){


        this.userService.registerAsAdministrator(email, password, name, surname);
        return "redirect:/users";

    }



    /**
     *                      *
     *         DELETE       *
     *                      *
     *                      **/

    @DeleteMapping("/delete/{userId}")
    public String deleteUser(@PathVariable Long userId){
        this.userService.delete(userId);
        return "redirect:/users";
    }
}
