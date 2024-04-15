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

import br.com.softsy.educacional.dto.ProfessorDisciplinaDTO;
import br.com.softsy.educacional.service.ProfessorDisciplinaService;

@RestController
@RequestMapping("/professorDisciplina")
public class ProfessorDisciplinaController {

    @Autowired
    private ProfessorDisciplinaService service;

    @GetMapping
    public ResponseEntity<List<ProfessorDisciplinaDTO>> listar() {
        List<ProfessorDisciplinaDTO> professorDisciplinas = service.listarTudo();
        return ResponseEntity.ok(professorDisciplinas);
    }

    @GetMapping("/{idProfessorDisciplina}")
    public ResponseEntity<ProfessorDisciplinaDTO> buscarPorId(@PathVariable Long idProfessorDisciplina) {
        ProfessorDisciplinaDTO professorDisciplina = service.buscarPorId(idProfessorDisciplina);
        return ResponseEntity.ok(professorDisciplina);
    }

    @PostMapping
    public ResponseEntity<ProfessorDisciplinaDTO> cadastrar(@RequestBody @Valid ProfessorDisciplinaDTO dto) {
        ProfessorDisciplinaDTO professorDisciplinaDTO = service.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(professorDisciplinaDTO.getIdProfessorDisciplina()).toUri();
        return ResponseEntity.created(uri).body(professorDisciplinaDTO);
    }

    @PutMapping
    public ResponseEntity<ProfessorDisciplinaDTO> atualizar(@RequestBody @Valid ProfessorDisciplinaDTO dto) {
        ProfessorDisciplinaDTO professorDisciplinaDTO = service.atualizar(dto);
        return ResponseEntity.ok(professorDisciplinaDTO);
    }

    @PutMapping("/{idProfessorDisciplina}/ativar")
    public ResponseEntity<?> ativar(@PathVariable Long idProfessorDisciplina) {
        service.ativaDesativa('S', idProfessorDisciplina);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{idProfessorDisciplina}/desativar")
    public ResponseEntity<?> desativar(@PathVariable Long idProfessorDisciplina) {
        service.ativaDesativa('N', idProfessorDisciplina);
        return ResponseEntity.ok().build();
    }
}
