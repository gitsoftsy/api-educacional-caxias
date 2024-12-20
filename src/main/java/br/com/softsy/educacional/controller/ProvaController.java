package br.com.softsy.educacional.controller;

import java.net.URI;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.softsy.educacional.dto.CadastroProvaDTO;
import br.com.softsy.educacional.dto.ProvaDTO;
import br.com.softsy.educacional.service.ProvaService;

@RestController
@RequestMapping("/prova")
public class ProvaController {
	
	 @Autowired
	 private ProvaService service;

	    @GetMapping
	    public ResponseEntity<List<ProvaDTO>> listar() {
	        return ResponseEntity.ok(service.listarTudo());
	    }
	    
	    @GetMapping("/{idProva}")
	    public ResponseEntity<ProvaDTO> buscarPorId(@PathVariable Long idProva) {
	        return ResponseEntity.ok(service.buscarPorId(idProva));
	    }
	    
	    @GetMapping("/turma/{idTurma}")
	    public ResponseEntity<List<ProvaDTO>> buscarPorIdTurma(@PathVariable Long idTurma) {
	        List<ProvaDTO> prova = service.buscarPorIdTurma(idTurma);
	        return ResponseEntity.ok(prova);
	    }

	    
	    @PostMapping
	    public ResponseEntity<ProvaDTO> cadastrar(@RequestBody @Valid CadastroProvaDTO dto) {
	        ProvaDTO provaDTO = service.salvar(dto);
	        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idProva}")
	                .buildAndExpand(provaDTO.getIdProva()).toUri();
	        return ResponseEntity.created(uri).body(provaDTO);
	    }

	    @PutMapping
	    public ResponseEntity<ProvaDTO> atualizar(@RequestBody @Valid CadastroProvaDTO dto) {
	        ProvaDTO provaDTO = service.atualizar(dto);
	        return ResponseEntity.ok(provaDTO);
	    }
	    
	    @GetMapping("/listarProvas")
	    public ResponseEntity<List<Map<String, Object>>> listarProvas(
	            @RequestParam (value = "idEscola", required = false) Long idEscola,
	            @RequestParam (value = "ano", required = false) Integer ano,
	            @RequestParam (value = "periodoLetivo", required = false) Integer periodoLetivo,
	            @RequestParam (value = "idTurno", required = false) Long idTurno,
	            @RequestParam (value = "idTurma", required = false) Long idTurma,
	            @RequestParam (value = "idDisciplina", required = false) Long idDisciplina)
	    {
 
	        List<Map<String, Object>> provas = service.listarProvas(idEscola, ano, periodoLetivo, idTurno, idTurma, idDisciplina);
	        return ResponseEntity.ok(provas);
	    }

	    @PutMapping("/{idProva}/ativar")
	    public ResponseEntity<Void> ativar(@PathVariable Long idProva) {
	    	service.ativaDesativa('S', idProva);
	        return ResponseEntity.ok().build();
	    }

	    @PutMapping("/{idProva}/desativar")
	    public ResponseEntity<Void> desativar(@PathVariable Long idProva) {
	    	service.ativaDesativa('N', idProva);
	        return ResponseEntity.ok().build();
	    }

}
