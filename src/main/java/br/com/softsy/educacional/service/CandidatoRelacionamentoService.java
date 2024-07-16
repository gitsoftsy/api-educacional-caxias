package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CadastroCandidatoRelacionamentoDTO;
import br.com.softsy.educacional.dto.CandidatoRelacionamentoDTO;
import br.com.softsy.educacional.model.Candidato;
import br.com.softsy.educacional.model.CandidatoRelacionamento;
import br.com.softsy.educacional.model.PapelPessoa;
import br.com.softsy.educacional.model.Pessoa;
import br.com.softsy.educacional.repository.CandidatoRelacionamentoRepository;
import br.com.softsy.educacional.repository.CandidatoRepository;
import br.com.softsy.educacional.repository.PapelPessoaRepository;
import br.com.softsy.educacional.repository.PessoaRepository;

@Service
public class CandidatoRelacionamentoService {
	
		@Autowired
		private CandidatoRelacionamentoRepository candidatoRelacionamentoRepository;
	
		@Autowired
	    private CandidatoRepository candidatoRepository;
	    
	    @Autowired
	    private PessoaRepository pessoaRepository;
	    
	    @Autowired
	    private PapelPessoaRepository  papelPessoaRepository;
	   
	   
	    @Transactional(readOnly = true)
	    public List<CandidatoRelacionamentoDTO> listarTudo() {
	        List<CandidatoRelacionamento> candidatoRelacionamento = candidatoRelacionamentoRepository.findAll();
	        return candidatoRelacionamento.stream()
	                .map(CandidatoRelacionamentoDTO::new)
	                .collect(Collectors.toList());
	   }

	    @Transactional(readOnly = true)
	    public CandidatoRelacionamentoDTO buscarPorId(Long id) {
	        return new CandidatoRelacionamentoDTO(candidatoRelacionamentoRepository.getReferenceById(id));
	    }
	    
	    @Transactional
	    public CandidatoRelacionamentoDTO salvar(CadastroCandidatoRelacionamentoDTO dto) {
	        CandidatoRelacionamento candidatoRelacionamento = criarCandidatoRelacionamentoAPartirDTO(dto);
	        candidatoRelacionamento = candidatoRelacionamentoRepository.save(candidatoRelacionamento);
	        return new CandidatoRelacionamentoDTO(candidatoRelacionamento);
	    }

	    @Transactional
	    public CandidatoRelacionamentoDTO atualizar(CadastroCandidatoRelacionamentoDTO dto) {
	    	CandidatoRelacionamento candidatoRelacionamento = candidatoRelacionamentoRepository.findById(dto.getIdCandidatoRelacionamento())
	                .orElseThrow(() -> new IllegalArgumentException("Candidato não encontrado"));
	        atualizarDados(candidatoRelacionamento, dto);
	        candidatoRelacionamento = candidatoRelacionamentoRepository.save(candidatoRelacionamento);
	        return new CandidatoRelacionamentoDTO(candidatoRelacionamento);
	    }


	    public CandidatoRelacionamento criarCandidatoRelacionamentoAPartirDTO(CadastroCandidatoRelacionamentoDTO dto) {
	        CandidatoRelacionamento candidatoRelacionamento = new CandidatoRelacionamento();


	        Candidato candidato = candidatoRepository.findById(dto.getCandidatoId())
	                .orElseThrow(() -> new IllegalArgumentException("Candidato não encontrado"));
	        Pessoa pessoa = pessoaRepository.findById(dto.getPessoaId())
	                .orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada"));
	        PapelPessoa papelPessoa = papelPessoaRepository.findById(dto.getPapelPessoaId())
	                .orElseThrow(() -> new IllegalArgumentException("Papel da Pessoa não encontrado"));

	
	        candidatoRelacionamento.setCandidato(candidato);
	        candidatoRelacionamento.setPessoa(pessoa);
	        candidatoRelacionamento.setPapelPessoa(papelPessoa);
	        candidatoRelacionamento.setAtivo('S');
	        candidatoRelacionamento.setDataCadastro(LocalDateTime.now());

	        return candidatoRelacionamento;
	    }



	    @Transactional
	    public void ativaDesativa(char status, Long idCadastroRelacionamento) {
	        CandidatoRelacionamento CandidatoRelacionamento = candidatoRelacionamentoRepository.getReferenceById(idCadastroRelacionamento);
	        CandidatoRelacionamento.setAtivo(status);
	    }
	    
	    
	    private void atualizarDados(CandidatoRelacionamento destino, CadastroCandidatoRelacionamentoDTO origem) {
	        BeanUtils.copyProperties(origem, destino, "idCandidato", "pessoaId", "papelPessoaId", "ativo");

	        Candidato candidato = candidatoRepository.findById(origem.getCandidatoId())
	                .orElseThrow(() -> new IllegalArgumentException("Candidato não encontrado"));
	        Pessoa pessoa = pessoaRepository.findById(origem.getPessoaId())
	                .orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada"));
	        PapelPessoa papelPessoa = papelPessoaRepository.findById(origem.getPapelPessoaId())
	                .orElseThrow(() -> new IllegalArgumentException("Papel da Pessoa não encontrado"));

	    
	        destino.setCandidato(candidato);
	        destino.setPessoa(pessoa);
	        destino.setPapelPessoa(papelPessoa);

	    }
	    
//	    @Transactional
//	    public void remover(Long idCandidatoRelacionamento) {
//	    	candidatoRelacionamentoRepository.deleteById(idCandidatoRelacionamento);
//	    }

}
