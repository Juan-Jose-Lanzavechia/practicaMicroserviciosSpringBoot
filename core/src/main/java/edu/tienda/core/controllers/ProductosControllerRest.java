package edu.tienda.core.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.tienda.core.configurations.ConfigurationParameters;
import edu.tienda.core.domain.Producto;
import edu.tienda.core.services.ProductoService;

@RestController
@RequestMapping("/productos")
public class ProductosControllerRest {
	
	@Autowired
	@Lazy
	private ProductoService productosService;
	
	@Autowired
	private ConfigurationParameters configurationParameters;
	
	
	@GetMapping
	public ResponseEntity<?> getProductos(){
		
		System.out.println("params: " + configurationParameters.toString());
		
		//Se recuperan todos los productos del servicio
		List<Producto> productos=productosService.getProductos();
		
		//Retornamos los productos del servicio en el body de la respuesta
		return ResponseEntity.ok(productos);
	}
	
	@GetMapping("/fake-productos")
	public ResponseEntity<?> fakeProductosAPI(){
		
		List<Producto> productos=new ArrayList<>(Arrays.asList(
				
				new Producto(1,"Camiseta Juventus", 1200.0,4),
				new Producto(2, "Camiseta River Plate", 1500.0, 2),
				new Producto(3, "Camiseta Boca Juniors", 500.0, 6)
				
				)
		);
		
		return ResponseEntity.ok(productos);
	}
	
}
