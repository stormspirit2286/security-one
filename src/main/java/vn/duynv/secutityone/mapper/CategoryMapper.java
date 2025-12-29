package vn.duynv.secutityone.mapper;

import org.mapstruct.Mapper;
import vn.duynv.secutityone.modal.Category;
import vn.duynv.secutityone.payload.request.CreateCategoryRequest;
import vn.duynv.secutityone.payload.response.CategoryResponse;

@Mapper( componentModel = "spring")
public interface CategoryMapper {
    CategoryResponse toResponse(Category category);

    Category toEntity(CreateCategoryRequest request);
}
