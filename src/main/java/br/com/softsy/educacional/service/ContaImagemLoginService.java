package br.com.softsy.educacional.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.ContaImagemLoginDTO;
import br.com.softsy.educacional.infra.exception.UniqueException;
import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.model.ContaImagemLogin;
import br.com.softsy.educacional.repository.ContaImagemLoginRepository;
import br.com.softsy.educacional.repository.ContaRepository;
import br.com.softsy.educacional.repository.AplicacaoRepository;

@Service
public class ContaImagemLoginService {

	@Autowired
	private ContaImagemLoginRepository repository;

	@Autowired
	private ContaRepository contaRepository;

	@Autowired
	private AplicacaoRepository aplicacaoRepository;

	@Transactional(readOnly = true)
	public List<ContaImagemLoginDTO> listarTudo() {
		List<ContaImagemLogin> contasImagensLogin = repository.findAll();
		return contasImagensLogin.stream().map(ContaImagemLoginDTO::new).collect(Collectors.toList());
	}

	@Transactional
	public List<ContaImagemLoginDTO> listarImagens(Long idConta, String aplicacao, String apenasExibiveis) {

		Conta conta = contaRepository.findById(idConta)
				.orElseThrow(() -> new EntityNotFoundException("Conta com ID " + idConta + " não encontrada."));

		if ("S".equalsIgnoreCase(apenasExibiveis)) {
			if (aplicacao == null || aplicacao.trim().isEmpty()) {
				throw new IllegalArgumentException(
						"Quando o parametro apenasExibiveis for informado 'S', o parâmetro 'aplicacao' é obrigatorio.");
			}
		}

		if (aplicacao != null) {
			boolean aplicacaoExiste = aplicacaoRepository.findByAplicacaoIgnoreCase(aplicacao).isPresent();

			if (!aplicacaoExiste) {
				throw new IllegalArgumentException("Aplicação '" + aplicacao + "' não encontrada.");
			}
		}

		List<ContaImagemLogin> imagens = repository.findAll().stream()
				.filter(img -> img.getConta().getIdConta().equals(idConta))
				.filter(img -> aplicacao == null || (img.getAplicacao() != null
						&& img.getAplicacao().getAplicacao().trim().equalsIgnoreCase(aplicacao.trim())))
				.collect(Collectors.toList());

		return imagens.stream().map(ContaImagemLoginDTO::new).collect(Collectors.toList());
	}

}