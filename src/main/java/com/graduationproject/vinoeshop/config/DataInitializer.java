package com.graduationproject.vinoeshop.config;

import com.graduationproject.vinoeshop.model.Category;
import com.graduationproject.vinoeshop.model.Manufacturer;
import com.graduationproject.vinoeshop.model.User;
import com.graduationproject.vinoeshop.model.enumerations.Role;
import com.graduationproject.vinoeshop.service.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataInitializer {

    public static final String ADMIN = "admin";

    //private final UserService userService;

    private final CategoryService categoryService;
    private final WineService wineService;
    private final ManufacturerService manufacturerService;
    private final TypeService typeService;
    private final UserService userService;

    public DataInitializer(CategoryService categoryService, WineService wineService, ManufacturerService manufacturerService, TypeService typeService, UserService userService) {
        this.categoryService = categoryService;
        this.wineService = wineService;
        this.manufacturerService = manufacturerService;
        this.typeService = typeService;
        this.userService = userService;
    }


    @PostConstruct
    public void initData(){


        /** CREATE CATEGORIES */
        for (int i = 1; i < 6; i++) {
            Category category = new Category("Category" + i);
            this.categoryService.create(category.getName());
        }


        /** CREATE TYPES OF WINE */
        for (int i = 1; i < 6; i++) {
            this.typeService.create("Type of wine"+i, "Description for type: " + i, this.categoryService.listAllCategories().get(i-1).getId());
        }

        /** CREATE MANUFACTURERS */
        for (int i = 1; i < 11; i++) {
            Manufacturer manufacturer = new Manufacturer("Manufacturer " + i, "Address" + i);
            this.manufacturerService.create(manufacturer.getName(),manufacturer.getAddress());
        }


        /** IMAGE URLS**/
        List<String> urls = new ArrayList<>();
        urls.add("https://8wines.com/media/catalog/product/cache/c7c042d8294616e945a4ef11616f811c/m/o/montelena_1_1.png");
        urls.add("https://8wines.com/media/catalog/product/cache/c7c042d8294616e945a4ef11616f811c/p/a/paradigm-cabernet-sauvignon-2016.png");
        urls.add("https://data.callmewine.com/imgprodotto/sancerre-domaine-vacheron-2020_36670.jpg");
        urls.add("https://8wines.com/media/catalog/product/cache/c7c042d8294616e945a4ef11616f811c/c/h/chateau-leoube-sparkling-de-leoube-rose.png");
        urls.add("https://data.callmewine.com/imgprodotto/etna-bianco-le-sabbie-delletna-firriato-2021_40784.jpg");
        urls.add("https://data.callmewine.com/imgprodotto/etna-bianco-alta-mora-cusumano-2020_38372.jpg");
        urls.add("https://data.callmewine.com/imgprodotto/champagne-brut-grande-reserve-potel-prieux_22511.jpg");
        urls.add("https://data.callmewine.com/imgprodotto/champagne-brut-r-ruinart_30255.jpg");
        urls.add("https://data.callmewine.com/imgprodotto/due-rosso-giannitessari-2019_36038.jpg");
        urls.add("https://data.callmewine.com/imgprodotto/achille-bindi-sergardi-2020_36331.jpg");
        urls.add("https://data.callmewine.com/imgprodotto/sangiovese-di-romagna-lona-bona-trer%C3%A8-2021_39226.jpg");


        /** FINALLY, CREATING THE WINES **/
        for (int i = 1; i < 11; i++) {
            this.wineService.create("Wine " + i,
                    20.9 * i,
                    i*i,
                    urls.get(i),
                    i % 5L + 1,
                    i % 5L + 1,
                    i % 5L + 1);
        }


        userService.saveUser(new User("popovski2","1234","petar","popovski", Role.ROLE_CUSTOMER));
        userService.saveUser(new User("trajko@gmail.com","1234","trajko","trajkovski",Role.ROLE_ADMIN));
        userService.saveUser(new User("angela.madjar","1234","angela","madjar",Role.ROLE_CUSTOMER));
        userService.saveUser(new User("manufacturer@m.com","1234","angela","1234",Role.ROLE_MANUFACTURER));
        userService.saveUser(new User("ppopovski53@gmail.com","1234","angela","1234",Role.ROLE_ADMIN));





    }


}
