package br.com.softsy.educacional.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.softsy.educacional.dto.CadastroContaImagemLoginDTO;
import br.com.softsy.educacional.dto.ContaImagemLoginDTO;
import br.com.softsy.educacional.service.ContaImagemLoginService;

@RestController
@RequestMapping("/conta/imagens")
public class ContaImagemLoginController {

	@Autowired
	private ContaImagemLoginService service;

	@GetMapping
	public ResponseEntity<List<ContaImagemLoginDTO>> listarImagens(
			@RequestHeader(name = "idConta", required = true) Long idConta,
			@RequestParam(required = false) String aplicacao, @RequestParam(required = false) String apenasExibiveis) {

		List<ContaImagemLoginDTO> imagens = service.listarImagens(idConta, aplicacao, apenasExibiveis);
		return ResponseEntity.ok(imagens);
	}

	@GetMapping("/obter")
	public ResponseEntity<?> getImagemAtiva(@RequestHeader("idConta") Long idConta,
			@RequestParam("aplicacao") String aplicacao) {

		Long idAplicacao = converterAplicacaoParaId(aplicacao);
		if (idAplicacao == null) {
			return ResponseEntity.badRequest().body("Aplicação inválida.");
		}

		List<Map<String, Object>> imagens = service.obtemImagemLogin(idConta, idAplicacao);

		if (imagens.isEmpty()) {

			Map<String, Object> emptyResponse = new LinkedHashMap<>();
			emptyResponse.put("mensagem", "Nenhuma imagem encontrada para os parâmetros informados.");
			emptyResponse.put("status", "erro");

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(emptyResponse);
		}

		return ResponseEntity.ok(imagens);
	}

	private Long converterAplicacaoParaId(String aplicacao) {
		Map<String, Long> aplicacoes = Map.of("Secretaria", 1L, "Aluno", 2L, "Docente", 3L);
		return aplicacoes.get(aplicacao);
	}

	@PostMapping
	public ResponseEntity<Map<String, Object>> salvarImagemLogin(@RequestHeader("idConta") Long idConta,
			@RequestBody CadastroContaImagemLoginDTO dto) throws IOException {

		if (idConta == null) {
			Map<String, Object> errorResponse = new LinkedHashMap<>();
			errorResponse.put("mensagem", "O idConta não pode ser nulo.");
			errorResponse.put("status", "erro");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}

		dto.setIdConta(idConta);
		CadastroContaImagemLoginDTO resultado = service.salvarImagemLogin(idConta, dto);

		if (resultado == null) {
			Map<String, Object> errorResponse = new LinkedHashMap<>();
			errorResponse.put("mensagem", "Já existe uma imagem com essas datas de exibição.");
			errorResponse.put("status", "erro");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}

		Map<String, Object> response = new LinkedHashMap<>();
		response.put("mensagem", "Imagem cadastrada com sucesso!");
		response.put("imagem", resultado);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@PutMapping
	public ResponseEntity<Map<String, Object>> atualizarImagemLogin(
	        @RequestHeader("idConta") Long idConta,
	        @RequestBody CadastroContaImagemLoginDTO dto) throws IOException {

	    if (idConta == null) {
	        Map<String, Object> errorResponse = new LinkedHashMap<>();
	        errorResponse.put("mensagem", "O idConta não pode ser nulo.");
	        errorResponse.put("status", "erro");
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	    }

	    dto.setIdConta(idConta);
	    CadastroContaImagemLoginDTO resultado = service.atualizarImagemLogin(idConta, dto);

	    Map<String, Object> response = new LinkedHashMap<>();
	    response.put("mensagem", "Imagem atualizada com sucesso!");
	    response.put("imagem", resultado);

	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}


	@DeleteMapping("/{idContaLogo}")
	public ResponseEntity<Void> excluir(@PathVariable Long idContaLogo) {
		try {
			service.excluir(idContaLogo);
			return ResponseEntity.noContent().build();
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ContaLogo não encontrado para exclusão", e);
		}
	}

	@GetMapping("/{idContaImagemLogin}")
	public ResponseEntity<Map<String, Object>> buscarImagem(
			@RequestHeader(name = "idConta", required = true) Long idConta,
			@PathVariable(name = "idContaImagemLogin", required = true) Long idContaImagemLogin) {

		ContaImagemLoginDTO resultado = service.buscarImagemPorId(idContaImagemLogin, idConta);

		Map<String, Object> response = new LinkedHashMap<>();
		response.put("mensagem", "Imagem encontrada com sucesso!");
		response.put("imagem", resultado);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}