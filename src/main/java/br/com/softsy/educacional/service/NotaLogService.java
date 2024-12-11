package br.com.softsy.educacional.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CadastroNotaLogDTO;
import br.com.softsy.educacional.dto.NotaLogDTO;
import br.com.softsy.educacional.model.Aluno;
import br.com.softsy.educacional.model.Nota;
import br.com.softsy.educacional.model.NotaLog;
import br.com.softsy.educacional.model.Professor;
import br.com.softsy.educacional.model.Prova;
import br.com.softsy.educacional.model.Usuario;
import br.com.softsy.educacional.repository.AlunoRepository;
import br.com.softsy.educacional.repository.NotaLogRepository;
import br.com.softsy.educacional.repository.NotaRepository;
import br.com.softsy.educacional.repository.ProfessorRepository;
import br.com.softsy.educacional.repository.ProvaRepository;
import br.com.softsy.educacional.repository.UsuarioRepository;

@Service
public class NotaLogService {
	
	@Autowired
    private NotaLogRepository repository;
	
	@Autowired
    private NotaRepository notaRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ProvaRepository provaRepository;
    
	@Autowired
    private ProfessorRepository professorRepository;
	
	@Autowired
    private UsuarioRepository usuarioRepository;
	
	  @Transactional(readOnly = true)
	    public List<NotaLogDTO> listarTudo() {
	        List<NotaLog> notas = repository.findAll();
	        return notas.stream()
	                .map(NotaLogDTO::new)
	                .collect(Collectors.toList());
	    }
	  
	  @Transactional(readOnly = true)
	  public List<NotaLogDTO> buscarPorIdAluno(Long idAluno) {
	      List<NotaLog> notaLog = repository.findByAluno_IdAluno(idAluno).orElse(Collections.emptyList());

	      if (notaLog.isEmpty()) {
	          throw new NoSuchElementException("Não há notas registradas para o aluno com ID: " + idAluno);
	      }
	      return notaLog.stream()
	              .map(NotaLogDTO::new)
	              .collect(Collectors.toList());
	  }

	  @Transactional(readOnly = true)
	  public List<NotaLogDTO> buscarPorIdProva(Long idProva) {
	      List<NotaLog> notaLog = repository.findByProva_IdProva(idProva).orElse(Collections.emptyList());

	      if (notaLog.isEmpty()) {
	          throw new NoSuchElementException("Não há notas registradas para a prova com ID: " + idProva);
	      }

	      return notaLog.stream()
	              .map(NotaLogDTO::new)
	              .collect(Collectors.toList());
	  }


	    @Transactional(readOnly = true)
	    public List<NotaLogDTO> buscarPorIdNota(Long idNota) {
	        List<NotaLog> notaLog = repository.findByNota_IdNota(idNota).orElse(Collections.emptyList());
	        if (notaLog.isEmpty()) {
	            throw new NoSuchElementException("Não há notas registradas para o ID: " + idNota);
	        }
	        return notaLog.stream()
	                .map(NotaLogDTO::new)
	                .collect(Collectors.toList());
	    }

	    
		
	    @Transactional
	    public NotaLogDTO salvar(CadastroNotaLogDTO dto) throws IOException {
	        NotaLog notaLog = criarNotaLogAPartirDTO(dto);
	        notaLog = repository.save(notaLog);
	        return new NotaLogDTO(notaLog);
	    }

	    private NotaLog criarNotaLogAPartirDTO(CadastroNotaLogDTO dto) {
	    	NotaLog notaLog = new NotaLog();
	        BeanUtils.copyProperties(dto, notaLog, "idNotaLog", "dataCadastro", "aluno", "prova", "nota");

	    	if(dto.getUsuarioId() == null && dto.getProfessorId() == null) {
				throw new IllegalArgumentException("Pelo menos um dos campos usuarioId ou professorId deve ser preenchido");
			}
			if(dto.getUsuarioId() != null && dto.getProfessorId() != null) {
				throw new IllegalArgumentException("Informe apenas um dos campos, usuarioId ou professorId.");
			}
			
			
	        Aluno aluno = alunoRepository.findById(dto.getAlunoId())
	                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado"));

	        Prova prova = provaRepository.findById(dto.getProva())
	                .orElseThrow(() -> new IllegalArgumentException("Prova não encontrada"));

	        Nota nota = notaRepository.findById(dto.getNota())
	                .orElseThrow(() -> new IllegalArgumentException("Nota não encontrada"));
	        
	        // Validar e configurar o Usuário ou Professor
	        if (dto.getUsuarioId() != null) {
	            Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
	                    .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
	            notaLog.setUsuario(usuario);
	        } else {
	            Professor professor = professorRepository.findById(dto.getProfessorId())
	                    .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado"));
	            notaLog.setProfessor(professor);
	        }

	        notaLog.setAluno(aluno);
	        notaLog.setNota(nota);
	        notaLog.setProva(prova);
	        notaLog.setOperacao(dto.getOperacao());
	        notaLog.setNotaAnterior(dto.getNotaAnterior());
	        notaLog.setNotaAtual(dto.getNotaAtual());
	        notaLog.setDataCadastro(LocalDateTime.now());

	        return notaLog;
	    }   
	    
}
