package vn.duynv.secutityone.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.duynv.secutityone.payload.request.CreateCategoryRequest;
import vn.duynv.secutityone.payload.response.CategoryResponse;
import vn.duynv.secutityone.payload.response.PageResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse createCategory(CreateCategoryRequest createCategoryRequest);

    PageResponse<CategoryResponse> getCategories(int page, int size, List<String> sort);

    CategoryResponse getCategory(Long categoryId);

    CategoryResponse updateCategory(Long categoryId, CreateCategoryRequest createCategoryRequest);

    void deleteCategory(Long categoryId);

    List<CategoryResponse> createMultipleCategory(List<CreateCategoryRequest> createCategoryRequest);

}
