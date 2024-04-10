package br.com.softsy.educacional.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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

import br.com.softsy.educacional.dto.OrgaoPublicoDTO;
import br.com.softsy.educacional.model.OrgaoPublico;
import br.com.softsy.educacional.service.OrgaoPublicoService;

@RestController
@RequestMapping("/orgaoPublico")
public class OrgaoPublicoController {

    @Autowired
    private OrgaoPublicoService service;

    @GetMapping
    public ResponseEntity<List<OrgaoPublico>> listar() {
        return ResponseEntity.ok(service.listarTudo());
    }

    @GetMapping("/{idOrgaoPublico}")
    public ResponseEntity<OrgaoPublicoDTO> buscarPorId(@PathVariable Long idOrgaoPublico) {
        OrgaoPublicoDTO orgaoPublicoDTO = service.buscarPorId(idOrgaoPublico);
        return ResponseEntity.ok(orgaoPublicoDTO);
    }

    @PostMapping
    public ResponseEntity<OrgaoPublicoDTO> cadastrar(@RequestBody @Valid OrgaoPublicoDTO dto) {
        OrgaoPublicoDTO orgaoPublicoDTO = service.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(orgaoPublicoDTO.getIdOrgaoPublico()).toUri();
        return ResponseEntity.created(uri).body(orgaoPublicoDTO);
    }

    @PutMapping
    public ResponseEntity<OrgaoPublicoDTO> atualizar(@RequestBody @Valid OrgaoPublicoDTO dto) {
        OrgaoPublicoDTO orgaoPublicoDTO = service.atualizar(dto);
        return ResponseEntity.ok(orgaoPublicoDTO);
    }

    @PutMapping("/{idOrgaoPublico}/ativar")
    public ResponseEntity<?> ativar(@PathVariable Long idOrgaoPublico) {
        service.ativarDesativar('S', idOrgaoPublico);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{idOrgaoPublico}/desativar")
    public ResponseEntity<?> desativar(@PathVariable Long idOrgaoPublico) {
        service.ativarDesativar('N', idOrgaoPublico);
        return ResponseEntity.ok().build();
    }
}
