package com.graduationproject.vinoeshop.web;

import com.graduationproject.vinoeshop.model.Category;
import com.graduationproject.vinoeshop.model.Wine;
import com.graduationproject.vinoeshop.service.WineService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = {"/home","/"})
public class HomeController {

    private final WineService wineService;

    public HomeController(WineService wineService) {
        this.wineService = wineService;
    }

    /**
     *                          *
     *                          *
     *      GET FUNCTIONS       *
     *                          *
     *                          *
     **/


    @GetMapping
    public String getHomePage(@RequestParam(required = false)String error, Model model){

        //  if there is some error, put it into the model in order to display it on frontend
        if(error !=null && !error.isEmpty()){
            model.addAttribute("hasError",true);
            model.addAttribute("error",error);
        }

        //  get all wines
        List<Wine> wines = this.wineService.listAllWines().stream().limit(4).collect(Collectors.toList());
        model.addAttribute("wines",wines);

        return "home";
    }

}
