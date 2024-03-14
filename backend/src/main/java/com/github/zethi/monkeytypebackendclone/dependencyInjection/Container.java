package com.github.zethi.monkeytypebackendclone.dependencyInjection;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.zethi.monkeytypebackendclone.entity.JSON;
import com.github.zethi.monkeytypebackendclone.repositorys.DictionaryRepository;
import com.github.zethi.monkeytypebackendclone.repositorys.implementation.JSONDictionaryRepositoryImpl;
import com.github.zethi.monkeytypebackendclone.services.FileService;
import com.github.zethi.monkeytypebackendclone.utils.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public final class Container {

    private final Environment environment;

    @Autowired
    public Container(Environment environment) {
        this.environment = environment;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public ObjectMapper instanceObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.registerModule(new JavaTimeModule());

        return objectMapper;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public JSON JSON(@Autowired ObjectMapper objectMapper) {
        return new JSON(objectMapper);
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public DictionaryRepository dictionaryRepository(@Autowired FileService fileService, @Autowired JsonParser jsonParser, @Autowired ObjectMapper objectMapper) {
        return new JSONDictionaryRepositoryImpl(environment.getProperty("dictionaries.path"), fileService, jsonParser, objectMapper);
    }

}
