package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.NivelEscolaridadeDTO;
import br.com.softsy.educacional.infra.exception.UniqueException;
import br.com.softsy.educacional.model.DependenciaAdministrativa;
import br.com.softsy.educacional.model.NivelEscolaridade;
import br.com.softsy.educacional.repository.DependenciaAdministrativaRepository;
import br.com.softsy.educacional.repository.NivelEscolaridadeRepository;

@Service
public class NivelEscolaridadeService {

	@Autowired
	private NivelEscolaridadeRepository repository;
	
	@Autowired 
	private DependenciaAdministrativaRepository dependenciaAdministrativaRepository;

	public List<NivelEscolaridade> listarTudo() {
		return repository.findAll();
	}

	@Transactional(readOnly = true)
	public NivelEscolaridadeDTO buscarPorId(Long id) {
		return new NivelEscolaridadeDTO(repository.getReferenceById(id));
	}

	@Transactional
	public NivelEscolaridadeDTO salvar(NivelEscolaridadeDTO dto) {

		NivelEscolaridade nivelEscolaridade = criarNivelEscolaridadeAPartirDTO(dto);

		nivelEscolaridade = repository.save(nivelEscolaridade);
		return new NivelEscolaridadeDTO(nivelEscolaridade);
	}

	private NivelEscolaridade criarNivelEscolaridadeAPartirDTO(NivelEscolaridadeDTO dto) {
		NivelEscolaridade nivelEscolaridade = new NivelEscolaridade();
		DependenciaAdministrativa dependenciaAdm = dependenciaAdministrativaRepository.findById(dto.getDependenciaAdmId())
                .orElseThrow(() -> new IllegalArgumentException("Dependência administrativa não encontrada"));
		BeanUtils.copyProperties(dto, nivelEscolaridade, "idNivelEscolaridade", "ativo", "dataCadastro");
		nivelEscolaridade.setDependenciaAdm(dependenciaAdm);
		nivelEscolaridade.setAtivo('S');
		nivelEscolaridade.setDataCadastro(LocalDateTime.now());
		return nivelEscolaridade;
	}

	@Transactional
	public NivelEscolaridadeDTO atualizar(NivelEscolaridadeDTO dto) {
		NivelEscolaridade nivelEscolaridade = repository.getReferenceById(dto.getIdNivelEscolaridade());
		atualizaDados(nivelEscolaridade, dto);
		return new NivelEscolaridadeDTO(nivelEscolaridade);
	}

	@Transactional
	public void ativaDesativa(char status, Long IdNivelEscolaridade) {
		NivelEscolaridade nivelEscolaridade = repository.getReferenceById(IdNivelEscolaridade);
		nivelEscolaridade.setAtivo(status);
	}


	private void atualizaDados(NivelEscolaridade destino, NivelEscolaridadeDTO origem) {
		BeanUtils.copyProperties(origem, destino, "IdNivelEscolaridade", "ativo", "dataCadastro");
		DependenciaAdministrativa dependenciaAdm = dependenciaAdministrativaRepository.findById(origem.getDependenciaAdmId())
                .orElseThrow(() -> new IllegalArgumentException("Dependência administrativa não encontrada"));
		destino.setDependenciaAdm(dependenciaAdm);

	}

}
