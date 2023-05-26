package com.freewan.lebeboo.common.storage;


import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.freewan.lebeboo.Route.FILES;
import static com.freewan.lebeboo.Route.ROOT;
import static com.freewan.lebeboo.Route.V1_URI;


@RestController
@RequestMapping(ROOT + V1_URI + FILES)
@Slf4j
public class FileController {

    private final StorageService storageService;

    public FileController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> getFileByFilename(@PathVariable String filename, HttpServletRequest request) throws StorageFileNotFoundException {
        Resource resource = storageService.loadAsResource(filename);
        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            log.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
    }

    @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<FileUploadApiResponse> uploadFile(@RequestParam("file") MultipartFile file) throws StorageFileNotFoundException {
        String filename = FileStorageUtils.createNewFileName(file.getOriginalFilename());
        storageService.store(file, filename);
        return new ResponseEntity<>(new FileUploadApiResponse(filename), HttpStatus.CREATED);
    }
}
