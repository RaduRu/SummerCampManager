package DomainModel;

public class SiblingFee implements FeeStrategy{
    final double DISCOUNT = 0.1;
    @Override
    public double getFee(Subscription subscription) {
        return WEEKLY_FEE - (WEEKLY_FEE * DISCOUNT);
    }
}
