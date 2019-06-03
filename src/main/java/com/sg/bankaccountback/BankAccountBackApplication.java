package com.sg.bankaccountback;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication //equivalent to @Configuration + @EnableAutoConfiguration + @ComponentScan
@EnableResourceServer
@EnableSwagger2
public class BankAccountBackApplication {

    public static void main(String[] args) {

        SpringApplication.run(BankAccountBackApplication.class, args);
    }


}
