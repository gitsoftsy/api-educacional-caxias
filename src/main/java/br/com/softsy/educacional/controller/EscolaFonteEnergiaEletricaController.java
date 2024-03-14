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

import br.com.softsy.educacional.dto.CadastroEscolaFonteEnergiaEletricaDTO;
import br.com.softsy.educacional.dto.EscolaFonteEnergiaEletricaDTO;
import br.com.softsy.educacional.service.EscolaFonteEnergiaEletricaService;

@RestController
@RequestMapping("/escolaFonteEnergiaEletrica")
public class EscolaFonteEnergiaEletricaController {

    @Autowired
    private EscolaFonteEnergiaEletricaService service;

    @GetMapping
    public ResponseEntity<List<EscolaFonteEnergiaEletricaDTO>> listar(){
        return ResponseEntity.ok(service.listarTudo());
    }

    @GetMapping("/escola/{idEscola}")
    public ResponseEntity<List<EscolaFonteEnergiaEletricaDTO>> buscarPorIdEscola(@PathVariable Long idEscola){
        List<EscolaFonteEnergiaEletricaDTO> escolasFonteEnergiaEletrica = service.buscarPorIdEscola(idEscola);
        return ResponseEntity.ok(escolasFonteEnergiaEletrica);
    }

    @PostMapping
    public ResponseEntity<EscolaFonteEnergiaEletricaDTO> cadastrar(@RequestBody @Valid CadastroEscolaFonteEnergiaEletricaDTO dto){
        EscolaFonteEnergiaEletricaDTO cadastroEscolaFonteEnergiaEletricaDTO = service.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(cadastroEscolaFonteEnergiaEletricaDTO.getIdEscolaFonteEnergiaEletrica()).toUri();
        return ResponseEntity.created(uri).body(cadastroEscolaFonteEnergiaEletricaDTO);
    }

    @PutMapping
    public ResponseEntity<EscolaFonteEnergiaEletricaDTO> atualizar(@RequestBody @Valid CadastroEscolaFonteEnergiaEletricaDTO dto){
        EscolaFonteEnergiaEletricaDTO escolaFonteEnergiaEletricaDTO = service.atualizar(dto);
        return ResponseEntity.ok(escolaFonteEnergiaEletricaDTO);
    }

    @DeleteMapping("/{idEscolaFonteEnergiaEletrica}")
    public ResponseEntity<?> excluir(@PathVariable Long idEscolaFonteEnergiaEletrica){
        service.remover(idEscolaFonteEnergiaEletrica);
        return ResponseEntity.ok().build();
    }
}
