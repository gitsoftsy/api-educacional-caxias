package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.TipoDependenciaDTO;
import br.com.softsy.educacional.infra.exception.UniqueException;
import br.com.softsy.educacional.model.TipoDependencia;
import br.com.softsy.educacional.repository.TipoDependenciaRepository;

@Service
public class TipoDependenciaService {
	
	@Autowired
	private TipoDependenciaRepository repository;
	
	public List<TipoDependencia> listarTudo(){
		return repository.findAll();
	}
	
	@Transactional(readOnly = true)
	public TipoDependenciaDTO buscarPorId(Long id){
		return new TipoDependenciaDTO(repository.getReferenceById(id));
	}
	
	@Transactional
	public TipoDependenciaDTO salvar(TipoDependenciaDTO dto) {
		validarTipoDependencia(dto.getTipoDependencia());
		
		TipoDependencia tipoDependencia = criarTipoDependenciaAPartirDTO(dto);
		
		tipoDependencia = repository.save(tipoDependencia);
		return new TipoDependenciaDTO(tipoDependencia);
	}
	
	private void validarTipoDependencia(String tipoDependencia) {
		Optional<TipoDependencia> TipoDependenciaExistente = repository.findByTipoDependencia(tipoDependencia).stream().findFirst();
		if(TipoDependenciaExistente.isPresent()) {
			throw new UniqueException("Essa forma já existe.");
		}
	}
	
	private TipoDependencia criarTipoDependenciaAPartirDTO(TipoDependenciaDTO dto) {
		TipoDependencia tipoDependencia = new TipoDependencia();
		BeanUtils.copyProperties(dto, tipoDependencia, "idTipoDependencia", "tipoDependencia", "ativo", "dataCadastro");
		tipoDependencia.setAtivo('S');
		tipoDependencia.setDataCadastro(LocalDateTime.now());
		return tipoDependencia;
	}
	
	@Transactional
	public TipoDependenciaDTO atualizar(TipoDependenciaDTO dto) {
		TipoDependencia tipoDependencia = repository.getReferenceById(dto.getIdTipoDependencia());
		atualizaDados(tipoDependencia, dto);
		return new TipoDependenciaDTO(tipoDependencia);
	}
	
	private void atualizaDados(TipoDependencia destino, TipoDependenciaDTO origem) {
		BeanUtils.copyProperties(origem, destino, "idTipoDependencia", "tipoDependencia", "ativo", "dataCadastro");
		
	}

}
