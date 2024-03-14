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

import br.com.softsy.educacional.dto.CadastroEscolaEsgotamentoSanitarioDTO;
import br.com.softsy.educacional.dto.EscolaEsgotamentoSanitarioDTO;
import br.com.softsy.educacional.service.EscolaEsgotamentoSanitarioService;

@RestController
@RequestMapping("/escolaEsgotamentoSanitario")
public class EscolaEsgotamentoSanitarioController {

    @Autowired
    private EscolaEsgotamentoSanitarioService service;

    @GetMapping
    public ResponseEntity<List<EscolaEsgotamentoSanitarioDTO>> listar() {
        return ResponseEntity.ok(service.listarTudo());
    }

    @GetMapping("/escola/{idEscola}")
    public ResponseEntity<List<EscolaEsgotamentoSanitarioDTO>> buscarPorIdEscola(@PathVariable Long idEscola) {
        List<EscolaEsgotamentoSanitarioDTO> escolasEsgotamentoSanitario = service.buscarPorIdEscola(idEscola);
        return ResponseEntity.ok(escolasEsgotamentoSanitario);
    }

    @PostMapping
    public ResponseEntity<EscolaEsgotamentoSanitarioDTO> cadastrar(@RequestBody @Valid CadastroEscolaEsgotamentoSanitarioDTO dto) {
        EscolaEsgotamentoSanitarioDTO cadastroEscolaEsgotamentoSanitarioDTO = service.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(cadastroEscolaEsgotamentoSanitarioDTO.getIdEscolaEsgotamentoSanitario()).toUri();
        return ResponseEntity.created(uri).body(cadastroEscolaEsgotamentoSanitarioDTO);
    }

    @PutMapping
    public ResponseEntity<EscolaEsgotamentoSanitarioDTO> atualizar(@RequestBody @Valid CadastroEscolaEsgotamentoSanitarioDTO dto) {
        EscolaEsgotamentoSanitarioDTO escolaEsgotamentoSanitarioDTO = service.atualizar(dto);
        return ResponseEntity.ok(escolaEsgotamentoSanitarioDTO);
    }

    @DeleteMapping("/{idEscolaEsgotamentoSanitario}")
    public ResponseEntity<?> excluir(@PathVariable Long idEscolaEsgotamentoSanitario) {
        service.remover(idEscolaEsgotamentoSanitario);
        return ResponseEntity.ok().build();
    }
}
