package atv2025_02_17.entities;

import java.util.ArrayList;

public class FederalRevenue {
    private ArrayList<People> peopleList;

    public FederalRevenue() {
        this.peopleList = new ArrayList<People>();
    }

    public void addPeople(People people) {
        peopleList.add(people);
    }

    public void showTaxesPaid() {
        double totalTaxes = 0;

        for(People people : peopleList) {
            System.out.println(people.getName() + ": $ " + people.taxCalculation());
            totalTaxes += people.taxCalculation();
        }

        System.out.println("TOTAL TAXES: $ " + totalTaxes);
    }
}
