package app;

import domain.Category;
import domain.Item;
import domain.Site;
import service.ItemService;
import java.io.IOException;
public class Application {


    public static void main(String[] args) throws IOException {
        ItemService service = new ItemService();
        service.makeConnection();


        Site site = new Site();
        site.setName("Argentina");
        site = service.insertSite(site);


        System.out.println("Getting site...");
        Site siteFromDB = service.getSiteByCode(site.getCode());
        System.out.println("Site from DB  --> " + siteFromDB);


        System.out.println("insertando categoria");
        Category category = new Category();
        category.setSiteID("MLA");
        category = service.insertCategory(category);


        System.out.println("Getting category...");
        Category categoryFromDB = service.getCategoryByID(category.getId());
        System.out.println("Category from DB  --> " + categoryFromDB);

        System.out.printf("Insertando item");
        Item item   = new Item();
        item.setName("MacBook Air 13");
        item= service.insertItem(item);

        System.out.println("Getting item...");
        Item itemFromDB = service.getItemById(item.getId());
        System.out.println("Site from DB  --> " + item);

        System.out.println("Changing name to item...");
        item.setName("Iphon 7s ");
        service.updateItemById(item.getId(), item);
        System.out.println("Item updated  --> " + item);

        System.out.println("Deleting Shubham...");
        service.deleteItemById(itemFromDB.getId());

        service.closeConnection();
    }
}
