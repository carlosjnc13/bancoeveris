package br.bancoeveris.app.service;

import br.bancoeveris.app.request.OperacaoRequest;
import br.bancoeveris.app.request.TransferenciaRequest;
import br.bancoeveris.app.response.BaseResponse;

public interface OperacaoService {
	
	 double Saldo(Long contaId);
	 
	 BaseResponse criarSaque(OperacaoRequest operacaoRequest);
	 
	 BaseResponse criarDeposito(OperacaoRequest operacaoRequest);
	 
	 BaseResponse transferencia(TransferenciaRequest transferenciaRequest);
	 
}
