package DomainModel;

public class Subscription {
    private int Weeksnum;
    private Child child;
    private FeeStrategy feeStrategy;
    private boolean isPaid;

    public Subscription(int Weeksnum, Child child, FeeStrategy feeStrategy) {
        this.Weeksnum = Weeksnum;
        this.child = child;
        this.feeStrategy = feeStrategy;
    }

    public double getFee(){
        return feeStrategy.getFee(this);
    }

    public int getWeeksnum() {
        return Weeksnum;
    }

    public void setWeeksnum(int weeksnum) {
        Weeksnum = weeksnum;
    }

    public Child getChild() {
        return child;
    }
}
