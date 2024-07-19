package br.com.softsy.educacional.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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
import br.com.softsy.educacional.model.Uf;
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
import br.com.softsy.educacional.utils.ImageManager;

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
	
	public String getLogoById(Long idEscola, String caminho) throws IOException {
        Optional<Escola> escolaOptional = repository.findById(idEscola);
        
        String imagemCarregada;
        imagemCarregada = ImageManager.buscaImagem(caminho);

      if (escolaOptional.isPresent()) {
            return imagemCarregada;
        } else {
            return null;
        }
    }
	
	
	@Transactional(readOnly = true)
	public List<EscolaDTO> listarAtivos(){
		return repository.findEscolaByAtivo('S').stream().map(EscolaDTO::new).collect(Collectors.toList());
	}
	
	@Transactional
	public CadastroEscolaDTO salvar(CadastroEscolaDTO dto) {
//	    validarCnpjUnico(dto.getCnpj());
	    validarCodigoInepUnico(dto.getCodigoInep());

	    String base64 = "";
	    Escola escola = criarEscolaAPartirDTO(dto);
	    
	    base64 = escola.getLogoEscola();
	    
	    escola.setLogoEscola(null);
	    // Salvando a escola no banco de dados após tratar a imagem
	    escola = repository.save(escola);

	    // Manipulando a imagem e obtendo o caminho
	    String caminhoIMG = ImageManager.salvaImagemEscola(base64, escola.getIdEscola(), "logoEscola" + dto.getNomeEscola());

	    // Setando a imagem diretamente no objeto escola
	    escola.setLogoEscola(caminhoIMG); 
	    dto.setLogoEscola(caminhoIMG);
	    dto.setIdEscola(escola.getIdEscola());
	    
	    atualizaDados(escola, dto);
	    
	    // Criando o DTO com os dados atualizados da escola
	    CadastroEscolaDTO escolaCriada = new CadastroEscolaDTO(escola);

	    return escolaCriada;
	}
	
	@Transactional
	public EscolaDTO atualizar(CadastroEscolaDTO dto) {
		Escola escola = repository.getReferenceById(dto.getIdEscola());
	      
	    escola = repository.save(escola);

	    // Manipulando a imagem e obtendo o caminho
	    String caminhoIMG = ImageManager.atualizaImagemEscola(dto.getIdEscola(), dto.getLogoEscola());

	    // Setando a imagem diretamente no objeto escola
	    escola.setLogoEscola(caminhoIMG); 
	    dto.setLogoEscola(caminhoIMG);
	    dto.setIdEscola(escola.getIdEscola());
		
		
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
		
		Localizacao localizacao = null;
        if (dto.getLocalizacaoId() != null) {
        	localizacao = localizacaoRepository.findById(dto.getLocalizacaoId())
                    .orElseThrow(() -> new IllegalArgumentException("Localização não encontrada"));
        }
        
        DependenciaAdministrativa dependenciaAdm = null;
        if (dto.getDependenciaAdmId() != null) {
        	dependenciaAdm = dependenciaRepository.findById(dto.getDependenciaAdmId())
                    .orElseThrow(() -> new IllegalArgumentException("Dependencia ADM não encontrada"));
        }

		Conta conta = contaRepository.findById(dto.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
		
		SituacaoFuncionamento situacaoFuncionamento = null;
        if (dto.getSituacaoFuncionamentoId() != null) {
        	situacaoFuncionamento = situacaoRepository.findById(dto.getSituacaoFuncionamentoId())
                    .orElseThrow(() -> new IllegalArgumentException("Situação de funcionamento não encontrada"));
        }

        FormaOcupacaoPredio formaOcupacao = null;
        if (dto.getFormaOcupacaoPredioId() != null) {
        	formaOcupacao = formaRepository.findById(dto.getFormaOcupacaoPredioId())
                    .orElseThrow(() -> new IllegalArgumentException("Forma de ocupação não encontrada"));
        }

        Zoneamento zoneamento = null;
        if (dto.getZoneamentoId() != null) {
        	zoneamento = zoneamentoRepository.findById(dto.getZoneamentoId())
                    .orElseThrow(() -> new IllegalArgumentException("Zoneamento não encontrado"));
        }

        
        CategoriaEscolaPrivada categoriaEscolaPrivada = null;
        if (dto.getCategoriaEscolaPrivadaId() != null) {
        	categoriaEscolaPrivada = categoriaEscolaPrivadaRepository.findById(dto.getCategoriaEscolaPrivadaId())
                    .orElseThrow(() -> new IllegalArgumentException("Categoria de escola privada não encontrada"));
        }

        EntidadeSuperior entidadeSuperior = null;
        if (dto.getEntidadeSuperiorId() != null) {
        	entidadeSuperior = entidadeSuperiorRepository.findById(dto.getEntidadeSuperiorId())
                    .orElseThrow(() -> new IllegalArgumentException("Entidade superior não encontrada"));
        }

        OrgaoPublico orgaoPublico = null;
        if (dto.getOrgaoPublicoId() != null) {
        	orgaoPublico = orgaoRepository.findById(dto.getOrgaoPublicoId())
                    .orElseThrow(() -> new IllegalArgumentException("Orgão público não encontrado"));
        }

		BeanUtils.copyProperties(dto, escola, "ativo","dataCadastro","idEscola", "localizacaoId", "dependenciaAdmId", "situacaoFuncionamentoId", "formaOcupacaoPredioId", "entidadeSuperiorId", "zoneamentoId", "categoriaEscolaPrivadaId", "orgaoPublicoId", "cnpj");
		escola.setLocalizacao(localizacao);
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
	
//	@Transactional
//	private void validarCnpjUnico(String cnpj) {
//		Optional<Escola> cnpjExistente = repository.findEscolaByCnpj(cnpj).stream().findFirst();
//		if(cnpjExistente.isPresent()) {
//			throw new UniqueException("Já existe uma escola com esse CNPJ");
//		}
//	}
	
	@Transactional
	private void validarCodigoInepUnico(String codigoInep) {
		Optional<Escola> codInepExistente = repository.findEscolaByCodigoInep(codigoInep).stream().findFirst();
		if(codInepExistente.isPresent()) {
			throw new UniqueException("Já existe uma escola com esse código de Inep");
		}
	}
	
	public void atualizaDados(Escola destino, CadastroEscolaDTO origem) {
		BeanUtils.copyProperties(origem, destino, "ativo", "dataCadastro", "idEscola","localizacaoId", "dependenciaAdmId", "situacaoFuncionamentoId", "formaOcupacaoPredioId", "entidadeSuperiorId", "zoneamentoId", "categoriaEscolaPrivadaId", "orgaoPublicoId");
		Conta conta = contaRepository.findById(origem.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
		destino.setConta(conta);
		destino.setLogoEscola(origem.getLogoEscola());
	}
	
	public List<Map<String, Object>> listaEscolasUsuario(Long idUsuario, Long idConta) {
        List<Object[]> resultList = repository.listaEscolasUsuario(idUsuario, idConta);
        List<Map<String, Object>> mappedResultList = new ArrayList<>();

        for (Object[] result : resultList) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("idEscola", result[0]);
            resultMap.put("idConta", result[1]);
            resultMap.put("dtCadastro", result[2]);
            resultMap.put("ativo", result[3]);
            resultMap.put("nomeEscola", result[4]);
            resultMap.put("logoEscola", result[5]);
            resultMap.put("tipoEscola", result[6]);
            resultMap.put("idCategoriaEscolaPrivada", result[7]);
            resultMap.put("cnpj", result[8]);
            resultMap.put("codigoInep", result[9]);
            resultMap.put("cep", result[10]);
            resultMap.put("endereco", result[11]);
            resultMap.put("numero", result[12]);
            resultMap.put("complemento", result[13]);
            resultMap.put("bairro", result[14]);
            resultMap.put("municipio", result[15]);
            resultMap.put("distrito", result[16]);
            resultMap.put("uf", result[17]);
            resultMap.put("numCme", result[18]);
            resultMap.put("numParecerCme", result[19]);
            resultMap.put("latitude", result[20]);
            resultMap.put("longitude", result[21]);
            resultMap.put("idLocalizacao", result[22]);
            resultMap.put("idEntidadeSuperior", result[23]);
            resultMap.put("email", result[24]);
            resultMap.put("idSituacaoFuncionamento", result[25]);
            resultMap.put("educacaoIndigena", result[26]);
            resultMap.put("exameSelecao", result[27]);
            resultMap.put("compartilhaEspaco", result[28]);
            resultMap.put("usaEspacoEntornoEscolar", result[29]);
            resultMap.put("pppAtualizado12Meses", result[30]);
            resultMap.put("idFormaOcupacaoPredio", result[31]);
            resultMap.put("idZoneamento", result[32]);
            resultMap.put("idOrgaoPublico", result[33]);
            resultMap.put("idDependenciaAdiministrativa", result[34]);
            mappedResultList.add(resultMap);
        }

        return mappedResultList;
    }
	
}
