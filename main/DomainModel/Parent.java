package DomainModel;

public class Parent {

    private String idcode;
    private String name;
    private String surname;
    private String phonenumber;

    private Parent(String idcode, String name, String surname, String phonenumber){
        this.idcode=idcode;
        this.name= name;
        this.surname=surname;
        this.phonenumber=phonenumber;
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

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
}
