package br.com.softsy.educacional.controller;

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

import br.com.softsy.educacional.dto.CadastroProfessorEscolaDTO;
import br.com.softsy.educacional.dto.ProfessorEscolaDTO;
import br.com.softsy.educacional.service.ProfessorEscolaService;

@RestController
@RequestMapping("/professorEscola")
public class ProfessorEscolaController {

    @Autowired
    private ProfessorEscolaService professorEscolaService;

    @GetMapping
    public ResponseEntity<List<ProfessorEscolaDTO>> listar() {
        List<ProfessorEscolaDTO> professorEscolas = professorEscolaService.listarTudo();
        return ResponseEntity.ok(professorEscolas);
    }

    @GetMapping("/professor/{idProfessor}")
    public ResponseEntity<List<ProfessorEscolaDTO>> buscarPorIdProfessor(@PathVariable Long idProfessor) {
        List<ProfessorEscolaDTO> professorEscolas = professorEscolaService.buscarPorIdProfessor(idProfessor);
        return ResponseEntity.ok(professorEscolas);
    }

    @PostMapping
    public ResponseEntity<ProfessorEscolaDTO> cadastrar(@RequestBody @Valid CadastroProfessorEscolaDTO dto) {
        ProfessorEscolaDTO professorEscolaDTO = professorEscolaService.salvar(dto);
        return ResponseEntity.ok(professorEscolaDTO);
    }

    @PutMapping
    public ResponseEntity<ProfessorEscolaDTO> atualizar(@RequestBody @Valid CadastroProfessorEscolaDTO dto) {
        ProfessorEscolaDTO professorEscolaDTO = professorEscolaService.atualizar(dto);
        return ResponseEntity.ok(professorEscolaDTO);
    }
    
    @PutMapping("/{idProfessorEscola}/ativar")
    public ResponseEntity<?> ativar(@PathVariable Long idProfessorEscola){
        professorEscolaService.ativaDesativa('S', idProfessorEscola);
        return ResponseEntity.ok().build();    
    }
    
    @PutMapping("/{idProfessorEscola}/desativar")
    public ResponseEntity<?> desativar(@PathVariable Long idProfessorEscola){
        professorEscolaService.ativaDesativa('N', idProfessorEscola);
        return ResponseEntity.ok().build();
    }
}

