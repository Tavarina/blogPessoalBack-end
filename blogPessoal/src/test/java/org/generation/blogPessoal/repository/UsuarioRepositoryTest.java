package org.generation.blogPessoal.repository;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.generation.blogPessoal.model.Usuario;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;



@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UsuarioRepositoryTest {

	@Autowired
	private UsuarioRepository repository;
	
	@BeforeAll
	void start() {
		Usuario usuario = new Usuario("VagnerBoaz", "134652");
		if(repository.findByUsuario(usuario.getUsuario())!=null)
			repository.save(usuario);
		
		usuario = new Usuario("LucasBoaz", "134652");
		if(repository.findByUsuario(usuario.getUsuario())!=null)
			repository.save(usuario);
		
		usuario = new Usuario("MarceloBoaz", "134652");
		if(repository.findByUsuario(usuario.getUsuario())!=null)
			repository.save(usuario);
	}
	

	@Test
	public void findByUsuarioRetornaUsuario() throws Exception {

		Usuario usuario = repository.findByUsuario("SilvaSilva").get();
		assertTrue(usuario.getUsuario().equals("SilvaSilva"));
	}
	
	@Test
	public void findAllByUsuarioContainingIgnoreCaseRetornaContato() {

		List<Usuario> listaDeUsuarios = repository.findAllByUsuarioContainingIgnoreCase("Silva");
		assertEquals(3, listaDeUsuarios.size());
	}
	
	@AfterAll
	public void end() {
		repository.deleteAll();
	}

}
