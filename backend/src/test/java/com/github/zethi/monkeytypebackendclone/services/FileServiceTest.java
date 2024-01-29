package com.github.zethi.monkeytypebackendclone.services;


import com.github.zethi.monkeytypebackendclone.exceptions.CanNotCreateFileException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public final class FileServiceTest {

    private final FileService fileService;
    private final Path examplePath;

    @Autowired
    public FileServiceTest(FileService fileService) {
        this.fileService = fileService;
        this.examplePath = Paths.get("src/test/resources/data/test.txt");
    }


    @Test
    @Order(1)
    @DisplayName("Create file")
    public void shouldCreateAFile() throws IOException, CanNotCreateFileException {
        fileService.createFile(examplePath);
    }

    @Test
    @Order(2)
    @DisplayName("Validate that file exists")
    public void shouldValidateIfAExistentFileExists() {
        Assertions.assertTrue(fileService.exists(examplePath.toAbsolutePath()));
    }

    @Test
    @Order(3)
    @DisplayName("Validate that file not exists")
    public void shouldValidateThatANotExistentFileNotExist() {
        Assertions.assertFalse(fileService.exists(Paths.get("src/test/resources/data/invalid.txt")));
    }

    @Test
    @Order(4)
    @DisplayName("Read content from a blank file")
    public void shouldReturnVoidContentFromABlankFile() throws IOException {
        Assertions.assertEquals("", fileService.read(examplePath.toAbsolutePath()));
    }

    @Test
    @Order(5)
    @DisplayName("Write content")
    public void shouldWriteContentInAFile() throws IOException {
        fileService.write(examplePath, "test example");
    }

    @Test
    @Order(6)
    @DisplayName("Read content from file")
    public void shouldReturnContentFromFile() throws IOException {
        Assertions.assertEquals("test example", fileService.read(examplePath.toAbsolutePath()));
    }

    @Test
    @Order(7)
    @DisplayName("Delete file")
    public void shouldDeleteAFile() {
        fileService.delete(examplePath.toAbsolutePath());
    }

}
