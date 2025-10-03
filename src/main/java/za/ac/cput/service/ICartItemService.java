package za.ac.cput.service;

import za.ac.cput.domain.CartItem;

/*
ICartItemService.java
ICartItem service
Author: Thandolwethu P MSELEKU(223162477)
Date: 03 August 2025
*/

import java.util.List;

 public interface ICartItemService extends IService<CartItem,Long>{
     //    @Override
     //    public CartItem create(CartItem cartItem) {
     //        Long userId = cartItem.getUser().getUserId();
     //        Long productId = cartItem.getProduct().getProductID();
     //
     //        User user = userRepository.findById(userId)
     //                .orElseThrow(() -> new IllegalArgumentException("User not found"));
     //        Product product = productRepository.findById(productId)
     //                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
     //
     //        // check if product already in cart
     //        CartItem existing = cartItemRepository.findByUser_UserIdAndProduct_ProductID(userId, productId)
     //                .orElse(null);
     //
     //        if (existing != null) {
     //            CartItem updated = new CartItem.Builder()
     //                    .copy(existing)
     //                    .setQuantity(existing.getQuantity() + cartItem.getQuantity())
     //                    .setPrice(product.getPrice())
     //                    .build();
     //            return cartItemRepository.save(updated);
     //        }
     //
     //        CartItem newItem = new CartItem.Builder()
     //                .copy(cartItem)
     //                .setUser(user)
     //                .setProduct(product)
     //                .setPrice(product.getPrice())
     //                .build();
     //
     //        return cartItemRepository.save(newItem);
     //    }


     List<CartItem> getAll();
}
