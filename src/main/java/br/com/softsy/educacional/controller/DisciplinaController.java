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

import br.com.softsy.educacional.dto.CadastroDisciplinaDTO;
import br.com.softsy.educacional.dto.DisciplinaDTO;
import br.com.softsy.educacional.service.DisciplinaService;

@RestController
@RequestMapping("/disciplina")
public class DisciplinaController {

    @Autowired
    private DisciplinaService disciplinaService;

    @GetMapping
    public ResponseEntity<List<DisciplinaDTO>> listar() {
        List<DisciplinaDTO> disciplinas = disciplinaService.listarTudo();
        return ResponseEntity.ok(disciplinas);
    }

    @GetMapping("/conta/{idConta}")
    public ResponseEntity<List<DisciplinaDTO>> buscarPorIdConta(@PathVariable Long idConta) {
        List<DisciplinaDTO> disciplinas = disciplinaService.buscarPorIdConta(idConta);
        return ResponseEntity.ok(disciplinas);
    }
    
    @GetMapping("/areaConhecimento/{idAreaConhecimento}")
    public ResponseEntity<List<DisciplinaDTO>> buscarPorIdAreaConhecimento(@PathVariable Long idAreaConhecimento) {
        List<DisciplinaDTO> disciplinas = disciplinaService.buscarPorIdAreaConhecimento(idAreaConhecimento);
        return ResponseEntity.ok(disciplinas);
    }

    @PostMapping
    public ResponseEntity<DisciplinaDTO> cadastrar(@RequestBody @Valid CadastroDisciplinaDTO dto) {
        DisciplinaDTO disciplinaDTO = disciplinaService.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(disciplinaDTO.getIdDisciplina()).toUri();
        return ResponseEntity.created(uri).body(disciplinaDTO);
    }

    @PutMapping
    public ResponseEntity<DisciplinaDTO> atualizar(@RequestBody @Valid CadastroDisciplinaDTO dto) {
        DisciplinaDTO disciplinaDTO = disciplinaService.atualizar(dto);
        return ResponseEntity.ok(disciplinaDTO);
    }
    
	@PutMapping("/{idDisciplina}/ativar")
	public ResponseEntity<?> ativar(@PathVariable Long idDisciplina){
		disciplinaService.ativaDesativa('S', idDisciplina);
		return ResponseEntity.ok().build();	
	}
	
	
	@PutMapping("/{idDisciplina}/desativar")
	public ResponseEntity<?> desativar(@PathVariable Long idDisciplina){
		disciplinaService.ativaDesativa('N', idDisciplina);
		return ResponseEntity.ok().build();
	}
}
