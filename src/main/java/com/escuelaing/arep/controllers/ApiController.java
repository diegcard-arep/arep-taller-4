package com.escuelaing.arep.controllers;

import com.escuelaing.arep.annotations.GetMapping;
import com.escuelaing.arep.annotations.PostMapping;
import com.escuelaing.arep.annotations.RequestParam;
import com.escuelaing.arep.annotations.RestController;

/**
 * API Controller que maneja los endpoints que el frontend está llamando.
 * Devuelve respuestas en formato JSON para ser parseadas por JavaScript.
 */
@RestController
public class ApiController {

    @GetMapping("/api/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        // Retornamos JSON como string para que el frontend pueda parsearlo
        return String.format("{\"message\": \"Hello, %s!\", \"timestamp\": \"%d\", \"status\": \"success\"}", 
                           name, System.currentTimeMillis());
    }

    @GetMapping("/api/weather")
    public String weather() {
        // Simulamos información del clima de Bogotá
        return "{"
                + "\"city\": \"Bogotá\","
                + "\"temperature\": \"18°C\","
                + "\"description\": \"Parcialmente nublado\","
                + "\"humidity\": \"75%\","
                + "\"message\": \"Datos simulados del servidor HTTP\","
                + "\"timestamp\": " + System.currentTimeMillis() + ","
                + "\"status\": \"success\""
                + "}";
    }

    @GetMapping("/api/quote")
    public String quote() {
        // Lista de citas inspiradoras para retornar aleatoriamente
        String[] quotes = {
            "{\"content\": \"El éxito es la suma de pequeños esfuerzos repetidos día tras día.\", \"author\": \"Robert Collier\"}",
            "{\"content\": \"La única forma de hacer un gran trabajo es amar lo que haces.\", \"author\": \"Steve Jobs\"}",
            "{\"content\": \"El futuro pertenece a quienes creen en la belleza de sus sueños.\", \"author\": \"Eleanor Roosevelt\"}",
            "{\"content\": \"No esperes por el momento perfecto, toma el momento y hazlo perfecto.\", \"author\": \"Anónimo\"}",
            "{\"content\": \"El código es como el humor. Cuando tienes que explicarlo, es malo.\", \"author\": \"Cory House\"}"
        };
        
        // Seleccionar una cita aleatoria
        int randomIndex = (int) (Math.random() * quotes.length);
        String selectedQuote = quotes[randomIndex];
        
        // Agregar campos adicionales
        return selectedQuote.replace("}", 
                ", \"message\": \"Cita inspiradora del día\", "
                + "\"timestamp\": " + System.currentTimeMillis() + ", "
                + "\"status\": \"success\"}");
    }

    // Métodos POST equivalentes para manejar las peticiones POST del frontend
    @PostMapping("/api/hello")
    public String helloPost(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("{\"message\": \"Hello, %s! (via POST)\", \"timestamp\": \"%d\", \"status\": \"success\"}", 
                           name, System.currentTimeMillis());
    }

    @PostMapping("/api/weather")
    public String weatherPost() {
        return "{"
                + "\"city\": \"Bogotá\","
                + "\"temperature\": \"19°C\","
                + "\"description\": \"Soleado (via POST)\","
                + "\"humidity\": \"70%\","
                + "\"message\": \"Datos del clima via POST\","
                + "\"timestamp\": " + System.currentTimeMillis() + ","
                + "\"status\": \"success\""
                + "}";
    }

    @PostMapping("/api/quote")
    public String quotePost() {
        String[] quotes = {
            "{\"content\": \"El éxito no es definitivo, el fracaso no es fatal: lo que cuenta es el valor para continuar.\", \"author\": \"Winston Churchill\"}",
            "{\"content\": \"La innovación distingue entre un líder y un seguidor.\", \"author\": \"Steve Jobs\"}",
            "{\"content\": \"El único modo de hacer un gran trabajo es amar lo que haces.\", \"author\": \"Steve Jobs\"}",
            "{\"content\": \"El progreso es imposible sin cambio, y aquellos que no pueden cambiar sus mentes no pueden cambiar nada.\", \"author\": \"George Bernard Shaw\"}"
        };
        
        int randomIndex = (int) (Math.random() * quotes.length);
        String selectedQuote = quotes[randomIndex];
        
        return selectedQuote.replace("}", 
                ", \"message\": \"Cita inspiradora via POST\", "
                + "\"timestamp\": " + System.currentTimeMillis() + ", "
                + "\"status\": \"success\"}");
    }
}