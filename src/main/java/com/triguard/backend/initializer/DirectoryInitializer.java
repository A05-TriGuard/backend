package com.triguard.backend.initializer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;

@Slf4j
@Component
public class DirectoryInitializer implements CommandLineRunner {

    @Override
    public void run(String... args) {
        File staticDir = new File("static");
        if (!staticDir.exists()) {
            if (staticDir.mkdir()) {
                log.info("Create static directory successfully.");
            } else {
                log.error("Create static directory failed.");
            }
        }
    }
}
