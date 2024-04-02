package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CadastroPessoaDTO;
import br.com.softsy.educacional.dto.PessoaDTO;
import br.com.softsy.educacional.infra.exception.UniqueException;
import br.com.softsy.educacional.model.Municipio;
import br.com.softsy.educacional.model.Pais;
import br.com.softsy.educacional.model.Pessoa;
import br.com.softsy.educacional.model.Raca;
import br.com.softsy.educacional.model.Uf;
import br.com.softsy.educacional.repository.MunicipioRepository;
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
    	
    	validarCpf(dto.getCpf());
        Pessoa pessoa = criarPessoaAPartirDTO(dto);

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

    private Pessoa criarPessoaAPartirDTO(CadastroPessoaDTO dto) {
        Pessoa pessoa = new Pessoa();
        Raca raca = racaRepository.findById(dto.getRacaId())
                .orElseThrow(() -> new IllegalArgumentException("Raça não encontrada"));
        Pais paisNascimento = paisRepository.findById(dto.getPaisNascimentoId())
                .orElseThrow(() -> new IllegalArgumentException("País de nascimento não encontrado"));
        Uf ufNascimento = ufRepository.findById(dto.getUfNascimentoId())
                .orElseThrow(() -> new IllegalArgumentException("UF de nascimento não encontrado"));
        Municipio municipioNascimento = municipioRepository.findById(dto.getMunicipioNascimentoId())
                .orElseThrow(() -> new IllegalArgumentException("Município de nascimento não encontrado"));
        Pais paisResidencia = paisRepository.findById(dto.getPaisResidenciaId())
                .orElseThrow(() -> new IllegalArgumentException("País de residência não encontrado"));

        BeanUtils.copyProperties(dto, pessoa, "ativo", "dataCadastro", "idPessoa", "racaId", "paisNascimentoId", "ufNascimentoId",
                "municipioNascimentoId", "paisResidenciaId");

        pessoa.setRaca(raca);
        pessoa.setPaisNascimento(paisNascimento);
        pessoa.setUfNascimento(ufNascimento);
        pessoa.setMunicipioNascimento(municipioNascimento);
        pessoa.setPaisResidencia(paisResidencia);
        pessoa.setAtivo('S');
        pessoa.setDataCadastro(LocalDateTime.now());

        return pessoa;
    }
    
	private void validarCpf(String cpf) {
		Optional<Pessoa> cpfExistente = repository.findByCpf(cpf).stream().findFirst();
		if(cpfExistente.isPresent()) {
			throw new UniqueException("Já existe uma pessoa com o CPF fornecido.");
		}
	}

    private void atualizaDados(Pessoa destino, CadastroPessoaDTO origem) {
        BeanUtils.copyProperties(origem, destino, "ativo", "dataCadastro", "idPessoa", "racaId", "paisNascimentoId", "ufNascimentoId",
                "municipioNascimentoId", "paisResidenciaId");
    }
}