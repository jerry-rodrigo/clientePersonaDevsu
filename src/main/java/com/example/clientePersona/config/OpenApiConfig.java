package com.example.clientePersona.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración para OpenAPI/Swagger para documentar la API REST.
 * Esta clase define la configuración del documento OpenAPI generado automáticamente.
 */
@Configuration
public class OpenApiConfig {

    /**
     * Configura y personaliza el objeto OpenAPI para la API de clientes y personas.
     *
     * @return Un objeto OpenAPI personalizado con el título, la versión y la descripción de la API.
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Cliente-Persona API")
                        .version("1.0")
                        .description("API para gestionar clientes y personas en la aplicación")
                );
    }
}
