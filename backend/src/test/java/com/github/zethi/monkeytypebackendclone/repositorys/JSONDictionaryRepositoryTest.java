package com.github.zethi.monkeytypebackendclone.repositorys;

import com.github.zethi.monkeytypebackendclone.entity.Dictionary;
import com.github.zethi.monkeytypebackendclone.exceptions.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public final class JSONDictionaryRepositoryTest {

    private final JSONDictionaryRepository jsonDictionaryRepository;
    private final String testDictionaryName;
    private final String testDictionaryWriteName;

    @Autowired
    public JSONDictionaryRepositoryTest(JSONDictionaryRepository jsonDictionaryRepository) {
        this.jsonDictionaryRepository = jsonDictionaryRepository;
        this.testDictionaryName = "test";
        this.testDictionaryWriteName = testDictionaryName + "-write";
    }

    @Test
    @Order(1)
    @DisplayName("Create blank dictionary")
    public void shouldCreateADictionary() throws CanNotCreateDictionaryException, JsonNodeIsNotAObjectException, IOException {
        jsonDictionaryRepository.save(testDictionaryName);
        Assertions.assertTrue(jsonDictionaryRepository.exists(testDictionaryName));
    }

    @Test
    @Order(2)
    @DisplayName("Create dictionary with content")
    public void shouldCreateADirectoryWithDefaultData() throws JsonNodeIsNotAObjectException, IOException, CanNotCreateDictionaryException {
        Dictionary dictionary = new Dictionary(testDictionaryWriteName, new String[]{"one", "two", "three", "fourth"});
        jsonDictionaryRepository.save(dictionary.getName(), dictionary);
    }

    @Test
    @Order(3)
    @DisplayName("Get dictionary with content")
    public void shouldGetADictionary() throws DictionaryNotFoundException, IOException {
        Dictionary dictionary = jsonDictionaryRepository.get(testDictionaryWriteName);

        Assertions.assertEquals(dictionary.getName(), testDictionaryWriteName);
        Assertions.assertArrayEquals(new String[]{"one", "two", "three", "fourth"}, dictionary.getWords());
    }

    @Test
    @Order(4)
    @DisplayName("Update dictionary")
    public void shouldUpdateDictionary() throws DictionaryNotFoundException, JsonNodeIsNotAObjectException, IOException {
        Dictionary dictionary = new Dictionary("test", new String[]{"one", "three"});

        jsonDictionaryRepository.update(testDictionaryWriteName, dictionary);

        Dictionary updatedDictionary = jsonDictionaryRepository.get(testDictionaryWriteName);

        Assertions.assertArrayEquals(new String[]{"one", "three"}, updatedDictionary.getWords());
    }

    @Test
    @Order(5)
    @DisplayName("Delete dictionaries")
    public void shouldDeleteADictionary() throws DictionaryNotFoundException {
        jsonDictionaryRepository.delete(testDictionaryName);
        jsonDictionaryRepository.delete(testDictionaryWriteName);

        Assertions.assertFalse(jsonDictionaryRepository.exists(testDictionaryName));
        Assertions.assertFalse(jsonDictionaryRepository.exists(testDictionaryWriteName));
    }
}
