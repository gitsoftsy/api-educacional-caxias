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

import br.com.softsy.educacional.dto.CadastroEscolaRegimeEscolarDTO;
import br.com.softsy.educacional.dto.EscolaRegimeEscolarDTO;
import br.com.softsy.educacional.service.EscolaRegimeEscolarService;

@RestController
@RequestMapping("/escolaRegimeEscolar")
public class EscolaRegimeEscolarController {

    @Autowired
    private EscolaRegimeEscolarService service;

    @GetMapping
    public ResponseEntity<List<EscolaRegimeEscolarDTO>> listar(){
        return ResponseEntity.ok(service.listarTudo());
    }

    @GetMapping("/escola/{idEscola}")
    public ResponseEntity<List<EscolaRegimeEscolarDTO>> buscarPorIdEscola(@PathVariable Long idEscola){
        List<EscolaRegimeEscolarDTO> escolasRegimeEscolar = service.buscarPorIdEscola(idEscola);
        return ResponseEntity.ok(escolasRegimeEscolar);
    }

    @PostMapping
    public ResponseEntity<EscolaRegimeEscolarDTO> cadastrar(@RequestBody @Valid CadastroEscolaRegimeEscolarDTO dto){
        EscolaRegimeEscolarDTO cadastroEscolaRegimeEscolarDTO = service.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(cadastroEscolaRegimeEscolarDTO.getIdEscolaRegimeEscolar()).toUri();
        return ResponseEntity.created(uri).body(cadastroEscolaRegimeEscolarDTO);
    }

    @PutMapping
    public ResponseEntity<EscolaRegimeEscolarDTO> atualizar(@RequestBody @Valid CadastroEscolaRegimeEscolarDTO dto){
        EscolaRegimeEscolarDTO escolaRegimeEscolarDTO = service.atualizar(dto);
        return ResponseEntity.ok(escolaRegimeEscolarDTO);
    }

    @DeleteMapping("/{idEscolaRegimeEscolar}")
    public ResponseEntity<?> excluir(@PathVariable Long idEscolaRegimeEscolar){
        service.remover(idEscolaRegimeEscolar);
        return ResponseEntity.ok().build();
    }
}
