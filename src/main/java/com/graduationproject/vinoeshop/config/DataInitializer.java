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


     // This annotation is commented now because it was only needed for the testing phase and running the application for the
    // first time, in order to fill the database with some information
   // @PostConstruct
    public void initData(){


        /** CREATE CATEGORIES */
       /* for (int i = 1; i < 6; i++) {
            Category category = new Category("Category" + i);
            this.categoryService.create(category.getName());

        }*/
        this.categoryService.create("White");
        this.categoryService.create("Red");
        this.categoryService.create("Rosé");
        this.categoryService.create("Sparkling");
        this.categoryService.create("Dessert");




        /** CREATE TYPES OF WINE */
       /* for (int i = 1; i < 6; i++) {
            this.typeService.create("Type of wine"+i, "Description for type: " + i, this.categoryService.listAllCategories().get(i-1).getId());
        }*/
        // white wine types
        this.typeService.create("Sauvignon Blanc", "The aromatic and easy-drinking nature of Sauvignon Blanc has seen it become our most popular white wine. While most famous as a refreshing fruit-driven style full of tropical characters, it can also be made with some time in barrel to add complexity."
                , this.categoryService.findByName("White").getId());

        this.typeService.create("Chardonnay", "The world’s most popular white wine, Chardonnay is a versatile white grape variety that originated in the Burgundy wine region in France and is grown widely throughout the world."
                , this.categoryService.findByName("White").getId());

        this.typeService.create("Riesling", "Riesling is a white grape variety that originated in the Rhine region. Riesling is an aromatic grape variety displaying flowery, almost perfumed, aromas as well as high acidity. It is used to make dry, semi-sweet, sweet, and sparkling white wines."
                , this.categoryService.findByName("White").getId());

        // red wine types
        this.typeService.create("Pinot Noir", "Pinot Noir is a red-wine grape variety of the species Vitis vinifera. The name may also refer to wines created predominantly from pinot noir grapes. The name is derived from the French words for pine and black."
                , this.categoryService.findByName("Red").getId());

        this.typeService.create("Merlot", "Merlot is a dark blue–colored wine grape variety, that is used as both a blending grape and for varietal wines. The name Merlot is thought to be a diminutive of merle, the French name for the blackbird, probably a reference to the color of the grape."
                , this.categoryService.findByName("Red").getId());

        this.typeService.create("Cabernet Sauvignon", "Cabernet Sauvignon is one of the world's most widely recognized red wine grape varieties. It is grown in nearly every major wine producing country among a diverse spectrum of climates from Australia, Canada to Lebanon's Beqaa Valley."
                , this.categoryService.findByName("Red").getId());

        // rose wine types
        this.typeService.create("Grenache Rosé", "Grenache rosés (especially those produced in Provence) are among the driest in the world. They typically have bright flavors of grapefruit, berries, watermelon, cucumber, and herbs, as well as distinctly floral qualities."
                , this.categoryService.findByName("Rosé").getId());

        this.typeService.create("Cabernet Sauvignon Rosé", "Its bright fruit hints of pomegranate, citrus and cranberry.This versatile wine is ideal whether you're serving watermelon & feta for starters, grilling a plank of salmon, or cooling down on a hot summer night."
                , this.categoryService.findByName("Rosé").getId());

        // sparkling wine types
        this.typeService.create("Champagne", "Champagne is a sparkling wine originated and produced in the Champagne wine region of France under the rules of the appellation, that demand specific vineyard practices, sourcing of grapes exclusively from designated places within it."
                , this.categoryService.findByName("Sparkling").getId());

        this.typeService.create("Prosecco", "Prosecco is a light-bodied, vibrant, fresh, highly aromatic and crisp wine. It has a medium to high amount of acidity and large, frothy bubbles."
                , this.categoryService.findByName("Sparkling").getId());

        this.typeService.create("Cava", "Cava is a light to medium bodied, typically dry, sparkling wine – with zesty citrus flavours, a distinct minerality and racy acidity. Cavas aged longer on the lees often develop a beautiful baked apple note."
                , this.categoryService.findByName("Sparkling").getId());

        // dessert wine types
        this.typeService.create("Eiswein", "Eiswein or Ice wine is a type of dessert wine produced from grapes that have been frozen while still on the vine. The sugars and other dissolved solids do not freeze, but the water does."
                , this.categoryService.findByName("Dessert").getId());





        /** CREATE MANUFACTURERS */
     /*   for (int i = 1; i < 11; i++) {
            Manufacturer manufacturer = new Manufacturer("Manufacturer " + i, "Address" + i);
            this.manufacturerService.create(manufacturer.getName(),manufacturer.getAddress());
        }*/

        this.manufacturerService.create("Château Lafite Rothschild","Bordeaux, France");
        this.manufacturerService.create("Penfolds","Australia");
        this.manufacturerService.create("Chateau Petrus Bordeaux","Bordeaux, France");
        this.manufacturerService.create("Louis Roederer","Champagne, France");
        this.manufacturerService.create("Domaine de la Romanée-Conti","Burgundy, France");
        this.manufacturerService.create("Moët & Chandon","Champagne, France");



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
        urls.add("https://data.callmewine.com/imgprodotto/chianti-nipozzano-frescobaldi-2019_44296_list.jpg");
        urls.add("https://data.callmewine.com/imgprodotto/pecorino-donna-orgilla-fiorano-2021_44866_list.jpg");
        urls.add("https://data.callmewine.com/imgprodotto/rosato-aka-produttori-di-manduria-2021_39042_list.jpg");
        urls.add("https://data.callmewine.com/imgprodotto/champagne-brut-les-3-muses-hoerter_44387_list.jpg");


        /** FINALLY, CREATING THE WINES **/
    /*    for (int i = 1; i < 11; i++) {
            this.wineService.create("Wine " + i,
                    20.9 * i,
                    i*i,
                    urls.get(i),
                    i % 5L + 1,
                    i % 5L + 1,
                    i % 5L + 1);
        }*/

        // white wines
        //Sauvignon Blanc (1), Chardonnay(2), Riesling(3)

        this.wineService.create("Les Légendes Blanc",129.99,1000,urls.get(4),1L,1L,1L);
        this.wineService.create("Pinot Noir Santa Lucia",99.99,1000,urls.get(1),2L,1L,4L);


        this.wineService.create("Reserve Bin Chardonnay 2020",49.99,1000,urls.get(2),1L,2L,2L);
        this.wineService.create("Alta Mora 1983",49.99,1000,urls.get(5),2L,3L,5L);
        this.wineService.create("Grenache Rosé 2015",99.99,1000,urls.get(3),3L,6L,7L);

        this.wineService.create("Bin 51 Eden Valley Riesling 2019",89.99,1000,urls.get(0),1L,2L,3L);

        //red wines
        // Pinot noir(4), Merlot(5), Cabernet sauvignon (6),
         this.wineService.create("Les Légendes Bordeaux",59.99,1000,urls.get(8),2L,1L,6L);
        this.wineService.create("Brut Paradis",129.99,1000,urls.get(6),4L,6L,10L);

        // rose wines
        this.wineService.create("Cabernet Sauvignon Rosé 1999",49.99,1000,urls.get(13),3L,5L,8L);


        // sparkling wines
        this.wineService.create("Champagne Brut 2012",69.99,1000,urls.get(14),4L,6L,9L);


        // dessert wines
        this.wineService.create("Vintage Port Dessert 2020",45.99,1000,urls.get(11),5L,4L,12L);



        /** ADDING USERS **/
        //  ADMIN
        userService.saveUser(new User("vinoshopgraduationowner@gmail.com","Administrator","Admin","1234",Role.ROLE_ADMIN));
        // CUSTOMERS
        userService.saveUser(new User("ppopovski53@gmail.com","Petar","Popovski","1234", Role.ROLE_CUSTOMER));
        userService.saveUser(new User("customerfortestingpurposes@gmail.com","John","Smith","1234",Role.ROLE_CUSTOMER));





    }


}
