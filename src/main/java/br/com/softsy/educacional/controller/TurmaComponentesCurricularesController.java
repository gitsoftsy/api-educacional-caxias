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

import br.com.softsy.educacional.dto.CadastroTurmaComponentesCurricularesDTO;
import br.com.softsy.educacional.dto.FormaOrganEnsinoDTO;
import br.com.softsy.educacional.dto.TurmaComponentesCurricularesDTO;
import br.com.softsy.educacional.service.TurmaComponentesCurricularesService;

@RestController
@RequestMapping("/turmaComponentesCurriculares")
public class TurmaComponentesCurricularesController {

	@Autowired
    private TurmaComponentesCurricularesService service;

    @GetMapping
    public ResponseEntity<List<TurmaComponentesCurricularesDTO>> listar(){
        return ResponseEntity.ok(service.listarTudo());
    }
    
	@GetMapping("/{idTurmaComponentesCurriculares}")
	public ResponseEntity<TurmaComponentesCurricularesDTO> buscarPorId(@PathVariable Long idTurmaComponentesCurriculares) {
		return ResponseEntity.ok(service.buscarPorId(idTurmaComponentesCurriculares));
	}

    @PostMapping
    public ResponseEntity<TurmaComponentesCurricularesDTO> cadastrar(@RequestBody @Valid CadastroTurmaComponentesCurricularesDTO dto){
        TurmaComponentesCurricularesDTO cadastroTurmaComponentesCurricularesDTO = service.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(cadastroTurmaComponentesCurricularesDTO.getIdTurmaComponentesCurriculares()).toUri();
        return ResponseEntity.created(uri).body(cadastroTurmaComponentesCurricularesDTO);
    }

    @PutMapping
    public ResponseEntity<TurmaComponentesCurricularesDTO> atualizar(@RequestBody @Valid CadastroTurmaComponentesCurricularesDTO dto){
        TurmaComponentesCurricularesDTO turmaComponentesCurricularesDTO = service.atualizar(dto);
        return ResponseEntity.ok(turmaComponentesCurricularesDTO);
    }

    @DeleteMapping("/{idTurmaComponentesCurriculares}")
    public ResponseEntity<?> excluir(@PathVariable Long idTurmaComponentesCurriculares){
        service.remover(idTurmaComponentesCurriculares);
        return ResponseEntity.ok().build();
    }
}
