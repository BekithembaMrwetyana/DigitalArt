package za.ac.cput.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import za.ac.cput.domain.*;
import za.ac.cput.factory.CartItemFactory;
/*
CartItemControllerTest.java
CartItemControllerTest POJO class
Author: Thandolwethu P Mseleku
Date: 07 Aug 2025
*/
import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.MethodName.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CartItemControllerTest {

private static CartItem cartItem;
private static Cart cart;
private static User user;
private static Product product;

@Autowired
private TestRestTemplate restTemplate;
    private static final String BASE_URL = "http://localhost:8080/ADP_Capstone_Project/cart_Item";

    @BeforeAll
    public static void setUp() {

        user = new User.Builder()
                .setFirstName("Thando")
                .setLastName("Mseleku")
                //.setEmail("123@gmail.com")
                .setPassword("password123")
                .build();

         cart = new Cart.Builder()
                .setUserID(user)
                .build();

        product = new Product.Builder()
                .setProductID(1L)
                .setTitle("Digital Art")
                .setDescription("A beautiful digital painting")
                .setPrice(49.99)
                //.setCategoryID("C001")
                .build();

        cartItem = CartItemFactory.createCartItem(cart,product,user,25,10.000);
    }
 @Test
    void a_create(){
        String url = BASE_URL + "/create";
      ResponseEntity<CartItem>postResponse = restTemplate.postForEntity(url, cartItem, CartItem.class);
     assertNotNull(postResponse);
     CartItem cartItemCreated = postResponse.getBody();
     assertEquals(cartItem.getCartItemID(), cartItemCreated.getCartItemID());
     System.out.println("cartItemCreated:" + cartItemCreated);

 }

 @Test
    void b_read(){
        String url = BASE_URL + "read" + cartItem.getCartItemID();
        ResponseEntity<CartItem>response = this.restTemplate.getForEntity(url, CartItem.class);
        assertEquals(cartItem.getCartItemID(), response.getBody().getCartItemID());
     System.out.println("read =" + response.getBody());
 }

    @Test
 void c_update(){
        CartItem updatedCartItem = new CartItem.Builder().copy(cartItem).setQuantity(100).build();
        String url = BASE_URL + "/update";
        ResponseEntity<CartItem>response =this.restTemplate.getForEntity(BASE_URL + "read" + cartItem.getCartItemID(), CartItem.class);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
        //assertEquals(updatedCartItem.getQuantity(), response.getBody().getCartItemID());
        System.out.println("updated" +response.getBody());

 }


    @Test
    @Disabled
    void e_delete(){
        String url = BASE_URL + "/delete/" + cartItem.getCartItemID();
        this.restTemplate.delete(url);
     ResponseEntity<CartItem>response = this.restTemplate.getForEntity(BASE_URL + "/read/" + cartItem.getCartItemID(), CartItem.class);
     assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
     System.out.println("Deleted: true"+cartItem);
 }


 @Test
    void d_getAll(){
        String url =BASE_URL + "/getAll";
     ResponseEntity<CartItem[]>response = this.restTemplate.getForEntity(url, CartItem[].class);
     assertNotNull(response.getBody());
     System.out.println("Get All; ");
     for (CartItem cartItem : response.getBody()) {
         System.out.println(cartItem);
     }

 }
}


