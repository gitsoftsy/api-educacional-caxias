package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CadastroOfertaConcursoDTO;
import br.com.softsy.educacional.dto.OfertaConcursoDTO;
import br.com.softsy.educacional.model.Concurso;
import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.model.Curriculo;
import br.com.softsy.educacional.model.Curso;
import br.com.softsy.educacional.model.Escola;
import br.com.softsy.educacional.model.OfertaConcurso;
import br.com.softsy.educacional.model.Serie;
import br.com.softsy.educacional.model.Turno;
import br.com.softsy.educacional.repository.ConcursoRepository;
import br.com.softsy.educacional.repository.ContaRepository;
import br.com.softsy.educacional.repository.CurriculoRepository;
import br.com.softsy.educacional.repository.CursoRepository;
import br.com.softsy.educacional.repository.EscolaRepository;
import br.com.softsy.educacional.repository.OfertaConcursoRepository;
import br.com.softsy.educacional.repository.SerieRepository;
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
    private ContaRepository contaRepository;


    @Autowired
    private EscolaRepository escolaRepository;

    @Autowired
    private TurnoRepository turnoRepository;
    
    @Autowired
    private SerieRepository serieRepository;
    
    @Autowired
    private CurriculoRepository curriculoRepository;
    
    @Autowired
    private EntityManager entityManager;

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
    
    public Map<String, String> verificaOfertaETurmaAtiva(Long idEscola, Long idConta) {
        StringBuilder sql = new StringBuilder();
        sql.append("CALL PROC_PODE_DESATIVAR_ESCOLA(:pIdEscola, :pIdConta)");

        Query query = entityManager.createNativeQuery(sql.toString());

        query.setParameter("pIdEscola", idEscola);
        query.setParameter("pIdConta", idConta);

        Object[] result = (Object[]) query.getSingleResult();

        String podeDesativar = (String) result[0];
        String ofertaAtiva = (String) result[1];
        String turmaAtiva = (String) result[2];

        Map<String, String> resultado = new HashMap<>();
        resultado.put("podeDesativar", podeDesativar);
        resultado.put("ofertaAtiva", ofertaAtiva);
        resultado.put("turmaAtiva", turmaAtiva);

        return resultado;
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
        Serie serie = serieRepository.findById(dto.getSerieId())
                .orElseThrow(() -> new IllegalArgumentException("Serie não encontrada"));
        
		Curriculo curriculo = null;
        if (dto.getCurriculoId() != null) {
        	curriculo = curriculoRepository.findById(dto.getCurriculoId())
                    .orElseThrow(() -> new IllegalArgumentException("Currículo não encontradi"));
        }

        oferta.setConcurso(concurso);
        oferta.setCurso(curso);
        oferta.setEscola(escola);
        oferta.setTurno(turno);
        oferta.setSerie(serie);
        oferta.setCurriculo(curriculo);
        oferta.setDescricaoOferta(dto.getDescricaoOferta());
        oferta.setVagas(dto.getVagas());
        oferta.setMinVagasAbertTurma(dto.getMinVagasAbertTurma());
        oferta.setDataCadastro(LocalDateTime.now());
        oferta.setAtivo('S');
        return oferta;
    }
    
    public List<Map<String, Object>> listaOfertaCursoUsuario(Long idUsuario) {
        StringBuilder sql = new StringBuilder();
        sql.append("CALL PROC_LISTA_OFERTA_CURSO_USUARIO(:pIdUsuario)");

        Query query = entityManager.createNativeQuery(sql.toString());

        query.setParameter("pIdUsuario", idUsuario);

        List<Object[]> resultList = query.getResultList();
        List<Map<String, Object>> mappedResultList = new ArrayList<>();

        for (Object[] result : resultList) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("idOfertaConcurso", result[0]);
            resultMap.put("concurso", result[1]);
            resultMap.put("idConcurso", result[2]);
            resultMap.put("nomeCurso", result[3]);
            resultMap.put("codigoCurso", result[4]);
            resultMap.put("nomeEscola", result[5]);
            resultMap.put("turno", result[6]);
            resultMap.put("idSerie", result[7]);
            resultMap.put("descricaoOferta", result[8]);
            resultMap.put("vagas", result[9]);
            resultMap.put("minVagasAbertTurma", result[10]);
            resultMap.put("ativo", result[11]);
            resultMap.put("idEscola", result[12]);
            resultMap.put("idTurno", result[13]);
            resultMap.put("idCurso", result[14]);
            resultMap.put("idCurriculo", result[15]);
            resultMap.put("curriculo", result[16]);
            resultMap.put("descricaoCurriculo", result[17]);
            mappedResultList.add(resultMap);
        }

        return mappedResultList;
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
        Serie serie = serieRepository.findById(origem.getSerieId())
                .orElseThrow(() -> new IllegalArgumentException("Serie não encontrada"));
        
		Curriculo curriculo = null;
        if (origem.getCurriculoId() != null) {
        	curriculo = curriculoRepository.findById(origem.getCurriculoId())
                    .orElseThrow(() -> new IllegalArgumentException("Currículo não encontrado"));
        }

        destino.setConcurso(concurso);
        destino.setCurso(curso);
        destino.setEscola(escola);
        destino.setTurno(turno);
        destino.setSerie(serie);
        destino.setCurriculo(curriculo);
        destino.setDescricaoOferta(origem.getDescricaoOferta());
        destino.setVagas(origem.getVagas());
        destino.setMinVagasAbertTurma(origem.getMinVagasAbertTurma());
    }
}	