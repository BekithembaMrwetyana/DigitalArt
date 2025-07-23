package factory;
/*
OrderItemFactory.java
OrderItem Factory class
Author: Thimna Gogwana 222213973
Date: 25 May 2025
*/

import domain.OrderItem;
import domain.Product;
import util.Helper;
import org.springframework.stereotype.Component;

@Component
public class OrderItemFactory {

    public OrderItem create(int orderID, Product product, int quantity, double unitPrice) {
        validateCreationParameters(orderID, product, quantity, unitPrice);

        return new OrderItem.Builder()
                .setOrderID(orderID)
                .setProduct(product)
                .setQuantity(quantity)
                .setUnitPrice(unitPrice)
                .calculateSubTotal()
                .build();
    }

    public OrderItem createWithId(int itemID, int orderID, Product product,
                                  int quantity, double unitPrice) {
        validateCreationParameters(orderID, product, quantity, unitPrice);
        if (!Helper.isValidOrderItemID(itemID)) {
            throw new IllegalArgumentException("Invalid OrderItem ID: " + itemID);
        }

        return new OrderItem.Builder()
                .setItemID(itemID)
                .setOrderID(orderID)
                .setProduct(product)
                .setQuantity(quantity)
                .setUnitPrice(unitPrice)
                .calculateSubTotal()
                .build();
    }

    public OrderItem copy(OrderItem original) {
        if (original == null) {
            throw new IllegalArgumentException("Original OrderItem cannot be null");
        }
        Helper.validateOrderItem(original);

        return new OrderItem.Builder()
                .copy(original)
                .build();
    }

    public OrderItem updateQuantity(OrderItem original, int newQuantity) {
        if (original == null) {
            throw new IllegalArgumentException("Original OrderItem cannot be null");
        }
        if (!Helper.isValidQuantity(newQuantity)) {
            throw new IllegalArgumentException("Invalid quantity: " + newQuantity);
        }
        Helper.validateOrderItem(original);

        return new OrderItem.Builder()
                .copy(original)
                .setQuantity(newQuantity)
                .calculateSubTotal()
                .build();
    }

    private void validateCreationParameters(int orderID, Product product,
                                            int quantity, double unitPrice) {
        if (!Helper.isValidOrderID(orderID)) {
            throw new IllegalArgumentException("Invalid Order ID: " + orderID);
        }
        if (!Helper.isValidProductAssociation(product)) {
            throw new IllegalArgumentException("Invalid Product association");
        }
        if (!Helper.isValidQuantity(quantity)) {
            throw new IllegalArgumentException("Invalid quantity: " + quantity);
        }
        if (!Helper.isValidUnitPrice(unitPrice)) {
            throw new IllegalArgumentException("Invalid unit price: " + unitPrice);
        }
    }
}

