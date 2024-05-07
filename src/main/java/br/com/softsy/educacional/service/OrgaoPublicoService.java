package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.OrgaoPublicoDTO;
import br.com.softsy.educacional.dto.ZoneamentoDTO;
import br.com.softsy.educacional.infra.exception.UniqueException;
import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.model.DependenciaAdministrativa;
import br.com.softsy.educacional.model.OrgaoPublico;
import br.com.softsy.educacional.model.Zoneamento;
import br.com.softsy.educacional.repository.ContaRepository;
import br.com.softsy.educacional.repository.DependenciaAdministrativaRepository;
import br.com.softsy.educacional.repository.OrgaoPublicoRepository;

@Service
public class OrgaoPublicoService {
	
	@Autowired
	private OrgaoPublicoRepository repository;
	
	@Autowired 
	private ContaRepository contaRepository;
	
	@Transactional(readOnly = true)
	public List<OrgaoPublicoDTO> buscarPorIdConta(Long id) {
		List<OrgaoPublico> orgaoPublio = repository.findByConta_IdConta(id)
				.orElseThrow(() -> new IllegalArgumentException("Erro ao buscar orgaoPublico por id de conta"));
		return orgaoPublio.stream()
				.map(OrgaoPublicoDTO::new)
				.collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public OrgaoPublicoDTO buscarPorId(Long id) {
		return new OrgaoPublicoDTO(repository.getReferenceById(id));
	}
	
	@Transactional
	public OrgaoPublicoDTO salvar(OrgaoPublicoDTO dto) {
		
		OrgaoPublico orgaoPublico = criarOrgaoPublicoAPartirDTO(dto);
		
		orgaoPublico = repository.save(orgaoPublico);
		return new OrgaoPublicoDTO(orgaoPublico);
	}
	
	
	private OrgaoPublico criarOrgaoPublicoAPartirDTO(OrgaoPublicoDTO dto) {
		OrgaoPublico orgaoPublico = new OrgaoPublico();
		Conta conta = contaRepository.findById(dto.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
		BeanUtils.copyProperties(dto, orgaoPublico, "idOrgaoPublico", "dataCadastro", "ativo");
		orgaoPublico.setConta(conta);
		orgaoPublico.setDataCadastro(LocalDateTime.now());
		orgaoPublico.setAtivo('S');
		return orgaoPublico;
	}
	
	@Transactional
	public void ativarDesativar(char status, Long idOrgaoPublico) {
		OrgaoPublico orgaoPublico = repository.getReferenceById(idOrgaoPublico);
		orgaoPublico.setAtivo(status);
	}
	
	@Transactional
	public OrgaoPublicoDTO atualizar(OrgaoPublicoDTO dto) {
		OrgaoPublico orgaoPublico = repository.getReferenceById(dto.getIdOrgaoPublico());
		atualizarDados(orgaoPublico, dto);
		return new OrgaoPublicoDTO(orgaoPublico);
	}
	
	private void atualizarDados(OrgaoPublico destino, OrgaoPublicoDTO origem) {
		BeanUtils.copyProperties(origem, destino, "idOrgaoPublico", "dataCadastro", "ativo");
		Conta conta = contaRepository.findById(origem.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
		destino.setConta(conta);
	}
}