package br.com.softsy.educacional.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.softsy.educacional.dto.EscolaLixoDTO;
import br.com.softsy.educacional.service.EscolaLixoService;

@RestController
@RequestMapping("/escolaLixo")
public class EscolaLixoController {

    @Autowired
    private EscolaLixoService escolaLixoService;

    @PostMapping
    public ResponseEntity<EscolaLixoDTO> cadastrar(@RequestBody @Valid EscolaLixoDTO dto) {
        EscolaLixoDTO escolaLixoDTO = escolaLixoService.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(escolaLixoDTO.getIdEscolaLixo()).toUri();
        return ResponseEntity.created(uri).body(escolaLixoDTO);
    }

    @PutMapping
    public ResponseEntity<EscolaLixoDTO> atualizar(@RequestBody @Valid EscolaLixoDTO dto) {
        EscolaLixoDTO escolaLixoDTO = escolaLixoService.atualizar(dto);
        return ResponseEntity.ok(escolaLixoDTO);
    }

    @DeleteMapping("/{idEscolaLixo}")
    public ResponseEntity<?> excluir(@PathVariable Long idEscolaLixo) {
        escolaLixoService.remover(idEscolaLixo);
        return ResponseEntity.ok().build();
    }
}
