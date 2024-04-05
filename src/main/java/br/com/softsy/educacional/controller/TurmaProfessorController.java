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

import br.com.softsy.educacional.dto.CadastroTurmaProfessorDTO;
import br.com.softsy.educacional.dto.TurmaProfessorDTO;
import br.com.softsy.educacional.service.TurmaProfessorService;

@RestController
@RequestMapping("/turmaProfessor")
public class TurmaProfessorController {

    @Autowired
    private TurmaProfessorService turmaProfessorService;

    @GetMapping
    public ResponseEntity<List<TurmaProfessorDTO>> listar() {
        List<TurmaProfessorDTO> turmasProfessores = turmaProfessorService.listarTudo();
        return ResponseEntity.ok(turmasProfessores);
    }

    @GetMapping("/professor/{idProfessor}")
    public ResponseEntity<List<TurmaProfessorDTO>> buscarPorIdProfessor(@PathVariable Long idProfessor) {
        List<TurmaProfessorDTO> turmasProfessores = turmaProfessorService.buscarPorIdProfessor(idProfessor);
        return ResponseEntity.ok(turmasProfessores);
    }

    @PostMapping
    public ResponseEntity<TurmaProfessorDTO> cadastrar(@RequestBody @Valid CadastroTurmaProfessorDTO dto) {
        TurmaProfessorDTO turmaProfessorDTO = turmaProfessorService.salvar(dto);
        return ResponseEntity.ok(turmaProfessorDTO);
    }

    @PutMapping
    public ResponseEntity<TurmaProfessorDTO> atualizar(@RequestBody @Valid CadastroTurmaProfessorDTO dto) {
        TurmaProfessorDTO turmaProfessorDTO = turmaProfessorService.atualizar(dto);
        return ResponseEntity.ok(turmaProfessorDTO);
    }

    @DeleteMapping("/{idProfessorDeficiencia}")
    public ResponseEntity<?> excluir(@PathVariable Long idProfessorDeficiencia) {
        turmaProfessorService.remover(idProfessorDeficiencia);
        return ResponseEntity.ok().build();
    }
}