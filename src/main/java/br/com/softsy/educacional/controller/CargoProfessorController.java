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

import br.com.softsy.educacional.dto.CargoProfessorDTO;
import br.com.softsy.educacional.model.CargoProfessor;
import br.com.softsy.educacional.service.CargoProfessorService;

@RestController
@RequestMapping("/cargoProfessor")
public class CargoProfessorController {

    @Autowired
    private CargoProfessorService service;

    @GetMapping
    public ResponseEntity<List<CargoProfessor>> listar() {
        return ResponseEntity.ok(service.listarTudo());
    }

    @GetMapping("/{idCargoProfessor}")
    public ResponseEntity<CargoProfessorDTO> buscarPorId(@PathVariable Long idCargoProfessor) {
        return ResponseEntity.ok(service.buscarPorId(idCargoProfessor));
    }

    @PostMapping
    public ResponseEntity<CargoProfessorDTO> cadastrar(@RequestBody @Valid CargoProfessorDTO dto) {
        CargoProfessorDTO cargoDTO = service.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(cargoDTO.getIdCargoProfessor()).toUri();
        return ResponseEntity.created(uri).body(cargoDTO);
    }

    @PutMapping
    public ResponseEntity<?> atualizar(@RequestBody @Valid CargoProfessorDTO dto) {
        return ResponseEntity.ok(service.atualizar(dto));
    }

    @PutMapping("/{idCargoProfessor}/ativar")
    public ResponseEntity<?> ativar(@PathVariable Long idCargoProfessor) {
        service.ativaDesativa('S', idCargoProfessor);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{idCargoProfessor}/desativar")
    public ResponseEntity<?> desativar(@PathVariable Long idCargoProfessor) {
        service.ativaDesativa('N', idCargoProfessor);
        return ResponseEntity.ok().build();
    }
}
