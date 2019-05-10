package domain;

public class Category {

    private String id;
    private String name;
    private String siteID;

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

    public String getSiteID() {
        return siteID;
    }

    public void setSiteID(String siteID) {
        this.siteID = siteID;
    }

    @Override
    public String toString() {
        return "id: " + id +
                " name: " + name +
                " siteID: " + siteID;
    }
}
