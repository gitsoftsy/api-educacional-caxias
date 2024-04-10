package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.TipoEnsinoMedioDTO;
import br.com.softsy.educacional.infra.exception.UniqueException;
import br.com.softsy.educacional.model.DependenciaAdministrativa;
import br.com.softsy.educacional.model.TipoEnsinoMedio;
import br.com.softsy.educacional.repository.DependenciaAdministrativaRepository;
import br.com.softsy.educacional.repository.TipoEnsinoMedioRepository;

@Service
public class TipoEnsinoMedioService {

	@Autowired
	private TipoEnsinoMedioRepository repository;
	
	@Autowired 
	private DependenciaAdministrativaRepository dependenciaAdministrativaRepository;

	public List<TipoEnsinoMedio> listarTudo() {
		return repository.findAll();
	}

	@Transactional(readOnly = true)
	public TipoEnsinoMedioDTO buscarPorId(Long id) {
		return new TipoEnsinoMedioDTO(repository.getReferenceById(id));
	}

	@Transactional
	public TipoEnsinoMedioDTO salvar(TipoEnsinoMedioDTO dto) {

		TipoEnsinoMedio tipoEnsinoMedio = criarTipoEnsinoMedioAPartirDTO(dto);

		tipoEnsinoMedio = repository.save(tipoEnsinoMedio);
		return new TipoEnsinoMedioDTO(tipoEnsinoMedio);
	}

	private TipoEnsinoMedio criarTipoEnsinoMedioAPartirDTO(TipoEnsinoMedioDTO dto) {
		TipoEnsinoMedio tipoEnsinoMedio = new TipoEnsinoMedio();
		DependenciaAdministrativa dependenciaAdm = dependenciaAdministrativaRepository.findById(dto.getDependenciaAdmId())
                .orElseThrow(() -> new IllegalArgumentException("Dependência administrativa não encontrada"));
		BeanUtils.copyProperties(dto, tipoEnsinoMedio, "idDestinacaoLixo", "ativo", "dataCadastro");
		tipoEnsinoMedio.setDependenciaAdm(dependenciaAdm);
		tipoEnsinoMedio.setAtivo('S');
		tipoEnsinoMedio.setDataCadastro(LocalDateTime.now());
		return tipoEnsinoMedio;
	}

	@Transactional
	public TipoEnsinoMedioDTO atualizar(TipoEnsinoMedioDTO dto) {
		TipoEnsinoMedio tipoEnsinoMedio = repository.getReferenceById(dto.getIdTipoEnsinoMedio());
		atualizaDados(tipoEnsinoMedio, dto);
		return new TipoEnsinoMedioDTO(tipoEnsinoMedio);
	}

	private void atualizaDados(TipoEnsinoMedio destino, TipoEnsinoMedioDTO origem) {
		DependenciaAdministrativa dependenciaAdm = dependenciaAdministrativaRepository.findById(origem.getDependenciaAdmId())
                .orElseThrow(() -> new IllegalArgumentException("Dependência administrativa não encontrada"));
		BeanUtils.copyProperties(origem, destino, "idTipoEnsinoMedio", "ativo", "dataCadastro");
		destino.setDependenciaAdm(dependenciaAdm);

	}

	@Transactional
	public void ativaDesativa(char status, Long idTipoEnsinoMedio) {
		TipoEnsinoMedio tipoEnsinoMedio = repository.getReferenceById(idTipoEnsinoMedio);
		tipoEnsinoMedio.setAtivo(status);
	}

}
