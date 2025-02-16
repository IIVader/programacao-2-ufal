package atv2025_02_10.application;

import atv2025_02_10.entities.Product;
import atv2025_02_10.entities.ShoppingCart;

public class Main {
        public static void main(String[] args) {

            Product product1 = new Product("Produto 1", 21.0);
            Product product2 = new Product("Produto 2", 14.0);
            Product product3 = new Product("Produto 3", 1.0);
            Product product4 = new Product("Produto 4", 27.0);

            ShoppingCart shoppingCart = new ShoppingCart(1);

            shoppingCart.addProduct(product1);
            shoppingCart.addProduct(product2);
            shoppingCart.addProduct(product3);
            shoppingCart.addProduct(product4);

            System.out.println(shoppingCart.getItemCount(product1.getName()));
            System.out.println(shoppingCart.getTotalPrice());

            System.out.println(shoppingCart.getContent());

            shoppingCart.removeProduct(product4);

            System.out.println(shoppingCart.getContent());

        }
    }
