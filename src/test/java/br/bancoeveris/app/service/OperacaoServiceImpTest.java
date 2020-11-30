package br.bancoeveris.app.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.bancoeveris.app.model.Conta;
import br.bancoeveris.app.model.Operacao;
import br.bancoeveris.app.repository.ContaRepository;
import br.bancoeveris.app.repository.OperacaoRepository;
import br.bancoeveris.app.request.OperacaoRequest;
import br.bancoeveris.app.request.TransferenciaRequest;
import br.bancoeveris.app.response.BaseResponse;
import br.bancoeveris.app.service.implement.OperacaoServiceImp;

@ExtendWith(SpringExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class OperacaoServiceImpTest {
	
		static Operacao operacao = new Operacao();
		static Conta contaDestino = new Conta();
		static Conta contaOrigem = new Conta();
		static OperacaoRequest request = new OperacaoRequest();
		static TransferenciaRequest transferenciaRequest = new TransferenciaRequest();
	
	
		
		@InjectMocks
		OperacaoServiceImp operacaoServiceImp;
		
		@Mock
		OperacaoRepository operacaoRepository;
		
		@Mock
		ContaRepository contaRepository;
		
		@BeforeEach
        public void initMock() {
        	MockitoAnnotations.initMocks(this);
        }
		
		@BeforeAll
		public void setup() {
			
			
			contaOrigem.setHash("123456");
			contaOrigem.setId(1L);
			contaOrigem.setNome("Fulano");
			contaOrigem.setSaldo(0.0);
			
			contaDestino.setHash("654321");
			contaDestino.setId(2L);
			contaDestino.setNome("Fulano2");
			contaDestino.setSaldo(0.0);
			
			operacao.setContaDestino(contaDestino);
			operacao.setContaOrigem(contaOrigem);
			operacao.setId(1L);
			operacao.setValor(500.00);
			
			request.setHash("123456");
			request.setValor(0.0);
			
			transferenciaRequest.setHashDestino("654321");
			transferenciaRequest.setHashOrigem("123456");
			transferenciaRequest.setValor(0.0);
			
		}
		
	@Test
	public void deveRetornarValorValido_QuandoCalcularSaldoSaque() {
		
		operacao.setTipo("S");
		List<Operacao> lista = new ArrayList<Operacao>();
		lista.add(operacao);
		
		Mockito.when(this.operacaoRepository.findOperacoesPorConta(1L)).thenReturn(lista);
		Double resultadoEsperado = operacaoServiceImp.Saldo(1L);
		Assertions.assertTrue(resultadoEsperado != null);
		
	}
	
	@Test
	public void deveRetornarValorValido_QuandoCalcularSaldoDeposito() {
		
		operacao.setTipo("D");
		List<Operacao> lista = new ArrayList<Operacao>();
		lista.add(operacao);
		
		Mockito.when(this.operacaoRepository.findOperacoesPorConta(1L)).thenReturn(lista);
		Double resultadoEsperado = operacaoServiceImp.Saldo(1L);
		Assertions.assertTrue(resultadoEsperado != null);
		
	}
	@Test
	public void deveRetornarValorValido_QuandoCalcularSaldoTransferencia() {
		
		operacao.setTipo("T");
		List<Operacao> lista = new ArrayList<Operacao>();
		lista.add(operacao);
		
		Mockito.when(this.operacaoRepository.findOperacoesPorConta(1L)).thenReturn(lista);
		Double resultadoEsperado = operacaoServiceImp.Saldo(1L);
		Assertions.assertTrue(resultadoEsperado != null);
		
	}
	
	@Test
	public void deveRetornarValorValido_QuandoCalcularSaldoTransferencia2() {
		
		operacao.setTipo("T");
		operacao.setContaDestino(contaOrigem);
		operacao.setContaOrigem(contaDestino);
		
		List<Operacao> lista = new ArrayList<Operacao>();
		lista.add(operacao);
		
		Mockito.when(this.operacaoRepository.findOperacoesPorConta(1L)).thenReturn(lista);
		Double resultadoEsperado = operacaoServiceImp.Saldo(1L);
		Assertions.assertTrue(resultadoEsperado != null);
		
	}
	
	@Test
	public void deveRetornarSucesso_QuandoCriarOperacaoSaque() {
		
		Mockito.when(this.contaRepository.findByHash(Mockito.anyString())).thenReturn(new Conta("123456","Fulano",0.0));
		BaseResponse respostaEsperada = operacaoServiceImp.criarSaque(request);
		Assertions.assertEquals(respostaEsperada.getStatusCode(), 200);
		
	}
	
	@Test
	public void deveRetornarErro404_QuandoCriarOperacaoSaque() {
		
		Mockito.when(this.contaRepository.findByHash(null)).thenReturn(new Conta("123456","Fulano",0.0));
		BaseResponse respostaEsperada = operacaoServiceImp.criarSaque(request);
		Assertions.assertEquals(respostaEsperada.getStatusCode(), 404);
		
	}
	
	@Test
	public void deveRetornarSucesso_QuandoCriarOperacaoDeposito() {
		
		Mockito.when(this.contaRepository.findByHash(Mockito.anyString())).thenReturn(new Conta("123456","Fulano",0.0));
		BaseResponse respostaEsperada = operacaoServiceImp.criarDeposito(request);
		Assertions.assertEquals(respostaEsperada.getStatusCode(), 200);
		
	}
	@Test
	public void deveRetornarErro404_QuandoCriarOperacaoDeposito() {
		
		Mockito.when(this.contaRepository.findByHash(null)).thenReturn(new Conta("123456","Fulano",0.0));
		BaseResponse respostaEsperada = operacaoServiceImp.criarDeposito(request);
		Assertions.assertEquals(respostaEsperada.getStatusCode(), 404);
		
	}
	@Test
	public void deveRetornarSucesso_QuandoCriarOperacaoTransferencia() {
		
		Mockito.when(this.contaRepository.findByHash(Mockito.anyString())).thenReturn(new Conta("123456","Fulano",0.0));
		
		BaseResponse respostaEsperada = operacaoServiceImp.transferencia(transferenciaRequest);
		Assertions.assertEquals(respostaEsperada.getStatusCode(), 200);
	}
	@Test
	public void deveRetornarErro404ContaOrigem_QuandoCriarOperacaoTransferencia() {
		
		TransferenciaRequest request = new TransferenciaRequest();
		request.setHashOrigem(null);
		
		Mockito.when(this.contaRepository.findByHash(request.getHashOrigem())).thenReturn(null);
		
		BaseResponse respostaEsperada = operacaoServiceImp.transferencia(transferenciaRequest);
		Assertions.assertEquals(respostaEsperada.getStatusCode(), 404);
	}
	@Test
	public void deveRetornarErro404ContaDestino_QuandoCriarOperacaoTransferencia() {

		TransferenciaRequest request = new TransferenciaRequest();
		request.setHashDestino(null);
		
		Mockito.when(this.contaRepository.findByHash(transferenciaRequest.getHashOrigem())).thenReturn(new Conta("123456","Fulano",0.0));
		Mockito.when(this.contaRepository.findByHash(request.getHashDestino())).thenReturn(null);
		
		BaseResponse respostaEsperada = operacaoServiceImp.transferencia(transferenciaRequest);
		Assertions.assertEquals(respostaEsperada.getStatusCode(), 404);
	}
	
}
