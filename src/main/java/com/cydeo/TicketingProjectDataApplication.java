package com.cydeo;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication//bu annotation @Configuration annotation unu kapsiyor.
public class TicketingProjectDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicketingProjectDataApplication.class, args);
    }

    //I am trying to add bean in the container throw @Bean annotation
    //Create a class annotated with @Configuration
    //Write a method which returns the object that you are trying to add in the container
    //Annotate this method with @Bean

    //ModelMapper mapper = new ModelMapper(); Tightly couple oldugu icin bu sekilde degil bean seklinde eklemek istiyorum.
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    //ucuncu party bir library den obje loosely bir obje olusturmak istedigimiz zaman runner methodunu kullanabiliriz.Flyway, modelmapper gibi  gibi

}
