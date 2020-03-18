package com.index.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootLuceneApplication {

	public static void main(String[] args) {
		
		for(String arg:args) {
            System.out.println("*******CL Argument*****"+arg);       
            
      }

		
		SpringApplication.run(SpringBootLuceneApplication.class, args);
	}

}
