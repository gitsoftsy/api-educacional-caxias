package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CadastroEscolaDTO;
import br.com.softsy.educacional.dto.EscolaDTO;
import br.com.softsy.educacional.infra.exception.UniqueException;
import br.com.softsy.educacional.model.CategoriaEscolaPrivada;
import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.model.DependenciaAdministrativa;
import br.com.softsy.educacional.model.EntidadeSuperior;
import br.com.softsy.educacional.model.Escola;
import br.com.softsy.educacional.model.FormaOcupacaoPredio;
import br.com.softsy.educacional.model.Localizacao;
import br.com.softsy.educacional.model.OrgaoPublico;
import br.com.softsy.educacional.model.SituacaoFuncionamento;
import br.com.softsy.educacional.model.Zoneamento;
import br.com.softsy.educacional.repository.CategoriaEscolaPrivadaRepository;
import br.com.softsy.educacional.repository.ContaRepository;
import br.com.softsy.educacional.repository.DependenciaAdministrativaRepository;
import br.com.softsy.educacional.repository.EntidadeSuperiorRepository;
import br.com.softsy.educacional.repository.EscolaRepository;
import br.com.softsy.educacional.repository.FormaOcupacaoPredioRepository;
import br.com.softsy.educacional.repository.LocalizacaoRepository;
import br.com.softsy.educacional.repository.OrgaoPublicoRepository;
import br.com.softsy.educacional.repository.SituacaoFuncionamentoRepository;
import br.com.softsy.educacional.repository.ZoneamentoRepository;

@Service
public class EscolaService {
	
	@Autowired 
	private EscolaRepository repository;

	@Autowired 
	private LocalizacaoRepository localizacaoRepository;
	
	@Autowired 
	private DependenciaAdministrativaRepository dependenciaRepository;
	
	@Autowired 
	private ContaRepository contaRepository;
	
	@Autowired 
	private SituacaoFuncionamentoRepository situacaoRepository;
	
	@Autowired 
	private FormaOcupacaoPredioRepository formaRepository;
	
	@Autowired
	private ZoneamentoRepository zoneamentoRepository;
	
	@Autowired
	private CategoriaEscolaPrivadaRepository categoriaEscolaPrivadaRepository;
	
	@Autowired
	private EntidadeSuperiorRepository entidadeSuperiorRepository;
	
	@Autowired
	private OrgaoPublicoRepository orgaoRepository;
	
	
	@Transactional(readOnly = true)
		public List<EscolaDTO> buscarPorIdConta(Long id) {
			List<Escola> escola = repository.findByConta_IdConta(id)
					.orElseThrow(() -> new IllegalArgumentException("Erro ao buscar escola por id de conta"));
			return escola.stream()
					.map(EscolaDTO::new)
					.collect(Collectors.toList());
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
		
		validarCnpjUnico(dto.getCnpj());
		validarCodigoInepUnico(dto.getCodigoInep());
		
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
		Conta conta = contaRepository.findById(dto.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
		SituacaoFuncionamento situacaoFuncionamento = situacaoRepository.findById(dto.getSituacaoFuncionamentoId())
				.orElseThrow(() -> new IllegalArgumentException("Situação não encontrada"));
		FormaOcupacaoPredio formaOcupacao = formaRepository.findById(dto.getFormaOcupacaoPredioId())
				.orElseThrow(() -> new IllegalAccessError("FormaOcupação não encontrada"));
		Zoneamento zoneamento = zoneamentoRepository.findById(dto.getZoneamentoId())
				.orElseThrow(()-> new IllegalArgumentException("Zoneamento não encontrado"));
		CategoriaEscolaPrivada categoriaEscolaPrivada = categoriaEscolaPrivadaRepository.findById(dto.getCategoriaEscolaPrivadaId())
				.orElseThrow(() -> new IllegalArgumentException("CategoriaEscolaPrivada não encontrada"));
		EntidadeSuperior entidadeSuperior = entidadeSuperiorRepository.findById(dto.getEntidadeSuperiorId())
				.orElseThrow(()-> new IllegalArgumentException("Entidade superior não encontrada"));
		OrgaoPublico orgaoPublico = orgaoRepository.findById(dto.getOrgaoPublicoId())
				.orElseThrow(() -> new IllegalArgumentException("Orgao público não encontrado"));
		BeanUtils.copyProperties(dto, escola, "ativo", "dataCadastro", "logoEscola" ,"idEscola", "localizacaoId", "dependenciaAdmId", "situacaoFuncionamentoId", "formaOcupacaoPredioId", "entidadeSuperiorId", "zoneamentoId", "categoriaEscolaPrivadaId", "orgaoPublicoId");
		escola.setLocalizacao(localizacao);
		escola.setLogoEscola(Base64.decodeBase64(dto.getLogoEscola()));
		escola.setDependenciaAdm(dependenciaAdm);
		escola.setSituacaoFuncionamento(situacaoFuncionamento);
		escola.setFormaOcupacaoPredio(formaOcupacao);
		escola.setZoneamento(zoneamento);
		escola.setCategoriaEscolaPrivada(categoriaEscolaPrivada);
		escola.setEntidadeSuperior(entidadeSuperior);
		escola.setOrgaoPublico(orgaoPublico);
		escola.setConta(conta);		
		escola.setAtivo('S');
		escola.setDataCadastro(LocalDateTime.now());
		return escola;
		
	}
	
	@Transactional
	private void validarCnpjUnico(String cnpj) {
		Optional<Escola> cnpjExistente = repository.findEscolaByCnpj(cnpj).stream().findFirst();
		if(cnpjExistente.isPresent()) {
			throw new UniqueException("Já existe uma escola com esse CNPJ");
		}
	}
	
	@Transactional
	private void validarCodigoInepUnico(String codigoInep) {
		Optional<Escola> codInepExistente = repository.findEscolaByCodigoInep(codigoInep).stream().findFirst();
		if(codInepExistente.isPresent()) {
			throw new UniqueException("Já existe uma escola com esse código de Inep");
		}
	}
	
	private void atualizaDados(Escola destino, CadastroEscolaDTO origem) {
		BeanUtils.copyProperties(origem, destino, "ativo", "dataCadastro", "idEscola", "logoEscola","localizacaoId", "dependenciaAdmId", "situacaoFuncionamentoId", "formaOcupacaoPredioId", "entidadeSuperiorId", "zoneamentoId", "categoriaEscolaPrivadaId", "orgaoPublicoId");
		Conta conta = contaRepository.findById(origem.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
		destino.setConta(conta);
		destino.setLogoEscola(Base64.decodeBase64(origem.getLogoEscola()));
	}
}
