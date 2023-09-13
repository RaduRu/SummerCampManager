package DomainModel;

import java.util.ArrayList;

public class Educator {
    private String email;
    private String name;
    private String surname;
    private ArrayList<Workshift> workshifts;

    public  Educator(String email, String name, String surname){
        this.email=email;
        this.name= name;
        this.surname=surname;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public ArrayList<Workshift> getWorkshifts() {
        return workshifts;
    }

    public void setWorkshifts(ArrayList<Workshift> workshifts) {
        this.workshifts = workshifts;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname=surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
