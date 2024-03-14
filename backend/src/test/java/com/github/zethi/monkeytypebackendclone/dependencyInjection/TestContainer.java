package com.github.zethi.monkeytypebackendclone.dependencyInjection;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.zethi.monkeytypebackendclone.repositorys.DictionaryRepository;
import com.github.zethi.monkeytypebackendclone.repositorys.implementation.JSONDictionaryRepositoryImpl;
import com.github.zethi.monkeytypebackendclone.services.FileService;
import com.github.zethi.monkeytypebackendclone.utils.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestContainer {

    @Bean
    public DictionaryRepository dictionaryRepository(@Autowired FileService fileService, @Autowired JsonParser jsonParser, @Autowired ObjectMapper objectMapper) {
        return new JSONDictionaryRepositoryImpl("src/test/resources/data/dictionaries", fileService, jsonParser, objectMapper);
    }


}