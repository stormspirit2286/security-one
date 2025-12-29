package vn.duynv.secutityone.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.duynv.secutityone.exception.ApiException;
import vn.duynv.secutityone.exception.ErrorCode;
import vn.duynv.secutityone.mapper.CategoryMapper;
import vn.duynv.secutityone.modal.Category;
import vn.duynv.secutityone.payload.request.CreateCategoryRequest;
import vn.duynv.secutityone.payload.response.CategoryResponse;
import vn.duynv.secutityone.payload.response.PageResponse;
import vn.duynv.secutityone.repository.CategoryRepository;
import vn.duynv.secutityone.service.CategoryService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResponse createCategory(CreateCategoryRequest createCategoryRequest) {
        if (categoryRepository.existsBySlug(createCategoryRequest.getSlug())) {
            throw new ApiException(ErrorCode.SLUG_ALREADY_EXISTS);
        }
        log.info("Create category request: {}", createCategoryRequest.getSlug());
        Category category = categoryMapper.toEntity(createCategoryRequest);
        return categoryMapper.toResponse(categoryRepository.save(category));
    }

    @Override
    public PageResponse<CategoryResponse> getCategories(int page, int size, List<String> sort) {
        Sort sortObj = Sort.unsorted();
        if (sort != null && !sort.isEmpty()) {
            List<Sort.Order> orders = sort.stream().map(s -> {
                String[] parts = s.split(",");
                String field = parts[0];
                Sort.Direction direction = parts.length > 1 && "desc".equalsIgnoreCase(parts[1])
                        ? Sort.Direction.DESC
                        : Sort.Direction.ASC;
                return new Sort.Order(direction, field);
            }).toList();

            sortObj =  Sort.by(orders);
        }
        Pageable pageable = PageRequest.of(page, size, sortObj);
        Page<Category> pageResult = categoryRepository.findAll(pageable);
        return mapToPageResponse(pageResult);
    }

    private PageResponse<CategoryResponse> mapToPageResponse(Page<Category> pageResult) {
        List<CategoryResponse> content = pageResult.getContent()
                .stream()
                .map(categoryMapper::toResponse)
                .collect(Collectors.toList());

        return PageResponse.<CategoryResponse>builder()
                .content(content)
                .pageNumber(pageResult.getNumber())
                .pageSize(pageResult.getSize())
                .totalElements(pageResult.getTotalElements())
                .totalPages(pageResult.getTotalPages())
                .last(pageResult.isLast())
                .first(pageResult.isFirst())
                .empty(pageResult.isEmpty())
                .build();
    }

    @Override
    public CategoryResponse getCategory(Long categoryId) {
        return null;
    }

    @Override
    public CategoryResponse updateCategory(Long categoryId, CreateCategoryRequest createCategoryRequest) {
        return null;
    }

    @Override
    public void deleteCategory(Long categoryId) {

    }

    @Override
    public List<CategoryResponse> createMultipleCategory(List<CreateCategoryRequest> createCategoryRequest) {
        List<Category> categoryList = createCategoryRequest.stream().map(categoryMapper::toEntity).toList();
        List<Category> savedCate = categoryRepository.saveAll(categoryList);
        return savedCate.stream().map(categoryMapper::toResponse).toList();
    }
}
