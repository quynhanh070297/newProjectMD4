package ra.controller;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ra.model.entity.Category;
import ra.service.impl.CategoryService;

@RestController
@RequestMapping("/api.myservice.com/v1")
@CrossOrigin("*")
public class CategoryController {
    @Autowired
   private CategoryService categoryService;

    /**
     *
     - [x] 4287    ROLE_ADMIN   -    PUT     -    Chỉnh sửa thông tin danh mục
     -    Bắt buộc    /api.myservice.com/v1/admin/categories/{categoryId}
     */
    @PutMapping("/admin/categories/updateCategory")
    public ResponseEntity<?> updateCatalog(@Valid @RequestBody Category category, @RequestParam Long id  ) throws Exception
    {
        return ResponseEntity.ok(categoryService.saveCatalog(category,id));
    }

    /**
     ROLE_ADMIN   -    POST    -    Thêm mới danh mục
     -    Bắt buộc    /api.myservice.com/v1/admin/categories
     */
    @PostMapping("/admin/categories/addNewCategory")
    public ResponseEntity<?> addNewCatalog(@Valid @RequestBody Category category) throws Exception {
        return ResponseEntity.ok(categoryService.saveCatalog(category,null));
    }

    /**
     - [ ] 4285    ROLE_ADMIN   -    GET     -    Lấy về thông tin danh mục theo id
     -    Bắt buộc    /api.myservice.com/v1/admin/categories/{categoryId}
     */
    @GetMapping("/admin/categories/getCategoriesByID/{categoryId}")
    public ResponseEntity<?> getCategory(@PathVariable Long categoryId) throws Exception {
        return ResponseEntity.ok(categoryService.getCategory(categoryId));
    }

    /**
     ROLE_ADMIN   -    GET     -    Lấy về danh sách tất cả danh mục (sắp xếp và phân trang)Bắt buộc
     /api.myservice.com/v1/admin/categories
     */
    @GetMapping("/admin/categories")
    public ResponseEntity<?> getListCatalogs(@RequestParam Integer page) {
        return ResponseEntity.ok(categoryService.getCatalogsList(page).getContent());
    }

    /**
     ROLE_ADMIN   -    DELETE  -    Xóa danh mục
     -    Bắt buộc    /api.myservice.com/v1/admin/categories/{categoryId}
     */
    @DeleteMapping("/admin/categories/deleteById/{categoryId}")
    public ResponseEntity<?> deleteCatalogs(@PathVariable Long categoryId) throws Exception {
        return ResponseEntity.ok(categoryService.deleteCategory(categoryId));
    }
}
