package com.artsem.api.rideservice.config;

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
        server.setUrl("http://localhost:8084");
        server.setDescription("Ride service");

        Contact myContact = new Contact();
        myContact.setName("Artsem Maiseyenka");
        myContact.setEmail("artsem.maiseyenka@gmail.com");

        Info information = new Info()
                .title("Ride Service")
                .version("1.0")
                .description("This API exposes endpoints to manage rides")
                .contact(myContact);
        return new OpenAPI().info(information).servers(List.of(server));
    }
}