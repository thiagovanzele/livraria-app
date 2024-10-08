package br.com.teste.livraria.model.entities;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_pedido")
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "data_pedido")
	private LocalDateTime data;
	
	@ManyToOne
	@JoinColumn(name = "id_cliente")
	private Cliente cliente;
	
	@OneToMany(mappedBy = "id.pedido", cascade = CascadeType.ALL)
	private Set<ItemPedido> itens = new HashSet<ItemPedido>();

	public Pedido() {
		super();
	}

	public Pedido(LocalDateTime instante, Cliente cliente) {
		this.data = instante;
		this.cliente = cliente;
		this.cliente.getPedidos().add(this);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime instante) {
		this.data = instante;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public Set<ItemPedido> getItens () {
		return itens;
	}
	
	public void setItens(Set<ItemPedido> itens) {
		this.itens = itens;
	}
	
	public String getTotal() {
		Double sum = 0.0;
		
		for (ItemPedido item : itens) {
			sum += item.getSubTotal();
		}
		
		return String.format("%.2f", sum);
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
		Pedido other = (Pedido) obj;
		return Objects.equals(id, other.id);
	}
	
	
}
