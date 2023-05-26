package com.freewan.lebeboo.common.storage;

import com.freewan.lebeboo.Route;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.freewan.lebeboo.Route.FILES;
import static com.freewan.lebeboo.Route.V1_URI;

public class FileStorageUtils {

    public static String createNewFileName() {
        return UUID.randomUUID().toString();
    }

    public static String createNewFileName(String originalFileName) {
        String fileName = createNewFileName();
        String extension = FilenameUtils.getExtension(originalFileName) != null ?
                FilenameUtils.getExtension(originalFileName) : "";
        return String.format("%s.%s", fileName, extension);

    }

    public static boolean isImageFile(MultipartFile file) {
        return file != null && Objects.requireNonNull(file.getContentType()).startsWith("image/");
    }

    public static boolean areImageFiles(List<MultipartFile> multipartFiles) {
        return multipartFiles.stream().anyMatch(file -> !isImageFile(file));
    }

    public static String getFileUrlByFilename(String filename) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(Route.ROOT)
                .path(V1_URI)
                .path(FILES)
                .path("/" + filename)
                .toUriString();
    }
}
