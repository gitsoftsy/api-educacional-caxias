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

import br.com.softsy.educacional.dto.CadastroProfessorFormacaoDTO;
import br.com.softsy.educacional.dto.ProfessorFormacaoDTO;
import br.com.softsy.educacional.service.ProfessorFormacaoService;

@RestController
@RequestMapping("/professorFormacao")
public class ProfessorFormacaoController {

    @Autowired
    private ProfessorFormacaoService professorFormacaoService;

    @GetMapping
    public ResponseEntity<List<ProfessorFormacaoDTO>> listar() {
        List<ProfessorFormacaoDTO> professorFormacoes = professorFormacaoService.listarTudo();
        return ResponseEntity.ok(professorFormacoes);
    }

    @GetMapping("/professor/{idProfessor}")
    public ResponseEntity<List<ProfessorFormacaoDTO>> buscarPorIdProfessor(@PathVariable Long idProfessor) {
        List<ProfessorFormacaoDTO> professorFormacoes = professorFormacaoService.buscarPorIdProfessor(idProfessor);
        return ResponseEntity.ok(professorFormacoes);
    }

    @PostMapping
    public ResponseEntity<ProfessorFormacaoDTO> cadastrar(@RequestBody @Valid CadastroProfessorFormacaoDTO dto) {
        ProfessorFormacaoDTO professorFormacaoDTO = professorFormacaoService.salvar(dto);
        return ResponseEntity.ok(professorFormacaoDTO);
    }

    @PutMapping
    public ResponseEntity<ProfessorFormacaoDTO> atualizar(@RequestBody @Valid CadastroProfessorFormacaoDTO dto) {
        ProfessorFormacaoDTO professorFormacaoDTO = professorFormacaoService.atualizar(dto);
        return ResponseEntity.ok(professorFormacaoDTO);
    }
    
	@PutMapping("/{idProfessorFormacao}/ativar")
	public ResponseEntity<?> ativar(@PathVariable Long idProfessorFormacao){
		professorFormacaoService.ativaDesativa('S', idProfessorFormacao);
		return ResponseEntity.ok().build();	
	}
	
	
	@PutMapping("/{idProfessorFormacao}/desativar")
	public ResponseEntity<?> desatviar(@PathVariable Long idProfessorFormacao){
		professorFormacaoService.ativaDesativa('N', idProfessorFormacao);
		return ResponseEntity.ok().build();
	}

}
