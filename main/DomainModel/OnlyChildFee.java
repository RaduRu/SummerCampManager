package DomainModel;

public class OnlyChildFee implements FeeStrategy{
    @Override
    public double getFee(Subscription subscription) {
        return 0;
    }
}
