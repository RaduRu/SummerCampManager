package main.DomainModel;

public class Activity {
    private String date;
    private String time;
    private String description;
    private TypeOfActivity type;

    public Activity(String date, String time, String description, TypeOfActivity type) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }

    public TypeOfActivity getType() {
        return type;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(TypeOfActivity type) {
        this.type = type;
    }






}
