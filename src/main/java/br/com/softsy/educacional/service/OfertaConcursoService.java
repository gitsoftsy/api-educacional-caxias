package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CadastroOfertaConcursoDTO;
import br.com.softsy.educacional.dto.OfertaConcursoDTO;
import br.com.softsy.educacional.model.Concurso;
import br.com.softsy.educacional.model.Curso;
import br.com.softsy.educacional.model.Escola;
import br.com.softsy.educacional.model.OfertaConcurso;
import br.com.softsy.educacional.model.Turno;
import br.com.softsy.educacional.repository.ConcursoRepository;
import br.com.softsy.educacional.repository.CursoRepository;
import br.com.softsy.educacional.repository.EscolaRepository;
import br.com.softsy.educacional.repository.OfertaConcursoRepository;
import br.com.softsy.educacional.repository.TurnoRepository;

@Service
public class OfertaConcursoService {

    @Autowired
    private OfertaConcursoRepository ofertaConcursoRepository;

    @Autowired
    private ConcursoRepository concursoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private EscolaRepository escolaRepository;

    @Autowired
    private TurnoRepository turnoRepository;

    @Transactional(readOnly = true)
    public List<CadastroOfertaConcursoDTO> listarTudo() {
        List<OfertaConcurso> ofertas = ofertaConcursoRepository.findAll();
        return ofertas.stream()
                .map(CadastroOfertaConcursoDTO::new)
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public List<OfertaConcursoDTO> buscarPorIdConcurso(Long idConcurso) {
        List<OfertaConcurso> curso = ofertaConcursoRepository.findByConcurso_idConcurso(idConcurso)
                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar oferta de concurso por ID da conta"));
        return curso.stream()
                .map(OfertaConcursoDTO::new)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public CadastroOfertaConcursoDTO buscarPorId(Long id) {
        OfertaConcurso oferta = ofertaConcursoRepository.getReferenceById(id);
        return new CadastroOfertaConcursoDTO(oferta);
    }

    @Transactional
    public CadastroOfertaConcursoDTO salvar(CadastroOfertaConcursoDTO dto) {
        OfertaConcurso oferta = criarOfertaAPartirDTO(dto);
        oferta = ofertaConcursoRepository.save(oferta);
        return new CadastroOfertaConcursoDTO(oferta);
    }

    @Transactional
    public CadastroOfertaConcursoDTO atualizar(CadastroOfertaConcursoDTO dto) {
        OfertaConcurso oferta = ofertaConcursoRepository.findById(dto.getIdOfertaConcurso())
                .orElseThrow(() -> new IllegalArgumentException("Oferta de concurso não encontrada"));
        atualizarDados(oferta, dto);
        return new CadastroOfertaConcursoDTO(oferta);
    }

    @Transactional
    public void ativaDesativa(char status, Long idOfertaConcurso) {
        OfertaConcurso oferta = ofertaConcursoRepository.getReferenceById(idOfertaConcurso);
        oferta.setAtivo(status);
    }
    

    private OfertaConcurso criarOfertaAPartirDTO(CadastroOfertaConcursoDTO dto) {
        OfertaConcurso oferta = new OfertaConcurso();
        Concurso concurso = concursoRepository.findById(dto.getConcursoId())
                .orElseThrow(() -> new IllegalArgumentException("Concurso não encontrado"));
        Curso curso = cursoRepository.findById(dto.getCursoId())
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado"));
        Escola escola = escolaRepository.findById(dto.getEscolaId())
                .orElseThrow(() -> new IllegalArgumentException("Escola não encontrada"));
        Turno turno = turnoRepository.findById(dto.getTurnoId())
                .orElseThrow(() -> new IllegalArgumentException("Turno não encontrado"));

        oferta.setConcurso(concurso);
        oferta.setCurso(curso);
        oferta.setEscola(escola);
        oferta.setTurno(turno);
        oferta.setSerie(dto.getSerie());
        oferta.setDescricaoOferta(dto.getDescricaoOferta());
        oferta.setVagas(dto.getVagas());
        oferta.setMinVagasAbertTurma(dto.getMinVagasAbertTurma());
        oferta.setDataCadastro(LocalDateTime.now());
        oferta.setAtivo('S');
        return oferta;
    }

    private void atualizarDados(OfertaConcurso destino, CadastroOfertaConcursoDTO origem) {
        Concurso concurso = concursoRepository.findById(origem.getConcursoId())
                .orElseThrow(() -> new IllegalArgumentException("Concurso não encontrado"));
        Curso curso = cursoRepository.findById(origem.getCursoId())
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado"));
        Escola escola = escolaRepository.findById(origem.getEscolaId())
                .orElseThrow(() -> new IllegalArgumentException("Escola não encontrada"));
        Turno turno = turnoRepository.findById(origem.getTurnoId())
                .orElseThrow(() -> new IllegalArgumentException("Turno não encontrado"));

        destino.setConcurso(concurso);
        destino.setCurso(curso);
        destino.setEscola(escola);
        destino.setTurno(turno);
        destino.setSerie(origem.getSerie());
        destino.setDescricaoOferta(origem.getDescricaoOferta());
        destino.setVagas(origem.getVagas());
        destino.setMinVagasAbertTurma(origem.getMinVagasAbertTurma());
    }
}	