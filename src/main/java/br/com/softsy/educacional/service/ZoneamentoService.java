package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.EscolaDestinacaoLixoDTO;
import br.com.softsy.educacional.dto.ZoneamentoDTO;
import br.com.softsy.educacional.infra.exception.UniqueException;
import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.model.DependenciaAdministrativa;
import br.com.softsy.educacional.model.EscolaDestinacaoLixo;
import br.com.softsy.educacional.model.Zoneamento;
import br.com.softsy.educacional.repository.ContaRepository;
import br.com.softsy.educacional.repository.DependenciaAdministrativaRepository;
import br.com.softsy.educacional.repository.ZoneamentoRepository;

@Service
public class ZoneamentoService {
	
	@Autowired
	private ZoneamentoRepository repository;
	
	@Autowired 
	private ContaRepository contaRepository;
	
	@Transactional(readOnly = true)
	public List<ZoneamentoDTO> buscarPorIdConta(Long id) {
		List<Zoneamento> zoenamento = repository.findByConta_IdConta(id)
				.orElseThrow(() -> new IllegalArgumentException("Erro ao buscar zoneamento por id de conta"));
		return zoenamento.stream()
				.map(ZoneamentoDTO::new)
				.collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public ZoneamentoDTO buscarPorId(Long id) {
		return new ZoneamentoDTO(repository.getReferenceById(id));
	}
	
	@Transactional
	public ZoneamentoDTO salvar(ZoneamentoDTO dto) {
		
		Zoneamento zoneamento = criarZoneamentoAPartirDTO(dto);
		
		zoneamento = repository.save(zoneamento);
		return new ZoneamentoDTO(zoneamento);
	}

	
	private Zoneamento criarZoneamentoAPartirDTO(ZoneamentoDTO dto) {
		Zoneamento zoneamento = new Zoneamento();
		Conta conta = contaRepository.findById(dto.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
		BeanUtils.copyProperties(dto, zoneamento, "idZoneamento", "ativo", "dataCadastro");
		zoneamento.setConta(conta);
		zoneamento.setDataCadastro(LocalDateTime.now());
		zoneamento.setAtivo('S');
		return zoneamento;
	}
	
	@Transactional
	public void ativaDesativa(char status, Long idZoneamento) {
		Zoneamento zoneamento = repository.getReferenceById(idZoneamento);
		zoneamento.setAtivo(status);
	}
	
	@Transactional
	public ZoneamentoDTO atualizar(ZoneamentoDTO dto) {
		Zoneamento zoneamento = repository.getReferenceById(dto.getIdZoneamento());
		atualizaDados(zoneamento, dto);
		return new ZoneamentoDTO(zoneamento);
	}
	
	private void atualizaDados(Zoneamento destino, ZoneamentoDTO origem) {
		BeanUtils.copyProperties(origem, destino, "idZoneamento", "ativo", "dataCadastro");
		Conta conta = contaRepository.findById(origem.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));;
		destino.setConta(conta);
	}
}
