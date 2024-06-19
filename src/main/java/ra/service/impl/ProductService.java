package ra.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import ra.model.dto.request.ProductRequest;
import ra.model.entity.Product;
import ra.repository.CategoryRepository;
import ra.repository.ProductRepository;

import java.util.Date;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public Product findById(Long id) throws Exception
    {
        Product product = productRepository.findById(id).orElse(null);
        if (product==null){
            throw new Exception("Is not exits");
        }else {
            return product;
        }
    }
    public List<Product> findAllByCategory(Long id) throws Exception {
        List<Product> products= productRepository.findAllByCategoryCategoryId(id);
        if (products.isEmpty()){
            throw new Exception("Is not exits");
        }else {
            return products;
        }
    }

    public List<Product> find10NewPro() throws Exception {
        List<Product> products= productRepository.findTop10ByOrderByCreatedAtDesc();
        if (products.isEmpty()){
            throw new Exception(" Product is not exits");
        }else {
            return products;
        }
    }

        public List<Product> findByProductNameOrDescription(String name,String description) throws Exception {
        List<Product> products= productRepository.findProductByProductNameOrDescription(name, description);
        if (products.isEmpty()){
            throw new Exception("Product is not exits");
        }else {
            return products;
        }
    }
    public Product savePro(ProductRequest productRequest, Long id) throws Exception {
        Product product = Product.builder()
                .productId(id)
                .stockQuantity(productRequest.getStockQuantity())
                .sku(productRequest.getSku())
                .productName(productRequest.getProductName())
                .image(productRequest.getImage())
                .category(categoryRepository.findById(productRequest.getCategoryID()).orElseThrow(() -> new Exception("Category is not exits")))
                .unitPrice(productRequest.getUnitPrice())
                .build();


        if (id==null){
            product.setCreatedAt(new Date());
            product.setUpdatedAt(new Date());
        }else {
            product.setUpdatedAt(new Date());
        }


       return productRepository.save(product);
    }

    public Page<Product> getProductList(Integer page) {
        int size = 3;
        Sort sort = Sort.by(Sort.Direction.ASC, "productName");
        Pageable pageable = PageRequest.of(page, size, sort);
        return productRepository.findAll(pageable);
    }

    public Boolean deletePro(Long id) throws Exception {
        productRepository.delete(productRepository.findById(id).orElseThrow(()->new Exception("Product is not exits")));
        return true;
    }
    public Boolean deleteProductById(Long id) throws Exception {
        productRepository.delete(findById(id));
        return true;
    }
    public void productQuantityChange(Long id,Integer quantity) throws Exception {

        Product product = findById(id);
        if (product.getStockQuantity()<quantity){
            throw new Exception("ProductQuantity is not exits");
        }
        product.setStockQuantity(product.getStockQuantity()-quantity);

        productRepository.save(product);
    }



}