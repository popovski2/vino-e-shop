package com.graduationproject.vinoeshop.web;

import com.graduationproject.vinoeshop.model.Category;
import com.graduationproject.vinoeshop.model.Manufacturer;
import com.graduationproject.vinoeshop.model.Type;
import com.graduationproject.vinoeshop.model.Wine;
import com.graduationproject.vinoeshop.service.CategoryService;
import com.graduationproject.vinoeshop.service.ManufacturerService;
import com.graduationproject.vinoeshop.service.TypeService;
import com.graduationproject.vinoeshop.service.WineService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.google.gson.Gson;


import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/edit-form/{id}")
    public String editWinePage(@PathVariable Long id, Model model){
        if(this.wineService.findById(id).isPresent()){

            Wine wine = this.wineService.findById(id).get();
            List<Category> categories = this.categoryService.listAllCategories();
            List<Manufacturer> manufacturers = this.manufacturerService.listAllManufacturers();
            List<Type> types = this.typeService.listAllTypes();
            model.addAttribute("categories", categories);
            model.addAttribute("manufacturers", manufacturers);
            model.addAttribute("types", types);
            model.addAttribute("wine", wine);
            model.addAttribute("bodyContent", "add-wine");
            return "add-wine";
        }
        return "redirect:/wines?error=WineNotFound";
    }

    @GetMapping("add-form")
    public String addWinePage(Model model){
        List<Category> categories = this.categoryService.listAllCategories();
        List<Manufacturer> manufacturers = this.manufacturerService.listAllManufacturers();
        List<Type> types = this.typeService.listAllTypes();
        model.addAttribute("categories", categories);
        model.addAttribute("manufacturers", manufacturers);
        model.addAttribute("types", types);
        model.addAttribute("bodyContent", "add-wine");
        return "add-wine";

    }



    /**
     *                          *
     *                          *
     *      POST FUNCTIONS      *
     *                          *
     *                          *
     **/

    @PostMapping("/add")
    public String saveWine(   @RequestParam(required = false) Long id,
                              @RequestParam String name,
                              @RequestParam Double price,
                              @RequestParam Integer quantity,
                              @RequestParam String imageUrl,
                              @RequestParam Long categoryId,
                              @RequestParam Long manufacturerId,
                              @RequestParam Long typeId) {
        if (id != null) {
            this.wineService.update(id, name, price, quantity, imageUrl, categoryId, manufacturerId, typeId);
        } else {
            this.wineService.create(name, price, quantity,  imageUrl, categoryId, manufacturerId, typeId);
        }
        return "redirect:/wines";
    }



    /**
     *                          *
     *                          *
     *     DELETE FUNCTIONS     *
     *                          *
     *                          *
     **/

    @DeleteMapping("/delete/{id}")
    public String deleteWine(@PathVariable Long id){
        this.wineService.delete(id);
        return "redirect:/wines";
    }



    /** STA JE OVO**/
    @ResponseBody
    @RequestMapping(value = "loadTypesByCategory/{id}", method = RequestMethod.GET)
    public String loadTypesByCategory(@PathVariable("id") Long id) {
        Gson gson = new Gson();
        return gson.toJson(typeService.findAllTypesWithCategoryId(id));
    }

    @GetMapping("/getWinesBy/{name}")
    public String getWinesBy(@PathVariable String name, Model model) {
        if (this.categoryService.findByName(name) != null) {
            List<Wine> winesByCategory = this.wineService.listWinesByCategoryName(name);
            model.addAttribute("winesByCategory", winesByCategory);
            return "redirect:/wines";
        }
        return "redirect:/wines?error=WineNotFound";
    }

    @GetMapping(value = "/types")
    //@ResponseBody
    public List<Type> getTypes(@RequestParam Long id) {
        return this.typeService.listAllTypes().stream()
                .filter(t -> t.getCategory().getId().equals(id))
                .collect(Collectors.toList());
    }



}
