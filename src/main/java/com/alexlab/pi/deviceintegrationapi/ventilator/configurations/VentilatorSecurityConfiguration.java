package com.alexlab.pi.deviceintegrationapi.ventilator.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order(99)
public class VentilatorSecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
      http.antMatcher("/api/ventilator/**")
          .authorizeRequests()
              .anyRequest().permitAll().and().csrf().disable();
  }
}
