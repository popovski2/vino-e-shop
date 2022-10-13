package com.graduationproject.vinoeshop.service.impl;

import com.graduationproject.vinoeshop.model.*;
import com.graduationproject.vinoeshop.model.exceptions.InvalidShoppingCartIdException;
import com.graduationproject.vinoeshop.model.exceptions.InvalidUserIdException;
import com.graduationproject.vinoeshop.repository.OrderRepository;
import com.graduationproject.vinoeshop.repository.ShoppingCartRepository;
import com.graduationproject.vinoeshop.repository.UserRepository;
import com.graduationproject.vinoeshop.repository.WineRepository;
import com.graduationproject.vinoeshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;


    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final WineRepository wineRepository;
    private final ShoppingCartRepository shoppingCartRepository;

    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, WineRepository wineRepository, ShoppingCartRepository shoppingCartRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.wineRepository = wineRepository;
        this.shoppingCartRepository = shoppingCartRepository;
    }


    @Override
    public void stripeCheckout() {

    }

    @Override
    public void sendMailToAdmin(ShoppingCart shoppingCart) {
        EmailDto emailDto = new EmailDto();
        emailDto.setDate(shoppingCart.getDateCreated());
        emailDto.setBuyerEmail(shoppingCart.getUser().getUsername());
        emailDto.setBuyerName(shoppingCart.getUser().getName());
        emailDto.setWines(shoppingCart.getWines());
        emailDto.setPrice(shoppingCart.getTotalPrice());


        // Try block to check for exceptions
        try {

            // Creating a simple mail message
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            System.out.println("SimpleMailMessage instantiated");
            String stringBuilder = this.createEmailBody(emailDto);
            System.out.println("Stringbuilder made");

            // Setting up necessary details
            mailMessage.setFrom(sender);
            System.out.println("Mail message set from (sender)");

            mailMessage.setTo(emailDto.getBuyerEmail());
            System.out.println("Mail message set to (receiver)");

            mailMessage.setText(stringBuilder);
            System.out.println("setText(stringBuilder)");

            mailMessage.setSubject("Vino - Purchased wines");
            System.out.println("setSubject to Vino - Purchased wines");

            // Sending the mail
            javaMailSender.send(mailMessage);
            System.out.println("Mail Sent Successfully...");
        }

        // Catch block to handle the exceptions
        catch (Exception e) {
            System.out.println("Error while Sending Mail");
        }

    }

    @Override
    public Order create(Long shoppingCartId) {
        ShoppingCart shoppingCart = this.shoppingCartRepository.findById(shoppingCartId).orElseThrow(() -> new InvalidShoppingCartIdException(shoppingCartId));
        Order order = new Order(shoppingCart);
        return this.orderRepository.save(order);
    }



    /** helper function **/
    public String createEmailBody(EmailDto emailDto){
        List<String> wineNames = emailDto.getWines().stream().map(Wine::getName).collect(Collectors.toList());
        wineNames.stream().forEach(w->w.toString());

        String stringBuilder = "Customer: " + emailDto.getBuyerName() + "\nEmail: " + emailDto.getBuyerEmail()
                + "\nProducts: " + wineNames
                + "\nDate: " + emailDto.getDate()
                + "\nPrice: " + emailDto.getPrice();

        return stringBuilder;
    }
}
