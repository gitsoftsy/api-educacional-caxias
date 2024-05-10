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
import br.com.softsy.educacional.model.TipoAtoRegulatorio;
import br.com.softsy.educacional.repository.TipoAtoRegulatorioRepository;

@Service
public class TipoAtoRegulatorioService {
	
	@Autowired
	private TipoAtoRegulatorioRepository repository;

	
	@Transactional(readOnly = true)
	public List<TipoAtoRegulatorio> listarTudo() {
		return repository.findAll();
	}
	
	@Transactional(readOnly = true)
	public TipoAtoRegulatorioDTO buscarPorId(Long id){
		return new TipoAtoRegulatorioDTO(repository.getReferenceById(id));
	}
	
	@Transactional
	public TipoAtoRegulatorioDTO salvar(TipoAtoRegulatorioDTO dto) {
		validarTipoAtoRegulatorio(dto.getTipoAtoRegulatorio());
		
		TipoAtoRegulatorio tipoAto = criarTipoAtoAPartirDTO(dto);
		
		tipoAto = repository.save(tipoAto);
		return new TipoAtoRegulatorioDTO(tipoAto);
	}
	
	private TipoAtoRegulatorio criarTipoAtoAPartirDTO(TipoAtoRegulatorioDTO dto) {
		TipoAtoRegulatorio tipoAto = new TipoAtoRegulatorio();
		BeanUtils.copyProperties(dto, tipoAto, "idTipoAtoRegulatorio", "ativo", "dataCadastro");
		tipoAto.setAtivo('S');
		tipoAto.setDataCadastro(LocalDateTime.now());
		return tipoAto;
	}
	
	@Transactional
	private void validarTipoAtoRegulatorio(String tipoAtoRegulatorio) {
		Optional<TipoAtoRegulatorio> atoExistente = repository.findByTipoAtoRegulatorio(tipoAtoRegulatorio).stream().findFirst();
		if(atoExistente.isPresent()) {
			throw new UniqueException("Esse tipo de ato regulatório já existe.");
		}
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
		
	}

}
