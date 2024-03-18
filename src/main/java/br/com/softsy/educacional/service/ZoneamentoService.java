package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.ZoneamentoDTO;
import br.com.softsy.educacional.infra.exception.UniqueException;
import br.com.softsy.educacional.model.Zoneamento;
import br.com.softsy.educacional.repository.ZoneamentoRepository;

@Service
public class ZoneamentoService {
	
	@Autowired
	private ZoneamentoRepository repository;
	
	public List<Zoneamento> listarTudo() {
		return repository.findAll();
	}
	
	@Transactional(readOnly = true)
	public ZoneamentoDTO buscarPorId(Long id) {
		return new ZoneamentoDTO(repository.getReferenceById(id));
	}
	
	@Transactional
	public ZoneamentoDTO salvar(ZoneamentoDTO dto) {
		validarZoneamento(dto.getZoneamento());
		
		Zoneamento zoneamento = criarZoneamentoAPartirDTO(dto);
		
		zoneamento = repository.save(zoneamento);
		return new ZoneamentoDTO(zoneamento);
	}
	
	private void validarZoneamento(String zoneamento) {
		Optional<Zoneamento> zoneamentoExistente = repository.findByZoneamento(zoneamento).stream().findFirst();
		if (zoneamentoExistente.isPresent()) {
			throw new UniqueException("Esse zoneamento já existe.");
		}
	}
	
	private Zoneamento criarZoneamentoAPartirDTO(ZoneamentoDTO dto) {
		Zoneamento zoneamento = new Zoneamento();
		BeanUtils.copyProperties(dto, zoneamento, "idZoneamento", "ativo", "dataCadastro");
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
	}
}
