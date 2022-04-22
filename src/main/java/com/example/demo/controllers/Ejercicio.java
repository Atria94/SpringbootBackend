package com.example.demo.controllers;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import com.example.demo.models.Joke;
import com.example.demo.models.Person;
import com.example.demo.services.JokeService;
import com.example.demo.services.RickAndMortyService;
import com.example.demo.utils.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Ejercicio {

    @Autowired
    RickAndMortyService rickAndMortyService;

    @Autowired
    JokeService jokeService;

    //http://localhost:8080/
    @GetMapping("/")
    public String greet(){
        return "Bienvenido al servidor backend";
    }

    //http://localhost:8080/aleatorio
    @GetMapping("/aleatorio")
    public String randomNumber(){
        long r = Math.round(Math.random()*100);
        return r + "";
    }

    //http://localhost:8080/palindromo/ana
    @GetMapping("/palindromo/{name}")
    public String Palindrome(@PathVariable String name){
        boolean palindrome= Utils.isPalindrome(name);
        return palindrome ? "Si es palindromo" : "No es palindromo";
    }

    //http://localhost:8080/sumar?num1=5&num2=2
    @GetMapping("/sumar")
    public String add(@RequestParam String num1,
                        @RequestParam String num2){
        int resultado = Integer.parseInt(num1) + Integer.parseInt(num2);
        //return "La suma de "+num1+" y el numero "+num2+" es "+resultado;
        Object params[] = {num1, num2, resultado};
        return MessageFormat.format("La suma de {0} y {1} es {2}", params);
    }
    @PostMapping("/saveProductOnDisk")
    public String saveProductOnDisk(@RequestParam Map<String, String> body){
        //obtener los datos de un producto {articulo, precio}
        String article = body.get("article");
        String price = body.get("price");
        
        //valido que el articulo precio no sean vacios
        if(article.equals("") || price.equals(""))   return "Error, datos incorrectos";
       
        //valido que el precio no sea negativo
        if(Integer.parseInt(price) < 0)   return "Error el precio es negativo";

        //guardo en el disco duro esa informacion
        try {
            Utils.save("datos.txt", article+", "+price+"\n");
        } catch (IOException e) {
            e.printStackTrace();
            return "Error al guardar en disco";
        }

        //devuelvo un msg al cliente "producto guarddado correctamente"
        return "Producto guardado correctamente";
    }

    @PostMapping("/removeFile")
    public String removeFile(){
        boolean result=Utils.remove("datos.txt");
        
        return result ? "borrado correcto" : "no se puede borrar";
    }

    //http://localhost:8080/rickandmorty/random
    @GetMapping("/rickandmorty/random")
    public String getRickAndMortyRandom(){
        Person c= rickAndMortyService.getCharacterFromAPI();
        return "<img src='"+c.image+"'/>";
        //return MessageFormat.format("<img src='{0}'/>",c.image);
    }

    @GetMapping("/rickandmorty/list")
    public String getRickAndMortyRandomList(){
        String web="<h1>Lista de personas</h1>";
       ArrayList<Person> persons = rickAndMortyService.getCharactersFromAPI();
       for (Person person : persons) {
           web+="<img src='"+person.image+"'/>";
       }
       return web;
    }

    //Listar chistes
    @GetMapping("/listarchistes")
    public String jokeList(){
        ArrayList<Joke> jokes = jokeService.getAllJokes();
        String listado = "";
        for(Joke joke: jokes){
            listado+= joke.getText();
            listado += "<br/>";
        }
        return listado;
    }

    //Insertar un nuevo chiste
    @PostMapping("/insertarchiste")
    public String addJoke(@RequestParam Map<String, String> body){
        String jokeText = body.get("text");
        jokeText.replaceAll("<", "");
        jokeText.replaceAll(">", "");
        Joke joke=new Joke();
        joke.setText(jokeText);
        jokeService.saveJoke(joke);
        return "Chiste creado correctamente";
    }
}
