package br.com.softsy.educacional.controller;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
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

import br.com.softsy.educacional.dto.CadastroNotaDTO;
import br.com.softsy.educacional.dto.CadastroNotaLogDTO;
import br.com.softsy.educacional.dto.NotaDTO;
import br.com.softsy.educacional.model.NotaLog;
import br.com.softsy.educacional.repository.NotaLogRepository;
import br.com.softsy.educacional.service.NotaLogService;
import br.com.softsy.educacional.service.NotaService;

@RestController
@RequestMapping("/nota")
public class NotaController {

    @Autowired
    private NotaService service;
    
    @Autowired
    private NotaLogService notaLogService;
    
	@Autowired
    private NotaLogRepository notaLogRepository;

    @GetMapping
    public ResponseEntity<List<NotaDTO>> listar() {
        return ResponseEntity.ok(service.listarTudo());
    }

    @GetMapping("/{idNota}")
    public ResponseEntity<NotaDTO> buscarPorId(@PathVariable Long idNota) {
        return ResponseEntity.ok(service.buscarPorId(idNota));
    }
    
    @GetMapping("/aluno/{idAluno}")
    public ResponseEntity<List<NotaDTO>> buscarPorIdAluno(@PathVariable Long idAluno) {
        List<NotaDTO> alunos = service.buscarPorIdAluno(idAluno);
        return ResponseEntity.ok(alunos);
    }

    @PostMapping
    public ResponseEntity<NotaDTO> cadastrar(@RequestBody @Valid CadastroNotaDTO dto) throws IOException {
        NotaDTO notaDTO = service.salvar(dto);

        CadastroNotaLogDTO notaLogDTO = new CadastroNotaLogDTO();
        notaLogDTO.setAlunoId(dto.getAlunoId());
        notaLogDTO.setProva(dto.getProvaId());
        notaLogDTO.setNota(notaDTO.getIdNota());
        notaLogDTO.setOperacao('I'); 
        notaLogDTO.setNotaAtual(dto.getNota());
        notaLogDTO.setUsuarioId(dto.getUsuarioId());
        notaLogDTO.setProfessorId(dto.getProfessorId());

        notaLogService.salvar(notaLogDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{idNota}")
                .buildAndExpand(notaDTO.getIdNota())
                .toUri();
        return ResponseEntity.created(uri).body(notaDTO);
    }

    @PutMapping
    public ResponseEntity<NotaDTO> atualizar(@RequestBody @Valid CadastroNotaDTO dto) throws IOException {
        NotaDTO notaExistente = service.buscarPorId(dto.getIdNota());

        NotaDTO notaDTO = service.atualizar(dto);

        CadastroNotaLogDTO notaLogDTO = new CadastroNotaLogDTO();
        notaLogDTO.setNota(dto.getIdNota());
        notaLogDTO.setNotaAnterior(notaExistente.getNota());
        notaLogDTO.setNotaAtual(dto.getNota());
        notaLogDTO.setAlunoId(dto.getAlunoId());
        notaLogDTO.setProva(dto.getProvaId());
        notaLogDTO.setUsuarioId(dto.getUsuarioId());
        notaLogDTO.setProfessorId(dto.getProfessorId());
        notaLogDTO.setDataCadastro(LocalDateTime.now());

        notaLogService.criarNovoRegistro(notaLogDTO);

        return ResponseEntity.ok(notaDTO);
    }

}