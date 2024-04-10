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
import br.com.softsy.educacional.model.DependenciaAdministrativa;
import br.com.softsy.educacional.model.Zoneamento;
import br.com.softsy.educacional.repository.DependenciaAdministrativaRepository;
import br.com.softsy.educacional.repository.ZoneamentoRepository;

@Service
public class ZoneamentoService {
	
	@Autowired
	private ZoneamentoRepository repository;
	
	@Autowired 
	private DependenciaAdministrativaRepository dependenciaAdministrativaRepository;
	
	public List<Zoneamento> listarTudo() {
		return repository.findAll();
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
		DependenciaAdministrativa dependenciaAdm = dependenciaAdministrativaRepository.findById(dto.getDependenciaAdmId())
                .orElseThrow(() -> new IllegalArgumentException("Dependência administrativa não encontrada"));
		BeanUtils.copyProperties(dto, zoneamento, "idZoneamento", "ativo", "dataCadastro");
		zoneamento.setDependenciaAdm(dependenciaAdm);
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
		DependenciaAdministrativa dependenciaAdm = dependenciaAdministrativaRepository.findById(origem.getDependenciaAdmId())
                .orElseThrow(() -> new IllegalArgumentException("Dependência administrativa não encontrada"));
		destino.setDependenciaAdm(dependenciaAdm);
	}
}
