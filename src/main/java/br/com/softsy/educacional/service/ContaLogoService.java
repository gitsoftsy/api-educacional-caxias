package br.com.softsy.educacional.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
		Optional<ContaLogo> optionalContaLogo = repository.findByContaIdContaAndAplicacaoIdAplicacao(dto.getContaId(),
				dto.getAplicacaoId());

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

		dto.setAplicacaoId(contaLogo.getAplicacao().getIdAplicacao());
		dto.setAplicacao(contaLogo.getAplicacao().getAplicacao());

		return dto;
	}

	public ResponseEntity<Object> buscarLogosPorConta(Long idConta, String aplicacao) {
		List<ContaLogo> contaLogos;

		if (aplicacao != null) {
			contaLogos = repository.findByConta_IdContaAndAplicacao_Aplicacao(idConta, aplicacao);
		} else {
			contaLogos = repository.findByConta(idConta);
		}

		if (contaLogos.isEmpty()) {
			return ResponseEntity.ok(List.of(new ContaLogoDTO(getLogoSoftsy())));
		}

		List<ContaLogoDTO> contaLogosDTO = contaLogos.stream().map(logo -> new ContaLogoDTO(transformarEmUrl(logo)))
				.collect(Collectors.toList());

		return ResponseEntity.ok(contaLogosDTO);
	}

	private ContaLogo getLogoSoftsy() {
		ContaLogo logoSoftsy = new ContaLogo();
		logoSoftsy.setIdContaLogo(0L);
		logoSoftsy.setPathLogo(gerarUrlImagem("Softsy.png")); // Ajuste para o nome da imagem, sem "uploads"
		logoSoftsy.setDataCadastro(LocalDateTime.now());

		Aplicacao aplicacao = new Aplicacao();
		aplicacao.setIdAplicacao(0L);
		aplicacao.setAplicacao("Softsy");

		Conta contaPadrao = new Conta();
		contaPadrao.setIdConta(0L);

		logoSoftsy.setAplicacao(aplicacao);
		logoSoftsy.setConta(contaPadrao);

		return logoSoftsy;
	}

	public ContaLogo transformarEmUrl(ContaLogo logo) {
		logo.setPathLogo(gerarUrlImagem(logo.getPathLogo())); // Converte caminho em URL
		return logo;
	}

	private String gerarUrlImagem(String caminhoRelativo) {
		return ServletUriComponentsBuilder.fromCurrentContextPath().path("/").path(caminhoRelativo).toUriString();
	}

	@Transactional
	public void excluir(Long id) {
		repository.deleteById(id);
	}

}
