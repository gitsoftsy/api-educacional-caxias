package br.com.softsy.educacional.controller;

import java.net.URI;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.softsy.educacional.dto.ContaImagemLoginDTO;
import br.com.softsy.educacional.infra.exception.UniqueException;
import br.com.softsy.educacional.model.AllResponse;
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

}