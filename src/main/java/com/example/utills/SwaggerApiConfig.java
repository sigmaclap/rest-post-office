package com.example.utills;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.Generated;

@OpenAPIDefinition(
        info = @Info(
                title = "Почтовое отслеживание",
                description = "В системе реализована регистрация почтовых отправлений — письма, " +
                        "посылки — их передвижение между почтовыми отделениями, " +
                        "а также возможность получения информации и всей " +
                        "истории передвижения конкретного почтового отправления.", version = "1.0.0",
                contact = @Contact(
                        name = "Ilya Shubin"
                )
        )
)
@Generated
public class SwaggerApiConfig {
}
