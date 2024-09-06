package vn.huan.shoppingcart.ShoppingCart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.huan.shoppingcart.ShoppingCart.model.Category;
import vn.huan.shoppingcart.ShoppingCart.response.ApiResponse;
import vn.huan.shoppingcart.ShoppingCart.service.category.ICategoryService;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {

    private final ICategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCatregores(){
        try {
            List<Category> categories = categoryService.getAllCategory();
            return ResponseEntity.ok(new ApiResponse("Found",categories));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error:",INTERNAL_SERVER_ERROR));
        }
    }
    @PostMapping("/add")
    public  ResponseEntity<ApiResponse> addCategory(@RequestBody Category name){
        try {
            Category theCategory = categoryService.addCategory(name);
            return  ResponseEntity.ok(new ApiResponse("Success",theCategory));
        } catch (Exception e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }

    }
    @GetMapping("/category/{id}/category")
    public ResponseEntity<ApiResponse> getCategoryByid(@PathVariable Long id){
        try {
            Category theCategory = categoryService.getCategoryById(id);
            return ResponseEntity.ok(new ApiResponse("Found", theCategory));
        } catch (Exception e) {
            return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }
    @GetMapping("/category/{name}/category")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name){
        try {
            Category theCategory = categoryService.getCategoryByName(name);
            return ResponseEntity.ok(new ApiResponse("Found", theCategory));
        } catch (Exception e) {
            return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }
    @DeleteMapping("/category/{id}/delete")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id){
        try {
            categoryService.getCategoryById(id);
            return ResponseEntity.ok(new ApiResponse("Found", null));
        } catch (Exception e) {
            return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }
    @PutMapping("/category/{id}/update")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id, @RequestBody Category category){
        try {
             Category updateCategory= categoryService.updateCategory(category,id);
            return ResponseEntity.ok(new ApiResponse("Update succes", updateCategory));
        } catch (Exception e) {
            return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }



}
