package br.com.softsy.educacional.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import br.com.softsy.educacional.dto.CadastroContaImagemLoginDTO;
import br.com.softsy.educacional.dto.ContaImagemLoginDTO;
import br.com.softsy.educacional.model.Aplicacao;
import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.model.ContaImagemLogin;
import br.com.softsy.educacional.repository.AplicacaoRepository;
import br.com.softsy.educacional.repository.ContaImagemLoginRepository;
import br.com.softsy.educacional.repository.ContaRepository;
import br.com.softsy.educacional.utils.ImageManager;

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

		if (apenasExibiveis != null && !apenasExibiveis.equalsIgnoreCase("S")
				&& !apenasExibiveis.equalsIgnoreCase("N")) {
			throw new IllegalArgumentException("O parâmetro 'apenasExibiveis' deve ser 'S' ou 'N'.");
		}

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

	@Transactional
	public CadastroContaImagemLoginDTO salvarImagemLogin(Long idConta, CadastroContaImagemLoginDTO dto)
			throws IOException {

		Conta conta = contaRepository.findById(idConta)
				.orElseThrow(() -> new EntityNotFoundException("Conta não encontrada"));

		Aplicacao aplicacao = aplicacaoRepository.findByAplicacao(dto.getAplicacao())
				.orElseThrow(() -> new EntityNotFoundException("Aplicação não encontrada"));
		List<ContaImagemLogin> imagensExistentes = repository.findByConta_IdConta(idConta);

		// Se a data de início e data de fim forem informadas
		if (dto.getDataInicioExibicao() != null && dto.getDataFimExibicao() != null) {
			for (ContaImagemLogin imagem : imagensExistentes) {
				if (isDateRangeOverlap(dto.getDataInicioExibicao(), dto.getDataFimExibicao(),
						imagem.getDataInicioExibicao(), imagem.getDataFimExibicao())) {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
							"Já existe uma imagem com essas datas de exibição.");
				}
			}
		} else if (dto.getDataInicioExibicao() != null) {
			for (ContaImagemLogin imagem : imagensExistentes) {
				if (imagem.getDataFimExibicao() == null
						&& imagem.getDataInicioExibicao().equals(dto.getDataInicioExibicao())) {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
							"Já existe uma imagem com essa data de início.");
				}
			}
		} else if (dto.getDataFimExibicao() != null) {
			for (ContaImagemLogin imagem : imagensExistentes) {
				if (imagem.getDataInicioExibicao() == null
						&& imagem.getDataFimExibicao().equals(dto.getDataFimExibicao())) {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
							"Já existe uma imagem com essa data de fim.");
				}
			}
		} else {
			for (ContaImagemLogin imagem : imagensExistentes) {
				if (imagem.getDataInicioExibicao() == null && imagem.getDataFimExibicao() == null) {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
							"Já existe uma imagem sem datas de exibição.");
				}
			}
		}
		String base64 = dto.getPathImagem();
		String caminhoIMG = ImageManager.salvaImagemContaLogin(base64, conta.getIdConta(), "login_" + conta.getConta());
		List<ContaImagemLogin> imagensExistentesAplicacao = repository
				.findByConta_IdContaAndAplicacao(conta.getIdConta(), aplicacao);

		if (!imagensExistentesAplicacao.isEmpty()) {
			ImageManager.excluirImagem(imagensExistentesAplicacao.get(0).getPathImagem());
		}

		ContaImagemLogin contaImagemLogin = new ContaImagemLogin();
		contaImagemLogin.setConta(conta);
		contaImagemLogin.setAplicacao(aplicacao);
		contaImagemLogin.setPathImagem(caminhoIMG);
		contaImagemLogin.setDataCadastro(LocalDateTime.now());
		contaImagemLogin.setDataInicioExibicao(dto.getDataInicioExibicao());
		contaImagemLogin.setDataFimExibicao(dto.getDataFimExibicao());

		contaImagemLogin = repository.save(contaImagemLogin);

		dto.setIdContaImagemLogin(contaImagemLogin.getIdContaImagemLogin());
		dto.setPathImagem(contaImagemLogin.getPathImagem());
		dto.setDataCadastro(contaImagemLogin.getDataCadastro());
		dto.setAplicacaoId(contaImagemLogin.getAplicacao().getIdAplicacao());
		dto.setAplicacao(contaImagemLogin.getAplicacao().getAplicacao());

		return dto;
	}

	// Método auxiliar para verificar se duas faixas de datas se sobrepõem
	private boolean isDateRangeOverlap(LocalDateTime start1, LocalDateTime end1, LocalDateTime start2,
			LocalDateTime end2) {
		return (start1.isBefore(end2) && end1.isAfter(start2));
	}

	@Transactional
	public void excluir(Long id) {
		repository.deleteById(id);
	}

}