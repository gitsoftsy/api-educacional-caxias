package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.TipoAtoRegulatorioDTO;
import br.com.softsy.educacional.dto.ZoneamentoDTO;
import br.com.softsy.educacional.infra.exception.UniqueException;
import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.model.DependenciaAdministrativa;
import br.com.softsy.educacional.model.TipoAtoRegulatorio;
import br.com.softsy.educacional.model.Zoneamento;
import br.com.softsy.educacional.repository.ContaRepository;
import br.com.softsy.educacional.repository.DependenciaAdministrativaRepository;
import br.com.softsy.educacional.repository.TipoAtoRegulatorioRepository;

@Service
public class TipoAtoRegulatorioService {
	
	@Autowired
	private TipoAtoRegulatorioRepository repository;
	
	@Autowired 
	private ContaRepository contaRepository;
	
	@Transactional(readOnly = true)
	public List<TipoAtoRegulatorioDTO> buscarPorIdConta(Long id) {
		List<TipoAtoRegulatorio> tipoAtoRegulatorio = repository.findByConta_IdConta(id)
				.orElseThrow(() -> new IllegalArgumentException("Erro ao buscar tipoAtoRegulatorio por id de conta"));
		return tipoAtoRegulatorio.stream()
				.map(TipoAtoRegulatorioDTO::new)
				.collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public TipoAtoRegulatorioDTO buscarPorId(Long id){
		return new TipoAtoRegulatorioDTO(repository.getReferenceById(id));
	}
	
	@Transactional
	public TipoAtoRegulatorioDTO salvar(TipoAtoRegulatorioDTO dto) {
		
		TipoAtoRegulatorio tipoAto = criarTipoAtoAPartirDTO(dto);
		
		tipoAto = repository.save(tipoAto);
		return new TipoAtoRegulatorioDTO(tipoAto);
	}
	
	private TipoAtoRegulatorio criarTipoAtoAPartirDTO(TipoAtoRegulatorioDTO dto) {
		TipoAtoRegulatorio tipoAto = new TipoAtoRegulatorio();
		Conta conta = contaRepository.findById(dto.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
		BeanUtils.copyProperties(dto, tipoAto, "idTipoAtoRegulatorio", "ativo", "dataCadastro");
		tipoAto.setConta(conta);
		tipoAto.setAtivo('S');
		tipoAto.setDataCadastro(LocalDateTime.now());
		return tipoAto;
	}
	
	@Transactional
	public TipoAtoRegulatorioDTO atualizar(TipoAtoRegulatorioDTO dto) {
		TipoAtoRegulatorio tipoAto = repository.getReferenceById(dto.getIdTipoAtoRegulatorio());
		atualizaDados(tipoAto, dto);
		return new TipoAtoRegulatorioDTO(tipoAto);
	}
	
	@Transactional
	public void ativaDesativa(char status, Long idTipoAtoRegulatorio) {
		TipoAtoRegulatorio tipoAto = repository.getReferenceById(idTipoAtoRegulatorio);
		tipoAto.setAtivo(status);
	}
	
	private void atualizaDados(TipoAtoRegulatorio destino, TipoAtoRegulatorioDTO origem) {
		BeanUtils.copyProperties(origem, destino, "idTipoAtoRegulatorio", "ativo", "dataCadastro");
		Conta conta = contaRepository.findById(origem.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
		destino.setConta(conta);
		
	}

}
