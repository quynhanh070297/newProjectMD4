package ra.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ra.model.entity.Product;
import ra.model.entity.User;
import ra.model.entity.WishList;
import ra.repository.ProductRepository;
import ra.repository.WishListRepository;
import ra.security.principals.CustomUserDetail;
import ra.service.UserService;

import java.util.List;
import java.util.Optional;

@Service
public class WishListService {
    @Autowired
    UserService userService;
    @Autowired
    WishListRepository wishListRepository;
    @Autowired
    ProductRepository productRepository;

    public Boolean AddOrDeleteToWishList(Long productId) throws Exception
    {
        CustomUserDetail userDetails = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getUserByUserName(userDetails.getUsername());
        Product product = productRepository.findById(productId).orElseThrow(() -> new Exception("san pham khong ton tai"));
        Optional<WishList> wishList = wishListRepository.findByUserAndProduct(user, product);
        if (wishList.isPresent()) {
            wishListRepository.delete(wishList.get());
        } else {
            wishListRepository.save(WishList.builder()
                    .wishListId(null)
                    .product(productRepository.findById(productId).orElseThrow(() -> new Exception("san phan khong ton tai")))
                    .user(user)
                    .build());
        }
        return true;
    }

    public List<WishList> getWishLists()  {
        CustomUserDetail userDetails = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getUserByUserName(userDetails.getUsername());
        return wishListRepository.findAllByUser(user);
    }

}
