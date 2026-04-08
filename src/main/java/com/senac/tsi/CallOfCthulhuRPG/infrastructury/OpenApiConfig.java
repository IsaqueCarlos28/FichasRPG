package com.senac.tsi.CallOfCthulhuRPG.infrastructury;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "CallOfCthulhuRPG Fichas",
                version = "0.1.0",
                description = "Crie e Utilize a sua fichas para jogar CallOfCthulhu com seus amigos!",
                contact = @Contact(
                        name = "Isaque Carlos",
                        email = "isaquecarlos2016@gmail.com"
                ),
                license = @License(
                        name = "MIT License",
                        url = "https://opensource.org" // URL to the MIT license
                )
        )
)
public class OpenApiConfig {
}
