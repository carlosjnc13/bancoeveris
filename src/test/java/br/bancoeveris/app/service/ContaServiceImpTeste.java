package br.bancoeveris.app.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.bancoeveris.app.model.Conta;
import br.bancoeveris.app.repository.ContaRepository;
import br.bancoeveris.app.request.ContaRequest;
import br.bancoeveris.app.response.BaseResponse;
import br.bancoeveris.app.response.ContaResponse;
import br.bancoeveris.app.response.ListContaResponse;
import br.bancoeveris.app.service.implement.ContaServiceImp;

@ExtendWith(SpringExtension.class)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ContaServiceImpTeste {
	
	@TestConfiguration
    static class ContaServiceImpTesteConfiguration{
		
		static ContaRequest novaContaRequest = new ContaRequest();
		static Conta novaConta = new Conta();
		static ContaResponse response = new ContaResponse();
		static List<Conta> lista = new ArrayList<>();
		
        @InjectMocks
        ContaServiceImp contaServiceImp;
        
        @Mock
        ContaRepository contaRepository;
        
        @Mock
        OperacaoService operacaoService;
        
        @BeforeEach
        public void initMock() {
        	MockitoAnnotations.initMocks(this);
        }
        
        @BeforeAll
        public static void setup() {
        	
        	novaContaRequest.setNome("Qualquer");
        	
        	novaConta.setNome("Fulano");
        	novaConta.setId(1L);
        	novaConta.setSaldo(0.0);
        	novaConta.setHash("132462342");
        	
        	lista.add(novaConta);
        	
        }
        	
        //TESTE DE INSERÇÃO
        @Test
        public void inserirConta() {
        	
        	Mockito.when(contaRepository.save(novaConta))
        		.thenReturn(novaConta);
        	
        	BaseResponse respostaEsperada = contaServiceImp.inserir(novaContaRequest);
        	
        	Assertions.assertEquals(respostaEsperada.getStatusCode(), 201);
        }
        
        @Test
        public void inserirContaSemNome() {
        	
        	ContaRequest request = new ContaRequest();
        	BaseResponse respostaEsperada = contaServiceImp.inserir(request);
        	Assertions.assertEquals(respostaEsperada.getStatusCode(), 400);
        	
        }
        
        //TESTE DE OBTENÇÃO POR ID
        @Test
        public void obterById() {
        	
        	Mockito.when(contaRepository.findById(1L))
    		.thenReturn(java.util.Optional.of(novaConta));
        	
        	ContaResponse respostaEsperada = contaServiceImp.obter(1L);
        	
        	Assertions.assertEquals(respostaEsperada.getStatusCode(), 200);
        	Assertions.assertTrue(respostaEsperada.getNome() != null);
        	Assertions.assertTrue(respostaEsperada.getHash() != null);
        	Assertions.assertTrue(respostaEsperada.getSaldo() == null);
        	Assertions.assertTrue(respostaEsperada.getId() != null);
        	
        }
        
        @Test
        public void obterByIdInexistente() {
        	
        	Optional<Conta> conta = Optional.empty();
        	
        	Mockito.when(contaRepository.findById(1L))
    		.thenReturn(conta);
        	
        	ContaResponse respostaEsperada = contaServiceImp.obter(1L);
        	Assertions.assertEquals(respostaEsperada.getStatusCode(), 400);
      	
        }
        //TESTE DE OBTENÇÃO LISTA
        @Test
        public void listar() {
        	
        	Mockito.when(contaRepository.findAll())
    		.thenReturn(lista);
        	
        	ListContaResponse respostaEsperada = contaServiceImp.listar();
        	
        	Assertions.assertEquals(respostaEsperada.getStatusCode(), 200);
        	Assertions.assertTrue(respostaEsperada.getContas() != null);
        	
        }
        //TESTE DE ATUALIZACÃO
        @Test
        public void atualizar() {
        	
        	Mockito.when(contaRepository.findById(1L))
    		.thenReturn(java.util.Optional.of(novaConta));
        	
        	Mockito.when(contaRepository.save(novaConta))
    		.thenReturn(novaConta);
        	
        	BaseResponse respostaEsperada = contaServiceImp.atualizar(1L, novaContaRequest);
        	Assertions.assertEquals(respostaEsperada.getStatusCode(), 200);
        	
        }
        
        @Test
        public void atualizarComIdInexistente() {
        	
        	ContaRequest request = new ContaRequest();
        	
        	Mockito.when(contaRepository.findById(1L))
    		.thenReturn(java.util.Optional.of(novaConta));
        	
        	BaseResponse respostaEsperada = contaServiceImp.atualizar(1L, request);
        	Assertions.assertEquals(respostaEsperada.getStatusCode(), 400);
        	
        	
        }
        //TESTE DE EXCLUSÃO
        @Test
        public void deletar() {
        	
        	Mockito.when(contaRepository.findById(1L))
    		.thenReturn(java.util.Optional.of(novaConta));
        	
        	BaseResponse respostaEsperada = contaServiceImp.deletar(1L);
        	
//        	Mockito.verify(contaRepository, times(1))
//            .delete(novaConta);
        	
        	Assertions.assertEquals(respostaEsperada.getStatusCode(), 200);
        	
        }
        
        @Test
        public void deletarIdInvalido() {
        	
        	BaseResponse respostaEsperada = contaServiceImp.deletar(-1l);
        	Assertions.assertEquals(respostaEsperada.getStatusCode(), 400);
        	
        }
        @Test
        public void deletarIdInvalido2() {
        	
        	BaseResponse respostaEsperada = contaServiceImp.deletar(null);
        	Assertions.assertEquals(respostaEsperada.getStatusCode(), 400);
        	
        }
        
        //TESTE DE SALDO
        @Test
        public void saldo() {
        	
        	Mockito.when(contaRepository.findByHash("123"))
    		.thenReturn(novaConta);
        	
        	Mockito.when(operacaoService.Saldo(novaConta.getId()))
    		.thenReturn(0.0);
        	
        	ContaResponse respostaEsperada = contaServiceImp.Saldo("123");
        	Assertions.assertEquals(respostaEsperada.getStatusCode(), 200);
        }
        
        @Test
        public void saldoContaInexistente() {
        	
        	Mockito.when(contaRepository.findByHash("123"))
    		.thenReturn(null);
        	
        	ContaResponse respostaEsperada = contaServiceImp.Saldo("123");
        	Assertions.assertEquals(respostaEsperada.getStatusCode(), 400);
        	
        	
        }
        
  
    }

}
