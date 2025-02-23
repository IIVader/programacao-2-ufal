package atv2025_02_17.entities;

public class PeopleIndividual extends People {
    private double healthcareExpenses;

    public PeopleIndividual(String name, double annualIncome, double healthcareExpenses) {
        super(name, annualIncome);
        this.healthcareExpenses = healthcareExpenses;
    }

    public double getHealthcareExpenses() {
        return healthcareExpenses;
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public double getAnnualIncome() {
        return super.getAnnualIncome();
    }

    @Override
    public double taxCalculation() {
        double totalPay = 0;

        if(getAnnualIncome() < 20000) {
            totalPay = ((getAnnualIncome() * 0.15) - (getHealthcareExpenses() * 0.5));
        } else {
            totalPay = ((getAnnualIncome() * 0.25) - (getHealthcareExpenses() * 0.5));
        }

        return totalPay;
    }
}
