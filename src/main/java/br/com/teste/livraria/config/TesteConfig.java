package br.com.teste.livraria.config;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.teste.livraria.model.entities.Autor;
import br.com.teste.livraria.model.entities.Cliente;
import br.com.teste.livraria.model.entities.Editora;
import br.com.teste.livraria.model.entities.Endereco;
import br.com.teste.livraria.model.entities.Livro;
import br.com.teste.livraria.model.entities.Pedido;
import br.com.teste.livraria.model.entities.Resumo;
import br.com.teste.livraria.model.repositories.AutorRepository;
import br.com.teste.livraria.model.repositories.ClienteRepository;
import br.com.teste.livraria.model.repositories.EditoraRepository;
import br.com.teste.livraria.model.repositories.EnderecoRepository;
import br.com.teste.livraria.model.repositories.LivroRepository;
import br.com.teste.livraria.model.repositories.PedidoRepository;
import br.com.teste.livraria.model.services.EnderecoService;

@Configuration
@Profile("test")
public class TesteConfig implements CommandLineRunner {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private LivroRepository livroRepository;
	
	@Autowired
	private AutorRepository autorRepository;
	
	@Autowired
	private EditoraRepository editoraRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private EnderecoService enderecoService;
	
	
	@Override
	public void run(String... args) throws Exception {
		
		Endereco endereco = enderecoService.buscarEnderecoPorCep("09061030", "344");
		endereco = enderecoRepository.save(endereco);
		Cliente cliente = new Cliente("Thiago Vanzele", "123456" , "thiago@email.com", endereco);
		cliente = clienteRepository.save(cliente);
		Resumo resumo = new Resumo("Livro de Bruxos");
		Editora editora = new Editora("Alamo");
		editora = editoraRepository.save(editora);
		Autor autor = new Autor("J.K Rowlling");
		autor = autorRepository.save(autor);
		Set<Autor> autores = Set.of(autor);
		Livro livro = new Livro("Harry Pooter", resumo, editora, autores, 80.98);
		livro = livroRepository.save(livro);
		
		Pedido pedido = new Pedido(LocalDateTime.now(), cliente);
		pedido = pedidoRepository.save(pedido);
		
	}

}
