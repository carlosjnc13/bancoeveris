package br.bancoeveris.app.response;

import java.util.List;

import br.bancoeveris.app.model.*;

public class ListContaResponse extends BaseResponse {

	// PROPRIEDADES
	private List<Conta> Contas;

	// CONSTRUTOR
	public List<Conta> getContas() {
		return Contas;
	}

	// METODO
	public void setContas(List<Conta> contas) {
		Contas = contas;
	}
	
	public ListContaResponse() {
		
	}

	public ListContaResponse(List<Conta> contas,int statusCode) {
		this.Contas = contas;
		this.statusCode = statusCode;
	}
	
	
}