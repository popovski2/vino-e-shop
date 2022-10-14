package com.graduationproject.vinoeshop.web;

import com.graduationproject.vinoeshop.model.ChargeRequest;
import com.graduationproject.vinoeshop.model.ShoppingCart;
import com.graduationproject.vinoeshop.model.User;
import com.graduationproject.vinoeshop.service.OrderService;
import com.graduationproject.vinoeshop.service.ShoppingCartService;
import com.graduationproject.vinoeshop.service.impl.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ChargeController {

    private final ShoppingCartService shoppingCartService;
    private final OrderService orderService;


    @Autowired
    private StripeService paymentsService;

    public ChargeController(ShoppingCartService shoppingCartService, OrderService orderService) {
        this.shoppingCartService = shoppingCartService;
        this.orderService = orderService;
    }

    @PostMapping("/charge")
    public String charge(ChargeRequest chargeRequest, Model model,Authentication authentication)
            throws StripeException {
        chargeRequest.setDescription("Example charge");
        chargeRequest.setCurrency(ChargeRequest.Currency.EUR);
        Charge charge = paymentsService.charge(chargeRequest);
        model.addAttribute("id", charge.getId());
        model.addAttribute("status", charge.getStatus());
        model.addAttribute("chargeId", charge.getId());
        model.addAttribute("balance_transaction", charge.getBalanceTransaction());

        try{
            sendMail(model,authentication);
            return "charge";
        }
        catch (RuntimeException exception){
            return "redirect:/shopping-cart?error="+exception.getMessage();
        }

    }

    @ExceptionHandler(StripeException.class)
    public String handleError(Model model, StripeException ex) {
        model.addAttribute("error", ex.getMessage());
        return "result";
    }



    /** HELPER FUNCTION TO SEND MAIL **/
    public void sendMail(Model model, Authentication authentication){

            User user = (User) authentication.getPrincipal();
            ShoppingCart shoppingCart = this.shoppingCartService.getActiveShoppingCart(user.getUsername());

            //sega ovde napravi eden Order (user,shoppingcart
            this.orderService.create(shoppingCart.getId());
            this.orderService.sendMailToAdmin(shoppingCart);
            this.orderService.sendMailToCustomer(shoppingCart);
            this.shoppingCartService.emptyShoppingCart(user.getUsername());


    }
}
