package br.com.softsy.educacional.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.softsy.educacional.dto.CadastroCandidatoDTO;
import br.com.softsy.educacional.dto.CandidatoDTO;
import br.com.softsy.educacional.service.CandidatoService;

@RestController
@RequestMapping("/candidatos")
public class CandidatoController {
	

    @Autowired
    private CandidatoService candidatoService;

    @GetMapping
    public ResponseEntity<List<CandidatoDTO>> listar() {
        List<CandidatoDTO> candidatos = candidatoService.listarTudo();
        return ResponseEntity.ok(candidatos);
    }

    @GetMapping("/{idCandidato}")
    public ResponseEntity<CandidatoDTO> buscarPorId(@PathVariable Long idCandidato) {
        CandidatoDTO candidatoDto = candidatoService.buscarPorId(idCandidato);
        return ResponseEntity.ok(candidatoDto);
    }
    

    @PostMapping
    public ResponseEntity<CandidatoDTO> cadastrar(@RequestBody @Valid CadastroCandidatoDTO dto) {
        CandidatoDTO candidatoDTO = candidatoService.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(candidatoDTO.getIdCandidato()).toUri();
        return ResponseEntity.created(uri).body(candidatoDTO);
    }


    @PutMapping
    public ResponseEntity<?> atualizar(@RequestBody @Valid CadastroCandidatoDTO dto) {
        return ResponseEntity.ok(candidatoService.atualizar(dto));
    }
	
    
    @DeleteMapping("/{idCandidato}")
    public ResponseEntity<?> excluir(@PathVariable Long idCandidato) {
    	candidatoService.remover(idCandidato);
        return ResponseEntity.ok().build();
    }
    
}
