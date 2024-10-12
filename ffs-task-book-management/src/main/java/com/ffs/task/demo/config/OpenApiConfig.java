package com.ffs.task.demo.config;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "hussein",
                        email = "huss@contact.com",
                        url = "http://ffs.com"
                ),
                description = "OpenApi Documentation for book management app",
                title = "Api Documentation- book store app ",
                version = "1.0",
                license = @License(
                        name = "Licenece name",
                        url = "http://licence"
                ),
                termsOfService = "Terms Of Service"
        ),
        servers = {
                @Server(
                        description = "Local Env",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "Prod Env",
                        url = "http://localhost:8080"
                )
        })


public class OpenApiConfig{
}
