package com.assignment.telus.todos.config;

import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
  @Bean
  public OpenAPI myOpenAPI() {

    Contact contact = new Contact();
    contact.setEmail("victorofff@gmail.com");
    contact.setName("Victor Zoubok");
    contact.setUrl("https://dummy.com");

    License mitLicense = new License().name("MIT License")
        .url("https://choosealicense.com/licenses/mit/");

    Info info = new Info()
        .title("Telus Todo Assignment")
        .version("1.0")
        .contact(contact)
        .description("This API exposes endpoints to manage tutorials.")
        .termsOfService("https://dummy.com/terms")
        .license(mitLicense);

    return new OpenAPI().info(info);
  }
}
