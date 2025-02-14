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

import br.com.softsy.educacional.dto.CadastroPagarmeRecebedorEscolaDTO;
import br.com.softsy.educacional.dto.EscolaDTO;
import br.com.softsy.educacional.dto.PagarmeRecebedorEscolaDTO;
import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.model.Escola;
import br.com.softsy.educacional.model.PagarmeRecebedorEscola;
import br.com.softsy.educacional.repository.ContaRepository;
import br.com.softsy.educacional.repository.EscolaRepository;
import br.com.softsy.educacional.repository.PagarmeRecebedorEscolaRepository;

@Service
public class PagarmeRecebedorEscolaService {

	@Autowired
	private PagarmeRecebedorEscolaRepository pagarmeRecebedorEscolaRepository;

	@Autowired
	private ContaRepository contaRepository;

	@Autowired
	private EscolaRepository escolaRepository;
	
	@Autowired
	private EntityManager entityManager;

	@Transactional(readOnly = true)
	public List<PagarmeRecebedorEscolaDTO> listarTudo() {
		return pagarmeRecebedorEscolaRepository.findAll().stream().map(PagarmeRecebedorEscolaDTO::new)
				.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public PagarmeRecebedorEscolaDTO buscarPorId(Long idPagarmeRecebedorEscola) {
		return pagarmeRecebedorEscolaRepository.findById(idPagarmeRecebedorEscola).map(PagarmeRecebedorEscolaDTO::new)
				.orElseThrow(() -> new IllegalArgumentException("Recebedor não encontrado"));
	}

	@Transactional
	public PagarmeRecebedorEscolaDTO salvar(CadastroPagarmeRecebedorEscolaDTO dto) {
		PagarmeRecebedorEscola recebedor = criarPagarmeRecebedorEscolaAPartirDTO(dto);
		recebedor = pagarmeRecebedorEscolaRepository.save(recebedor);
		return new PagarmeRecebedorEscolaDTO(recebedor);
	}


	@Transactional
	public PagarmeRecebedorEscolaDTO atualizar(CadastroPagarmeRecebedorEscolaDTO dto) {
		PagarmeRecebedorEscola recebedor = pagarmeRecebedorEscolaRepository.findById(dto.getIdPagarmeRecebedorEscola())
				.orElseThrow(() -> new IllegalArgumentException("Recebedor não encontrado"));
		atualizaDados(recebedor, dto);
		return new PagarmeRecebedorEscolaDTO(recebedor);
	}

	@Transactional
	public void ativaDesativa(char status, Long idPagarmeRecebedorEscola) {
		PagarmeRecebedorEscola recebedor = pagarmeRecebedorEscolaRepository.findById(idPagarmeRecebedorEscola)
				.orElseThrow(() -> new IllegalArgumentException("Recebedor não encontrado"));
		recebedor.setAtivo(status);
	}


	@Transactional(readOnly = true)
	public List<PagarmeRecebedorEscolaDTO> listarPorIdEscola(Long escolaId) {
		List<PagarmeRecebedorEscolaDTO> recebedores = pagarmeRecebedorEscolaRepository.findByEscola_IdEscola(escolaId)
				.stream().map(PagarmeRecebedorEscolaDTO::new).collect(Collectors.toList());

		if (recebedores.isEmpty()) {
			throw new IllegalArgumentException("Nenhum recebedor encontrado para a escola com ID " + escolaId);
		}

		return recebedores;
	}

	private PagarmeRecebedorEscola criarPagarmeRecebedorEscolaAPartirDTO(CadastroPagarmeRecebedorEscolaDTO dto) {
		if (dto.getTipoRepasse() == 'P' && dto.getValorRepasse().compareTo(new java.math.BigDecimal(100)) > 0) {
			throw new IllegalArgumentException("O valor de repasse não pode ser maior que 100 para o tipo P.");
		}

		Conta conta = contaRepository.findById(dto.getContaId())
				.orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
		Escola escola = escolaRepository.findById(dto.getEscolaId())
				.orElseThrow(() -> new IllegalArgumentException("Escola não encontrada"));

		PagarmeRecebedorEscola recebedor = new PagarmeRecebedorEscola();
		recebedor.setConta(conta);
		recebedor.setEscola(escola);
		recebedor.setDataCadastro(LocalDateTime.now());
		recebedor.setAtivo(dto.getAtivo());
		recebedor.setTipoRepasse(dto.getTipoRepasse());
		recebedor.setValorRepasse(dto.getValorRepasse());
		recebedor.setIdRecebedor(1L);

		return recebedor;
	}

	private void atualizaDados(PagarmeRecebedorEscola destino, CadastroPagarmeRecebedorEscolaDTO origem) {
		Conta conta = contaRepository.findById(origem.getContaId())
				.orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
		Escola escola = escolaRepository.findById(origem.getEscolaId())
				.orElseThrow(() -> new IllegalArgumentException("Escola não encontrada"));

		destino.setConta(conta);
		destino.setEscola(escola);
		destino.setAtivo(origem.getAtivo());
		destino.setTipoRepasse(origem.getTipoRepasse());
		destino.setValorRepasse(origem.getValorRepasse());
	}
	
	
	public List<Map<String, Object>> listarParceiroPorEscola(Long idEscola) {
	    String sql = "CALL PROC_LISTAR_PARCEIRO_POR_ESCOLA(:pIdEscola)";

	    Query query = entityManager.createNativeQuery(sql);
	    query.setParameter("pIdEscola", idEscola);

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
	
	public List<Map<String, Object>> listarEscolaPorRecebedor(Long idRecebedor) {
	    String sql = "CALL PROC_LISTAR_ESCOLA_POR_RECEBEDOR(:pIdRecebedor)";

	    Query query = entityManager.createNativeQuery(sql);
	    query.setParameter("pIdRecebedor", idRecebedor);

	    List<Object[]> resultList = query.getResultList();
	    List<Map<String, Object>> mappedResultList = new ArrayList<>();

	    for (Object[] result : resultList) {
	        Map<String, Object> resultMap = new HashMap<>();
	        resultMap.put("idEscola", result[0]);
	        resultMap.put("nomeEscola", result[1]);
	        resultMap.put("CodigoEscola", result[2]);
	        resultMap.put("tipoRepasse", result[3]);  
	        resultMap.put("valorRepasse", result[4]); 
	        resultMap.put("ativo", result[5]);        

	        mappedResultList.add(resultMap);
	    }

	    return mappedResultList;
	}


}
