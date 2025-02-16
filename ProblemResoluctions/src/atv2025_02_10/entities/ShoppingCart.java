package atv2025_02_10.entities;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ShoppingCart {
    private int customerId;
    private ArrayList<Product> productList;

    public ShoppingCart(int customerId) {
        this.customerId = customerId;
        this.productList = new ArrayList<>();
    }

    public void addProduct(Product product) {
        productList.add(product);
    }

    public void removeProduct(Product product) {
        productList.remove(product);
    }

    public String getContent() {
        return this.productList.stream()
                .map(Product::getName)
                .collect(Collectors.joining(", "));
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getItemCount(String productName) {
        int count = 0;

        for (Product product : productList) {
            if(product.getName().equalsIgnoreCase(productName)) {
                count ++;
            }
        }

        return  count;
    }

    public double getTotalPrice() {
        double totalPrice = 0.0;

        for (Product product : productList) {
            totalPrice += product.getPrice();
        }

        return totalPrice;
    }
}
