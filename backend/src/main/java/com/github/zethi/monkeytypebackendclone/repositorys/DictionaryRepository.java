package com.github.zethi.monkeytypebackendclone.repositorys;

import com.github.zethi.monkeytypebackendclone.entity.Dictionary;
import com.github.zethi.monkeytypebackendclone.exceptions.*;

import java.io.IOException;

public interface DictionaryRepository {
    Dictionary get(String name) throws IOException, DictionaryNotFoundException;
    void save(String name, Dictionary dictionary) throws IOException, JsonNodeIsNotAObjectException, DictionaryAlreadyExistsException, CanNotCreateDictionaryException;
    void save(String name) throws IOException, DictionaryAlreadyExistsException, CanNotCreateDictionaryException;
    void delete(String name) throws DictionaryNotFoundException;
    boolean exists(String name);
    void update(String name, Dictionary dictionary) throws DictionaryNotFoundException, JsonNodeIsNotAObjectException, IOException;
}
