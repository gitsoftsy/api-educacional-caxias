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

import br.com.softsy.educacional.controller.ProfessorController.AllResponse;
import br.com.softsy.educacional.dto.CadastroTurmaDTO;
import br.com.softsy.educacional.dto.TurmaDTO;
import br.com.softsy.educacional.service.TurmaService;

@RestController
@RequestMapping("/turma")
public class TurmaController {

    @Autowired
    private TurmaService turmaService;

    @GetMapping
    public ResponseEntity<List<TurmaDTO>> listar() {
        List<TurmaDTO> turmas = turmaService.listarTudo();
        return ResponseEntity.ok(turmas);
    }

    @PostMapping
    public ResponseEntity<TurmaDTO> cadastrar(@RequestBody @Valid CadastroTurmaDTO dto) {
        TurmaDTO cadastroDTO = turmaService.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(cadastroDTO.getIdTurma()).toUri();
        return ResponseEntity.created(uri).body(cadastroDTO);
    }


    @GetMapping("/{idTurma}")
    public ResponseEntity<TurmaDTO> buscarPorId(@PathVariable Long idTurma) {
        TurmaDTO turma = turmaService.buscarPorId(idTurma);
        return ResponseEntity.ok(turma);
    }
    
    @GetMapping("/filtrar")
    public ResponseEntity<AllResponse> filtrarTurmaDisciplinaEscola(
            @RequestParam(value = "idEscola", required = false) Long idEscola,
            @RequestParam(value = "idDisciplina", required = false) Long idDisciplina
    ) {
        if (idEscola == null && idDisciplina == null) {
            return ResponseEntity.badRequest().body(new AllResponse("Por favor, informe ao menos um parâmetro na requisição.", new ArrayList<>()));
        }

        List<Map<String, Object>> result = turmaService.filtrarTurmaPorEscolaEDisciplina(idEscola, idDisciplina);

        if (result.isEmpty()) {
            return ResponseEntity.ok(new AllResponse("Nenhum resultado encontrado para os parâmetros informados.", new ArrayList<>()));
        }

        return ResponseEntity.ok(new AllResponse("Encontrado!", new ArrayList<>(result)));
    }
    
    @GetMapping("/alunos")
    public ResponseEntity<AllResponse> listarAlunosTurma(
            @RequestParam(value = "idTurma", required = false) Long idTurma
    ) {
        if (idTurma == null) {
            return ResponseEntity.badRequest().body(new AllResponse("Por favor, informe ao menos um parâmetro na requisição.", new ArrayList<>()));
        }

        List<Map<String, Object>> result = turmaService.listarAlunosTurma(idTurma);

        if (result.isEmpty()) {
            return ResponseEntity.ok(new AllResponse("Nenhum resultado encontrado para os parâmetros informados.", new ArrayList<>()));
        }

        return ResponseEntity.ok(new AllResponse("Encontrado!", new ArrayList<>(result)));
    }
    
    @GetMapping("/secretaria")
    public ResponseEntity<AllResponse> listarTurmasSecretaria() {

        List<Map<String, Object>> result = turmaService.listarTurmasSecretaria();

        if (result.isEmpty()) {
            return ResponseEntity.ok(new AllResponse("Nenhuma turma encontrada.", new ArrayList<>()));
        }

        return ResponseEntity.ok(new AllResponse("Encontrado!", new ArrayList<>(result)));
    }
    
    @GetMapping("/filtroTurmas")
	public Object filtrarTurmaDisciplinas(
			@RequestParam(value = "idProfessor", required = false) Long idProfessor,
			@RequestParam(value = "ano", required = false) Integer ano,
			@RequestParam(value = "idPeriodoLetivo", required = false) Long idPeriodoLetivo,
			@RequestParam(value = "idEscola", required = false) Long idEscola,
			@RequestParam(value = "idDisciplina", required = false) Long idDisciplina,
			@RequestParam(value = "idTurno", required = false) Long idTurno,
			@RequestParam(value = "idCurso", required = false) Long idCurso
    )
    {
		if (idProfessor == null) {
			return "Por favor, informe o parâmetro na requisição.";
		}

		List<Map<String, Object>> result = turmaService.filtrarTurmaDisciplinas(idProfessor, ano, idPeriodoLetivo, idEscola, idDisciplina, idTurno, idCurso);

		if (result.isEmpty()) {
			return "Nenhum resultado encontrado para os parâmetros informados.";
		}

		return result;
	}

    
    

    @PutMapping
    public ResponseEntity<?> atualizar(@RequestBody @Valid CadastroTurmaDTO dto) {
        return ResponseEntity.ok(turmaService.atualizar(dto));
    }
    
    @DeleteMapping("/{idTurma}")
    public ResponseEntity<Void> excluir(@PathVariable Long idTurma) {
        turmaService.remover(idTurma);
        return ResponseEntity.ok().build();
    }
    
    @PutMapping("/{idTurma}/ativar")
    public ResponseEntity<?> ativar(@PathVariable Long idTurma) {
    	turmaService.ativaDesativa('S', idTurma);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{idTurma}/desativar")
    public ResponseEntity<?> desatviar(@PathVariable Long idTurma) {
    	turmaService.ativaDesativa('N', idTurma);
        return ResponseEntity.ok().build();
    }

    public class AllResponse {
        private String message;
        private List<Object> data;

        public AllResponse(String message, List<Object> data) {
            this.message = message;
            this.data = data;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
        
        public List<Object> getData() {
            return data;
        }

        public void setData(List<Object> data) {
            this.data = data;
        }
    }
    
}