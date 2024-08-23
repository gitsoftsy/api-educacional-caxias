package br.com.softsy.educacional.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import br.com.softsy.educacional.dto.CadastroPessoaProfessorDTO;
import br.com.softsy.educacional.dto.CadastroProfessorDTO;
import br.com.softsy.educacional.dto.PessoaDTO;
import br.com.softsy.educacional.dto.ProfessorDTO;
import br.com.softsy.educacional.infra.config.PasswordEncrypt;
import br.com.softsy.educacional.model.Pessoa;
import br.com.softsy.educacional.model.Professor;
import br.com.softsy.educacional.repository.PessoaRepository;
import br.com.softsy.educacional.repository.ProfessorRepository;
import br.com.softsy.educacional.service.PessoaService;
import br.com.softsy.educacional.service.ProfessorService;

@RestController
@RequestMapping("/professores")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;
    
    @Autowired
    private PessoaService pessoaService;
    
	@Autowired
	private PasswordEncrypt encrypt;
	
    @Autowired
    private PessoaRepository pessoaRepository;
    
    @Autowired
    private ProfessorRepository professorRepository;

    @GetMapping
    public ResponseEntity<List<ProfessorDTO>> listar() {
        return ResponseEntity.ok(professorService.listarTudo());
    }

    @PostMapping
    public ResponseEntity<CadastroProfessorDTO> cadastrar(@RequestBody @Valid CadastroProfessorDTO dto) {
        CadastroProfessorDTO cadastroDTO = professorService.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(cadastroDTO.getIdProfessor()).toUri();
        return ResponseEntity.created(uri).body(cadastroDTO);
    }
    
    @PostMapping("/pessoa-professor")
    public ResponseEntity<Object> cadastrarPessoaEProfessor(@RequestBody CadastroPessoaProfessorDTO dto) {
        try {
            Pessoa pessoa = pessoaService.criarPessoaAPartirDTO(dto.getPessoaDTO());
            pessoa.setSenha(encrypt.hashPassword(pessoa.getSenha()));
            pessoa = pessoaRepository.save(pessoa);

            dto.getProfessorDTO().setPessoaId(pessoa.getIdPessoa());

            Professor professor = professorService.criarProfessorAPartirDTO(dto.getProfessorDTO());
            professor.setSenha(encrypt.hashPassword(professor.getSenha()));
            professor = professorRepository.save(professor);

            CadastroResponseDTO responseDTO = new CadastroResponseDTO(pessoa.getIdPessoa(), professor.getIdProfessor());
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao cadastrar: " + e.getMessage());
        }
    }
    
    @PutMapping("/pessoa-professor")
    public ResponseEntity<Object> atualizarPessoaEProfessor(@RequestBody CadastroPessoaProfessorDTO dto) {
        try {
            PessoaDTO pessoaDTO = pessoaService.atualizar(dto.getPessoaDTO());
            ProfessorDTO professorDTO = professorService.atualizar(dto.getProfessorDTO());

            CadastroResponseDTO responseDTO = new CadastroResponseDTO(pessoaDTO.getIdPessoa(), professorDTO.getIdProfessor());
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar: " + e.getMessage());
        }
    }
    

    @GetMapping("/{idProfessor}")
    public ResponseEntity<ProfessorDTO> buscarPorId(@PathVariable Long idProfessor) {
        return ResponseEntity.ok(professorService.buscarPorId(idProfessor));
    }
    
    @GetMapping("/conta/{idConta}")
    public ResponseEntity<List<ProfessorDTO>> buscarPorIdConta(@PathVariable Long idConta) {
        List<ProfessorDTO> curso = professorService.buscarPorIdConta(idConta);
        return ResponseEntity.ok(curso);
    }
    
    @GetMapping("/disciplinas")
    public ResponseEntity<AllResponse> listaDisciplinasProfessor(
            @RequestParam(value = "idProfessor", required = false) Long idProfessor
    ) {
        if (idProfessor == null) {
            return ResponseEntity.badRequest().body(new AllResponse("Por favor, informe o parâmetro idProfessor na requisição.", new ArrayList<>()));
        }

        List<Map<String, Object>> result = professorService.listaDisciplinasProfessor(idProfessor);

        if (result.isEmpty()) {
            return ResponseEntity.ok(new AllResponse("Nenhum resultado encontrado para os parâmetros informados.", new ArrayList<>()));
        }

        return ResponseEntity.ok(new AllResponse("Encontrado!", new ArrayList<>(result)));
    }

    @GetMapping("/escolas")
    public ResponseEntity<AllResponse> listaEscolasProfessor(
            @RequestParam(value = "idProfessor", required = false) Long idProfessor
    ) {
        if (idProfessor == null) {
            return ResponseEntity.badRequest().body(new AllResponse("Por favor, informe o parâmetro idProfessor na requisição.", new ArrayList<>()));
        }

        List<Map<String, Object>> result = professorService.listaEscolasProfessor(idProfessor);

        if (result.isEmpty()) {
            return ResponseEntity.ok(new AllResponse("Nenhum resultado encontrado para os parâmetros informados.", new ArrayList<>()));
        }

        return ResponseEntity.ok(new AllResponse("Encontrado!", new ArrayList<>(result)));
    }

    @GetMapping("/filtrar")
    public ResponseEntity<AllResponse> filtrarProfessor(
            @RequestParam(value = "cpf", required = false) String cpf,
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "matricula", required = false) String matricula
    ) {
        if (cpf == null && nome == null && matricula == null) {
            return ResponseEntity.badRequest().body(new AllResponse("Por favor, informe ao menos um parâmetro na requisição.", new ArrayList<>()));
        }

        List<Map<String, Object>> result = professorService.filtrarProfessor(cpf, nome, matricula);

        if (result.isEmpty()) {
            return ResponseEntity.ok(new AllResponse("Nenhum resultado encontrado para os parâmetros informados.", new ArrayList<>()));
        }

        return ResponseEntity.ok(new AllResponse("Encontrado!", new ArrayList<>(result)));
    }
    
    @GetMapping("/filtrarDisciplinaEscola")
    public ResponseEntity<AllResponse> filtrarProfessorDisciplinaEscola(
            @RequestParam(value = "idEscola", required = false) Long idEscola,
            @RequestParam(value = "idDisciplina", required = false) Long idDisciplina
    ) {
        if (idEscola == null && idDisciplina == null) {
            return ResponseEntity.badRequest().body(new AllResponse("Por favor, informe ao menos um parâmetro na requisição.", new ArrayList<>()));
        }

        List<Map<String, Object>> result = professorService.filtrarProfessorPorEscolaEDisciplina(idEscola, idDisciplina);

        if (result.isEmpty()) {
            return ResponseEntity.ok(new AllResponse("Nenhum resultado encontrado para os parâmetros informados.", new ArrayList<>()));
        }

        return ResponseEntity.ok(new AllResponse("Encontrado!", new ArrayList<>(result)));
    }
    
    
    @GetMapping("/verificar-matricula")
    public ResponseEntity<AllResponse> verificarMatricula(
            @RequestParam(value = "matricula", required = false) String matricula
    ) {
        if (matricula == null) {
            return ResponseEntity.badRequest().body(new AllResponse("Por favor, informe o parâmetro matrícula na requisição.", new ArrayList<>()));
        }

        List<ProfessorDTO> result = professorService.buscarPorMatricula(matricula);

        if (result.isEmpty()) {
            return ResponseEntity.ok(new AllResponse("Nenhum resultado encontrado para os parâmetros informados.", new ArrayList<>()));
        }

        return ResponseEntity.ok(new AllResponse("Encontrado!", new ArrayList<>(result)));
    }
    
    @GetMapping("/verificar-usuario")
    public ResponseEntity<AllResponse> verificarUsuario(
            @RequestParam(value = "usuario", required = false) String usuario
    ) {
        if (usuario == null) {
            return ResponseEntity.badRequest().body(new AllResponse("Por favor, informe o parâmetro usuário na requisição.", new ArrayList<>()));
        }

        List<ProfessorDTO> result = professorService.buscarPorUsuario(usuario);

        if (result.isEmpty()) {
            return ResponseEntity.ok(new AllResponse("Nenhum resultado encontrado para os parâmetros informados.", new ArrayList<>()));
        }

        return ResponseEntity.ok(new AllResponse("Encontrado!", new ArrayList<>(result)));
    }


    @PutMapping
    public ResponseEntity<?> atualizar(@RequestBody @Valid CadastroProfessorDTO dto) {
        return ResponseEntity.ok(professorService.atualizar(dto));
    }

    @PutMapping("/{idProfessor}/ativar")
    public ResponseEntity<?> ativar(@PathVariable Long idProfessor) {
        professorService.ativaDesativa('S', idProfessor);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{idProfessor}/desativar")
    public ResponseEntity<?> desativar(@PathVariable Long idProfessor) {
        professorService.ativaDesativa('N', idProfessor);
        return ResponseEntity.ok().build();
    }
    
    
    private static class CadastroResponseDTO {
        private Long idPessoa;
        private Long idProfessor;

        public CadastroResponseDTO(Long idPessoa, Long idProfessor) {
            this.idPessoa = idPessoa;
            this.idProfessor = idProfessor;
        }

        // Getters e Setters
        public Long getIdPessoa() {
            return idPessoa;
        }

        public void setIdPessoa(Long idPessoa) {
            this.idPessoa = idPessoa;
        }

        public Long getIdProfessor() {
            return idProfessor;
        }

        public void setIdProfessor(Long idProfessor) {
            this.idProfessor = idProfessor;
        }
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