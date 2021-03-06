package br.bancoeveris.app.response;

public class ContaResponse extends BaseResponse{
	private Long id;
	private String nome;
	private String hash;
	private Double saldo;
	
	public ContaResponse() {
		
	}
	
	public ContaResponse(Long id, String nome, String hash, Double saldo, int statusCode) {
		super();
		this.id = id;
		this.nome = nome;
		this.hash = hash;
		this.saldo = saldo;
		this.statusCode = statusCode;
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
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public Double getSaldo() {
		return saldo;
	}
	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}
			
}
