package com.graduationproject.vinoeshop.web;

import com.graduationproject.vinoeshop.model.Order;
import com.graduationproject.vinoeshop.model.User;
import com.graduationproject.vinoeshop.service.OrderService;
import com.graduationproject.vinoeshop.service.ShoppingCartService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrdersController {



    private final ShoppingCartService shoppingCartService;
    private final OrderService orderService;


    public OrdersController(ShoppingCartService shoppingCartService, OrderService orderService) {
        this.shoppingCartService = shoppingCartService;
        this.orderService = orderService;
    }

    /**
     *                      *
     *          GET         *
     *                      *
     *                      **/

    @GetMapping("/admin")
    public String getAdminOrders(Model model){
        List<Order> orders  = this.orderService.getAllOrdersAdmin();
        model.addAttribute("orders", orders);
        return "admin-orders";
    }

    @GetMapping("/customer")
    public String getCustomerOrders(HttpServletRequest request, Authentication authentication, Model model){

        User user = (User) authentication.getPrincipal();
        List<Order> customerOrders = this.orderService.getAllOrdersCustomer(user);

        model.addAttribute("customerOrders", customerOrders);
        return "customer-orders";
    }

}
