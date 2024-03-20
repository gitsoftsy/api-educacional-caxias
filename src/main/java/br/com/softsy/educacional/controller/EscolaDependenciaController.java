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

import br.com.softsy.educacional.dto.CadastroEscolaDependenciaDTO;
import br.com.softsy.educacional.dto.EscolaDependenciaDTO;
import br.com.softsy.educacional.service.EscolaDependenciaService;

@RestController
@RequestMapping("/escolaDependencia")
public class EscolaDependenciaController {

    @Autowired
    private EscolaDependenciaService escolaDependenciaService;

    @GetMapping
    public ResponseEntity<List<EscolaDependenciaDTO>> listar() {
        List<EscolaDependenciaDTO> escolasDependencia = escolaDependenciaService.listarTudo();
        return ResponseEntity.ok(escolasDependencia);
    }

    @GetMapping("/escola/{idEscola}")
    public ResponseEntity<List<EscolaDependenciaDTO>> buscarPorIdEscola(@PathVariable Long idEscola) {
        List<EscolaDependenciaDTO> escolasDependencia = escolaDependenciaService.buscarPorIdEscola(idEscola);
        return ResponseEntity.ok(escolasDependencia);
    }

    @PostMapping
    public ResponseEntity<EscolaDependenciaDTO> cadastrar(@RequestBody @Valid CadastroEscolaDependenciaDTO dto) {
        EscolaDependenciaDTO escolaDependenciaDTO = escolaDependenciaService.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        		.buildAndExpand(escolaDependenciaDTO.getIdEscolaDependencia()).toUri();
        return ResponseEntity.created(uri).body(escolaDependenciaDTO);
    }

    @PutMapping
    public ResponseEntity<EscolaDependenciaDTO> atualizar(@RequestBody @Valid CadastroEscolaDependenciaDTO dto) {
        EscolaDependenciaDTO escolaDependenciaDTO = escolaDependenciaService.atualizar(dto);
        return ResponseEntity.ok(escolaDependenciaDTO);
    }
    
    @DeleteMapping("/{idEscolaDependencia}")
    public ResponseEntity<?> excluir(@PathVariable Long idEscolaDependencia) {
    	escolaDependenciaService.remover(idEscolaDependencia);
        return ResponseEntity.ok().build();
    }
}