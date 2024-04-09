package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.DestinacaoLixoDTO;
import br.com.softsy.educacional.dto.LocalizacaoDTO;
import br.com.softsy.educacional.infra.exception.UniqueException;
import br.com.softsy.educacional.model.DependenciaAdministrativa;
import br.com.softsy.educacional.model.DestinacaoLixo;
import br.com.softsy.educacional.model.Localizacao;
import br.com.softsy.educacional.repository.DependenciaAdministrativaRepository;
import br.com.softsy.educacional.repository.LocalizacaoRepository;

@Service
public class LocalizacaoService {

	@Autowired
	private LocalizacaoRepository repository;
	
	@Autowired 
	private DependenciaAdministrativaRepository dependenciaAdministrativaRepository;
	
	public List<Localizacao> listarTudo(){
		return repository.findAll();
	}
	
	@Transactional(readOnly = true)
	public LocalizacaoDTO buscarPorId(Long id){
		return new LocalizacaoDTO(repository.getReferenceById(id));
	}
	
	@Transactional
	public LocalizacaoDTO salvar(LocalizacaoDTO dto) {
		
		Localizacao localizacao = criarLocalizacaoAPartirDTO(dto);
		
		localizacao = repository.save(localizacao);
		return new LocalizacaoDTO(localizacao);
	}
	
	private Localizacao criarLocalizacaoAPartirDTO(LocalizacaoDTO dto) {
		Localizacao localizacao = new Localizacao();
		DependenciaAdministrativa dependenciaAdm = dependenciaAdministrativaRepository.findById(dto.getDependenciaAdmId())
                .orElseThrow(() -> new IllegalArgumentException("Dependência administrativa não encontrada"));
		BeanUtils.copyProperties(dto, localizacao, "idLocalizacao", "ativo", "dataCadastro");
		localizacao.setDependenciaAdm(dependenciaAdm);
		localizacao.setAtivo('S');
		localizacao.setDataCadastro(LocalDateTime.now());
		return localizacao;
	}
	
	@Transactional
	public LocalizacaoDTO atualizar(LocalizacaoDTO dto) {
		Localizacao localizacao = repository.getReferenceById(dto.getIdLocalizacao());
		atualizaDados(localizacao, dto);
		return new LocalizacaoDTO(localizacao);
	}
	
	@Transactional
	public void ativaDesativa(char status, Long idLocalizacao) {
		Localizacao localizacao = repository.getReferenceById(idLocalizacao);
		localizacao.setAtivo(status);
	}
	
	
	private void atualizaDados(Localizacao destino, LocalizacaoDTO origem) {
		BeanUtils.copyProperties(origem, destino, "idLocalizacao", "ativo", "dataCadastro");
		DependenciaAdministrativa dependenciaAdm = dependenciaAdministrativaRepository.findById(origem.getDependenciaAdmId())
                .orElseThrow(() -> new IllegalArgumentException("Dependência administrativa não encontrada"));
		destino.setDependenciaAdm(dependenciaAdm);
	}
}
