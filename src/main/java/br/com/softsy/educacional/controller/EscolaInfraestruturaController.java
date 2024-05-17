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

import br.com.softsy.educacional.dto.EscolaInfraestruturaDTO;
import br.com.softsy.educacional.service.EscolaInfraestruturaService;

@RestController
@RequestMapping("/escolaInfraestrutura")
public class EscolaInfraestruturaController {

    @Autowired
    private EscolaInfraestruturaService escolaInfraestruturaService;

    @PostMapping
    public ResponseEntity<EscolaInfraestruturaDTO> cadastrar(@RequestBody @Valid EscolaInfraestruturaDTO dto) {
        EscolaInfraestruturaDTO escolaInfraestruturaDTO = escolaInfraestruturaService.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(escolaInfraestruturaDTO.getIdEscolaInfraestrutura()).toUri();
        return ResponseEntity.created(uri).body(escolaInfraestruturaDTO);
    }

    @PutMapping
    public ResponseEntity<EscolaInfraestruturaDTO> atualizar(@RequestBody @Valid EscolaInfraestruturaDTO dto) {
        EscolaInfraestruturaDTO escolaInfraestruturaDTO = escolaInfraestruturaService.atualizar(dto);
        return ResponseEntity.ok(escolaInfraestruturaDTO);
    }

    @DeleteMapping("/{idEscolaInfraestrutura}")
    public ResponseEntity<?> excluir(@PathVariable Long idEscolaInfraestrutura) {
        escolaInfraestruturaService.remover(idEscolaInfraestrutura);
        return ResponseEntity.ok().build();
    }
}