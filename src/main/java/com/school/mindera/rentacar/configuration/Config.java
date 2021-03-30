
package com.school.mindera.rentacar.configuration;

import org.apache.catalina.filters.CorsFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;

/**
 * Config class for configurations
 */
@Configuration
public class Config {
    /**
     * corsFilter for filtering a registration
     * @return a FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedMethod("*");
        config.addAllowedOrigin("*");
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter());
        bean.setOrder(0);
        return bean;
    }

}
