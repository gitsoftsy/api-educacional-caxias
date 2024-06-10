package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CadastroCursoDTO;
import br.com.softsy.educacional.dto.CurriculoDTO;
import br.com.softsy.educacional.dto.CursoDTO;
import br.com.softsy.educacional.infra.exception.UniqueException;
import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.model.Curriculo;
import br.com.softsy.educacional.model.Curso;
import br.com.softsy.educacional.repository.ContaRepository;
import br.com.softsy.educacional.repository.CursoRepository;
import br.com.softsy.educacional.repository.EscolaRepository;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private ContaRepository contaRepository;

    @Transactional(readOnly = true)
    public List<CursoDTO> listarTudo() {
        List<Curso> cursos = cursoRepository.findAll();
        return cursos.stream()
                .map(CursoDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CursoDTO> buscarPorIdConta(Long idConta) {
        List<Curso> curso = cursoRepository.findByConta_IdConta(idConta)
                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar curso por ID da conta"));
        return curso.stream()
                .map(CursoDTO::new)
                .collect(Collectors.toList());
    }
    
	@Transactional(readOnly = true)
	public CursoDTO buscarPorId(Long id) {
		Curso curso = cursoRepository.getReferenceById(id);
		return new CursoDTO(curso);
	}

    @Transactional
    public CursoDTO salvar(CadastroCursoDTO dto) {
    	
    	validarCodCurso(dto.getCodCurso());
    	validarCodCursoInpe(dto.getCodCursoInpe());
    	
        Curso curso = criarCursoAPartirDTO(dto);
        curso = cursoRepository.save(curso);
        return new CursoDTO(curso);
    }

    @Transactional
    public CursoDTO atualizar(CadastroCursoDTO dto) {
        Curso curso = cursoRepository.findById(dto.getIdCurso())
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado"));
        atualizarDados(curso, dto);
        return new CursoDTO(curso);
    }


    private Curso criarCursoAPartirDTO(CadastroCursoDTO dto) {
        Curso curso = new Curso();
        Conta conta = contaRepository.findById(dto.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
        curso.setCodCurso(dto.getCodCurso());
        curso.setNome(dto.getNome());
        curso.setCodCursoInpe(dto.getCodCursoInpe());
        curso.setDataCadastro(LocalDateTime.now());
        curso.setAtivo('S');
        curso.setConta(conta);
        return curso;
    }
    
    private void validarCodCurso(String codCurso) {
        Optional<Curso> codCursoExistente = cursoRepository.findByCodCurso(codCurso).stream().findFirst();
        if (codCursoExistente.isPresent()) {
            throw new UniqueException("Esse código de curso já existe.");
        }
    }
    
    private void validarCodCursoInpe(String codCursoInpe) {
        Optional<Curso> codCursoExistente = cursoRepository.findByCodCursoInpe(codCursoInpe).stream().findFirst();
        if (codCursoExistente.isPresent()) {
            throw new UniqueException("Esse código cursoInpe já existe.");
        }
    }

	@Transactional
	public void ativaDesativa(char status, Long idCurso) {
		Curso curso = cursoRepository.getReferenceById(idCurso);
		curso.setAtivo(status);
	}
	
    
    private void atualizarDados(Curso destino, CadastroCursoDTO origem) {
        Conta conta = contaRepository.findById(origem.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
        destino.setCodCurso(origem.getCodCurso());
        destino.setNome(origem.getNome());
        destino.setCodCursoInpe(origem.getCodCursoInpe());
        destino.setConta(conta);
    }
}