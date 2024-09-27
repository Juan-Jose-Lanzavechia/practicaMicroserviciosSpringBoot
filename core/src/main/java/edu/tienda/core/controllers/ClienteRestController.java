package edu.tienda.core.controllers;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import edu.tienda.core.domain.Cliente;
import edu.tienda.core.exceptions.ResourceNotFoundException;
import edu.tienda.core.exceptions.BadRequestException;

@RestController
@RequestMapping("/clientes")
public class ClienteRestController {

	private List<Cliente> clientes= new ArrayList<>(Arrays.asList(
			
			new Cliente("arm","1234","Armtrong"),
			new Cliente("ald", "1234", "Aldrin"),
			new Cliente("col","1234","Collins")
			)
		);
	
	@GetMapping
	public ResponseEntity<?> getClientes(){
		
		return ResponseEntity.ok(clientes);
	}
	
	@GetMapping("/{userName}")
	public ResponseEntity<?> getCliente(@PathVariable String userName) {
		
		if(userName.length()!=3) {
			
			throw new BadRequestException("El parámetro nombre usuario debe contener 3 caracteres");
		}
		
		return clientes.stream().
				filter(cliente->cliente.getUsername().equalsIgnoreCase(userName))
				.findFirst()
				.map(ResponseEntity::ok)
				.orElseThrow(()-> new ResourceNotFoundException
						("Cliente" + userName + "no encontrado."));
				
	}
		
		/*for(Cliente cliente:clientes) {
			if (cliente.getUsername().equalsIgnoreCase(userName)) {
				return ResponseEntity.ok(cliente);
			}
		}
		throw new ResourceNotFoundException("Cliente no encontrado"); 
	}*/
		
		//Lo mismo que usando el for y el if pero usando programación funcional:
		/*return ResponseEntity.ok(clientes.stream().
				filter(cliente->cliente.getUsername().equalsIgnoreCase(userName)).
				findFirst().orElseThrow()); 
	}*/
	
	
	@PostMapping
	public ResponseEntity<?> altaCliente(@RequestBody Cliente cliente) {
		
		clientes.add(cliente);
		
		//Obteniendo URL del servicio
		
		URI location=ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{userName}")
				.buildAndExpand(cliente.getUsername())
				.toUri();
		
		return ResponseEntity.created(location).body(cliente);
	}
	
	
	@PutMapping
	public ResponseEntity<?> modificacionCliente(@RequestBody Cliente cliente) {
		
		Cliente clienteEncontrado=clientes.stream().
				
				filter(cli -> cli.getUsername().equalsIgnoreCase(cliente.getUsername())).
				
				findFirst().orElseThrow();
		
		clienteEncontrado.setPassword(cliente.getPassword());
		
		clienteEncontrado.setNombre(cliente.getNombre());
		
		return ResponseEntity.ok(clienteEncontrado);
	}
	
	
	@DeleteMapping("/{userName}")
	public ResponseEntity deleteCliente(@PathVariable String userName) {
		
		Cliente clienteEncontrado= clientes.stream().
				
				filter(cli -> cli.getUsername().equalsIgnoreCase(userName)).
				
				findFirst().orElseThrow();
		
		clientes.remove(clienteEncontrado);
		
		return ResponseEntity.noContent().build();
	}
	
}
