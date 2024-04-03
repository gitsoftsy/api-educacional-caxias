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

import br.com.softsy.educacional.dto.CadastroProfessorDTO;
import br.com.softsy.educacional.dto.ProfessorDTO;
import br.com.softsy.educacional.service.ProfessorService;

@RestController
@RequestMapping("/professores")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    @GetMapping
    public ResponseEntity<List<ProfessorDTO>> listar() {
        return ResponseEntity.ok(professorService.listarTudo());
    }

    @PostMapping
    public ResponseEntity<CadastroProfessorDTO> cadastrar(@RequestBody @Valid CadastroProfessorDTO dto) {
        CadastroProfessorDTO cadastroDTO = professorService.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(cadastroDTO.getIdProfessor()).toUri();
        return ResponseEntity.created(uri).body(cadastroDTO);
    }

    @GetMapping("/{idProfessor}")
    public ResponseEntity<ProfessorDTO> buscarPorId(@PathVariable Long idProfessor) {
        return ResponseEntity.ok(professorService.buscarPorId(idProfessor));
    }

    @PutMapping
    public ResponseEntity<?> atualizar(@RequestBody @Valid CadastroProfessorDTO dto) {
        return ResponseEntity.ok(professorService.atualizar(dto));
    }

    @PutMapping("/{idProfessor}/ativar")
    public ResponseEntity<?> ativar(@PathVariable Long idProfessor) {
        professorService.ativaDesativa('S', idProfessor);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{idProfessor}/desativar")
    public ResponseEntity<?> desativar(@PathVariable Long idProfessor) {
        professorService.ativaDesativa('N', idProfessor);
        return ResponseEntity.ok().build();
    }
}