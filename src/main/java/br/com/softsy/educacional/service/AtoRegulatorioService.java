package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.AtoRegulatorioDTO;
import br.com.softsy.educacional.model.AtoRegulatorio;
import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.repository.AtoRegulatorioRepository;
import br.com.softsy.educacional.repository.ContaRepository;

@Service
public class AtoRegulatorioService {
	
	@Autowired AtoRegulatorioRepository repository;
	
	@Autowired 
	private ContaRepository contaRepository;
	
	@Transactional(readOnly = true)
	public List<AtoRegulatorioDTO> buscarPorIdConta(Long id) {
		List<AtoRegulatorio> atoRegulatorio = repository.findByConta_IdConta(id)
				.orElseThrow(() -> new IllegalArgumentException("Erro ao buscar atoRegulatorio por id de conta"));
		return atoRegulatorio.stream()
				.map(AtoRegulatorioDTO::new)
				.collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public AtoRegulatorioDTO buscarPorId(Long id){
		return new AtoRegulatorioDTO(repository.getReferenceById(id));
	}
	
	@Transactional
	public AtoRegulatorioDTO salvar(AtoRegulatorioDTO dto) {
		
		AtoRegulatorio atoRegulatorio = criarAtoAPartirDTO(dto);
		
		atoRegulatorio = repository.save(atoRegulatorio);
		return new AtoRegulatorioDTO(atoRegulatorio);
	}
	
	private AtoRegulatorio criarAtoAPartirDTO(AtoRegulatorioDTO dto) {
		AtoRegulatorio atoRegulatorio = new AtoRegulatorio();
		Conta conta = contaRepository.findById(dto.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
		BeanUtils.copyProperties(dto, atoRegulatorio, "idAtoRegulatorio", "ativo", "dataCadastro");
		atoRegulatorio.setConta(conta);
		atoRegulatorio.setAtivo('S');
		atoRegulatorio.setDataCadastro(LocalDateTime.now());
		return atoRegulatorio;
	}
	
	@Transactional
	public AtoRegulatorioDTO atualizar(AtoRegulatorioDTO dto) {
		AtoRegulatorio atoRegulatorio = repository.getReferenceById(dto.getIdAtoRegulatorio());
		atualizaDados(atoRegulatorio, dto);
		return new AtoRegulatorioDTO(atoRegulatorio);
	}
	
	@Transactional
	public void ativaDesativa(char status, Long idAtoRegulatorio) {
		AtoRegulatorio atoRegulatorio = repository.getReferenceById(idAtoRegulatorio);
		atoRegulatorio.setAtivo(status);
	}
	
	
	private void atualizaDados(AtoRegulatorio destino, AtoRegulatorioDTO origem) {
		Conta conta = contaRepository.findById(origem.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
		BeanUtils.copyProperties(origem, destino, "idAtoRegulatorio", "ativo", "dataCadastro");
		destino.setConta(conta);
		
	}

}
