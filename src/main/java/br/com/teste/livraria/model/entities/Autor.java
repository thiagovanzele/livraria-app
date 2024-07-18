package br.com.teste.livraria.model.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.teste.livraria.model.dtos.AutorDto;
import br.com.teste.livraria.model.exceptions.ResourceNotFoundException;
import br.com.teste.livraria.model.repositories.LivroRepository;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_autor")
public class Autor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;

	@JsonIgnore
	@ManyToMany(mappedBy = "autores", cascade = CascadeType.PERSIST)
	private Set<Livro> livros = new HashSet<>();

	public Autor(String nome, Set<Livro> livros) {
		this.nome = nome;
		this.livros = livros;
	}
	
	public Autor(AutorDto autorDto, LivroRepository livroRepository) {
		this.nome = autorDto.nome();
		this.livros = autorDto.livrosIds().stream().map(id -> livroRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Autor.class, id))).collect(Collectors.toSet());
	}

	public Autor() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Set<Livro> getLivros() {
		return livros;
	}

	public void setLivros(Set<Livro> livros) {
		this.livros = livros;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Autor other = (Autor) obj;
		return Objects.equals(id, other.id);
	}

}
