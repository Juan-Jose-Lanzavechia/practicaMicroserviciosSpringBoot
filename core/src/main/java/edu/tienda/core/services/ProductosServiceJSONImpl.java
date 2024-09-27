package edu.tienda.core.services;

import java.io.IOException;
import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.tienda.core.domain.Producto;


@Service ("JSON")
@ConditionalOnProperty(
		value= "productos.estrategia",
		havingValue = "EN_JSON")
public class ProductosServiceJSONImpl implements ProductoService {

	@Override
	public List<Producto> getProductos() {
		
		List<Producto> productos;
		
		try {
			
			productos=new ObjectMapper()
				.readValue(this.getClass().getResourceAsStream("/productos.json"),
				new TypeReference<List<Producto>>() {});
			
			return productos;
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
