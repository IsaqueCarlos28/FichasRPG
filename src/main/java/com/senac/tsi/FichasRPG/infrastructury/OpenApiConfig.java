package com.senac.tsi.FichasRPG.infrastructury;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "FichasRPG",
                version = "0.0.1",
                description = """
                Crie e Utilize a sua fichas para jogar RPG com seus amigos!
                
                Sistemas de RPG suportados:
                - CallOfCthlhu;
                """,
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
