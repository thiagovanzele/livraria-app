package br.com.teste.livraria.model.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.teste.livraria.model.exceptions.ValidationException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_livro")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Livro implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String titulo;

	@JoinColumn(nullable = false, unique = true)
	@OneToOne(mappedBy = "livro", cascade = CascadeType.ALL)
	private Resumo resumo;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "id_editora", nullable = false)
	private Editora editora;

	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "tb_livro_autor", joinColumns = @JoinColumn(name = "id_livro"), inverseJoinColumns = @JoinColumn(name = "id_autor"))
	private Set<Autor> autores = new HashSet<>();

	private Double preco;

	public Livro() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitutlo(String title) {
		this.titulo = title;
	}

	public Resumo getResumo() {
		return resumo;
	}

	public void setResumo(Resumo resumo) {
		this.resumo = resumo;
	}

	public Editora getEditora() {
		return editora;
	}

	public void setEditora(Editora editora) {
		this.editora = editora;
	}

	public Set<Autor> getAutores() {
		return autores;
	}

	public void setAutores(Set<Autor> autores) {
		this.autores = autores;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	@PrePersist
	@PreUpdate
	private void validateAutores() {
		if (this.autores == null || this.autores.isEmpty()) {
			throw new ValidationException("Livro deve conter pelo menos um autor");
		}
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
		Livro other = (Livro) obj;
		return Objects.equals(id, other.id);
	}
}
