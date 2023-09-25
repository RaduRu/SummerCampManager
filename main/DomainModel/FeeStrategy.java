package main.DomainModel;

public interface FeeStrategy {
    int WEEKLY_FEE = 70;
    double getFee(Subscription subscription);
}
