package br.com.softsy.educacional.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.softsy.educacional.dto.CadastroCandidatoDTO;
import br.com.softsy.educacional.dto.CadastroCandidatoRelacionamentoDTO;
import br.com.softsy.educacional.dto.CandidatoRelacionamentoDTO;
import br.com.softsy.educacional.service.CandidatoRelacionamentoService;



@RestController
@RequestMapping("/candidatoRelacionamento")
public class CandidatoRelacionamentoController {
	
	 @Autowired
	 private CandidatoRelacionamentoService candidatoRelacionamentoService;
	 
	 
	 @GetMapping
	    public ResponseEntity<List<CandidatoRelacionamentoDTO>> listar() {
	        List<CandidatoRelacionamentoDTO> candidatoRelacionamentoDTO = candidatoRelacionamentoService.listarTudo();
	        return ResponseEntity.ok(candidatoRelacionamentoDTO);
	    }

	    @GetMapping("/{idCandidatoRelacionamento}")
	    public ResponseEntity<CandidatoRelacionamentoDTO> buscarPorId(@PathVariable Long idCandidatoRelacionamento) {
	    	CandidatoRelacionamentoDTO candidatoRelacionamentoDTO = candidatoRelacionamentoService.buscarPorId(idCandidatoRelacionamento);
	        return ResponseEntity.ok(candidatoRelacionamentoDTO);
	    }
	 
	  @PostMapping
	    public ResponseEntity<CandidatoRelacionamentoDTO> cadastrar(@RequestBody @Valid CadastroCandidatoRelacionamentoDTO dto) {
		  CandidatoRelacionamentoDTO candidatoRelacionamentoDTO = candidatoRelacionamentoService.salvar(dto);
	        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
	                .buildAndExpand(candidatoRelacionamentoDTO.getIdCandidatoRelacionamento()).toUri();
	        return ResponseEntity.created(uri).body(candidatoRelacionamentoDTO);
	    }
	  
	  @PutMapping
	    public ResponseEntity<?> atualizar(@RequestBody @Valid CadastroCandidatoRelacionamentoDTO dto) {
	        return ResponseEntity.ok(candidatoRelacionamentoService.atualizar(dto));
	    }
	  
	  @PutMapping("/{idCandidatoRelacionamento}/ativar")
	    public ResponseEntity<?> ativar(@PathVariable Long idCandidatoRelacionamento) {
		  candidatoRelacionamentoService.ativaDesativa('S', idCandidatoRelacionamento);
	        return ResponseEntity.ok().build();
	    }

	    @PutMapping("/{idCandidatoRelacionamento}/desativar")
	    public ResponseEntity<?> desativar(@PathVariable Long idCandidatoRelacionamento) {
	    	candidatoRelacionamentoService.ativaDesativa('N', idCandidatoRelacionamento);
	        return ResponseEntity.ok().build();
	    }


}
