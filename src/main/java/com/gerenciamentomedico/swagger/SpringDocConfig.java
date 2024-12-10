package com.gerenciamentomedico.swagger;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI openApi(){
        return new OpenAPI()
                .info(new Info()
                        .title("API para o gerenciamento de cadastros médicos")
                        .version("v1")
                        .description("REST API test")

                )
                .externalDocs(new ExternalDocumentation()
                        .description("Repositório")
                        .url("https://github.com/raphdgg/gerenciamentomedico")
                )
                .tags(
                        Arrays.asList(
                                new Tag().name("Pacientes").description("Movimentações dos pacientes"),
                                new Tag().name("Médicos").description("Movimentações dos médicos"),
                                new Tag().name("Consultas").description("Movimentações das consultas")
                        )
                );
    }
}
