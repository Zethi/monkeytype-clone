package com.github.zethi.monkeytypebackendclone.services;

import com.github.zethi.monkeytypebackendclone.entity.Dictionary;
import com.github.zethi.monkeytypebackendclone.exceptions.CanNotCreateDictionaryException;
import com.github.zethi.monkeytypebackendclone.exceptions.DictionaryAlreadyExistsException;
import com.github.zethi.monkeytypebackendclone.exceptions.DictionaryNotFoundException;
import com.github.zethi.monkeytypebackendclone.exceptions.JsonNodeIsNotAObjectException;
import com.github.zethi.monkeytypebackendclone.utils.Randomizer;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public final class DictionaryServiceTest {

    private final DictionaryService dictionaryService;
    private final String noWrittenDictionaryName;
    private final String writtenDictionaryName;

    @Autowired
    public DictionaryServiceTest(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
        this.noWrittenDictionaryName = "testService";
        this.writtenDictionaryName = "testService-write";
    }

    @Test
    @Order(1)
    @DisplayName("Should create a blank dictionary")
    public void shouldSaveABlankDictionary() throws DictionaryAlreadyExistsException, CanNotCreateDictionaryException, IOException, JsonNodeIsNotAObjectException {
        this.dictionaryService.save(noWrittenDictionaryName);

        Assertions.assertDoesNotThrow(() -> {
            this.dictionaryService.get(noWrittenDictionaryName);
        });
    }

    @Test
    @Order(2)
    @DisplayName("Should save a written dictionary")
    public void shouldSaveAWrittenDictionary() throws JsonNodeIsNotAObjectException, DictionaryAlreadyExistsException, CanNotCreateDictionaryException, IOException, DictionaryNotFoundException {

        String[] words = Randomizer.generateRandomStringArray(4, 5);
        Dictionary dictionary = new Dictionary(writtenDictionaryName, words);
        this.dictionaryService.save(writtenDictionaryName, dictionary);

        Assertions.assertDoesNotThrow(() -> {
            this.dictionaryService.get(writtenDictionaryName);
        });

        Assertions.assertEquals(dictionaryService.get(writtenDictionaryName).getName(), writtenDictionaryName);
        Assertions.assertArrayEquals(dictionaryService.get(writtenDictionaryName).getWords(), words);
    }

    @Test
    @Order(3)
    @DisplayName("Should update a dictionary")
    public void shouldUpdateDictionary() throws DictionaryNotFoundException, JsonNodeIsNotAObjectException, IOException {
        String updatedName = writtenDictionaryName;
        String[] updatedWords = Randomizer.generateRandomStringArray(4, 5);
        Dictionary updatedDictionary = new Dictionary(updatedName, updatedWords);
        this.dictionaryService.update(updatedName, updatedDictionary);

        Assertions.assertDoesNotThrow(() -> {
            this.dictionaryService.get(updatedName);
        });

        Assertions.assertEquals(this.dictionaryService.get(updatedName).getName(), updatedName);
        Assertions.assertArrayEquals(this.dictionaryService.get(updatedName).getWords(), updatedWords);
    }

    @Test
    @Order(4)
    @DisplayName("Should delete all the files created for test")
    public void shouldDeleteDictionaries() throws DictionaryNotFoundException {
        this.dictionaryService.delete(noWrittenDictionaryName);
        this.dictionaryService.delete(writtenDictionaryName);

        Assertions.assertThrows(DictionaryNotFoundException.class,
                () -> Assertions.assertNull(this.dictionaryService.get(noWrittenDictionaryName))
        );

        Assertions.assertThrows(DictionaryNotFoundException.class,
                () -> Assertions.assertNull(this.dictionaryService.get(writtenDictionaryName))
        );
    }

}
