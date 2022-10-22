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
@RequestMapping("/types")
public class TypesController {

    private final TypeService typeService;
    private final CategoryService categoryService;


    public TypesController(TypeService typeService, CategoryService categoryService) {
        this.typeService = typeService;
        this.categoryService = categoryService;
    }


    /**
     *                      *
     *          GET         *
     *                      *
     *                      **/

    @GetMapping
    public String getTypesPage(@RequestParam(required = false) String error, Model model){
        if(error!=null && !error.isEmpty()){
            model.addAttribute("hasError",error);
            model.addAttribute("error",error);

        }
        List<Type> types = this.typeService.listAllTypes();
        model.addAttribute("types",types);
        return "types";
    }

    @GetMapping("add-form")
    public String getAddTypePage(Model model){
        List<Category> categories = this.categoryService.listAllCategories();
        model.addAttribute("categories",categories);
        return "add-type";
    }

    @GetMapping("edit-form/{typeId}")
    public String getEditTypePage(@PathVariable Long typeId, Model model){

        if(this.typeService.findById(typeId).isPresent()) {
            Type type = this.typeService.findById(typeId).get();
            List<Category> categories = this.categoryService.listAllCategories();
            model.addAttribute("type", type);
            model.addAttribute("categories",categories);
            return "add-type";
        }
        return "redirect:/wines?error=TypeNotFound";
    }

    /**
     *                      *
     *          POST        *
     *                      *
     *                      **/
    @PostMapping("add")
    public String saveType(@RequestParam(required = false) Long id,
                           @RequestParam String name,
                           @RequestParam String description,
                           @RequestParam Long categoryId){

        if(id!=null){
            // update
            this.typeService.update(id,name,description,categoryId);
        }
        else{
            //save
            this.typeService.create(name,description,categoryId);
        }
        return "redirect:/types";
    }

    /**
     *                      *
     *         DELETE       *
     *                      *
     *                      **/
    @DeleteMapping("/delete/{typeId}")
    public String deleteType(@PathVariable Long typeId){
        this.typeService.delete(typeId);
        return "redirect:/types";
    }




}
