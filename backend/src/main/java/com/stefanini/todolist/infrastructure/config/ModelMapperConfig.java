package com.stefanini.todolist.infrastructure.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração do ModelMapper para mapeamento de objetos.
 *
 * <p>O ModelMapper é utilizado para converter entre entidades de domínio e DTOs,
 * reduzindo o código boilerplate e facilitando a manutenção.</p>
 *
 * @author Stefanini Challenge
 * @version 1.0
 * @since 2026-01-06
 */
@Configuration
public class ModelMapperConfig {

    /**
     * Cria e configura uma instância do ModelMapper.
     *
     * @return Instância configurada do ModelMapper
     */
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Configurações personalizadas podem ser adicionadas aqui
        modelMapper.getConfiguration()
                .setSkipNullEnabled(true)
                .setAmbiguityIgnored(true);

        return modelMapper;
    }
}

