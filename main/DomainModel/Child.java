package main.DomainModel;

public class Child {

    private String idcode;
    private String name;
    private String surname;
    private int age;
    private String details;

    private Subscription subscription;

    public Child(String idcode, String name, String surname, int age, String details){
        this.idcode=idcode;
        this.name=name;
        this.surname=surname;
        this.age=age;
        this.details=details;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public Subscription getSubscription() {
        return subscription;
    }
}

