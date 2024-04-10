package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CategoriaEscolaPrivadaDTO;
import br.com.softsy.educacional.infra.exception.UniqueException;
import br.com.softsy.educacional.model.CategoriaEscolaPrivada;
import br.com.softsy.educacional.model.DependenciaAdministrativa;
import br.com.softsy.educacional.repository.CategoriaEscolaPrivadaRepository;
import br.com.softsy.educacional.repository.DependenciaAdministrativaRepository;

@Service
public class CategoriaEscolaPrivadaService {
	
	@Autowired
	private CategoriaEscolaPrivadaRepository repository;
	
	@Autowired 
	private DependenciaAdministrativaRepository dependenciaAdministrativaRepository;
	
	public List<CategoriaEscolaPrivada> listarTudo() {
		return repository.findAll();
	}
	
	@Transactional(readOnly = true)
	public CategoriaEscolaPrivadaDTO buscarPorId(Long id) {
		return new CategoriaEscolaPrivadaDTO(repository.getReferenceById(id));
	}
	
	@Transactional
	public CategoriaEscolaPrivadaDTO salvar(CategoriaEscolaPrivadaDTO dto) {
		
		CategoriaEscolaPrivada categoria = criarCategoriaAPartirDTO(dto);
		
		categoria = repository.save(categoria);
		return new CategoriaEscolaPrivadaDTO(categoria);
	}
	
	
	private CategoriaEscolaPrivada criarCategoriaAPartirDTO(CategoriaEscolaPrivadaDTO dto) {
		CategoriaEscolaPrivada categoria = new CategoriaEscolaPrivada();
		DependenciaAdministrativa dependenciaAdm = dependenciaAdministrativaRepository.findById(dto.getDependenciaAdmId())
                .orElseThrow(() -> new IllegalArgumentException("Dependência administrativa não encontrada"));
		BeanUtils.copyProperties(dto, categoria, "idCategoriaEscolaPrivada", "ativo", "dataCadastro");
		categoria.setDependenciaAdm(dependenciaAdm);
		categoria.setDataCadastro(LocalDateTime.now());
		categoria.setAtivo('S');
		return categoria;
	}
	
	@Transactional
	public void ativaDesativa(char status, Long idCategoriaEscolaPrivada) {
		CategoriaEscolaPrivada categoria = repository.getReferenceById(idCategoriaEscolaPrivada);
		categoria.setAtivo(status);
	}
	
	@Transactional
	public CategoriaEscolaPrivadaDTO atualizar(CategoriaEscolaPrivadaDTO dto) {
		CategoriaEscolaPrivada categoria = repository.getReferenceById(dto.getIdCategoriaEscolaPrivada());
		atualizaDados(categoria, dto);
		return new CategoriaEscolaPrivadaDTO(categoria);
	}
	
	private void atualizaDados(CategoriaEscolaPrivada destino, CategoriaEscolaPrivadaDTO origem) {
		BeanUtils.copyProperties(origem, destino, "idCategoriaEscolaPrivada", "ativo", "dataCadastro");
		DependenciaAdministrativa dependenciaAdm = dependenciaAdministrativaRepository.findById(origem.getDependenciaAdmId())
                .orElseThrow(() -> new IllegalArgumentException("Dependência administrativa não encontrada"));
		destino.setDependenciaAdm(dependenciaAdm);
	}
}