package com.freewan.lebeboo.common.storage;

import com.freewan.lebeboo.StorageProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.stream.Stream;

@Service
@Slf4j
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;

    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public void store(MultipartFile file) {
        store(file, file.getOriginalFilename());
    }

    @Override
    public void store(MultipartFile file, String filename) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }
            Path destinationFile = this.rootLocation.resolve(
                            Paths.get(Objects.requireNonNull(filename)))
                    .normalize().toAbsolutePath();
            if (!destinationFile.getParent().toString().contains(this.rootLocation.toAbsolutePath().toString())) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file outside root image directory.");
            }
            if (!Files.exists(destinationFile)) {
                Files.createDirectories(destinationFile);
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
                log.info("Successfully saved file to {}", destinationFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new StorageException("Failed to store file.", e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) throws StorageException, StorageFileNotFoundException {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists()) {
                if (resource.isReadable()) {
                    return resource;
                } else throw new StorageException(
                        "Could not read file: " + filename
                );
            } else {
                throw new StorageFileNotFoundException(
                        "File Not found: " + filename);

            }
        } catch (MalformedURLException | StorageFileNotFoundException e) {
            throw new StorageFileNotFoundException("File not found: " + filename, e);
        }
    }

    @Override
    public void delete(String filename) throws IOException {
        FileSystemUtils.deleteRecursively(load(filename));
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void init() {
        if (!Files.exists(rootLocation)) {
            try {
                Files.createDirectories(rootLocation);
            } catch (IOException e) {
                throw new StorageException("Could not initialize storage", e);
            }
        }
    }
}
