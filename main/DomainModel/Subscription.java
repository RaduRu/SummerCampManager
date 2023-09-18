package DomainModel;

public class Subscription {
    private int Weeksnum;
    private Child child;
    private FeeStrategy feeStrategy;
    private boolean isPaid;

    public Subscription(int Weeksnum, Child child, FeeStrategy feeStrategy, boolean isPaid) {
        this.Weeksnum = Weeksnum;
        this.child = child;
        this.feeStrategy = feeStrategy;
        this.isPaid = isPaid;
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

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public FeeStrategy getFeeStrategy() {
        return feeStrategy;
    }

    public void setFeeStrategy(FeeStrategy feeStrategy) {
        this.feeStrategy = feeStrategy;
    }
}
