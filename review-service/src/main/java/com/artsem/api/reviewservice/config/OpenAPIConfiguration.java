package com.artsem.api.reviewservice.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfiguration {

    private final String serverUrl;
    private final String serverDescription;
    private final String contactName;
    private final String contactEmail;
    private final String apiTitle;
    private final String apiVersion;
    private final String apiDescription;

    public OpenAPIConfiguration(
            @Value("${openapi.service.url}") String serverUrl,
            @Value("${openapi.service.description}") String serverDescription,
            @Value("${openapi.contact.name}") String contactName,
            @Value("${openapi.contact.email}") String contactEmail,
            @Value("${openapi.info.title}") String apiTitle,
            @Value("${openapi.info.version}") String apiVersion,
            @Value("${openapi.info.description}") String apiDescription) {
        this.serverUrl = serverUrl;
        this.serverDescription = serverDescription;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
        this.apiTitle = apiTitle;
        this.apiVersion = apiVersion;
        this.apiDescription = apiDescription;
    }

    @Bean
    public OpenAPI defineOpenApi() {
        return new OpenAPI()
                .info(getApiInfo())
                .servers(getServers())
                .components(getComponents());
    }

    private Info getApiInfo() {
        return new Info()
                .title(apiTitle)
                .version(apiVersion)
                .description(apiDescription)
                .contact(getContact());
    }

    private Contact getContact() {
        Contact contact = new Contact();
        contact.setName(contactName);
        contact.setEmail(contactEmail);
        return contact;
    }

    private List<Server> getServers() {
        Server server = new Server();
        server.setUrl(serverUrl);
        server.setDescription(serverDescription);
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