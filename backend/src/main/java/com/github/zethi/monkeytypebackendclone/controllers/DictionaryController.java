package com.github.zethi.monkeytypebackendclone.controllers;

import com.github.zethi.monkeytypebackendclone.entity.Dictionary;
import com.github.zethi.monkeytypebackendclone.exceptions.*;
import com.github.zethi.monkeytypebackendclone.services.DictionaryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public final class DictionaryController {

    private final DictionaryService dictionaryService;

    @Autowired
    public DictionaryController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @GetMapping("/dictionary/{name}")
    public ResponseEntity<?> getDictionary(@PathVariable String name) throws DictionaryNotFoundException, IOException {
        return new ResponseEntity<>(dictionaryService.get(name), HttpStatus.OK);
    }

    @DeleteMapping("/dictionary/{name}")
    public ResponseEntity<?> deleteDictionary(@PathVariable String name) throws DictionaryNotFoundException {
        dictionaryService.delete(name);

        return new ResponseEntity<>("Dictionary with name '" + name + "' has been deleted", HttpStatus.OK);
    }

    @PutMapping("/dictionary/{name}")
    public ResponseEntity<?> updateDictionary(@PathVariable String name, @Valid @RequestBody Dictionary dictionary) throws DictionaryNotFoundException, JsonNodeIsNotAObjectException, IOException {
        dictionaryService.update(name, dictionary);

        return new ResponseEntity<>("Dictionary with name '" + name + "' has been updated", HttpStatus.OK);
    }

    @PostMapping("/dictionary")
    public ResponseEntity<?> createDictionary(@Valid @RequestBody Dictionary dictionary) throws JsonNodeIsNotAObjectException, DictionaryAlreadyExistsException, IOException, CanNotCreateDictionaryException {
        dictionaryService.save(dictionary.getName(), dictionary);

        return new ResponseEntity<>("Dictionary with name '" + dictionary.getName() + "' has been created", HttpStatus.CREATED);
    }
}
