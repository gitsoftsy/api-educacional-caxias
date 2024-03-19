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

import br.com.softsy.educacional.dto.CadastroEscolaProfissionalDTO;
import br.com.softsy.educacional.dto.EscolaProfissionalDTO;
import br.com.softsy.educacional.service.EscolaProfissionalService;

@RestController
@RequestMapping("/escolaProfissional")
public class EscolaProfissionalController {

    @Autowired
    private EscolaProfissionalService escolaProfissionalService;

    @GetMapping
    public ResponseEntity<List<EscolaProfissionalDTO>> listar() {
        List<EscolaProfissionalDTO> escolasProfissionais = escolaProfissionalService.listarTudo();
        return ResponseEntity.ok(escolasProfissionais);
    }

    @GetMapping("/escola/{idEscola}")
    public ResponseEntity<List<EscolaProfissionalDTO>> buscarPorIdEscola(@PathVariable Long idEscola) {
        List<EscolaProfissionalDTO> escolasProfissionais = escolaProfissionalService.buscarPorIdEscola(idEscola);
        return ResponseEntity.ok(escolasProfissionais);
    }

    @PostMapping
    public ResponseEntity<EscolaProfissionalDTO> cadastrar(@RequestBody @Valid CadastroEscolaProfissionalDTO dto) {
        EscolaProfissionalDTO escolaProfissionalDTO = escolaProfissionalService.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        		.buildAndExpand(escolaProfissionalDTO.getIdEscolaProfissional()).toUri();
        return ResponseEntity.created(uri).body(escolaProfissionalDTO);
    }

    @PutMapping
    public ResponseEntity<EscolaProfissionalDTO> atualizar(@RequestBody @Valid CadastroEscolaProfissionalDTO dto) {
        EscolaProfissionalDTO escolaProfissionalDTO = escolaProfissionalService.atualizar(dto);
        return ResponseEntity.ok(escolaProfissionalDTO);
    }

    @DeleteMapping("/{idEscolaProfissional}")
    public ResponseEntity<?> excluir(@PathVariable Long idEscolaProfissional) {
        escolaProfissionalService.remover(idEscolaProfissional);
        return ResponseEntity.ok().build();
    }
}
