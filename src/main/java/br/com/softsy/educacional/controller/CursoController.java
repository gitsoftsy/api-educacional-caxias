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

import br.com.softsy.educacional.dto.CadastroCursoDTO;
import br.com.softsy.educacional.dto.CursoDTO;
import br.com.softsy.educacional.service.CursoService;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @GetMapping
    public ResponseEntity<List<CursoDTO>> listar() {
        List<CursoDTO> cursos = cursoService.listarTudo();
        return ResponseEntity.ok(cursos);
    }

    @GetMapping("/conta/{idConta}")
    public ResponseEntity<List<CursoDTO>> buscarPorIdConta(@PathVariable Long idConta) {
        List<CursoDTO> curso = cursoService.buscarPorIdConta(idConta);
        return ResponseEntity.ok(curso);
    }

    @PostMapping
    public ResponseEntity<CursoDTO> cadastrar(@RequestBody @Valid CadastroCursoDTO dto) {
        CursoDTO cursoDTO = cursoService.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(cursoDTO.getIdCurso()).toUri();
        return ResponseEntity.created(uri).body(cursoDTO);
    }

    @PutMapping
    public ResponseEntity<CursoDTO> atualizar(@RequestBody @Valid CadastroCursoDTO dto) {
        CursoDTO cursoDTO = cursoService.atualizar(dto);
        return ResponseEntity.ok(cursoDTO);
    }
    
	@PutMapping("/{idCurso}/ativar")
	public ResponseEntity<?> ativar(@PathVariable Long idCurso){
		cursoService.ativaDesativa('S', idCurso);
		return ResponseEntity.ok().build();	
	}
	
	
	@PutMapping("/{idCurso}/desativar")
	public ResponseEntity<?> desatviar(@PathVariable Long idCurso){
		cursoService.ativaDesativa('N', idCurso);
		return ResponseEntity.ok().build();
	}
}
