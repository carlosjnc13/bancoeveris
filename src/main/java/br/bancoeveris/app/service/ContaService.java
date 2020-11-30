package br.bancoeveris.app.service;

import br.bancoeveris.app.request.ContaRequest;
import br.bancoeveris.app.response.BaseResponse;
import br.bancoeveris.app.response.ContaResponse;
import br.bancoeveris.app.response.ListContaResponse;

public interface ContaService {
	
	 ContaResponse inserir(ContaRequest contaRequest);
	 
	 ContaResponse obter(Long id);
	 
	 ListContaResponse listar();
	 
	 BaseResponse atualizar(Long id, ContaRequest contaRequest);
	 
	 BaseResponse deletar(Long id);
	 
	 ContaResponse Saldo(String hash);
	 
}
