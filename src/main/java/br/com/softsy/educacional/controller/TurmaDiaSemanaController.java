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

import br.com.softsy.educacional.dto.TurmaDiaSemanaDTO;
import br.com.softsy.educacional.service.TurmaDiaSemanaService;

@RestController
@RequestMapping("/turmaDiaSemana")
public class TurmaDiaSemanaController {

    @Autowired
    private TurmaDiaSemanaService service;

    @GetMapping
    public ResponseEntity<List<TurmaDiaSemanaDTO>> listar() {
        return ResponseEntity.ok(service.listarTudo());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurmaDiaSemanaDTO> buscarPorId(@PathVariable Long id) {
        TurmaDiaSemanaDTO turmaDiaSemanaDTO = service.buscarPorId(id);
        return ResponseEntity.ok(turmaDiaSemanaDTO);
    }

    @PostMapping
    public ResponseEntity<TurmaDiaSemanaDTO> cadastrar(@RequestBody @Valid TurmaDiaSemanaDTO dto) {
        TurmaDiaSemanaDTO turmaDiaSemanaDTO = service.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(turmaDiaSemanaDTO.getIdTurmaDiaSemana()).toUri();
        return ResponseEntity.created(uri).body(turmaDiaSemanaDTO);
    }

    @PutMapping
    public ResponseEntity<TurmaDiaSemanaDTO> atualizar(@RequestBody @Valid TurmaDiaSemanaDTO dto) {
        TurmaDiaSemanaDTO turmaDiaSemanaDTO = service.atualizar(dto);
        return ResponseEntity.ok(turmaDiaSemanaDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.ok().build();
    }
}
