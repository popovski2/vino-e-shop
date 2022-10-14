package com.graduationproject.vinoeshop.web;

import com.graduationproject.vinoeshop.model.ChargeRequest;
import com.graduationproject.vinoeshop.model.ShoppingCart;
import com.graduationproject.vinoeshop.model.User;
import com.graduationproject.vinoeshop.model.Wine;
import com.graduationproject.vinoeshop.service.OrderService;
import com.graduationproject.vinoeshop.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/shopping-cart")
public class ShoppingCartController {

    @Value("${STRIPE_PUBLIC_KEY}")
    private String stripePublicKey;


    private final ShoppingCartService shoppingCartService;
    private final OrderService orderService;


    public ShoppingCartController(ShoppingCartService shoppingCartService, OrderService orderService) {
        this.shoppingCartService = shoppingCartService;
        this.orderService = orderService;
    }

    /**
     *                      *
     *          GET         *
     *                      *
     *                      **/

    @GetMapping
    public String getShoppingCartPage(@RequestParam(required = false)String error,
                                      HttpServletRequest request,
                                      Model model){

        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        String username = request.getRemoteUser();
        ShoppingCart shoppingCart = this.shoppingCartService.getActiveShoppingCart(username);
        model.addAttribute("wines",this.shoppingCartService.listAllWinesInShoppingCart(shoppingCart.getId()));
        model.addAttribute("bodyContent","shopping-cart");
        model.addAttribute("totalPrice",this.shoppingCartService.computeTotalPrice(shoppingCart.getId()));


        /** for stripe payment **/
        model.addAttribute("stripePublicKey", stripePublicKey);
        model.addAttribute("currency", ChargeRequest.Currency.USD);

        return "shopping-cart";
    }


    /**
     *                      *
     *          POST        *
     *                      *
     *                      **/



    @PostMapping("/add-wine/{id}")
    public String addProductToShoppingCart(@PathVariable Long id, HttpServletRequest request, Authentication authentication, Model model){
        try{
            User user = (User) authentication.getPrincipal();
            this.shoppingCartService.addWinesToShoppingCart(user.getUsername(), id);
            return "redirect:/shopping-cart";
        } catch (RuntimeException exception){
            return "redirect:/shopping-cart?error="+ exception.getMessage();
        }
    }

    @PostMapping("/increaseQuantity/{id}")
    public String increaseQuantity(@PathVariable Long id, Authentication authentication, Model model){

        try{
            User user = (User) authentication.getPrincipal();
            this.shoppingCartService.increaseQuantity(user.getUsername(), id);
            return "redirect:/shopping-cart";
        } catch (RuntimeException exception){
            return "redirect:/shopping-cart?error="+ exception.getMessage();
        }
    }

    @PostMapping("/decreaseQuantity/{id}")
    public String decreaseQuantity(@PathVariable Long id, Authentication authentication, Model model){

        try{
            User user = (User) authentication.getPrincipal();
            this.shoppingCartService.decreaseQuantity(user.getUsername(), id);
            return "redirect:/shopping-cart";
        } catch (RuntimeException exception){
            return "redirect:/shopping-cart?error="+ exception.getMessage();
        }
    }

    /**
     *                      *
     *         DELETE       *
     *                      *
     *                      **/
    @DeleteMapping("/delete/{id}")
    public String deleteWineFromShoppingCart(@PathVariable Long id, Authentication authentication, Model model){
        try {
            User user = (User) authentication.getPrincipal();
            this.shoppingCartService.deleteFromShoppingCart(user.getUsername(), id);
            return "redirect:/shopping-cart";
        } catch (RuntimeException exception){
            return "redirect:/shopping-cart?error="+ exception.getMessage();
        }
    }
    @DeleteMapping("/emptyCart")
    public String emptyCart(Authentication authentication, Model model){
        try{
            User user = (User) authentication.getPrincipal();
            this.shoppingCartService.emptyShoppingCart(user.getUsername());
            return "redirect:/shopping-cart";
        }catch (RuntimeException exception){
            return "redirect:/shopping-cart?error="+ exception.getMessage();
        }
    }
}
