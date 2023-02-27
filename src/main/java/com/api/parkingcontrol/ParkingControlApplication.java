package com.api.parkingcontrol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController /*para essa classe saber que ela é um Bean do tipo Controller
e ser gerado as dependencias quando for necessário*/

public class ParkingControlApplication {/*é essa classe que ia inicializar a aplicação*/

	public static void main(String[] args) {
		SpringApplication.run(ParkingControlApplication.class, args);
	}

	@GetMapping("/") //a URI é apenas uma barra = / para indicar que será mostrado na raiz
	public String index(){
		return "Olá Mundo";
	}

}
