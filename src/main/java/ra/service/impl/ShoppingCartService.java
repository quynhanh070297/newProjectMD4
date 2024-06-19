package ra.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ra.model.entity.ShoppingCart;
import ra.model.entity.User;
import ra.repository.ProductRepository;
import ra.repository.ShoppingCartRepository;
import ra.security.principals.CustomUserDetail;
import ra.service.UserService;

import java.util.List;

@Service
public class ShoppingCartService {
    @Autowired
    ShoppingCartRepository shoppingCartRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserService userService;
    public ShoppingCart addToCart(Long productId,Integer quantity) throws Exception
    {
        CustomUserDetail userDetails = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user=userService.getUserByUserName(userDetails.getUsername());
ShoppingCart shoppingCart =  shoppingCartRepository.findShoppingCartByProductAndUser(productRepository.findById(productId).orElse(null),user);
        if (shoppingCart==null) {
            return shoppingCartRepository.save(ShoppingCart.builder()
                    .shoppingCartId(null)
                    .user(user)
                    .orderQuantity(quantity)
                    .product(productRepository.findById(productId).orElseThrow(() -> new Exception("Product is not exits")))
                    .build());
        }else {
            shoppingCart.setOrderQuantity(quantity);
            return shoppingCartRepository.save(shoppingCart);
        }
    }
    public List<ShoppingCart> getAllShoppingCart() throws Exception {
        CustomUserDetail userDetails = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user=userService.getUserByUserName(userDetails.getUsername());
       return shoppingCartRepository.findAllByUser(user);
    }
    public Boolean deleteShoppingCart() throws Exception {
        shoppingCartRepository.deleteAll(getAllShoppingCart());
        return true;
    }
    public Boolean deleteShoppingCartById(Long shoppingCartId) {
        shoppingCartRepository.deleteById(shoppingCartId);
        return true;
    }

}
