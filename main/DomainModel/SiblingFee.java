package DomainModel;

public class SiblingFee implements FeeStrategy{
    @Override
    public double getFee(Subscription subscription) {
        return 0;
    }
}
