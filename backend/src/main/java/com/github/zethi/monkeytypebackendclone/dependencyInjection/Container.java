package com.github.zethi.monkeytypebackendclone.dependencyInjection;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.zethi.monkeytypebackendclone.entity.JSON;
import com.github.zethi.monkeytypebackendclone.repositorys.DictionaryRepository;
import com.github.zethi.monkeytypebackendclone.repositorys.JSONDictionaryRepository;
import com.github.zethi.monkeytypebackendclone.services.FileService;
import com.github.zethi.monkeytypebackendclone.services.JsonParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public final class Container {

    @Bean
    public ObjectMapper instanceObjectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public JSON JSON(@Autowired ObjectMapper objectMapper) {
        return new JSON(objectMapper);
    }

    @Bean
    public DictionaryRepository dictionaryRepository(@Autowired FileService fileService, @Autowired JsonParserService jsonParser, @Autowired ObjectMapper objectMapper) {
        return new JSONDictionaryRepository("data/dictionaries", fileService, jsonParser, objectMapper);
    }

}
