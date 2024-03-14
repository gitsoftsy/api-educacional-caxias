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

import br.com.softsy.educacional.dto.CadastroEscolaLinguaDTO;
import br.com.softsy.educacional.dto.EscolaLinguaDTO;
import br.com.softsy.educacional.service.EscolaLinguaService;

@RestController
@RequestMapping("/escolaLingua")
public class EscolaLinguaController {

    @Autowired
    private EscolaLinguaService service;

    @GetMapping
    public ResponseEntity<List<EscolaLinguaDTO>> listar(){
        return ResponseEntity.ok(service.listarTudo());
    }

    @GetMapping("/escola/{idEscola}")
    public ResponseEntity<List<EscolaLinguaDTO>> buscarPorIdEscola(@PathVariable Long idEscola){
        List<EscolaLinguaDTO> escolasLingua = service.buscarPorIdEscola(idEscola);
        return ResponseEntity.ok(escolasLingua);
    }

    @PostMapping
    public ResponseEntity<EscolaLinguaDTO> cadastrar(@RequestBody @Valid CadastroEscolaLinguaDTO dto){
        EscolaLinguaDTO cadastroEscolaLinguaDTO = service.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(cadastroEscolaLinguaDTO.getIdEscolaLingua()).toUri();
        return ResponseEntity.created(uri).body(cadastroEscolaLinguaDTO);
    }

    @PutMapping
    public ResponseEntity<EscolaLinguaDTO> atualizar(@RequestBody @Valid CadastroEscolaLinguaDTO dto){
        EscolaLinguaDTO escolaLinguaDTO = service.atualizar(dto);
        return ResponseEntity.ok(escolaLinguaDTO);
    }

    @DeleteMapping("/{idEscolaLingua}")
    public ResponseEntity<?> excluir(@PathVariable Long idEscolaLingua){
        service.remover(idEscolaLingua);
        return ResponseEntity.ok().build();
    }
}