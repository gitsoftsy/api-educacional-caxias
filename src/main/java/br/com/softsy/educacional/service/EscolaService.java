package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CadastroEscolaDTO;
import br.com.softsy.educacional.dto.EscolaDTO;
import br.com.softsy.educacional.model.DependenciaAdministrativa;
import br.com.softsy.educacional.model.Escola;
import br.com.softsy.educacional.model.FormaOcupacaoPredio;
import br.com.softsy.educacional.model.Localizacao;
import br.com.softsy.educacional.model.SituacaoFuncionamento;
import br.com.softsy.educacional.repository.DependenciaAdministrativaRepository;
import br.com.softsy.educacional.repository.EscolaRepository;
import br.com.softsy.educacional.repository.FormaOcupacaoPredioRepository;
import br.com.softsy.educacional.repository.LocalizacaoRepository;
import br.com.softsy.educacional.repository.SituacaoFuncionamentoRepository;
@Service
public class EscolaService {
	
	@Autowired 
	private EscolaRepository repository;

	@Autowired 
	private LocalizacaoRepository localizacaoRepository;
	
	@Autowired 
	private DependenciaAdministrativaRepository dependenciaRepository;
	
	@Autowired 
	private SituacaoFuncionamentoRepository situacaoRepository;
	
	@Autowired 
	private FormaOcupacaoPredioRepository formaRepository;
	
	
	public List<EscolaDTO> listarTudo(){
		List<Escola> escolas = repository.findAll();
		
		return escolas.stream().map(EscolaDTO::new).collect(Collectors.toList());
	}

	
	@Transactional(readOnly = true)
	public EscolaDTO buscarPorId(Long id) {
		return new EscolaDTO(repository.getReferenceById(id));
	}
	
	
	@Transactional(readOnly = true)
	public List<EscolaDTO> listarAtivos(){
		return repository.findEscolaByAtivo('S').stream().map(EscolaDTO::new).collect(Collectors.toList());
	}
	
	@Transactional
	public CadastroEscolaDTO salvar(CadastroEscolaDTO dto) {
		
		Escola escola = criarEscolaAPartirDTO(dto);
		
		escola = repository.save(escola);
		return new CadastroEscolaDTO(escola);
	}
	
	@Transactional
	public EscolaDTO atualizar(CadastroEscolaDTO dto) {
		Escola escola = repository.getReferenceById(dto.getIdEscola());
		atualizaDados(escola, dto);
		return new EscolaDTO(escola);
	}
	
	@Transactional
	public void ativaDesativa(char status, Long id) {
		Escola escola = repository.getReferenceById(id);
		escola.setAtivo(status);
	}
	
	private Escola criarEscolaAPartirDTO(CadastroEscolaDTO dto) {
		Escola escola = new Escola();
		Localizacao localizacao = localizacaoRepository.findById(dto.getLocalizacaoId())
				.orElseThrow(() -> new IllegalArgumentException("Localizacao não encontrada"));
		DependenciaAdministrativa dependenciaAdm = dependenciaRepository.findById(dto.getDependenciaAdmId())
				.orElseThrow(() -> new IllegalArgumentException("Dependencia não encontrada"));
		SituacaoFuncionamento situacaoFuncionamento = situacaoRepository.findById(dto.getSituacaoFuncionamentoId())
				.orElseThrow(() -> new IllegalArgumentException("Situação não encontrada"));
		FormaOcupacaoPredio formaOcupacao = formaRepository.findById(dto.getFormaOcupacaoPredioId())
				.orElseThrow(() -> new IllegalAccessError("FormaOcupação não encontrada"));
		BeanUtils.copyProperties(dto, escola, "ativo", "dataCadastro", "idEscola", "localizacaoId", "dependenciaAdmId", "situacaoFuncionamentoId", "formaOcupacaoPredioId");
		escola.setLocalizacao(localizacao);
		escola.setDependenciaAdm(dependenciaAdm);
		escola.setSituacaoFuncionamento(situacaoFuncionamento);
		escola.setFormaOcupacaoPredio(formaOcupacao);
		escola.setAtivo('S');
		escola.setDataCadastro(LocalDateTime.now());
		return escola;
		
	}
	
	private void atualizaDados(Escola destino, CadastroEscolaDTO origem) {
		BeanUtils.copyProperties(origem, destino, "ativo", "dataCadastro", "idEscola");
	}
}
