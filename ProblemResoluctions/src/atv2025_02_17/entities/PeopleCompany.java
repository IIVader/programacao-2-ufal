package atv2025_02_17.entities;

public class PeopleCompany extends People {
    private int numberEmployees;

    public PeopleCompany(String name, double annualIncome, int numberEmployees) {
        super(name, annualIncome);
        this.numberEmployees = numberEmployees;
    }

    public int getNumberEmployees() {
        return numberEmployees;
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

        if(getNumberEmployees() <= 10) {
            totalPay = getAnnualIncome() * 0.16;
        } else {
            totalPay = getAnnualIncome() * 0.14;
        }

        return totalPay;
    }
}
