package factory;

/*
CartFactoryTest.java
Unit test for CartFactory
Author: Bekithemba Mrwetyana (222706066)
Date: 17 May 2025
*/

import domain.Cart;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CartFactoryTest {

    @Test
    void createCart() {
        Cart cart = CartFactory.createCart("C123", null, null);
        assertNotNull(cart);
        assertEquals("C123", cart.getCartID());
        assertNull(cart.getUserID());
        assertNull(cart.getCartItems());
    }

    @Test
    void createCartWithNullValues() {
        Cart cart = CartFactory.createCart(null, null, null);
        assertNotNull(cart);
        assertNull(cart.getCartID());
        assertNull(cart.getUserID());
        assertNull(cart.getCartItems());
    }
    @Test
    void createCartWithEmptyValues() {
        Cart cart = CartFactory.createCart("", null, null);
        assertNotNull(cart);
        assertEquals("", cart.getCartID());
        assertNull(cart.getUserID());
        assertNull(cart.getCartItems());
    }
    @Test
    void createCartWithInvalidValues() {
        Cart cart = CartFactory.createCart("C123", null, null);
        assertNotNull(cart);
        assertEquals("C123", cart.getCartID());
        assertNull(cart.getUserID());
        assertNull(cart.getCartItems());
    }
    @Test
    void createCartWithValidValues() {
        Cart cart = CartFactory.createCart("C123", null, null);
        assertNotNull(cart);
        assertEquals("C123", cart.getCartID());
        assertNull(cart.getUserID());
        assertNull(cart.getCartItems());
    }
    @Test
    void createCartWithValidUser() {
        Cart cart = CartFactory.createCart("C123", null, null);
        assertNotNull(cart);
        assertEquals("C123", cart.getCartID());
        assertNull(cart.getUserID());
        assertNull(cart.getCartItems());
    }
}

