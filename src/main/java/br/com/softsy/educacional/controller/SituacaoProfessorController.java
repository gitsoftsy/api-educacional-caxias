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

import br.com.softsy.educacional.dto.SituacaoProfessorDTO;
import br.com.softsy.educacional.service.SituacaoProfessorService;

@RestController
@RequestMapping("/situacaoProfessor")
public class SituacaoProfessorController {

    @Autowired
    private SituacaoProfessorService service;

    @GetMapping
    public ResponseEntity<List<SituacaoProfessorDTO>> listar() {
        return ResponseEntity.ok(service.listarTudo());
    }

    @GetMapping("/{idSituacaoProfessor}")
    public ResponseEntity<SituacaoProfessorDTO> buscarPorId(@PathVariable Long idSituacaoProfessor) {
        return ResponseEntity.ok(service.buscarPorId(idSituacaoProfessor));
    }

    @PostMapping
    public ResponseEntity<SituacaoProfessorDTO> cadastrar(@RequestBody @Valid SituacaoProfessorDTO dto) {
        SituacaoProfessorDTO situacaoProfessorDTO = service.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(situacaoProfessorDTO.getIdSituacaoProfessor()).toUri();
        return ResponseEntity.created(uri).body(situacaoProfessorDTO);
    }

    @PutMapping
    public ResponseEntity<?> atualizar(@RequestBody @Valid SituacaoProfessorDTO dto) {
        return ResponseEntity.ok(service.atualizar(dto));
    }

    @PutMapping("/{idSituacaoProfessor}/ativar")
    public ResponseEntity<?> ativar(@PathVariable Long idSituacaoProfessor) {
        service.ativaDesativa('S', idSituacaoProfessor);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{idSituacaoProfessor}/desativar")
    public ResponseEntity<?> desativar(@PathVariable Long idSituacaoProfessor) {
        service.ativaDesativa('N', idSituacaoProfessor);
        return ResponseEntity.ok().build();
    }
}