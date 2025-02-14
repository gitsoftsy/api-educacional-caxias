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

import br.com.softsy.educacional.dto.CadastroPagarmeRecebedorUtmDTO;
import br.com.softsy.educacional.dto.PagarmeRecebedorEscolaDTO;
import br.com.softsy.educacional.dto.PagarmeRecebedorUtmDTO;
import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.model.PagarmeRecebedorUtm;
import br.com.softsy.educacional.model.Utm;
import br.com.softsy.educacional.repository.ContaRepository;
import br.com.softsy.educacional.repository.PagarmeRecebedorUtmRepository;
import br.com.softsy.educacional.repository.UtmRepository;

@Service
public class PagarmeRecebedorUtmService {

	@Autowired
	private PagarmeRecebedorUtmRepository pagarmeRecebedorUtmRepository;

	@Autowired
	private ContaRepository contaRepository;

	@Autowired
	private UtmRepository utmRepository;
	
	@Autowired
	private EntityManager entityManager;

	@Transactional(readOnly = true)
	public List<PagarmeRecebedorUtmDTO> listarTudo() {
		List<PagarmeRecebedorUtm> recebedorUtm = pagarmeRecebedorUtmRepository.findAll();
		return recebedorUtm.stream().map(PagarmeRecebedorUtmDTO::new).collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public PagarmeRecebedorUtmDTO buscarPorId(Long idPagarmeRecebedorUtm) {
		return pagarmeRecebedorUtmRepository.findById(idPagarmeRecebedorUtm).map(PagarmeRecebedorUtmDTO::new)
				.orElseThrow(() -> new IllegalArgumentException("Recebedor Utm não encontrado"));
	}


	@Transactional
	public PagarmeRecebedorUtmDTO salvar(CadastroPagarmeRecebedorUtmDTO dto) {
		PagarmeRecebedorUtm recebedor = criarPagarmeRecebedorUtmAPartirDTO(dto);
		recebedor = pagarmeRecebedorUtmRepository.save(recebedor);
		return new PagarmeRecebedorUtmDTO(recebedor);
	}
	
	@Transactional
	public PagarmeRecebedorUtmDTO atualizar(CadastroPagarmeRecebedorUtmDTO dto) {
		PagarmeRecebedorUtm recebedor = pagarmeRecebedorUtmRepository.findById(dto.getIdPagarmeRecebedorUtm())
				.orElseThrow(() -> new IllegalArgumentException("Recebedor Utm não encontrado"));
		atualizaDados(recebedor, dto);
		return new PagarmeRecebedorUtmDTO(recebedor);
	}

	public PagarmeRecebedorUtm criarPagarmeRecebedorUtmAPartirDTO(CadastroPagarmeRecebedorUtmDTO dto) {
		if (dto.getTipoRepasse() == 'P' && dto.getValorRepasse().compareTo(new java.math.BigDecimal(100)) > 0) {
			throw new IllegalArgumentException("O valor de repasse não pode ser maior que 100 para o tipo P.");
		}

		PagarmeRecebedorUtm pagarmeRecebedorUtm = new PagarmeRecebedorUtm();
		Conta conta = contaRepository.findById(dto.getContaId())
				.orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
		Utm utm = utmRepository.findById(dto.getUtmId())
				.orElseThrow(() -> new IllegalArgumentException("Utm não encontrada"));

		pagarmeRecebedorUtm.setConta(conta);
		pagarmeRecebedorUtm.setUtm(utm);
		pagarmeRecebedorUtm.setDataCadastro(LocalDateTime.now());
		pagarmeRecebedorUtm.setAtivo(dto.getAtivo());
		pagarmeRecebedorUtm.setTipoRepasse(dto.getTipoRepasse());
		pagarmeRecebedorUtm.setValorRepasse(dto.getValorRepasse());
		pagarmeRecebedorUtm.setIdRecebedor(1L);
		return pagarmeRecebedorUtm;
	}

	private void atualizaDados(PagarmeRecebedorUtm destino, CadastroPagarmeRecebedorUtmDTO origem) {

		Conta conta = contaRepository.findById(origem.getContaId())
				.orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
		Utm utm = utmRepository.findById(origem.getUtmId())
				.orElseThrow(() -> new IllegalArgumentException("Utm não encontrada"));

		destino.setConta(conta);
		destino.setUtm(utm);
		destino.setDataCadastro(LocalDateTime.now());
		destino.setAtivo(origem.getAtivo());
		destino.setTipoRepasse(origem.getTipoRepasse());
		destino.setValorRepasse(origem.getValorRepasse());
	}

	@Transactional
	public void ativaDesativa(char status, Long idPagarmeRecebedorUtm) {
		PagarmeRecebedorUtm pagarmeRecebedorUtm = pagarmeRecebedorUtmRepository.findById(idPagarmeRecebedorUtm)
				.orElseThrow(() -> new IllegalArgumentException("RecebedorUtm não encontrado"));

		pagarmeRecebedorUtm.setAtivo(status);
	}
	
	
	public List<Map<String, Object>> listarParceiroPorUtm(Long idUtm) {
	    String sql = "CALL PROC_LISTAR_PARCEIRO_POR_UTM(:pIdUtm)";

	    Query query = entityManager.createNativeQuery(sql);
	    query.setParameter("pIdUtm", idUtm);

	    List<Object[]> resultList = query.getResultList();
	    List<Map<String, Object>> mappedResultList = new ArrayList<>();

	    for (Object[] result : resultList) {
	        Map<String, Object> resultMap = new HashMap<>();
	        resultMap.put("idParceiro", result[0]);
	        resultMap.put("nomeParceiro", result[1]);
	        resultMap.put("tipoRepasse", result[2]);  
	        resultMap.put("valorRepasse", result[3]); 
	        resultMap.put("ativo", result[4]);        

	        mappedResultList.add(resultMap);
	    }

	    return mappedResultList;
	}
	
	public List<Map<String, Object>> listarUtmPorRecebedor(Long idRecebedor) {
	    String sql = "CALL PROC_LISTAR_UTM_POR_PARCEIRO(:pIdRecebedor)";

	    Query query = entityManager.createNativeQuery(sql);
	    query.setParameter("pIdRecebedor", idRecebedor);

	    List<Object[]> resultList = query.getResultList();
	    List<Map<String, Object>> mappedResultList = new ArrayList<>();

	    for (Object[] result : resultList) {
	        Map<String, Object> resultMap = new HashMap<>();
	        resultMap.put("idUtm", result[0]);
	        resultMap.put("nomeUtm", result[1]);
	        resultMap.put("descrUtm", result[2]);
	        resultMap.put("tipoRepasse", result[3]);  
	        resultMap.put("valorRepasse", result[4]); 
	        resultMap.put("ativo", result[5]);        

	        mappedResultList.add(resultMap);
	    }

	    return mappedResultList;
	}

}
