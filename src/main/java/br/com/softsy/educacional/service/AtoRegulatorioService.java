package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.AtoRegulatorioDTO;
import br.com.softsy.educacional.infra.exception.UniqueException;
import br.com.softsy.educacional.model.AtoRegulatorio;
import br.com.softsy.educacional.repository.AtoRegulatorioRepository;

@Service
public class AtoRegulatorioService {
	
	@Autowired AtoRegulatorioRepository repository;
	
	public List<AtoRegulatorio> listarTudo(){
		return repository.findAll();
	}
	
	@Transactional(readOnly = true)
	public AtoRegulatorioDTO buscarPorId(Long id){
		return new AtoRegulatorioDTO(repository.getReferenceById(id));
	}
	
	@Transactional
	public AtoRegulatorioDTO salvar(AtoRegulatorioDTO dto) {
		validarAtoRegulatorio(dto.getAtoRegulatorio());
		
		AtoRegulatorio atoRegulatorio = criarAtoAPartirDTO(dto);
		
		atoRegulatorio = repository.save(atoRegulatorio);
		return new AtoRegulatorioDTO(atoRegulatorio);
	}
	
	private AtoRegulatorio criarAtoAPartirDTO(AtoRegulatorioDTO dto) {
		AtoRegulatorio atoRegulatorio = new AtoRegulatorio();
		BeanUtils.copyProperties(dto, atoRegulatorio, "idAtoRegulatorio", "ativo", "dataCadastro");
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
	
	private void validarAtoRegulatorio(String atoRegulatorio) {
		Optional<AtoRegulatorio> atoExistente = repository.findByAtoRegulatorio(atoRegulatorio).stream().findFirst();
		if(atoExistente.isPresent()) {
			throw new UniqueException("Essa destinação já existe.");
		}
	}
	
	private void atualizaDados(AtoRegulatorio destino, AtoRegulatorioDTO origem) {
		BeanUtils.copyProperties(origem, destino, "idAtoRegulatorio", "ativo", "dataCadastro");
		
	}

}
