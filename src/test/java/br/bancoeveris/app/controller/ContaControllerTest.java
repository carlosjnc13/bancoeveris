package br.bancoeveris.app.controller;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static org.hamcrest.CoreMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.google.gson.Gson;

import br.bancoeveris.app.request.ContaRequest;
import br.bancoeveris.app.response.BaseResponse;
import br.bancoeveris.app.response.ContaResponse;
import br.bancoeveris.app.response.ListContaResponse;
import br.bancoeveris.app.service.ContaService;
import io.restassured.http.ContentType;


@WebMvcTest(controllers = ContaController.class)
public class ContaControllerTest {
	
		static ContaResponse response = new ContaResponse();
		static ContaRequest request = new ContaRequest();
		static ListContaResponse listContaResponse = new ListContaResponse();
		
		@Autowired
		private ContaController contaController;
		
		@MockBean
		private ContaService contaService;
		
		@BeforeEach
		public void setup() {
			standaloneSetup(this.contaController);
		}
		
		@BeforeAll
		public static void Dados() {
			response.setStatusCode(200);
			response.setHash("123456");
			response.setId(1L);
			response.setNome("Fulano");
			response.setSaldo(0.0);
			
			request.setNome("Fulano");
			listContaResponse.statusCode = 200;
		}
		@Test
		public void deveRetornarSucesso_QuandoCriarConta() {
			
			Gson gson = new Gson();
			response.setStatusCode(201);
			response.setMessage("OK");
			
			when(this.contaService.inserir(Mockito.any())).thenReturn(response);
			
			given().contentType("application/json").body(gson.toJson(request))
				
				.when()
					.post("/contas")
				.then()
					.statusCode(HttpStatus.CREATED.value());
		}
		
		@Test
		public void deveRetornarSucesso_QuandoBuscarConta() {
			
			when(this.contaService.obter(1L)).thenReturn(response);
			
			given()
				.accept(ContentType.JSON)
				.when()
					.get("/contas/{id}", 1L)
				.then()
					.statusCode(HttpStatus.OK.value());
		}
		
		@Test
		public void deveRetornarErro500_QuandoBuscarConta() {
			
			//when(this.contaService.obter(1L)).thenReturn(response);
			
			given()
				.accept(ContentType.JSON)
				.when()
					.get("/contas/{id}", 1L)
				.then()
					.statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		
		@Test
		public void deveRetornarErro400_QuandoBuscarConta() {
			
			when(this.contaService.obter(1L)).thenReturn(new ContaResponse(null,null,null,null,400));
			
			given()
				.accept(ContentType.JSON)
				.when()
					.get("/contas/{id}", 1L)
				.then()
					.statusCode(HttpStatus.BAD_REQUEST.value());
		}
		
		
		
		@Test
		public void deveRetornarSucesso_QuandoListarContas() {
			
			when(this.contaService.listar()).thenReturn(listContaResponse);
		
			given()
				.accept(ContentType.JSON)
				.when()
					.get("/contas")
				.then()
					.statusCode(HttpStatus.OK.value());
		}
		
		@Test
		public void deveRetornarErro500_QuandoListarContas() {
			
			//when(this.contaService.listar()).thenReturn(listContaResponse);
		
			given()
				.accept(ContentType.JSON)
				.when()
					.get("/contas")
				.then()
					.statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		
		@Test
		public void deveRetornarErro400_QuandoListarContas() {
			
			when(this.contaService.listar()).thenReturn(new ListContaResponse(null,400));
		
			given()
				.accept(ContentType.JSON)
				.when()
					.get("/contas")
				.then()
					.statusCode(HttpStatus.BAD_REQUEST.value());
			
			
		}
		
		@Test
		public void deveRetornarSucesso_QuandoDeletarConta() {
			
			when(this.contaService.deletar(1L)).thenReturn(new BaseResponse(200,"Sucesso"));
			
			given()
				.accept(ContentType.JSON)
				.when()
					.delete("/contas/{id}",1L)
				.then()
					.statusCode(HttpStatus.OK.value());
		}
		
		@Test
		public void deveRetornarErro500_QuandoDeletarConta() {
			
			//when(this.contaService.deletar(1L)).thenReturn(new BaseResponse(200,"Sucesso"));
			
			given()
				.accept(ContentType.JSON)
				.when()
					.delete("/contas/{id}",1L)
				.then()
					.statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		
		@Test
		public void deveRetornareErro400_QuandoDeletarConta() {
			
			when(this.contaService.deletar(1L)).thenReturn(new BaseResponse(400,"Erro"));
			
			given()
				.accept(ContentType.JSON)
				.when()
					.delete("/contas/{id}",1L)
				.then()
					.statusCode(HttpStatus.BAD_REQUEST.value());
		}
		
		@Test
		public void deveRetornarSucesso_QuandoCalcularSaldoConta() {
			
			when(this.contaService.Saldo("123456")).thenReturn(response);
			
			given()
				.accept(ContentType.JSON)
				.when()
					.get("/contas/saldo/{hash}","123456")
				.then()
					.statusCode(HttpStatus.OK.value());
		}
		
		@Test
		public void deveRetornarErro500_QuandoCalcularSaldoConta() {
			
			//when(this.contaService.Saldo("123456")).thenReturn(response);
			
			given()
				.accept(ContentType.JSON)
				.when()
					.get("/contas/saldo/{hash}","123456")
				.then()
					.statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		
		@Test
		public void deveRetornarErro400_QuandoCalcularSaldoConta() {
			
			when(this.contaService.Saldo("123456")).thenReturn( new ContaResponse(null,null,null,null,400));
			
			given()
				.accept(ContentType.JSON)
				.when()
					.get("/contas/saldo/{hash}","123456")
				.then()
					.statusCode(HttpStatus.BAD_REQUEST.value());
		}
	
	
	

}
