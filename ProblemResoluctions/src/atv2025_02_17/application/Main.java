package atv2025_02_17.application;

import atv2025_02_17.entities.PeopleIndividual;
import atv2025_02_17.entities.PeopleCompany;
import atv2025_02_17.entities.FederalRevenue;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        FederalRevenue federalRevenue = new FederalRevenue();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of tax payers: ");
        int numberOfTaxPayers = scanner.nextInt();

        for (int i = 0; i < numberOfTaxPayers; i++) {
            System.out.println("Tax payer #" + (i + 1) + " data:");
            System.out.println("Individual or company (i/c)? ");
            String peopleType = scanner.next();
            if (peopleType.equalsIgnoreCase("i")) {
                System.out.println("Name: ");
                String name = scanner.next();
                System.out.println("Anual income: ");
                double anualIncome = scanner.nextDouble();
                System.out.println("Health expenditure: ");
                double healthExpenditure = scanner.nextDouble();

                PeopleIndividual PessoaFisica = new PeopleIndividual(name, anualIncome, healthExpenditure);

                federalRevenue.addPeople(PessoaFisica);
            } else if (peopleType.equalsIgnoreCase("c")) {
                System.out.println("Name: ");
                String name = scanner.next();
                System.out.println("Anual income: ");
                double anualIncome = scanner.nextDouble();
                System.out.println("Number of employees: ");
                int numberOfEmployess = scanner.nextInt();

                PeopleCompany PessoaJuridica = new PeopleCompany(name, anualIncome, numberOfEmployess);
                federalRevenue.addPeople(PessoaJuridica);
            }
        }

        System.out.println("TAXES PAID: ");
        federalRevenue.showTaxesPaid();

        scanner.close();
    }
}
