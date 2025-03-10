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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.softsy.educacional.dto.CadastroContaLogoDTO;
import br.com.softsy.educacional.dto.ContaLogoDTO;
import br.com.softsy.educacional.model.ContaLogo;
import br.com.softsy.educacional.service.ContaLogoService;

@RestController
@RequestMapping("/contaLogo")
public class ContaLogoController {

	@Autowired
	ContaLogoService service;

	@GetMapping
	public ResponseEntity<Object> getLogo(@RequestHeader("idConta") Long idConta,
			@RequestParam(value = "aplicacao", required = false) String aplicacao) {
		return service.buscarLogosPorConta(idConta, aplicacao);
	}

	@PostMapping
	public ResponseEntity<Map<String, Object>> salvarLogo(@RequestHeader("idConta") Long idConta,
			@RequestBody CadastroContaLogoDTO dto) throws IOException {

		if (idConta == null) {
			Map<String, Object> errorResponse = new LinkedHashMap<>();
			errorResponse.put("mensagem", "O idConta não pode ser nulo.");
			errorResponse.put("status", "erro");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}

		dto.setContaId(idConta);
		CadastroContaLogoDTO resultado = service.salvar(dto);

		if (resultado == null) {
			Map<String, Object> errorResponse = new LinkedHashMap<>();
			errorResponse.put("mensagem",
					"Já existe uma logo cadastrada para esta aplicação. Não é permitido mais de um logo por aplicação.");
			errorResponse.put("status", "erro");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}

		Map<String, Object> response = new LinkedHashMap<>();
		response.put("mensagem", "Logo cadastrado com sucesso!");
		response.put("logo", resultado);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@DeleteMapping("/{idContaImagemLogin}")
	public ResponseEntity<Void> excluir(@PathVariable Long idContaLogo) {
		try {
			service.excluir(idContaLogo);
			return ResponseEntity.noContent().build();
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ContaLogo não encontrado para exclusão", e);
		}
	}

}
