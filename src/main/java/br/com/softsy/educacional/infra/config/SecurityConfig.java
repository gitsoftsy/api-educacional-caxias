package br.com.softsy.educacional.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncrypt getPasswordEncrypt() {
        return new PasswordEncrypt();
    }
}
