package com.freewan.lebeboo.common;

import com.freewan.lebeboo.common.storage.FileSystemStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GlobalConfig {
    private final FileSystemStorageService fileSystemStorageService;

    @Bean
    CommandLineRunner init() {
        return args -> fileSystemStorageService.init();
    }
}
