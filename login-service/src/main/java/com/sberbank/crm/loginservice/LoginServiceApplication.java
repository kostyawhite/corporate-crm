package com.sberbank.crm.loginservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

// Отключен обработчик ошибок MVC Spring
// Начиная с Vaadin 14 это вызывает странное поведение перезагрузки
@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
public class LoginServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoginServiceApplication.class, args);
	}

}