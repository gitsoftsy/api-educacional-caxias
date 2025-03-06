package br.com.softsy.educacional.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CadastroContaLogoDTO;
import br.com.softsy.educacional.dto.ContaLogoDTO;
import br.com.softsy.educacional.model.Aplicacao;
import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.model.ContaLogo;
import br.com.softsy.educacional.repository.AplicacaoRepository;
import br.com.softsy.educacional.repository.ContaLogoRepository;
import br.com.softsy.educacional.repository.ContaRepository;
import br.com.softsy.educacional.utils.ImageManager;

@Service
public class ContaLogoService {

	@Autowired
	private ContaLogoRepository repository;

	@Autowired
	private ContaRepository contaRepository;

	@Autowired
	private AplicacaoRepository aplicacaoRepository;

	public List<ContaLogo> listarTudo() {
		return repository.findAll();
	}

	@Transactional(readOnly = true)
	public ContaLogoDTO buscarPorId(Long id) {
		return new ContaLogoDTO(repository.getReferenceById(id));
	}
	@Transactional
	public CadastroContaLogoDTO salvar(CadastroContaLogoDTO dto) throws IOException {
	    Conta conta = contaRepository.findById(dto.getContaId())
	        .orElseThrow(() -> new EntityNotFoundException("Conta não encontrada"));
	    Aplicacao aplicacao = aplicacaoRepository.findById(dto.getAplicacaoId())
	        .orElseThrow(() -> new EntityNotFoundException("Aplicação não encontrada"));

	    Optional<ContaLogo> optionalContaLogo = repository.findByContaIdContaAndAplicacaoIdAplicacao(dto.getContaId(), dto.getAplicacaoId());

	    if (optionalContaLogo.isPresent()) {
	        return null;
	    }

	    String base64 = dto.getPathLogo();
	    String caminhoIMG = ImageManager.salvaImagemConta(base64, conta.getIdConta(), "logoConta_" + conta.getConta());

	    ContaLogo contaLogo = new ContaLogo();
	    contaLogo.setConta(conta);
	    contaLogo.setAplicacao(aplicacao);
	    contaLogo.setPathLogo(caminhoIMG);
	    contaLogo.setDataCadastro(LocalDateTime.now());

	    contaLogo = repository.save(contaLogo);
	    dto.setIdContaLogo(contaLogo.getIdContaLogo());
	    dto.setPathLogo(contaLogo.getPathLogo());
	    dto.setDataCadastro(contaLogo.getDataCadastro());

	    return dto;
	}

	private ContaLogo criarContaLogoAPartirDTO(CadastroContaLogoDTO dto, Conta conta, Aplicacao aplicacao, String caminhoIMG) {
	    ContaLogo contaLogo = new ContaLogo();
	    contaLogo.setConta(conta);
	    contaLogo.setAplicacao(aplicacao);
	    contaLogo.setPathLogo(caminhoIMG);
	    contaLogo.setDataCadastro(LocalDateTime.now());
	    return contaLogo;
	}


	@Transactional
	public void excluir(Long id) {
		repository.deleteById(id);
	}

}
