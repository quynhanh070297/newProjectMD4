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
    @PutMapping("/admin/updateCatalog")
    public ResponseEntity<?> updateCatalog(@Valid @RequestBody Category category, @RequestParam Long id  ) throws Exception
    {
        return ResponseEntity.ok(categoryService.saveCatalog(category,id));
    }
    @PostMapping("/admin/addNewCatalog")
    public ResponseEntity<?> addNewCatalog(@Valid @RequestBody Category category) throws Exception {
        return ResponseEntity.ok(categoryService.saveCatalog(category,null));
    }
    @GetMapping("/admin/categoryById")
    public ResponseEntity<?> getCategory(@RequestParam Long id) throws Exception {
        return ResponseEntity.ok(categoryService.getCategory(id));
    }
    @GetMapping("/admin/categories")
    public ResponseEntity<?> getListCatalogs(@RequestParam Integer page) {
        return ResponseEntity.ok(categoryService.getCatalogsList(page).getContent());
    }
    @DeleteMapping("/admin/deleteCategory")
    public ResponseEntity<?> deleteCatalogs(@RequestParam Long id) throws Exception {
        return ResponseEntity.ok(categoryService.deleteCategory(id));
    }

}
