package com.graduationproject.vinoeshop.web;

import com.graduationproject.vinoeshop.model.Category;
import com.graduationproject.vinoeshop.model.Type;
import com.graduationproject.vinoeshop.service.CategoryService;
import com.graduationproject.vinoeshop.service.TypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoriesController {

    public final CategoryService categoryService;
    public final TypeService typeService;

    public CategoriesController(CategoryService categoryService, TypeService typeService) {
        this.categoryService = categoryService;
        this.typeService = typeService;
    }

    /**
     *                          *
     *                          *
     *      GET FUNCTIONS       *
     *                          *
     *                          *
     **/

    @GetMapping
    public String getCategoriesPage(@RequestParam(required = false) String error, Model model){
        if(error!=null && !error.isEmpty()){
            model.addAttribute("hasError",true);
            model.addAttribute("error",error);
        }
        List<Category> categories = this.categoryService.listAllCategories();
        List<Type> types = this.typeService.listAllTypes();

        //  this is only to show that 1 category can have multiple types
        // for example: White wine has types: Chardonnay, Riesling, etc..
        types.add(new Type("nov", "t", categories.get(1)));

        model.addAttribute("categories", categories);
        model.addAttribute("types", types);
        return "categories";
    }

    @GetMapping("/add-form")
    public String getAddCategoryForm(Model model){

        List<Type> types = this.typeService.listAllTypes();

        model.addAttribute("types",types);

        //  might delete it later
        model.addAttribute("body-content","add-wine");

        return "add-category";
    }





    /**
     *                          *
     *                          *
     *      POST FUNCTIONS      *
     *                          *
     *                          *
     **/

    @PostMapping("/add")
    public String saveCategory(@RequestParam(required = false) Long id, @RequestParam String name){

        if(id == null){
            this.categoryService.create(name);
        }
        else this.categoryService.update(id,name);

        return "redirect:/categories";
    }


    /**
     *                          *
     *                          *
     *     DELETE FUNCTIONS     *
     *                          *
     *                          *
     **/

    @DeleteMapping("/delete/{categoryId}")
    public String deleteCategory(@PathVariable Long categoryId){
        this.categoryService.delete(categoryId);
        return "redirect:/categories";
    }

}
