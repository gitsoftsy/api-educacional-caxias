package br.com.softsy.educacional.controller;

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

import br.com.softsy.educacional.dto.CadastroProfessorDeficienciaDTO;
import br.com.softsy.educacional.dto.ProfessorDeficienciaDTO;
import br.com.softsy.educacional.service.ProfessorDeficienciaService;

@RestController
@RequestMapping("/professoresDeficiencia")
public class ProfessorDeficienciaController {

    @Autowired
    private ProfessorDeficienciaService service;

    @GetMapping
    public ResponseEntity<List<ProfessorDeficienciaDTO>> listar() {
        List<ProfessorDeficienciaDTO> professoresDeficiencia = service.listarTudo();
        return ResponseEntity.ok(professoresDeficiencia);
    }

    @GetMapping("/professor/{idProfessor}")
    public ResponseEntity<List<ProfessorDeficienciaDTO>> buscarPorIdProfessor(@PathVariable Long idProfessor) {
        List<ProfessorDeficienciaDTO> professoresDeficiencia = service.buscarPorIdProfessor(idProfessor);
        return ResponseEntity.ok(professoresDeficiencia);
    }

    @PostMapping
    public ResponseEntity<ProfessorDeficienciaDTO> cadastrar(@RequestBody @Valid CadastroProfessorDeficienciaDTO dto) {
        ProfessorDeficienciaDTO professorDeficienciaDTO = service.salvar(dto);
        return ResponseEntity.ok(professorDeficienciaDTO);
    }

    @PutMapping
    public ResponseEntity<ProfessorDeficienciaDTO> atualizar(@RequestBody @Valid CadastroProfessorDeficienciaDTO dto) {
        ProfessorDeficienciaDTO professorDeficienciaDTO = service.atualizar(dto);
        return ResponseEntity.ok(professorDeficienciaDTO);
    }

    @DeleteMapping("/{idProfessorDeficiencia}")
    public ResponseEntity<?> excluir(@PathVariable Long idProfessorDeficiencia) {
        service.remover(idProfessorDeficiencia);
        return ResponseEntity.ok().build();
    }
}