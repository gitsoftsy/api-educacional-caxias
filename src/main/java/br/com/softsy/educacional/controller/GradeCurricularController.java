package br.com.softsy.educacional.controller;

import java.net.URI;
import java.util.List;

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

import br.com.softsy.educacional.dto.CadastroGradeCurricularDTO;
import br.com.softsy.educacional.dto.CurriculoDTO;
import br.com.softsy.educacional.dto.EscolaFornecimentoAguaDTO;
import br.com.softsy.educacional.dto.GradeCurricularDTO;
import br.com.softsy.educacional.service.GradeCurricularService;

@RestController
@RequestMapping("/gradeCurricular")
public class GradeCurricularController {

    @Autowired
    private GradeCurricularService service;

    @GetMapping
    public ResponseEntity<List<GradeCurricularDTO>> listar() {
        List<GradeCurricularDTO> gradeCurriculares = service.listarTudo();
        return ResponseEntity.ok(gradeCurriculares);
    }

    @GetMapping("/{idGradeCurricular}")
    public ResponseEntity<GradeCurricularDTO> buscarPorId(@PathVariable Long idGradeCurricular) {
        GradeCurricularDTO gradeCurricular = service.buscarPorId(idGradeCurricular);
        return ResponseEntity.ok(gradeCurricular);
    }
 
    @GetMapping("/curriculo/{idCurriculo}/serie/{idSerie}")
    public ResponseEntity<List<GradeCurricularDTO>> buscarPorIdCurriculoEIdSerie(
            @PathVariable Long idCurriculo,
            @PathVariable Long idSerie) {
        List<GradeCurricularDTO> gradeCurricular = service.buscarPorIdCurriculoEIdSerie(idCurriculo, idSerie);
        return ResponseEntity.ok(gradeCurricular);
    }
    

    @PostMapping
    public ResponseEntity<GradeCurricularDTO> cadastrar(@RequestBody @Valid CadastroGradeCurricularDTO dto) {
        GradeCurricularDTO gradeCurricularDTO = service.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(gradeCurricularDTO.getIdGradeCurricular()).toUri();
        return ResponseEntity.created(uri).body(gradeCurricularDTO);
    }

    @PutMapping
    public ResponseEntity<GradeCurricularDTO> atualizar(@RequestBody @Valid CadastroGradeCurricularDTO dto) {
        GradeCurricularDTO gradeCurricularDTO = service.atualizar(dto);
        return ResponseEntity.ok(gradeCurricularDTO);
    }

    @PutMapping("/{idGradeCurricular}/ativar")
    public ResponseEntity<?> ativar(@PathVariable Long idGradeCurricular) {
        service.ativaDesativa('S', idGradeCurricular);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{idGradeCurricular}/desativar")
    public ResponseEntity<?> desatviar(@PathVariable Long idGradeCurricular) {
        service.ativaDesativa('N', idGradeCurricular);
        return ResponseEntity.ok().build();
    }
}