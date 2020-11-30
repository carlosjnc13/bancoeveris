package br.bancoeveris.app.controller;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import com.google.gson.Gson;

import br.bancoeveris.app.request.OperacaoRequest;
import br.bancoeveris.app.request.TransferenciaRequest;
import br.bancoeveris.app.response.BaseResponse;
import br.bancoeveris.app.service.OperacaoService;

@WebMvcTest(controllers = OperacaoController.class)
public class OperacaoControllerTest {

	static BaseResponse response = new BaseResponse();
	static OperacaoRequest request = new OperacaoRequest();
	static TransferenciaRequest transferenciaRequest = new TransferenciaRequest();
	Gson gson = new Gson();

	@Autowired
	private OperacaoController operacaoController;

	@MockBean
	private OperacaoService operacaoService;

	@BeforeEach
	public void setup() {
		standaloneSetup(this.operacaoController);
	}

	@BeforeAll
	public static void dados() {
		response.setStatusCode(200);

		request.setHash("123456");
		request.setValor(0.0);
		
		transferenciaRequest.setHashDestino("123456");
		transferenciaRequest.setHashOrigem("654321");
		transferenciaRequest.setValor(0.0);

	}

	@Test
	public void deveRetornarSucesso_QuandoCriarSaque() {

		when(this.operacaoService.criarSaque(Mockito.any())).thenReturn(response);

		given().contentType("application/json").body(gson.toJson(request))

				.when().post("/operacoes/saque").then().statusCode(HttpStatus.OK.value());
	}

	@Test
	public void deveRetornarErro500_QuandoCriarSaque() {

		// when(this.operacaoService.criarSaque(Mockito.any())).thenReturn(response);

		given().contentType("application/json").body(gson.toJson(request))

				.when().post("/operacoes/saque").then().statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
	}

	@Test
	public void deveRetornarSucesso_QuandoCriarDeposito() {

		when(this.operacaoService.criarDeposito(Mockito.any())).thenReturn(response);

		given().contentType("application/json").body(gson.toJson(request))

				.when().post("/operacoes/deposito").then().statusCode(HttpStatus.OK.value());
	}

	@Test
	public void deveRetornarErro500_QuandoCriarDeposito() {

		// when(this.operacaoService.criarDeposito(Mockito.any())).thenReturn(response);

		given().contentType("application/json").body(gson.toJson(request))

				.when().post("/operacoes/deposito").then().statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
	}

	@Test
	public void deveRetornarSucesso_QuandoCriarTransferencia() {

		when(this.operacaoService.transferencia(Mockito.any())).thenReturn(response);

		given().contentType("application/json").body(gson.toJson(transferenciaRequest))

				.when().post("/operacoes/transferencia").then().statusCode(HttpStatus.OK.value());

	}
	@Test
	public void deveRetornarErro500_QuandoCriarTransferencia() {

		//when(this.operacaoService.transferencia(Mockito.any())).thenReturn(response);

		given().contentType("application/json").body(gson.toJson(transferenciaRequest))

				.when().post("/operacoes/transferencia").then().statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

	}

}
