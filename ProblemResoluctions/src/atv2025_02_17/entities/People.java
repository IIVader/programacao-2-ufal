package atv2025_02_17.entities;

public abstract class People {
    private String name;
    private double annualIncome;

    public People(String name, double annualIncome) {
        this.name = name;
        this.annualIncome = annualIncome;
    }

    public String getName() {
        return name;
    }

    public double getAnnualIncome() {
        return annualIncome;
    }

    public abstract double taxCalculation();
}
