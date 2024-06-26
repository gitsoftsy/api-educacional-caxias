package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CadastroPessoaDTO;
import br.com.softsy.educacional.dto.PessoaDTO;
import br.com.softsy.educacional.infra.config.PasswordEncrypt;
import br.com.softsy.educacional.infra.exception.NegocioException;
import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.model.Municipio;
import br.com.softsy.educacional.model.Nacionalidade;
import br.com.softsy.educacional.model.Pais;
import br.com.softsy.educacional.model.Pessoa;
import br.com.softsy.educacional.model.Raca;
import br.com.softsy.educacional.model.Uf;
import br.com.softsy.educacional.repository.ContaRepository;
import br.com.softsy.educacional.repository.MunicipioRepository;
import br.com.softsy.educacional.repository.NacionalidadeRepository;
import br.com.softsy.educacional.repository.PaisRepository;
import br.com.softsy.educacional.repository.PessoaRepository;
import br.com.softsy.educacional.repository.RacaRepository;
import br.com.softsy.educacional.repository.UfRepository;
@Service
public class PessoaService {

    @Autowired
    private PessoaRepository repository;

    @Autowired
    private RacaRepository racaRepository;

    @Autowired
    private PaisRepository paisRepository;

    @Autowired
    private UfRepository ufRepository;

    @Autowired
    private MunicipioRepository municipioRepository;
    
	@Autowired 
	private ContaRepository contaRepository;
	
	@Autowired
	private NacionalidadeRepository nacionalidadeRepository;
	
	@Autowired
	private PasswordEncrypt encrypt;

    public List<PessoaDTO> listarTudo() {
        List<Pessoa> pessoas = repository.findAll();

        return pessoas.stream().map(PessoaDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PessoaDTO buscarPorId(Long id) {
        return new PessoaDTO(repository.getReferenceById(id));
    }

    @Transactional
    public CadastroPessoaDTO salvar(CadastroPessoaDTO dto) {
    	
    	validarSenha(dto.getSenha());
        Pessoa pessoa = criarPessoaAPartirDTO(dto);

        pessoa.setSenha(encrypt.hashPassword(dto.getSenha()));
        pessoa = repository.save(pessoa);
        return new CadastroPessoaDTO(pessoa);
    }

    @Transactional
    public PessoaDTO atualizar(CadastroPessoaDTO dto) {
        Pessoa pessoa = repository.getReferenceById(dto.getIdPessoa());
        atualizaDados(pessoa, dto);
        return new PessoaDTO(pessoa);
    }

    @Transactional
    public void ativaDesativa(char status, Long id) {
        Pessoa pessoa = repository.getReferenceById(id);
        pessoa.setAtivo(status);
    }

    public Pessoa criarPessoaAPartirDTO(CadastroPessoaDTO dto) {
    	
        if (dto.getCertidaoNascimentoMunicipioCartorioId() == null && dto.getCertidaoCasamentoMunicipioCartorioId() == null) {
            throw new IllegalArgumentException("Pelo menos um dos campos certidaoNascimentoUfCartorioId ou certidaoCasamentoUfCartorioId deve ser preenchido");
        }
        
        Pessoa pessoa = new Pessoa();

        Raca raca = racaRepository.findById(dto.getRacaId())
                .orElseThrow(() -> new IllegalArgumentException("Raça não encontrada"));
        Pais paisNascimento = paisRepository.findById(dto.getPaisNascimentoId())
                .orElseThrow(() -> new IllegalArgumentException("País de nascimento não encontrado"));
        Municipio municipioCertidaoCasamento = null;
        if (dto.getCertidaoCasamentoMunicipioCartorioId() != null) {
        	municipioCertidaoCasamento = municipioRepository.findById(dto.getCertidaoCasamentoMunicipioCartorioId())
                    .orElseThrow(() -> new IllegalArgumentException("Município de casamento não encontrado"));
        }

        Municipio municipioCertidaoNascimento = null;
        if (dto.getCertidaoNascimentoMunicipioCartorioId() != null) {
        	municipioCertidaoNascimento = municipioRepository.findById(dto.getCertidaoNascimentoMunicipioCartorioId())
                    .orElseThrow(() -> new IllegalArgumentException("Município de nascimento não encontrado"));
        }
        
        Uf ufRgEmissor = null;
        if (dto.getRgUfEmissorId() != null) {
        	ufRgEmissor = ufRepository.findById(dto.getRgUfEmissorId())
                    .orElseThrow(() -> new IllegalArgumentException("UF de casamento não encontrado"));
        }

        Uf ufRneEmissor = null;
        if (dto.getRneUfEmissorId() != null) {
        	ufRneEmissor = ufRepository.findById(dto.getRneUfEmissorId())
                    .orElseThrow(() -> new IllegalArgumentException("UF de nascimento não encontrado"));
        }
        Municipio municipioNascimento = municipioRepository.findById(dto.getMunicipioNascimentoId())
                .orElseThrow(() -> new IllegalArgumentException("Município de nascimento não encontrado"));
        Pais paisResidencia = paisRepository.findById(dto.getPaisResidenciaId())
                .orElseThrow(() -> new IllegalArgumentException("País de residência não encontrado"));
        Nacionalidade nacionalidade = nacionalidadeRepository.findById(dto.getNacionalidadeId())
                .orElseThrow(() -> new IllegalArgumentException("Nacionalidade não encontrada"));
        Conta conta = contaRepository.findById(dto.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));

        BeanUtils.copyProperties(dto, pessoa, "dataCadastro", "idPessoa",
                "rgNumero", "rgOrgaoExpedidor",
                "rgDataExpedicao", "rneNumero", "rneOrgaoExpedidor", "rneDataExpedicao",
                "certidaoNascimentoDataEmissao", "certidaoNascimentoFolha", "certidaoNascimentoLivro",
                "certidaoNascimentoOrdem", "certidaoCasamentoNumero", "certidaoCasamentoCartorio", "certidaoCasamentoFolha",
                "certidaoCasamentoLivro", "certidaoCasamentoOrdem", "nomePai", "nomeMae", "cep", "endereco", "numero",
                "complemento", "bairro","municipio", "distrito", "uf", "telefone", "celular", "email", "empresa","ocupacao", "telefoneComercial", "senha", "ativo");


        pessoa.setConta(conta);
        pessoa.setRaca(raca);
        pessoa.setNacionalidadeId(nacionalidade);
        pessoa.setRgUfEmissor(ufRgEmissor);
        pessoa.setRneUfEmissor(ufRneEmissor);
        pessoa.setSexo(dto.getSexo());
        pessoa.setEstadoCivil(dto.getEstadoCivil());
        pessoa.setPaisNascimento(paisNascimento);
        pessoa.setMunicipioNascimento(municipioNascimento);
        pessoa.setCertidaoNascimentoMunicipioCartorio(municipioCertidaoNascimento);
        pessoa.setCertidaoCasamentoMunicipioCartorio(municipioCertidaoCasamento);
        pessoa.setUsuario(dto.getUsuario());
        pessoa.setPaisResidencia(paisResidencia);
        pessoa.setDtNascimento(dto.getDtNascimento());
        pessoa.setAtivo('S');
        pessoa.setDataCadastro(LocalDateTime.now());

        pessoa.setRgNumero(dto.getRgNumero());
        pessoa.setRgOrgaoExpedidor(dto.getRgOrgaoExpedidor());
        pessoa.setRgDataExpedicao(dto.getRgDataExpedicao());
        pessoa.setRneNumero(dto.getRneNumero());
        pessoa.setRneOrgaoExpedidor(dto.getRneOrgaoExpedidor());
        pessoa.setRneDataExpedicao(dto.getRneDataExpedicao());
        pessoa.setCertidaoNascimentoNumero(dto.getCertidaoNascimentoNumero());
        pessoa.setCertidaoNascimentoCartorio(dto.getCertidaoNascimentoCartorio());
        pessoa.setCertidaoNascimentoDataEmissao(dto.getCertidaoNascimentoDataEmissao());
        pessoa.setCertidaoNascimentoFolha(dto.getCertidaoNascimentoFolha());
        pessoa.setCertidaoNascimentoLivro(dto.getCertidaoNascimentoLivro());
        pessoa.setCertidaoNascimentoOrdem(dto.getCertidaoNascimentoOrdem());
        pessoa.setCertidaoCasamentoNumero(dto.getCertidaoCasamentoNumero());
        pessoa.setCertidaoCasamentoCartorio(dto.getCertidaoCasamentoCartorio());
        pessoa.setCertidaoCasamentoDataEmissao(dto.getCertidaoCasamentoDataEmissao());
        pessoa.setCertidaoCasamentoFolha(dto.getCertidaoCasamentoFolha());
        pessoa.setCertidaoCasamentoLivro(dto.getCertidaoCasamentoLivro());
        pessoa.setCertidaoCasamentoOrdem(dto.getCertidaoCasamentoOrdem());
        pessoa.setNomePai(dto.getNomePai());
        pessoa.setNomeMae(dto.getNomeMae());
        pessoa.setCep(dto.getCep());
        pessoa.setEndereco(dto.getEndereco());
        pessoa.setNumero(dto.getNumero());
        pessoa.setComplemento(dto.getComplemento());
        pessoa.setBairro(dto.getBairro());
        pessoa.setMunicipio(dto.getMunicipio());
        pessoa.setDistrito(dto.getDistrito());
        pessoa.setUf(dto.getUf());
        pessoa.setTelefone(dto.getTelefone());
        pessoa.setCelular(dto.getCelular());
        pessoa.setEmail(dto.getEmail());
        pessoa.setCpf(dto.getCpf());
        pessoa.setEmpresa(dto.getEmpresa());
        pessoa.setOcupacao(dto.getOcupacao());
        pessoa.setTelefoneComercial(dto.getTelefoneComercial());
        pessoa.setSenha(dto.getSenha());

        return pessoa;
    }
    
	private void validarSenha(String senha) {
		if (senha == "") {
			throw new NegocioException("A senha precisa ser informada!");
		}
	}
    

    private void atualizaDados(Pessoa pessoa, CadastroPessoaDTO dto) {
        Raca raca = racaRepository.findById(dto.getRacaId())
                .orElseThrow(() -> new IllegalArgumentException("Raça não encontrada"));
        Pais paisNascimento = paisRepository.findById(dto.getPaisNascimentoId())
                .orElseThrow(() -> new IllegalArgumentException("País de nascimento não encontrado"));
        Municipio municipioNascimento = municipioRepository.findById(dto.getMunicipioNascimentoId())
                .orElseThrow(() -> new IllegalArgumentException("Município de nascimento não encontrado"));
        Pais paisResidencia = paisRepository.findById(dto.getPaisResidenciaId())
                .orElseThrow(() -> new IllegalArgumentException("País de residência não encontrado"));
        Nacionalidade nacionalidade = nacionalidadeRepository.findById(dto.getNacionalidadeId())
                .orElseThrow(() -> new IllegalArgumentException("Nacionalidade não encontrada"));
        Conta conta = contaRepository.findById(dto.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));

        BeanUtils.copyProperties(dto, pessoa, "dataCadastro", "idPessoa", "racaId", "paisNascimentoId", "ufNascimentoId",
                "municipioNascimentoId", "paisResidenciaId", "rgNumero", "rgOrgaoExpedidor",
                "rgDataExpedicao", "rneNumero", "rneOrgaoExpedidor", "rneDataExpedicao",
                "certidaoNascimentoDataEmissao", "certidaoNascimentoFolha", "certidaoNascimentoLivro",
                "certidaoNascimentoOrdem", "certidaoCasamentoNumero", "certidaoCasamentoCartorio", "certidaoCasamentoFolha",
                "certidaoCasamentoLivro", "certidaoCasamentoOrdem", "nomePai", "nomeMae", "cep", "endereco", "numero",
                "complemento", "bairro", "municipio", "distrito", "uf", "telefone", "celular", "email", "empresa", "ocupacao", "telefoneComercial", "senha", "ativo");

        // Mapear os novos campos do DTO para o objeto Pessoa
        pessoa.setConta(conta);
        pessoa.setRaca(raca);
        pessoa.setNacionalidadeId(nacionalidade);
        pessoa.setRgUfEmissor(ufRepository.findById(dto.getRgUfEmissorId()).orElse(null));
        pessoa.setRneUfEmissor(ufRepository.findById(dto.getRneUfEmissorId()).orElse(null));
        pessoa.setSexo(dto.getSexo());
        pessoa.setEstadoCivil(dto.getEstadoCivil());
        pessoa.setPaisNascimento(paisNascimento);
        pessoa.setMunicipioNascimento(municipioNascimento);
        pessoa.setCertidaoNascimentoMunicipioCartorio(municipioRepository.findById(dto.getCertidaoNascimentoMunicipioCartorioId()).orElse(null));
        pessoa.setCertidaoCasamentoMunicipioCartorio(municipioRepository.findById(dto.getCertidaoCasamentoMunicipioCartorioId()).orElse(null));
        pessoa.setUsuario(dto.getUsuario());
        pessoa.setPaisResidencia(paisResidencia);
        pessoa.setDtNascimento(dto.getDtNascimento());
        pessoa.setAtivo('S');
        pessoa.setDataCadastro(LocalDateTime.now());

        // Configurar campos adicionais
        pessoa.setRgNumero(dto.getRgNumero());
        pessoa.setRgOrgaoExpedidor(dto.getRgOrgaoExpedidor());
        pessoa.setRgDataExpedicao(dto.getRgDataExpedicao());
        pessoa.setRneNumero(dto.getRneNumero());
        pessoa.setRneOrgaoExpedidor(dto.getRneOrgaoExpedidor());
        pessoa.setRneDataExpedicao(dto.getRneDataExpedicao());
        pessoa.setCertidaoNascimentoNumero(dto.getCertidaoNascimentoNumero());
        pessoa.setCertidaoNascimentoCartorio(dto.getCertidaoNascimentoCartorio());
        pessoa.setCertidaoNascimentoDataEmissao(dto.getCertidaoNascimentoDataEmissao());
        pessoa.setCertidaoNascimentoFolha(dto.getCertidaoNascimentoFolha());
        pessoa.setCertidaoNascimentoLivro(dto.getCertidaoNascimentoLivro());
        pessoa.setCertidaoNascimentoOrdem(dto.getCertidaoNascimentoOrdem());
        pessoa.setCertidaoCasamentoNumero(dto.getCertidaoCasamentoNumero());
        pessoa.setCertidaoCasamentoCartorio(dto.getCertidaoCasamentoCartorio());
        pessoa.setCertidaoCasamentoDataEmissao(dto.getCertidaoCasamentoDataEmissao());
        pessoa.setCertidaoCasamentoFolha(dto.getCertidaoCasamentoFolha());
        pessoa.setCertidaoCasamentoLivro(dto.getCertidaoCasamentoLivro());
        pessoa.setCertidaoCasamentoOrdem(dto.getCertidaoCasamentoOrdem());
        pessoa.setNomePai(dto.getNomePai());
        pessoa.setNomeMae(dto.getNomeMae());
        pessoa.setCep(dto.getCep());
        pessoa.setEndereco(dto.getEndereco());
        pessoa.setNumero(dto.getNumero());
        pessoa.setComplemento(dto.getComplemento());
        pessoa.setBairro(dto.getBairro());
        pessoa.setMunicipio(dto.getMunicipio());
        pessoa.setDistrito(dto.getDistrito());
        pessoa.setUf(dto.getUf());
        pessoa.setTelefone(dto.getTelefone());
        pessoa.setCelular(dto.getCelular());
        pessoa.setCpf(dto.getCpf());
        pessoa.setEmail(dto.getEmail());
        pessoa.setEmpresa(dto.getEmpresa());
        pessoa.setOcupacao(dto.getOcupacao());
        pessoa.setTelefoneComercial(dto.getTelefoneComercial());
        pessoa.setSenha(dto.getSenha());
    }
}