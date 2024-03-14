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

import br.com.softsy.educacional.dto.EscolaLicSanitarioDTO;
import br.com.softsy.educacional.service.EscolaLicSanitarioService;

@RestController
@RequestMapping("/escolaLicSanitario")
public class EscolaLicSanitarioController {

    @Autowired
    private EscolaLicSanitarioService service;

    @GetMapping
    public ResponseEntity<List<EscolaLicSanitarioDTO>> listar() {
        return ResponseEntity.ok(service.listarTudo());
    }

    @GetMapping("/escola/{idEscola}")
    public ResponseEntity<List<EscolaLicSanitarioDTO>> buscarPorIdEscola(@PathVariable Long idEscola) {
        List<EscolaLicSanitarioDTO> escolasLicSanitario = service.buscarPorIdEscola(idEscola);
        return ResponseEntity.ok(escolasLicSanitario);
    }

    @PostMapping
    public ResponseEntity<EscolaLicSanitarioDTO> cadastrar(@RequestBody @Valid EscolaLicSanitarioDTO dto) {
        EscolaLicSanitarioDTO cadastroEscolaLicSanitarioDTO = service.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(cadastroEscolaLicSanitarioDTO.getIdEscolaLicSanitario()).toUri();
        return ResponseEntity.created(uri).body(cadastroEscolaLicSanitarioDTO);
    }

    @PutMapping
    public ResponseEntity<EscolaLicSanitarioDTO> atualizar(@RequestBody @Valid EscolaLicSanitarioDTO dto) {
        EscolaLicSanitarioDTO escolaLicSanitarioDTO = service.atualizar(dto);
        return ResponseEntity.ok(escolaLicSanitarioDTO);
    }

    @DeleteMapping("/{idEscolaLicSanitario}")
    public ResponseEntity<?> excluir(@PathVariable Long idEscolaLicSanitario) {
        service.remover(idEscolaLicSanitario);
        return ResponseEntity.ok().build();
    }
}