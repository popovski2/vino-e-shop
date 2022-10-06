package com.graduationproject.vinoeshop.web;

import com.graduationproject.vinoeshop.model.Category;
import com.graduationproject.vinoeshop.model.Wine;
import com.graduationproject.vinoeshop.service.CategoryService;
import com.graduationproject.vinoeshop.service.ManufacturerService;
import com.graduationproject.vinoeshop.service.TypeService;
import com.graduationproject.vinoeshop.service.WineService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/wines")
public class WinesController {

    private final WineService wineService;
    private final CategoryService categoryService;
    private final TypeService typeService;
    private final ManufacturerService manufacturerService;

    public WinesController(WineService wineService, CategoryService categoryService, TypeService typeService, ManufacturerService manufacturerService) {
        this.wineService = wineService;
        this.categoryService = categoryService;
        this.typeService = typeService;
        this.manufacturerService = manufacturerService;
    }


    /**
     *                          *
     *                          *
     *      GET FUNCTIONS       *
     *                          *
     *                          *
     **/

    @GetMapping
    public String getWinesPage(@RequestParam(required = false)String error,  Model model){

        //  if there is some error, put it into the model in order to display it on frontend
        if(error !=null && !error.isEmpty()){
            model.addAttribute("hasError",true);
            model.addAttribute("error",error);
        }

        //  get all wines
        List<Wine> wines = this.wineService.listAllWines();
        model.addAttribute("wines",wines);

        //  get all categories
        List<Category> categories = this.categoryService.listAllCategories();
        model.addAttribute("categories",categories);

        return "wines";
    }

    @GetMapping("/{categoryName}")
    public String getWinesByCategoryName(@PathVariable String categoryName, Model model){

        if(this.categoryService.findByName(categoryName) != null){
            List<Wine> wines = this.wineService.listWinesByCategoryName(categoryName);
            model.addAttribute("winesByCategory",wines);
            return "wines";
        }
        return "redirect:/wines?error=WinesNotFound";
    }




    /**
     *                          *
     *                          *
     *      POST FUNCTIONS      *
     *                          *
     *                          *
     **/






}
