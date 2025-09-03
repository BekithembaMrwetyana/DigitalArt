package za.ac.cput.factory;
/*
CartItemFactoryTest.java
CartItemFactoryTest POJO class
Author: Thandolwethu P Mseleku
Date: 18 May 2025
*/
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Order;
import za.ac.cput.domain.*;
import za.ac.cput.factory.CartItemFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.MethodName.class)
class CartItemFactoryTest {
    private static final Contact contact = new Contact.Builder().setPhoneNumber("083655389").setAltNumber("123456789").build();

    private static final User user = new User.Builder().setLastName("Mseleku").setFirstName("Thandolwethu")
            .setPassword("password").setContact(contact).build();

    private static final Cart cart = CartFactory.createCart(user, new ArrayList<>());

    private static final ProductFactory productFactory = new ProductFactory();
    private static final Category category = new Category.Builder().setName("Pirates").setDescription("Pirates on the cruise").build();
    private static final Product product = productFactory.create(222L,category,"Beach Outing",
            "Pirates on the beach",15.000, "image1");
    private static final CartItem cartItem1 = CartItemFactory.createCartItem(cart,product,user,10,10.000);
    private static final CartItem cartItem2 = CartItemFactory.createCartItem(cart,product,user,-5,20.000);


    @Test
    @Order(1)
    void testCreateCartItem() {
        assertNotNull(cartItem1, "CartItem1 should not be null");
        System.out.println("CartItem1: " + cartItem1);
    }

    @Test
    @Order(2)
    void testCreateCartWithInvalidQuantity() {
        assertNull(cartItem2, "CartItem2 should be null due to invalid quantity");
        System.out.println("CartItem2 creation failed due to invalid quantity");
    }

    @Test
    @Order(3)
    @Disabled("Not yet implemented")
    void testCreateCartItemThatNotYetImplemented() {

    }}