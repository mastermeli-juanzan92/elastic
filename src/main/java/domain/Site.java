package domain;

public class Site {

    private String code;
    private String name;


    public String getCode() {
        return code;
    }

    public void setCode(String id) {
        this.code = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Code: " + code + " - Name: " + name;
    }
}
