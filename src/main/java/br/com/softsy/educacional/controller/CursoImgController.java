package br.com.softsy.educacional.controller;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.softsy.educacional.controller.LoginController.ErrorResponse;
import br.com.softsy.educacional.dto.CadastroCursoImgDTO;
import br.com.softsy.educacional.model.CursoImg;
import br.com.softsy.educacional.service.CursoImgService;

@RestController
@RequestMapping("/cursoImg")
public class CursoImgController {

    @Autowired
    private CursoImgService cursoImgService;

    @PostMapping("/imagem")
    public ResponseEntity<CadastroCursoImgDTO> cadastrar(@RequestBody @Valid CadastroCursoImgDTO dto) throws IOException {
    	CadastroCursoImgDTO cadastroCursoImgDTO = cursoImgService.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(cadastroCursoImgDTO.getIdCursoImg()).toUri();
        return ResponseEntity.created(uri).body(cadastroCursoImgDTO);
    }
    
}

