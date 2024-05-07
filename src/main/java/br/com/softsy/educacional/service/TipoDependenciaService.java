package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.TipoDependenciaDTO;
import br.com.softsy.educacional.dto.ZoneamentoDTO;
import br.com.softsy.educacional.infra.exception.UniqueException;
import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.model.DependenciaAdministrativa;
import br.com.softsy.educacional.model.TipoDependencia;
import br.com.softsy.educacional.model.Zoneamento;
import br.com.softsy.educacional.repository.ContaRepository;
import br.com.softsy.educacional.repository.DependenciaAdministrativaRepository;
import br.com.softsy.educacional.repository.TipoDependenciaRepository;

@Service
public class TipoDependenciaService {
	
	@Autowired
	private TipoDependenciaRepository repository;
	
	@Autowired 
	private ContaRepository contaRepository;
	
	@Transactional(readOnly = true)
	public List<TipoDependenciaDTO> buscarPorIdConta(Long id) {
		List<TipoDependencia> tipoDependencia = repository.findByConta_IdConta(id)
				.orElseThrow(() -> new IllegalArgumentException("Erro ao buscar tipoDependencia por id de conta"));
		return tipoDependencia.stream()
				.map(TipoDependenciaDTO::new)
				.collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public TipoDependenciaDTO buscarPorId(Long id){
		return new TipoDependenciaDTO(repository.getReferenceById(id));
	}
	
	@Transactional
	public TipoDependenciaDTO salvar(TipoDependenciaDTO dto) {
		
		TipoDependencia tipoDependencia = criarTipoDependenciaAPartirDTO(dto);
		
		tipoDependencia = repository.save(tipoDependencia);
		return new TipoDependenciaDTO(tipoDependencia);
	}
	
	
	@Transactional
	public void ativaDesativa(char status, Long idTipoDependencia) {
		TipoDependencia destinacao = repository.getReferenceById(idTipoDependencia);
		destinacao.setAtivo(status);
	}
	
	private TipoDependencia criarTipoDependenciaAPartirDTO(TipoDependenciaDTO dto) {
		TipoDependencia tipoDependencia = new TipoDependencia();
		Conta conta = contaRepository.findById(dto.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
		BeanUtils.copyProperties(dto, tipoDependencia, "idTipoDependencia", "ativo", "dataCadastro");
		tipoDependencia.setConta(conta);
		tipoDependencia.setAtivo('S');
		tipoDependencia.setDataCadastro(LocalDateTime.now());
		return tipoDependencia;
	}
	
	@Transactional
	public TipoDependenciaDTO atualizar(TipoDependenciaDTO dto) {
		TipoDependencia tipoDependencia = repository.getReferenceById(dto.getIdTipoDependencia());
		atualizaDados(tipoDependencia, dto);
		return new TipoDependenciaDTO(tipoDependencia);
	}
	
	private void atualizaDados(TipoDependencia destino, TipoDependenciaDTO origem) {
		BeanUtils.copyProperties(origem, destino, "idTipoDependencia", "ativo", "dataCadastro");
		Conta conta = contaRepository.findById(origem.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
		destino.setConta(conta);
		
	}

}
