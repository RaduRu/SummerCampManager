package DomainModel;

import java.util.ArrayList;

public class Educator {
    private String name;
    private String surname;
    private ArrayList<Workshift> workshifts;

    private Educator(String name, String surname){
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

    public void addWorkshift(Workshift workshift) {
        this.workshifts.add(workshift);
    }

    public void removeWorkshift(Workshift workshift) {
        this.workshifts.remove(workshift);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname=surname;
    }



}
