package com.github.zethi.monkeytypebackendclone.repositorys;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.zethi.monkeytypebackendclone.entity.Dictionary;
import com.github.zethi.monkeytypebackendclone.entity.JSON;
import com.github.zethi.monkeytypebackendclone.exceptions.*;
import com.github.zethi.monkeytypebackendclone.services.FileService;
import com.github.zethi.monkeytypebackendclone.utils.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JSONDictionaryRepository implements DictionaryRepository {

    private final String dictionaryPath;
    private final FileService fileService;
    private final JsonParser jsonParser;
    private final ObjectMapper objectMapper;

    @Autowired
    public JSONDictionaryRepository(String dictionaryPath, FileService fileService, JsonParser jsonParser, ObjectMapper objectMapper) {
        this.dictionaryPath = dictionaryPath;
        this.fileService = fileService;
        this.jsonParser = jsonParser;
        this.objectMapper = objectMapper;
    }

    @Override
    public Dictionary get(String name) throws IOException, DictionaryNotFoundException {
        Path path = Paths.get(this.dictionaryPath + "/" + name + ".json");

        if (!this.exists(name)) throw new DictionaryNotFoundException("Dictionary with name '" + name + "' not found");

        String data = fileService.read(path);
        JSON json = jsonParser.stringToJson(data);

        String dictionaryName = json.getValue("name", String.class).orElse(name);
        String[] words = json.getValue("words", String[].class).orElse(new String[]{});

        return new Dictionary(dictionaryName, words);
    }

    @Override
    public void save(String name, Dictionary dictionary) throws IOException, JsonNodeIsNotAObjectException, CanNotCreateDictionaryException {
        Path path = Paths.get(this.dictionaryPath + "/" + name + ".json");

        JSON json = new JSON(objectMapper);
        json.setField("name", dictionary.getName());
        json.setField("words", dictionary.getWords());

        try{
            fileService.createFile(path, json.toString());
        } catch (CanNotCreateFileException exception) {
            throw new CanNotCreateDictionaryException();
        }
    }

    @Override
    public void save(String name) throws FileAlreadyExistsException, CanNotCreateDictionaryException {
        Path path = Paths.get(this.dictionaryPath + "/" + name + ".json");

        try {
            fileService.createFile(path);
        } catch (CanNotCreateFileException exception) {
            throw new CanNotCreateDictionaryException();
        }
    }

    @Override
    public void delete(String name) throws DictionaryNotFoundException {
        Path path = Paths.get(this.dictionaryPath + "/" + name + ".json");

        if (!this.exists(name)) throw new DictionaryNotFoundException("Dictionary with name '" + name + "' not found");

        fileService.delete(path);
    }

    @Override
    public boolean exists(String name) {
        Path path = Paths.get(this.dictionaryPath + "/" + name + ".json");
        return fileService.exists(path);
    }

    @Override
    public void update(String name, Dictionary dictionary) throws DictionaryNotFoundException, JsonNodeIsNotAObjectException, IOException {
        Path path = Paths.get(this.dictionaryPath + "/" + name + ".json");

        if (!this.exists(dictionary.getName())) throw new DictionaryNotFoundException();

        JSON json = new JSON(objectMapper);
        json.setField("name", dictionary.getName());
        json.setField("words", dictionary.getWords());

        this.fileService.write(path, json.toString());
    }
}
