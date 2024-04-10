package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.TratamentoLixoDTO;
import br.com.softsy.educacional.infra.exception.UniqueException;
import br.com.softsy.educacional.model.DependenciaAdministrativa;
import br.com.softsy.educacional.model.TratamentoLixo;
import br.com.softsy.educacional.repository.DependenciaAdministrativaRepository;
import br.com.softsy.educacional.repository.TratamentoLixoRepository;

@Service
public class TratamentoLixoService {
	
	@Autowired
	private TratamentoLixoRepository repository;
	@Autowired 
	private DependenciaAdministrativaRepository dependenciaAdministrativaRepository;
	
	public List<TratamentoLixo> listarTudo(){
		return repository.findAll();
	}
	
	@Transactional(readOnly = true)
	public TratamentoLixoDTO buscarPorId(Long id){
		return new TratamentoLixoDTO(repository.getReferenceById(id));
	}
	
	@Transactional
	public TratamentoLixoDTO salvar(TratamentoLixoDTO dto) {

		TratamentoLixo tratamentoLixo = criarTratamentoLixoAPartirDTO(dto);
		
		tratamentoLixo = repository.save(tratamentoLixo);
		return new TratamentoLixoDTO(tratamentoLixo);
	}

	
	private TratamentoLixo criarTratamentoLixoAPartirDTO(TratamentoLixoDTO dto) {
		TratamentoLixo tratamentoLixo = new TratamentoLixo();
		DependenciaAdministrativa dependenciaAdm = dependenciaAdministrativaRepository.findById(dto.getDependenciaAdmId())
                .orElseThrow(() -> new IllegalArgumentException("Dependência administrativa não encontrada"));
		BeanUtils.copyProperties(dto, tratamentoLixo, "idTratamentoLixo", "ativo", "dataCadastro");
		tratamentoLixo.setDependenciaAdm(dependenciaAdm);
		tratamentoLixo.setDataCadastro(LocalDateTime.now());
		tratamentoLixo.setAtivo('S');
		return tratamentoLixo;
	}
	
	@Transactional
	public void ativaDesativa(char status, Long idTratamentoLixo) {
		TratamentoLixo destinacao = repository.getReferenceById(idTratamentoLixo);
		destinacao.setAtivo(status);
	}
	
	@Transactional
	public TratamentoLixoDTO atualizar(TratamentoLixoDTO dto) {
		TratamentoLixo tratamentoLixo = repository.getReferenceById(dto.getIdTratamentoLixo());
		atualizaDados(tratamentoLixo, dto);
		return new TratamentoLixoDTO(tratamentoLixo);
	}
	
	private void atualizaDados(TratamentoLixo destino, TratamentoLixoDTO origem) {
		BeanUtils.copyProperties(origem, destino, "idTratamentoLixo", "ativo", "dataCadastro");
		DependenciaAdministrativa dependenciaAdm = dependenciaAdministrativaRepository.findById(origem.getDependenciaAdmId())
                .orElseThrow(() -> new IllegalArgumentException("Dependência administrativa não encontrada"));
		destino.setDependenciaAdm(dependenciaAdm);
		
	}

}
