package com.artsem.api.authservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfiguration {

    @Bean
    public OpenAPI defineOpenApi() {
        Server server = new Server();
        server.setUrl("http://localhost:8081");
        server.setDescription("Authentication service");

        Contact myContact = new Contact();
        myContact.setName("Artsem Maiseyenka");
        myContact.setEmail("artsem.maiseyenka@gmail.com");

        Info information = new Info()
                .title("Authentication Service")
                .version("1.0")
                .description("This API exposes endpoints to authenticate users, manage them and their roles and groups")
                .contact(myContact);
        return new OpenAPI().info(information).servers(List.of(server));
    }

}