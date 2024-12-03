package com.artsem.api.rideservice.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Locale;

@Configuration
public class LocalizationConfig {

    public static final String DEFAULT_ENCODING = "ISO-8859-1";
    public static final String VALIDATION_BASENAME = "classpath:validation";
    private static final String EXCEPTION_BASENAME = "classpath:exceptions";

    @Bean
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver();
        resolver.setDefaultLocale(Locale.ENGLISH);
        return resolver;
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames(VALIDATION_BASENAME, EXCEPTION_BASENAME);
        messageSource.setDefaultEncoding(DEFAULT_ENCODING);
        return messageSource;
    }
}