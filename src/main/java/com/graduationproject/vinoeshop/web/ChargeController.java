package com.graduationproject.vinoeshop.web;

import com.graduationproject.vinoeshop.model.ChargeRequest;
import com.graduationproject.vinoeshop.model.Order;
import com.graduationproject.vinoeshop.model.ShoppingCart;
import com.graduationproject.vinoeshop.model.User;
import com.graduationproject.vinoeshop.service.OrderService;
import com.graduationproject.vinoeshop.service.ShoppingCartService;
import com.graduationproject.vinoeshop.service.UserService;
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
    private final UserService userService;


    @Autowired
    private StripeService paymentsService;

    public ChargeController(ShoppingCartService shoppingCartService, OrderService orderService, UserService userService) {
        this.shoppingCartService = shoppingCartService;
        this.orderService = orderService;
        this.userService = userService;
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
        System.out.println("======================================================================================== "+ex.getMessage());
        return "result";
    }



    /** HELPER FUNCTION TO SEND MAIL AND CHANGE THE STATUS OF THE SHOPPING CART TO FINISHED
     *
     *  AND ALSO ADD THE ORDER TO USER'S PROPERTY LIST<ORDER> ORDERS
     * **/
    public void sendMail(Model model, Authentication authentication){

            User user = (User) authentication.getPrincipal();
            ShoppingCart shoppingCart = this.shoppingCartService.getActiveShoppingCart(user.getUsername());

            //now make the Order object(shoppingCartId)
            Order order = this.orderService.create(shoppingCart.getId());
            this.userService.updateUsersOrders(shoppingCart.getUser().getId(), order.getId());
            this.orderService.sendMailToAdmin(shoppingCart);
            this.orderService.sendMailToCustomer(shoppingCart);
            this.shoppingCartService.emptyShoppingCart(user.getUsername());


    }
}
