package vn.duynv.secutityone.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.duynv.secutityone.payload.request.CreateCategoryRequest;
import vn.duynv.secutityone.payload.response.ApiResponse;
import vn.duynv.secutityone.payload.response.CategoryResponse;
import vn.duynv.secutityone.payload.response.PageResponse;
import vn.duynv.secutityone.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(@RequestBody CreateCategoryRequest createCategoryRequest) {
        CategoryResponse result = categoryService.createCategory(createCategoryRequest);
        ApiResponse<CategoryResponse> response = ApiResponse.success("Create category successful", result);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/multiple")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> createMultipleCategory(@RequestBody List<CreateCategoryRequest> createCategoryRequests) {
        List<CategoryResponse> result = categoryService.createMultipleCategory(createCategoryRequests);
        ApiResponse<List<CategoryResponse>> response = ApiResponse.success("Create categories successful", result);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<CategoryResponse>>> getCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) List<String> sort
    ) {
        PageResponse<CategoryResponse> result = categoryService.getCategories(page, size, sort);
        ApiResponse<PageResponse<CategoryResponse>> response = ApiResponse.success("Get categories successful", result);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
