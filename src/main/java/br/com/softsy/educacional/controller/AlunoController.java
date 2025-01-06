package br.com.softsy.educacional.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.softsy.educacional.dto.AlunoDTO;
import br.com.softsy.educacional.dto.CadastroAlunoDTO;
import br.com.softsy.educacional.dto.UsuarioDTO;
import br.com.softsy.educacional.model.AllResponse;
import br.com.softsy.educacional.service.AlunoService;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @GetMapping
    public ResponseEntity<List<AlunoDTO>> listar() {
        List<AlunoDTO> alunos = alunoService.listarTudo();
        return ResponseEntity.ok(alunos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlunoDTO> buscarPorId(@PathVariable Long id) {
        AlunoDTO alunoDTO = alunoService.buscarPorId(id);
        return ResponseEntity.ok(alunoDTO);
    }
    
    @GetMapping("/conta/{idConta}")
    public ResponseEntity<List<AlunoDTO>> buscarPorIdConta(@PathVariable Long idConta) {
        List<AlunoDTO> alunos = alunoService.buscarPorIdConta(idConta);
        return ResponseEntity.ok(alunos);
    }

    @PostMapping
    public ResponseEntity<AlunoDTO> cadastrar(@RequestBody @Valid CadastroAlunoDTO dto) {
        AlunoDTO alunoDTO = alunoService.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(alunoDTO.getIdAluno()).toUri();
        return ResponseEntity.created(uri).body(alunoDTO);
    }
    
    @PutMapping
    public ResponseEntity<AlunoDTO> atualizar(@RequestBody @Valid CadastroAlunoDTO dto) {
        return ResponseEntity.ok(alunoService.atualizar(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        alunoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/semPrematricula")
    public ResponseEntity<AllResponse> listarAlunosSemPrematricula() {

        List<Map<String, Object>> result = alunoService.listarAlunosSemPrematricula();

        if (result.isEmpty()) {
            return ResponseEntity.ok(new AllResponse("Não existem resultados para essa requisição.", new ArrayList<>()));
        }

        return ResponseEntity.ok(new AllResponse("Encontrado!", new ArrayList<>(result)));
    }
    
    @GetMapping("/turmaDisciplina")
	public Object listarTurmaDiscipliaAluno(@RequestParam(value = "idAluno", required = false) Long idAluno) {
		if (idAluno == null) {
			return "Por favor, informe o parâmetro na requisição.";
		}

		List<Map<String, Object>> result = alunoService.listarTurmaDisciplinaAluno(idAluno);

		if (result.isEmpty()) {
			return "Nenhum resultado encontrado para os parâmetros informados.";
		}

		return result;
	}
 
    
}