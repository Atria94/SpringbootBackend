package com.example.demo.services;

import java.text.MessageFormat;
import java.util.ArrayList;

import com.example.demo.models.Person;
import com.example.demo.models.Persons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RickAndMortyService {
    @Autowired
    RestTemplate restTemplate;

    public Person getCharacterFromAPI(){
        //TODO mejorar el random
        long randomNumber=Math.round(Math.random()*826)+1;
        String url= "https://rickandmortyapi.com/api/character/" +randomNumber ;
        Person person = restTemplate.getForObject(url, Person.class);
        return person;
    }
    public ArrayList<Person> getCharactersFromAPI(){
        ArrayList<Person> allPersons = new ArrayList<Person>();
        for(int i=0;i<=42;i++){
            String url = "https://rickandmortyapi.com/api/character/";
            Persons persons = restTemplate.getForObject(url, Persons.class);
            allPersons.addAll(persons.results);
        }
        
        return allPersons;
    }
}
