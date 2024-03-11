package br.com.softsy.educacional.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.softsy.educacional.model.DestinacaoLixo;
import br.com.softsy.educacional.service.DestinacaoLixoService;

@RestController
@RequestMapping("/destinacaoLixo")
public class DestinacaoLixoController {

	@Autowired DestinacaoLixoService service;
	
	@GetMapping
	public ResponseEntity<List<DestinacaoLixo>> listar(){
		return ResponseEntity.ok(service.listarTudo());
	}
}
