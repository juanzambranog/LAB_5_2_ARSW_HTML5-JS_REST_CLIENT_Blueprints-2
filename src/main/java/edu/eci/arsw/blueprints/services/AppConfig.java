package edu.eci.arsw.blueprints.services;

import edu.eci.arsw.blueprints.main.App;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = App.class)
public class AppConfig {
}
