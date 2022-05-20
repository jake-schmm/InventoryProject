package com.example.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

import javax.json.*;
import java.io.FileReader;
import java.io.FileNotFoundException;

@SpringBootApplication
@EnableAutoConfiguration(exclude= {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class InventoryApplication {

	public static void main(String[] args) throws FileNotFoundException {
		SpringApplication.run(InventoryApplication.class, args);
	
	}

}
