package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CadastroProvaDTO;
import br.com.softsy.educacional.dto.ProvaDTO;
import br.com.softsy.educacional.model.Prova;
import br.com.softsy.educacional.model.Turma;
import br.com.softsy.educacional.repository.ProvaRepository;
import br.com.softsy.educacional.repository.TurmaRepository;

@Service
public class ProvaService {

	@Autowired
	private ProvaRepository repository;
	
	@Autowired
    private TurmaRepository turmaRepository;
	
    @Autowired
    private EntityManager entityManager;
	
	
	@Transactional(readOnly = true)
	 public List<ProvaDTO> listarTudo() {
	    List<Prova> prova = repository.findAll();
	     return prova.stream()
	     .map(ProvaDTO::new)
	     .collect(Collectors.toList());
	    }
	
	@Transactional(readOnly = true)
    public ProvaDTO buscarPorId(Long id) {
        return new ProvaDTO(repository.getReferenceById(id));
    }
	
	@Transactional(readOnly = true)
    public List<ProvaDTO> buscarPorIdTurma(Long idTurma) {
        List<Prova> prova = repository.findByTurma_IdTurma(idTurma)
                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar prova por ID do turma"));
        return prova.stream()
                .map(ProvaDTO::new)
                .collect(Collectors.toList());
    }
		
	@Transactional
	public ProvaDTO salvar(CadastroProvaDTO dto) {
	    Prova prova = criarProvaAPartirDTO(dto);
	    prova = repository.save(prova);
	    return new ProvaDTO(prova);
	}

	private Prova criarProvaAPartirDTO(CadastroProvaDTO dto) {
	    Prova prova = new Prova();

	    Turma turma = turmaRepository.findById(dto.getTurmaId())  
	            .orElseThrow(() -> new IllegalArgumentException("Turma não encontrada"));

	    BeanUtils.copyProperties(dto, prova, "idProva", "dataCadastro");

	    prova.setTurma(turma); 
	    prova.setDataCadastro(LocalDateTime.now()); 

	    return prova;
	}
	
	@Transactional(readOnly = true)
    public List<Map<String, Object>> listarProvas(
            Long idEscola, Integer ano, Long idPeriodoLetivo,
            Long idTurno, Long idTurma, Long idDisciplina) {

        StringBuilder sql = new StringBuilder();
        sql.append("CALL PROC_LISTAR_PROVAS(:pIdEscola, :pAno, :pIdPeriodoLetivo, :pIdTurno, :pIdTurma, :pIdDisciplina)");

        Query query = entityManager.createNativeQuery(sql.toString());

        query.setParameter("pIdEscola", idEscola);
        query.setParameter("pAno", ano);
        query.setParameter("pIdPeriodoLetivo", idPeriodoLetivo);
        query.setParameter("pIdTurno", idTurno);
        query.setParameter("pIdTurma", idTurma);
        query.setParameter("pIdDisciplina", idDisciplina);


        List<Object[]> resultList = query.getResultList();
        List<Map<String, Object>> mappedResultList = new ArrayList<>();

        for (Object[] result : resultList) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("idProva", result[0]);
            resultMap.put("idTurma", result[1]);	
            resultMap.put("nomeAbreviado", result[2]);
            resultMap.put("descricao", result[3]);
            resultMap.put("dtDivulgacao", result[4]);
            resultMap.put("dtAgendaProva", result[5]);
            resultMap.put("ativo", result[6]);
            resultMap.put("tipoConceito", result[7]);
            resultMap.put("conceitoMax", result[8]);
            resultMap.put("dtLimiteRevisao", result[9]);
            resultMap.put("ehSimulado", result[10]);
            resultMap.put("formula", result[11]);
            resultMap.put("ordem", result[12]);
            mappedResultList.add(resultMap);
        }

        return mappedResultList;
    }

	    @Transactional
	    public ProvaDTO atualizar(CadastroProvaDTO dto) {
	        Prova prova = repository.findById(dto.getIdProva())
	                .orElseThrow(() -> new IllegalArgumentException("Prova não encontrada"));

	        atualizaDados(prova, dto);

	        prova = repository.save(prova);

	        return new ProvaDTO(prova);
	    }

	    private void atualizaDados(Prova destino, CadastroProvaDTO origem) {
	        Turma turma = turmaRepository.findById(origem.getTurmaId())
	                .orElseThrow(() -> new IllegalArgumentException("Turma não encontrada"));

	        BeanUtils.copyProperties(origem, destino, "idProva", "dataCadastro");

	        destino.setTurma(turma);
	    }


	    @Transactional
	    public void ativaDesativa(Character status, Long idProva) {
	        Prova prova = repository.findById(idProva)
	                .orElseThrow(() -> new IllegalArgumentException("Prova não encontrada"));

	        prova.setAtivo(status);
	    }
	
}
