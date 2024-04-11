package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CursoSerieDTO;
import br.com.softsy.educacional.dto.TurnoDTO;
import br.com.softsy.educacional.model.Curso;
import br.com.softsy.educacional.model.CursoSerie;
import br.com.softsy.educacional.model.Turno;
import br.com.softsy.educacional.repository.CursoRepository;
import br.com.softsy.educacional.repository.CursoSerieRepository;


@Service
public class CursoSerieService {

    @Autowired
    private CursoRepository cursoRepository;
	
    @Autowired
    private CursoSerieRepository cursoSerieRepository;

    @Transactional(readOnly = true)
    public List<CursoSerieDTO> listarTudo() {
        List<CursoSerie> cursos = cursoSerieRepository.findAll();
        return cursos.stream()
                .map(CursoSerieDTO::new)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public CursoSerieDTO buscarPorId(Long id) {
        CursoSerie cursoSerie = cursoSerieRepository.getReferenceById(id);
        return new CursoSerieDTO(cursoSerie);
    }

    @Transactional
    public CursoSerieDTO salvar(CursoSerieDTO dto) {
    	
        CursoSerie cursoSerie = criarCursoSerieAPartirDTO(dto);
        cursoSerie = cursoSerieRepository.save(cursoSerie);
        return new CursoSerieDTO(cursoSerie);
    }

    @Transactional
    public CursoSerieDTO atualizar(CursoSerieDTO dto) {
        CursoSerie cursoSerie = cursoSerieRepository.findById(dto.getIdCursoSerie())
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado"));
        atualizarDados(cursoSerie, dto);
        return new CursoSerieDTO(cursoSerie);
    }


    private CursoSerie criarCursoSerieAPartirDTO(CursoSerieDTO dto) {
        CursoSerie cursoSerie = new CursoSerie();
        Curso curso = cursoRepository.findById(dto.getCursoId())
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado"));
        cursoSerie.setCurso(curso);
        cursoSerie.setSerie(dto.getSerie());
        cursoSerie.setDescricao(dto.getDescricao());
        cursoSerie.setDataCadastro(LocalDateTime.now());
        cursoSerie.setAtivo('S');
        return cursoSerie;
    }
   

	@Transactional
	public void ativaDesativa(char status, Long idCursoSerie) {
		CursoSerie cursoSerie = cursoSerieRepository.getReferenceById(idCursoSerie);
		cursoSerie.setAtivo(status);
	}
	
    
    private void atualizarDados(CursoSerie destino, CursoSerieDTO origem) {
        Curso curso = cursoRepository.findById(origem.getCursoId())
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado"));
        destino.setCurso(curso);
        destino.setSerie(origem.getSerie());
        destino.setDescricao(origem.getDescricao());
    }
}