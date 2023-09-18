package DomainModel;

import java.util.ArrayList;

public class Parent {

    private String idcode;
    private String email;
    private String name;
    private String surname;
    private String cellphone ;
    private ArrayList<Child> children;

    public Parent(String idcode, String email, String name, String surname, String cellphone){
        this.idcode=idcode;
        this.email=email;
        this.name= name;
        this.surname=surname;
        this.cellphone=cellphone;
    }

    public String getIdcode() {
        return idcode;
    }

    public void setIdcode(String idcode) {
        this.idcode = idcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Child> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Child> children) {
        this.children = children;
    }
}
