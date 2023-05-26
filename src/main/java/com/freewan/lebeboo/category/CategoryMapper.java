package com.freewan.lebeboo.category;

import com.freewan.lebeboo.common.storage.FileStorageUtils;
import org.mapstruct.BeanMapping;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {
    @Mapping(target = "id", ignore = true)
    Category toEntity(CategoryDto categoryDto);

    @Mapping(source = "icon", target = "iconUrl", qualifiedByName = "fileNameToPath")
    CategoryDto toDto(Category category);

    @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Category partialUpdate(CategoryDto categoryDto, @MappingTarget Category category);

    List<CategoryDto> toDtos(List<Category> all);

    @Named("fileNameToPath")
    static String fileNameToPath(String filename) {
        if (filename == null || filename.isBlank()) return "";
        return FileStorageUtils.getFileUrlByFilename(filename);
    }
}