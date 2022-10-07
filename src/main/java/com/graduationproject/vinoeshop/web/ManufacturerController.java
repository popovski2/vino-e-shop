package com.graduationproject.vinoeshop.web;

import com.graduationproject.vinoeshop.model.Manufacturer;
import com.graduationproject.vinoeshop.model.Wine;
import com.graduationproject.vinoeshop.service.ManufacturerService;
import com.graduationproject.vinoeshop.service.WineService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/manufacturers")
public class ManufacturerController {

    private final ManufacturerService manufacturerService;
    private final WineService wineService;

    public ManufacturerController(ManufacturerService manufacturerService, WineService wineService) {
        this.manufacturerService = manufacturerService;
        this.wineService = wineService;
    }

    /**
     *                      *
     *          GET         *
     *                      *
     *                      **/

    @GetMapping
    public String getManufacturersPage(@RequestParam(required = false)String error, Model model){
        if(error!= null && !error.isEmpty()){
            model.addAttribute("hasError",error);
            model.addAttribute("error",error);
        }
        List<Manufacturer> manufacturers = this.manufacturerService.listAllManufacturers();
        model.addAttribute("manufacturers",manufacturers);
        return "manufacturers";
    }

    /** GET THE PAGE FOR ADDING THE MANUFACTURER**/
    @GetMapping("/add-form")
    public String addManufacturerPage(Model model){
        return "add-manufacturer";
    }

    /** GET THE PAGE FOR EDITING THE MANUFACTURER**/
    @GetMapping("/edit-form/{manufacturerId}")
    public String editManufacturerPage(@PathVariable Long manufacturerId, Model model){

        Manufacturer manufacturer = this.manufacturerService.findById(manufacturerId).get();
        model.addAttribute("manufacturer",manufacturer);
        // i get them here in order to show them on the page
        List<Wine> winesFromManufacturer = this.wineService.listWinesByManufacturer(manufacturerId);
        model.addAttribute("wines",winesFromManufacturer);
        return "edit-manufacturer";

    }

    /**
     *                      *
     *          POST        *
     *                      *
     *                      **/


    @PostMapping("/add")
    public String saveManufacturer(@RequestParam(required = false) Long id,@RequestParam String name, @RequestParam String address){

        if(id!=null){
            this.manufacturerService.update(id,name,address);
        }
        this.manufacturerService.create(name,address);
        return "redirect:/manufacturers";

    }




    /**
     *                      *
     *         DELETE       *
     *                      *
     *                      **/

    @DeleteMapping("/delete/{manufacturerId}")
    public String deleteManufacturer(@PathVariable Long manufacturerId){
        this.manufacturerService.delete(manufacturerId);
        return "redirect:/manufacturers";
    }



}
