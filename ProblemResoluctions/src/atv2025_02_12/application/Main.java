package atv2025_02_12.application;

import atv2025_02_12.entities.*;

public class Main {
    public static void main(String[] args) {

        Tv tv1 = new Tv("SmartTV Samsung", 2600.0, 50);
        Refrigerator refrigerator1 = new Refrigerator("Geladeira 2 portas Brastemp", 2900.0, 300);
        Stove stove1 = new Stove("Fogão Consul", 1159.0, 4);
        Product product4 = new Product("Produto 4", 27.0);

        ShoppingCart shoppingCart = new ShoppingCart(1);

        shoppingCart.addProduct(tv1);
        shoppingCart.addProduct(tv1);
        shoppingCart.addProduct(refrigerator1);
        shoppingCart.addProduct(stove1);
        shoppingCart.addProduct(product4);

        System.out.println(shoppingCart.getItemCount(tv1.getBrand()));
        System.out.println(shoppingCart.getTotalPrice());

        System.out.println(shoppingCart.getContent());

        shoppingCart.removeProduct(product4);

        System.out.println(shoppingCart.getContent());

    }
}
