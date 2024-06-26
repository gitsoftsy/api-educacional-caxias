package br.com.softsy.educacional.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CadastroPessoaNacionalidadeDTO;
import br.com.softsy.educacional.dto.PessoaNacionalidadeDTO;
import br.com.softsy.educacional.model.Nacionalidade;
import br.com.softsy.educacional.model.Pessoa;
import br.com.softsy.educacional.model.PessoaNacionalidade;
import br.com.softsy.educacional.repository.NacionalidadeRepository;
import br.com.softsy.educacional.repository.PessoaNacionalidadeRepository;
import br.com.softsy.educacional.repository.PessoaRepository;

@Service
public class PessoaNacionalidadeService {

    @Autowired
    private PessoaNacionalidadeRepository repository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private NacionalidadeRepository nacionalidadeRepository;

    @Transactional(readOnly = true)
    public List<PessoaNacionalidadeDTO> listarTudo() {
        List<PessoaNacionalidade> pessoasNacionalidade = repository.findAll();
        return pessoasNacionalidade.stream()
                .map(PessoaNacionalidadeDTO::new)
                .collect(Collectors.toList());
    }

//    @Transactional(readOnly = true)
//    public List<PessoaNacionalidadeDTO> buscarPorIdPessoa(Long id) {
//        List<PessoaNacionalidade> pessoasNacionalidade = repository.findByPessoaNacionalidade_IdPessoa(id)
//                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar nacionalidades da pessoa por ID"));
//        return pessoasNacionalidade.stream()
//                .map(PessoaNacionalidadeDTO::new)
//                .collect(Collectors.toList());
//    }

    @Transactional
    public PessoaNacionalidadeDTO salvar(CadastroPessoaNacionalidadeDTO dto) {
        PessoaNacionalidade pessoaNacionalidade = criarPessoaNacionalidadeAPartirDTO(dto);
        pessoaNacionalidade = repository.save(pessoaNacionalidade);
        return new PessoaNacionalidadeDTO(pessoaNacionalidade);
    }

    @Transactional
    public PessoaNacionalidadeDTO atualizar(CadastroPessoaNacionalidadeDTO dto) {
        PessoaNacionalidade pessoaNacionalidade = repository.getReferenceById(dto.getIdPessoaNacionalidade());
        atualizaDados(pessoaNacionalidade, dto);
        return new PessoaNacionalidadeDTO(pessoaNacionalidade);
    }

    @Transactional
    public void remover(Long id) {
        repository.deleteById(id);
    }

    private PessoaNacionalidade criarPessoaNacionalidadeAPartirDTO(CadastroPessoaNacionalidadeDTO dto) {
        PessoaNacionalidade pessoaNacionalidade = new PessoaNacionalidade();
        Pessoa pessoa = pessoaRepository.findById(dto.getPessoaId())
                .orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada"));
        Nacionalidade nacionalidade = nacionalidadeRepository.findById(dto.getNacionalidadeId())
                .orElseThrow(() -> new IllegalArgumentException("Nacionalidade não encontrada"));
        pessoaNacionalidade.setPessoa(pessoa);
        pessoaNacionalidade.setNacionalidade(nacionalidade);
        return pessoaNacionalidade;
    }

    private void atualizaDados(PessoaNacionalidade destino, CadastroPessoaNacionalidadeDTO origem) {
        destino.setPessoa(pessoaRepository.findById(origem.getPessoaId())
                .orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada")));
        destino.setNacionalidade(nacionalidadeRepository.findById(origem.getNacionalidadeId())
                .orElseThrow(() -> new IllegalArgumentException("Nacionalidade não encontrada")));
    }
}
