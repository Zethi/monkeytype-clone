package com.github.zethi.monkeytypebackendclone.controllers;

import com.github.zethi.monkeytypebackendclone.entity.Dictionary;
import com.github.zethi.monkeytypebackendclone.exceptions.CanNotCreateDictionaryException;
import com.github.zethi.monkeytypebackendclone.exceptions.DictionaryAlreadyExistsException;
import com.github.zethi.monkeytypebackendclone.exceptions.DictionaryNotFoundException;
import com.github.zethi.monkeytypebackendclone.exceptions.JsonNodeIsNotAObjectException;
import com.github.zethi.monkeytypebackendclone.services.DictionaryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

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
    public ResponseEntity<Dictionary> getDictionary(@PathVariable String name) throws DictionaryNotFoundException, IOException {
        return ResponseEntity.ok(dictionaryService.get(name));
    }

    @DeleteMapping("/dictionary/{name}")
    public ResponseEntity<Map<String, String>> deleteDictionary(@PathVariable String name) throws DictionaryNotFoundException {
        dictionaryService.delete(name);

        Map<String, String> body = new HashMap<>();
        body.put("message", "Dictionary '" + name + "' has been deleted successfully");

        return ResponseEntity.ok().body(body);
    }

    @PutMapping("/dictionary/{name}")
    public ResponseEntity<Void> updateDictionary(@PathVariable String name, @Valid @RequestBody Dictionary dictionary) throws DictionaryNotFoundException, JsonNodeIsNotAObjectException, IOException {
        dictionaryService.update(name, dictionary);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/dictionary")
    public ResponseEntity<Void> createDictionary(@Valid @RequestBody Dictionary dictionary, UriComponentsBuilder uriComponentsBuilder) throws JsonNodeIsNotAObjectException, DictionaryAlreadyExistsException, IOException, CanNotCreateDictionaryException {
        dictionaryService.save(dictionary.getName(), dictionary);

        final URI locationOfNewDictionary = uriComponentsBuilder
                .path("/api/dictionary/{name}")
                .buildAndExpand(dictionary.getName())
                .toUri();

        return ResponseEntity.created(locationOfNewDictionary).build();
    }
}
