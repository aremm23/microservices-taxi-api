package com.artsem.api.driverservice.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class OpenAPIConfiguration {

    private final OpenAPIProperties properties;

    @Bean
    public OpenAPI defineOpenApi() {
        return new OpenAPI()
                .info(getApiInfo())
                .servers(getServers())
                .components(getComponents());
    }

    private Info getApiInfo() {
        OpenAPIProperties.InfoProperties info = properties.getInfo();
        return new Info()
                .title(info.getTitle())
                .version(info.getVersion())
                .description(info.getDescription())
                .contact(getContact());
    }

    private Contact getContact() {
        OpenAPIProperties.ContactProperties contact = properties.getContact();
        return new Contact()
                .name(contact.getName())
                .email(contact.getEmail());
    }

    private List<Server> getServers() {
        OpenAPIProperties.ServerProperties serverProperties = properties.getServer();
        Server server = new Server()
                .url(serverProperties.getUrl())
                .description(serverProperties.getDescription());
        return List.of(server);
    }

    private Components getComponents() {
        return new Components().addSecuritySchemes("bearerAuth", getSecurityScheme());
    }

    private SecurityScheme getSecurityScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .name("Authorization")
                .description("Enter your Bearer token here");
    }
}
