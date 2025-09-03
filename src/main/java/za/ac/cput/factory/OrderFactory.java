package za.ac.cput.factory;

import za.ac.cput.domain.CartItem;
import za.ac.cput.domain.Order;
import za.ac.cput.domain.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public class OrderFactory {

    public static Order createOrder(Long orderID,
                                    List<CartItem> cartItems,
                                    double totalAmount,
                                    LocalDateTime orderDate,
                                    double orderAmount,
                                    OrderStatus paymentStatus) {
        if (orderAmount < 0 || totalAmount < 0) {
            return null;
        }

        return new Order.Builder()
                .setOrderID(orderID)
                .setCartItem(cartItems)
                .setTotalAmount(totalAmount)
                .setOrderDate(orderDate)
                .setOrderAmount(orderAmount)
                //.setPaymentStatus(paymentStatus)
                .build();
    }
}

