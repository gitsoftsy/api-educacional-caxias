package br.com.softsy.educacional.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.softsy.educacional.dto.CadastroNotaLogDTO;
import br.com.softsy.educacional.dto.NotaLogDTO;
import br.com.softsy.educacional.service.NotaLogService;

@RestController
@RequestMapping("/notaLog")
public class NotaLogController {
	
	 @Autowired
	 private NotaLogService service;

	 @GetMapping
	  public ResponseEntity<List<NotaLogDTO>> listar() {
	     return ResponseEntity.ok(service.listarTudo());
	    }


	    @GetMapping("/aluno/{idAluno}")
	    public ResponseEntity<List<NotaLogDTO>> buscarPorIdAluno(@PathVariable Long idAluno) {
	        List<NotaLogDTO> notaLogs = service.buscarPorIdAluno(idAluno);
	        return ResponseEntity.ok(notaLogs);
	    }

	
	    @GetMapping("/prova/{idProva}")
	    public ResponseEntity<List<NotaLogDTO>> buscarPorIdProva(@PathVariable Long idProva) {
	        List<NotaLogDTO> notaLogs = service.buscarPorIdProva(idProva);
	        return ResponseEntity.ok(notaLogs);
	    }

	   
	    @GetMapping("/nota/{idNota}")
	    public ResponseEntity<List<CadastroNotaLogDTO>> buscarPorIdNota(@PathVariable Long idNota) {
	        List<CadastroNotaLogDTO> notaLogs = service.buscarPorIdNota(idNota);
	        return ResponseEntity.ok(notaLogs);
	    }

	
}
