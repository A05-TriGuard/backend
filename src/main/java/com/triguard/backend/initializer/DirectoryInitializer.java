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
            boolean created = staticDir.mkdir();
            if (created) {
                log.info("静态文件目录创建成功");
            } else {
                log.error("静态文件目录创建失败");
            }
        }
    }
}
