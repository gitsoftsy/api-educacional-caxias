package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.TipoTelefoneDTO;
import br.com.softsy.educacional.infra.exception.UniqueException;
import br.com.softsy.educacional.model.TipoTelefone;
import br.com.softsy.educacional.repository.TipoTelefoneRepository;

@Service
public class TipoTelefoneService {
	
	@Autowired
	private TipoTelefoneRepository repository;
	
	public List<TipoTelefone> listarTudo(){
		return repository.findAll();
	}
	
	@Transactional(readOnly = true)
	public TipoTelefoneDTO buscarPorId(Long id){
		return new TipoTelefoneDTO(repository.getReferenceById(id));
	}
	
	@Transactional
	public TipoTelefoneDTO salvar(TipoTelefoneDTO dto) {
		validarTipoTelefone(dto.getTipoTelefone());
		
		TipoTelefone tipoTelefone = criarTipoTelefoneAPartirDTO(dto);
		
		tipoTelefone = repository.save(tipoTelefone);
		return new TipoTelefoneDTO(tipoTelefone);
	}
	
	private void validarTipoTelefone(String tipoTelefone) {
		Optional<TipoTelefone> TipoTelefoneExistente = repository.findByTipoTelefone(tipoTelefone).stream().findFirst();
		if(TipoTelefoneExistente.isPresent()) {
			throw new UniqueException("Essa forma já existe.");
		}
	}
	
	private TipoTelefone criarTipoTelefoneAPartirDTO(TipoTelefoneDTO dto) {
		TipoTelefone tipoTelefone = new TipoTelefone();
		BeanUtils.copyProperties(dto, tipoTelefone, "idTipoTelefone",  "dataCadastro");
		tipoTelefone.setDataCadastro(LocalDateTime.now());
		return tipoTelefone;
	}
	
	@Transactional
	public TipoTelefoneDTO atualizar(TipoTelefoneDTO dto) {
		TipoTelefone tipoTelefone = repository.getReferenceById(dto.getIdTipoTelefone());
		atualizaDados(tipoTelefone, dto);
		return new TipoTelefoneDTO(tipoTelefone);
	}
	
	private void atualizaDados(TipoTelefone destino, TipoTelefoneDTO origem) {
		BeanUtils.copyProperties(origem, destino, "idTipoTelefone", "dataCadastro");
		
	}

}
