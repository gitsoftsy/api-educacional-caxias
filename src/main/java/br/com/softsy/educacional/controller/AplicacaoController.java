package br.com.softsy.educacional.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.softsy.educacional.dto.AgendaDTO;
import br.com.softsy.educacional.dto.AplicacaoDTO;
import br.com.softsy.educacional.model.AllResponse;
import br.com.softsy.educacional.service.AplicacaoService;

@RestController
@RequestMapping("/aplicacao")
public class AplicacaoController {

	@Autowired
	private AplicacaoService service;

	@GetMapping
	public ResponseEntity<Map<String, Object>> listar() {
		List<AplicacaoDTO> aplicacoes = service.listarTudo();

		Map<String, Object> response = new LinkedHashMap<>();
		response.put("mensagem", "Dados encontrados:");
		response.put("quantidade", aplicacoes.size());
		response.put("dados", aplicacoes);

		return ResponseEntity.ok(response);
	}

}