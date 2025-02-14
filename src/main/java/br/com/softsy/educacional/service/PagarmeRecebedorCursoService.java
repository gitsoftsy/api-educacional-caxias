package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CadastroPagarmeRecebedorCursoDTO;
import br.com.softsy.educacional.dto.PagarmeRecebedorCursoDTO;
import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.model.Curso;
import br.com.softsy.educacional.model.PagarmeRecebedorCurso;
import br.com.softsy.educacional.repository.ContaRepository;
import br.com.softsy.educacional.repository.CursoRepository;
import br.com.softsy.educacional.repository.PagarmeRecebedorCursoRepository;

@Service
public class PagarmeRecebedorCursoService {
	
	@Autowired
	private PagarmeRecebedorCursoRepository pagarmeRecebedorCursoRepository;

	@Autowired
	private ContaRepository contaRepository;

	@Autowired
	private CursoRepository cursoRepository;
	
	@Autowired
	private EntityManager entityManager;
	
	@Transactional(readOnly = true)
	public List<PagarmeRecebedorCursoDTO> listarTudo() {
		List<PagarmeRecebedorCurso> recebedorUtm = pagarmeRecebedorCursoRepository.findAll();
		return recebedorUtm.stream().map(PagarmeRecebedorCursoDTO::new).collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public PagarmeRecebedorCursoDTO buscarPorId(Long idPagarmeRecebedorCurso) {
		return pagarmeRecebedorCursoRepository.findById(idPagarmeRecebedorCurso).map(PagarmeRecebedorCursoDTO::new)
				.orElseThrow(() -> new IllegalArgumentException("Recebedor Utm não encontrado"));
	}
	
	@Transactional
	public PagarmeRecebedorCursoDTO salvar(CadastroPagarmeRecebedorCursoDTO dto) {
		PagarmeRecebedorCurso recebedor = criarPagarmeRecebedorCursoAPartirDTO(dto);
		recebedor = pagarmeRecebedorCursoRepository.save(recebedor);
		return new PagarmeRecebedorCursoDTO(recebedor);
	}
	
	@Transactional
	public PagarmeRecebedorCursoDTO atualizar(CadastroPagarmeRecebedorCursoDTO dto) {
		PagarmeRecebedorCurso recebedor = pagarmeRecebedorCursoRepository.findById(dto.getIdPagarmeRecebedorCurso())
				.orElseThrow(() -> new IllegalArgumentException("Recebedor Utm não encontrado"));
		atualizaDados(recebedor, dto);
		return new PagarmeRecebedorCursoDTO(recebedor);
	}

	public PagarmeRecebedorCurso criarPagarmeRecebedorCursoAPartirDTO(CadastroPagarmeRecebedorCursoDTO dto) {
		if (dto.getTipoRepasse() == 'P' && dto.getValorRepasse().compareTo(new java.math.BigDecimal(100)) > 0) {
			throw new IllegalArgumentException("O valor de repasse não pode ser maior que 100 para o tipo P.");
		}

		PagarmeRecebedorCurso pagarmeRecebedorCurso = new PagarmeRecebedorCurso();
		Conta conta = contaRepository.findById(dto.getContaId())
				.orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
		Curso curso = cursoRepository.findById(dto.getCursoId())
				.orElseThrow(() -> new IllegalArgumentException("Utm não encontrada"));

		pagarmeRecebedorCurso.setConta(conta);
		pagarmeRecebedorCurso.setCurso(curso);
		pagarmeRecebedorCurso.setDataCadastro(LocalDateTime.now());
		pagarmeRecebedorCurso.setAtivo(dto.getAtivo());
		pagarmeRecebedorCurso.setTipoRepasse(dto.getTipoRepasse());
		pagarmeRecebedorCurso.setValorRepasse(dto.getValorRepasse());
		pagarmeRecebedorCurso.setIdRecebedor(1L);
		return pagarmeRecebedorCurso;
	}
	
	private void atualizaDados(PagarmeRecebedorCurso destino, CadastroPagarmeRecebedorCursoDTO origem) {

		Conta conta = contaRepository.findById(origem.getContaId())
				.orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
		Curso curso = cursoRepository.findById(origem.getCursoId())
				.orElseThrow(() -> new IllegalArgumentException("Utm não encontrada"));

		destino.setConta(conta);
		destino.setCurso(curso);
		destino.setDataCadastro(LocalDateTime.now());
		destino.setAtivo(origem.getAtivo());
		destino.setTipoRepasse(origem.getTipoRepasse());
		destino.setValorRepasse(origem.getValorRepasse());
	}
	
	@Transactional
	public void ativaDesativa(char status, Long idPagarmeRecebedorCurso) {
		PagarmeRecebedorCurso pagarmeRecebedorCurso = pagarmeRecebedorCursoRepository.findById(idPagarmeRecebedorCurso)
				.orElseThrow(() -> new IllegalArgumentException("RecebedorUtm não encontrado"));

		pagarmeRecebedorCurso.setAtivo(status);
	}
	
	public List<Map<String, Object>> listarParceiroPorCurso(Long idCurso) {
	    String sql = "CALL PROC_LISTAR_PARCEIRO_POR_CURSO(:pIdCurso)";

	    Query query = entityManager.createNativeQuery(sql);
	    query.setParameter("pIdCurso", idCurso);

	    List<Object[]> resultList = query.getResultList();
	    List<Map<String, Object>> mappedResultList = new ArrayList<>();

	    for (Object[] result : resultList) {
	        Map<String, Object> resultMap = new HashMap<>();
	        resultMap.put("idRecebedor", result[0]);
	        resultMap.put("nomeRecebedor", result[1]);
	        resultMap.put("tipoPessoa", result[2]);
	        resultMap.put("tipoRepasse", result[3]);  
	        resultMap.put("valorRepasse", result[4]); 
	        resultMap.put("ativo", result[5]);        

	        mappedResultList.add(resultMap);
	    }

	    return mappedResultList;
	}
	
	public List<Map<String, Object>> listarCursoPorParceiro(Long idRecebedor) {
	    String sql = "CALL PROC_LISTAR_CURSO_POR_RECEBEDOR(:pIdRecebedor)";

	    Query query = entityManager.createNativeQuery(sql);
	    query.setParameter("pIdRecebedor", idRecebedor);

	    List<Object[]> resultList = query.getResultList();
	    List<Map<String, Object>> mappedResultList = new ArrayList<>();

	    for (Object[] result : resultList) {
	        Map<String, Object> resultMap = new HashMap<>();
	        resultMap.put("idCurso", result[0]);
	        resultMap.put("CodRecebedor", result[1]);
	        resultMap.put("nomeCurso", result[2]);
	        resultMap.put("tipoRepasse", result[3]);  
	        resultMap.put("valorRepasse", result[4]); 
	        resultMap.put("ativo", result[5]);        

	        mappedResultList.add(resultMap);
	    }

	    return mappedResultList;
	}

}
