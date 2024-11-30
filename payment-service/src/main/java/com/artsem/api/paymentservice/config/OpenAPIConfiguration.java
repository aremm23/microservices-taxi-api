package com.artsem.api.paymentservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfiguration {

    private final String contactEmail;
    private final String contactName;
    private final String serviceUrl;
    private final String serviceDescription;
    private final String infoTitle;
    private final String infoVersion;
    private final String infoDescription;

    public OpenAPIConfiguration(
            @Value("${openapi.contact.email}") String contactEmail,
            @Value("${openapi.contact.name}") String contactName,
            @Value("${openapi.service.url}") String serviceUrl,
            @Value("${openapi.service.description}") String serviceDescription,
            @Value("${openapi.info.title}") String infoTitle,
            @Value("${openapi.info.version}") String infoVersion,
            @Value("${openapi.info.description}") String infoDescription
    ) {
        this.contactEmail = contactEmail;
        this.contactName = contactName;
        this.serviceUrl = serviceUrl;
        this.serviceDescription = serviceDescription;
        this.infoTitle = infoTitle;
        this.infoVersion = infoVersion;
        this.infoDescription = infoDescription;
    }

    @Bean
    public OpenAPI defineOpenApi() {
        Server server = new Server();
        server.setUrl(serviceUrl);
        server.setDescription(serviceDescription);

        Contact myContact = new Contact();
        myContact.setName(contactName);
        myContact.setEmail(contactEmail);

        Info information = new Info()
                .title(infoTitle)
                .version(infoVersion)
                .description(infoDescription)
                .contact(myContact);
        return new OpenAPI()
                .info(information)
                .servers(List.of(server));
    }
}