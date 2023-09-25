package main.DomainModel;

public class OnlyChildFee implements FeeStrategy{
    @Override
    public double getFee(Subscription subscription) {
        return WEEKLY_FEE;
    }
}
