package com.graduationproject.vinoeshop.web;

import com.graduationproject.vinoeshop.filter.CustomUsernamePasswordAuthenticationProvider;
import com.graduationproject.vinoeshop.model.exceptions.InvalidUserArgumentsException;
import com.graduationproject.vinoeshop.model.exceptions.PasswordsDoNotMatchException;
import com.graduationproject.vinoeshop.service.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final UserService userService;
    private final CustomUsernamePasswordAuthenticationProvider authenticationProvider;

    public RegisterController(UserService userService, CustomUsernamePasswordAuthenticationProvider authenticationProvider) {
        this.userService = userService;
        this.authenticationProvider = authenticationProvider;
    }

    /**
     *                          *
     *                          *
     *      GET FUNCTIONS       *
     *                          *
     *                          *
     **/

    @GetMapping
    public String getRegisterPage(@RequestParam(required = false) String error, Model model){
        if(error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        model.addAttribute("bodyContent","register");
        return "register";
    }

    /**
     *                          *
     *                          *
     *      POST FUNCTIONS      *
     *                          *
     *                          *
     **/
    @PostMapping
    public String register(HttpServletRequest request,
                           @RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String name,
                           @RequestParam String surname,
                           @RequestParam String address){
        try {
            this.userService.register(username,password,name,surname,address);
            // auto login after registration

            // TUKA MORA DA SE NAPRAVI REGISTER, NE MOZE PREKU USER SERVICE PA POSLE VO SESIJA DA GO CUVAME!!!
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password);
            System.out.println(" authenticationToken credentials: "+authenticationToken.getCredentials() +"authenticaionToken getDetails: "+authenticationToken.getDetails());
            authenticationToken.setDetails(new WebAuthenticationDetails(request));
            System.out.println("after authenticationToken.setDetails(newWebAuthenticationDetails(request)" + authenticationToken.getDetails());
            Authentication authentication = this.authenticationProvider.authenticate(authenticationToken);
            System.out.println("Authentication getDetails after authenticate(authenticationToken)" + authentication.getDetails());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return "redirect:/home";
        } catch (InvalidUserArgumentsException | PasswordsDoNotMatchException exception){
            return "redirect:/register?error=" + exception.getMessage();
        }
    }
}
