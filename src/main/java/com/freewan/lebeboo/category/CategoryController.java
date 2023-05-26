package com.freewan.lebeboo.category;

import com.freewan.lebeboo.Route;
import com.freewan.lebeboo.common.http.ApiResponse;
import com.freewan.lebeboo.common.http.ApiResponseCode;
import com.freewan.lebeboo.common.storage.FileSystemStorageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(Route.ROOT + Route.V1_URI + Route.CATEGORY)
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final FileSystemStorageService fileSystemStorageService;
    private final CategoryMapper mapper;

    @Operation(summary = "Get all category")
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CategoryDto> getAllCategories() {
        return mapper.toDtos(categoryService.findAll());
    }

    @Operation(summary = "Get category by id")
    @GetMapping(value = "/{categoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CategoryDto getCategoryById(@PathVariable Long categoryId) {
        return mapper.toDto(categoryService.findById(categoryId));
    }

    @Operation(summary = "Add a category")
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public CategoryDto addNewSector(@RequestBody CategoryDto dto) {
        Category category = mapper.toEntity(dto);
        return mapper.toDto(categoryService.save(category));
    }


    @Operation(summary = "Update a category")
    @PutMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public CategoryDto updateCategory(@RequestBody CategoryDto dto) {
        Category category = categoryService.findById(dto.getId());
        if(dto.getIcon() == null || dto.getIcon().isBlank()) {
            dto.setIcon(null);
        }
        category = mapper.partialUpdate(dto, category);
        return mapper.toDto(categoryService.save(category));
    }

    @Operation(summary = "Delete a category by id")
    @DeleteMapping(value = "/{categoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = IOException.class)
    public ApiResponse deleteCategoryById(@PathVariable Long categoryId) throws IOException {
        Category category = categoryService.findById(categoryId);
        categoryService.delete(category);
        fileSystemStorageService.delete(category.getIcon());
        return new ApiResponse(ApiResponseCode.SUCCESS, "Category deleted with success.");
    }
}
