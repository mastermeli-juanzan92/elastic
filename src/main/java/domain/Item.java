package domain;


public class Item {

    private String id;
    private String name;
    private String categoryID;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    @Override
    public String toString() {
        return "id: " + id +
                "name: " + name +
                "categoryID: " + categoryID.toString();
    }
}
