package com.github.zethi.monkeytypebackendclone.services;

import com.github.zethi.monkeytypebackendclone.entity.Dictionary;
import com.github.zethi.monkeytypebackendclone.exceptions.*;
import com.github.zethi.monkeytypebackendclone.repositorys.DictionaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public final class DictionaryService {

    private final DictionaryRepository dictionaryRepository;

    @Autowired
    public DictionaryService(DictionaryRepository dictionaryRepository) {
        this.dictionaryRepository = dictionaryRepository;
    }


    public void save(String name) throws DictionaryAlreadyExistsException, IOException, CanNotCreateDictionaryException, JsonNodeIsNotAObjectException {
        this.dictionaryRepository.save(name);
    }

    public void save(String name, Dictionary dictionary) throws JsonNodeIsNotAObjectException, DictionaryAlreadyExistsException, IOException, CanNotCreateDictionaryException {
        this.dictionaryRepository.save(name, dictionary);
    }

    public void update(String name, Dictionary newDictionary) throws DictionaryNotFoundException, JsonNodeIsNotAObjectException, IOException {
        this.dictionaryRepository.update(name, newDictionary);
    }

    public void delete(String name) throws DictionaryNotFoundException {
        this.dictionaryRepository.delete(name);
    }

    public Dictionary get(String name) throws DictionaryNotFoundException, IOException {
        return this.dictionaryRepository.get(name);
    }

}
