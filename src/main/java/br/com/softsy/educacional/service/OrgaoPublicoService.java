package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.OrgaoPublicoDTO;
import br.com.softsy.educacional.infra.exception.UniqueException;
import br.com.softsy.educacional.model.OrgaoPublico;
import br.com.softsy.educacional.repository.OrgaoPublicoRepository;

@Service
public class OrgaoPublicoService {
	
	@Autowired
	private OrgaoPublicoRepository repository;
	
	public List<OrgaoPublico> listarTudo() {
		return repository.findAll();
	}
	
	@Transactional(readOnly = true)
	public OrgaoPublicoDTO buscarPorId(Long id) {
		return new OrgaoPublicoDTO(repository.getReferenceById(id));
	}
	
	@Transactional
	public OrgaoPublicoDTO salvar(OrgaoPublicoDTO dto) {
		validarSigla(dto.getSigla());
		validarOrgaoPublico(dto.getOrgaoPublico());
		
		OrgaoPublico orgaoPublico = criarOrgaoPublicoAPartirDTO(dto);
		
		orgaoPublico = repository.save(orgaoPublico);
		return new OrgaoPublicoDTO(orgaoPublico);
	}
	
	private void validarSigla(String sigla) {
		Optional<OrgaoPublico> orgaoPublicoExistente = repository.findBySigla(sigla).stream().findFirst();
		if (orgaoPublicoExistente.isPresent()) {
			throw new UniqueException("Essa sigla já está em uso.");
		}
	}
	
	private void validarOrgaoPublico(String orgaoPublico) {
		Optional<OrgaoPublico> orgaoPublicoExistente = repository.findByOrgaoPublico(orgaoPublico).stream().findFirst();
		if (orgaoPublicoExistente.isPresent()) {
			throw new UniqueException("Esse órgão público já existe.");
		}
	}
	
	private OrgaoPublico criarOrgaoPublicoAPartirDTO(OrgaoPublicoDTO dto) {
		OrgaoPublico orgaoPublico = new OrgaoPublico();
		BeanUtils.copyProperties(dto, orgaoPublico, "idOrgaoPublico", "dataCadastro", "ativo");
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
	}
}