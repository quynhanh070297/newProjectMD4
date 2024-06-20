package ra.controller;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ra.model.dto.request.ProductRequest;
import ra.service.impl.ProductService;

@RestController
@RequestMapping("/api.myservice.com/v1")
@CrossOrigin("*")
public class ProductController {
    @Autowired
    private ProductService productService;

    /**
     PermitAll    -    GET     -    Chi tiết thông tin sản phẩm theo id
     */
    @GetMapping("/products/{productId}")
    public ResponseEntity<?> getProductDetail(@PathVariable Long productId) throws Exception
    {
      return ResponseEntity.ok(productService.findById(productId));
    }
    /**
     * PermitAll    -    GET     -    Danh sách sản phẩm theo danh mục  -    Bắt buộc
     * */
    @GetMapping("/products/byCategories")
    public ResponseEntity<?> getProductByCatalog(@RequestParam Long id) throws Exception {
        return ResponseEntity.ok(productService.findAllByCategory(id));
    }

    /**
     * PermitAll    -    GET     -    Danh sách sản phẩm mới */
    @GetMapping("/products/new-products")
    public ResponseEntity<?> getNewProduct() throws Exception {
        return ResponseEntity.ok(productService.find10NewPro());
    }

    /**
     * @apiNote 4246    PermitAll    -    GET     -    Tìm kiếm sản phẩm theo tên hoặc mô tả
     * */
    @GetMapping("/products/search")
    public ResponseEntity<?> getProByNameOrDecr(@RequestParam (required = false) String name ,@RequestParam(required = false) String description) throws Exception {
        return ResponseEntity.ok(productService.findByProductNameOrDescription(name,description));
    }

    /**
     - [ ] 4282    ROLE_ADMIN   -    PUT     -    Chỉnh sửa thông tin sản phẩm
     -    Bắt buộc    /api.myservice.com/v1/admin/products/{productId}
     */
    @PutMapping("/admin/products/updatePro")
    public ResponseEntity<?> updateProduct(@Valid @RequestBody ProductRequest product, @RequestParam Long id  ) throws Exception {
        return ResponseEntity.ok(productService.savePro(product,id));
    }

    /**
     - [x] 4281    ROLE_ADMIN   -    POST    -    Thêm mới sản phẩm
     -    Bắt buộc    /api.myservice.com/v1/admin/products
     */
    @PutMapping("/admin/products/addNewPro")
    public ResponseEntity<?> addNewPro(@Valid @RequestBody ProductRequest product) throws Exception {
        return ResponseEntity.ok(productService.savePro(product,null));
    }

    /**
     *
     - [x] 4280    ROLE_ADMIN   -    GET     -    Lấy về thông tin sản phẩm theo id
     -    Bắt buộc    /api.myservice.com/v1/admin/products/{productId}
     */
    @GetMapping("/admin/productDetail/{productId}")
    public ResponseEntity<?> getProductDetailAdmin(@PathVariable Long productId) throws Exception {
        return ResponseEntity.ok(productService.findById(productId));
    }

    /**
     4279    ROLE_ADMIN   -    GET     -    Lấy về danh sách tất cả sản phẩm (sắp xếp và phân trang)Bắt buộc
     /api.myservice.com/v1/admin/products
     */
    @GetMapping("/admin/products")
    public ResponseEntity<?> getListPro(@RequestParam Integer page) {
        return ResponseEntity.ok(productService.getProductList(page).getContent());
    }

    /**
     - [x] 4283    ROLE_ADMIN   -    DELETE  -    Xóa sản phẩm
     -    Bắt buộc    /api.myservice.com/v1/admin/products/{productId}
     */
    @DeleteMapping("/admin/products/deletePro/{productId}")
    public ResponseEntity<?> deleteCatalogs(@PathVariable Long productId) throws Exception {
        return ResponseEntity.ok(productService.deletePro(productId));
    }
    /**
     * 4247 PermitAll    -    GET     -    Danh sách sản phẩm được bán (có phân trang và sắp xếp)
     * Chua lam
     * */
}
