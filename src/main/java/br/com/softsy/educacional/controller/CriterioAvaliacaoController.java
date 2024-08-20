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

import br.com.softsy.educacional.dto.CriterioAvaliacaoDTO;
import br.com.softsy.educacional.service.CriterioAvaliacaoService;

@RestController
@RequestMapping("/criteriosAvaliacao")
public class CriterioAvaliacaoController {

    @Autowired
    private CriterioAvaliacaoService service;

    @GetMapping
    public ResponseEntity<List<CriterioAvaliacaoDTO>> listar() {
        return ResponseEntity.ok(service.listarTudo());
    }

    @GetMapping("/{idCriterioAvaliacao}")
    public ResponseEntity<CriterioAvaliacaoDTO> buscarPorId(@PathVariable Long idCriterioAvaliacao) {
        return ResponseEntity.ok(service.buscarPorId(idCriterioAvaliacao));
    }

    @PostMapping
    public ResponseEntity<CriterioAvaliacaoDTO> cadastrar(@RequestBody @Valid CriterioAvaliacaoDTO dto) {
        CriterioAvaliacaoDTO criterioAvaliacaoDTO = service.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(criterioAvaliacaoDTO.getIdCriterioAvaliacao()).toUri();
        return ResponseEntity.created(uri).body(criterioAvaliacaoDTO);
    }

    @PutMapping
    public ResponseEntity<CriterioAvaliacaoDTO> atualizar(@RequestBody @Valid CriterioAvaliacaoDTO dto) {
        return ResponseEntity.ok(service.atualizar(dto));
    }

    @PutMapping("/{idCriterioAvaliacao}/ativar")
    public ResponseEntity<Void> ativar(@PathVariable Long idCriterioAvaliacao) {
        service.ativaDesativa('S', idCriterioAvaliacao);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{idCriterioAvaliacao}/desativar")
    public ResponseEntity<Void> desativar(@PathVariable Long idCriterioAvaliacao) {
        service.ativaDesativa('N', idCriterioAvaliacao);
        return ResponseEntity.ok().build();
    }
}