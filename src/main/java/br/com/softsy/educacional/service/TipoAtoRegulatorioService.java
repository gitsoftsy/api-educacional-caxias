package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.TipoAtoRegulatorioDTO;
import br.com.softsy.educacional.infra.exception.UniqueException;
import br.com.softsy.educacional.model.DependenciaAdministrativa;
import br.com.softsy.educacional.model.TipoAtoRegulatorio;
import br.com.softsy.educacional.repository.DependenciaAdministrativaRepository;
import br.com.softsy.educacional.repository.TipoAtoRegulatorioRepository;

@Service
public class TipoAtoRegulatorioService {
	
	@Autowired
	private TipoAtoRegulatorioRepository repository;
	
	@Autowired 
	private DependenciaAdministrativaRepository dependenciaAdministrativaRepository;
	
	public List<TipoAtoRegulatorio> listarTudo(){
		return repository.findAll();
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
		DependenciaAdministrativa dependenciaAdm = dependenciaAdministrativaRepository.findById(dto.getDependenciaAdmId())
                .orElseThrow(() -> new IllegalArgumentException("Dependência administrativa não encontrada"));
		BeanUtils.copyProperties(dto, tipoAto, "idTipoAtoRegulatorio", "ativo", "dataCadastro");
		tipoAto.setDependenciaAdm(dependenciaAdm);
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
		DependenciaAdministrativa dependenciaAdm = dependenciaAdministrativaRepository.findById(origem.getDependenciaAdmId())
                .orElseThrow(() -> new IllegalArgumentException("Dependência administrativa não encontrada"));
		destino.setDependenciaAdm(dependenciaAdm);
		
	}

}
