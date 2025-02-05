package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CadastroPagarmeRecebedorEscolaDTO;
import br.com.softsy.educacional.dto.PagarmeRecebedorEscolaDTO;
import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.model.Escola;
import br.com.softsy.educacional.model.PagarmeRecebedorEscola;
import br.com.softsy.educacional.repository.ContaRepository;
import br.com.softsy.educacional.repository.EscolaRepository;
import br.com.softsy.educacional.repository.PagarmeRecebedorEscolaRepository;

@Service
public class PagarmeRecebedorEscolaService {
	
	@Autowired
	private PagarmeRecebedorEscolaRepository pagarmeRecebedorEscolaRepository;

	@Autowired
	private ContaRepository contaRepository;

	@Autowired
	private EscolaRepository escolaRepository;
	
	@Transactional(readOnly = true)
	public List<PagarmeRecebedorEscolaDTO> listarTudo() {
		List<PagarmeRecebedorEscola> recebedorEscola = pagarmeRecebedorEscolaRepository.findAll();
		return recebedorEscola.stream().map(PagarmeRecebedorEscolaDTO::new).collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public PagarmeRecebedorEscolaDTO buscarPorId(Long id){
		return new PagarmeRecebedorEscolaDTO(pagarmeRecebedorEscolaRepository.getReferenceById(id));
	}

	@Transactional
	public PagarmeRecebedorEscolaDTO salvar(CadastroPagarmeRecebedorEscolaDTO dto) {
		PagarmeRecebedorEscola recebedor = criarPagarmeRecebedorEscolaAPartirDTO(dto);
		recebedor = pagarmeRecebedorEscolaRepository.save(recebedor);
		return new PagarmeRecebedorEscolaDTO(recebedor);
	}

	@Transactional
	public PagarmeRecebedorEscolaDTO atualizar(CadastroPagarmeRecebedorEscolaDTO dto) {
		PagarmeRecebedorEscola recebedor = pagarmeRecebedorEscolaRepository.getReferenceById(dto.getIdPagarmeRecebedorEscola());
		atualizaDados(recebedor, dto);
		return new PagarmeRecebedorEscolaDTO(recebedor);
	}

	public PagarmeRecebedorEscola criarPagarmeRecebedorEscolaAPartirDTO(CadastroPagarmeRecebedorEscolaDTO dto) {
		if (dto.getTipoRepasse() == 'P' && dto.getValorRepasse().compareTo(new java.math.BigDecimal(100)) > 0) {
			throw new IllegalArgumentException("O valor de repasse não pode ser maior que 100 para o tipo P.");
		}

		PagarmeRecebedorEscola pagarmeRecebedorEscola = new PagarmeRecebedorEscola();
		Conta conta = contaRepository.findById(dto.getContaId())
				.orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
		Escola escola = escolaRepository.findById(dto.getEscolaId())
				.orElseThrow(() -> new IllegalArgumentException("Escola não encontrada"));

		pagarmeRecebedorEscola.setConta(conta);
		pagarmeRecebedorEscola.setEscola(escola);
		pagarmeRecebedorEscola.setDataCadastro(LocalDateTime.now());
		pagarmeRecebedorEscola.setAtivo(dto.getAtivo());
		pagarmeRecebedorEscola.setTipoRepasse(dto.getTipoRepasse());
		pagarmeRecebedorEscola.setValorRepasse(dto.getValorRepasse());
		return pagarmeRecebedorEscola;
	}

	private void atualizaDados(PagarmeRecebedorEscola destino, CadastroPagarmeRecebedorEscolaDTO origem) {

		Conta conta = contaRepository.findById(origem.getContaId())
				.orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
		Escola escola = escolaRepository.findById(origem.getEscolaId())
				.orElseThrow(() -> new IllegalArgumentException("Escola não encontrada"));


		destino.setConta(conta);
		destino.setEscola(escola);
		destino.setDataCadastro(LocalDateTime.now());
		destino.setAtivo(origem.getAtivo());
		destino.setTipoRepasse(origem.getTipoRepasse());
		destino.setValorRepasse(origem.getValorRepasse());
	}

	@Transactional
	public void ativaDesativa(char status, Long idPagarmeRecebedorEscola) {
		PagarmeRecebedorEscola pagarmeRecebedorEscola = pagarmeRecebedorEscolaRepository.findById(idPagarmeRecebedorEscola)
				.orElseThrow(() -> new IllegalArgumentException("RecebedorUtm não encontrado"));

		pagarmeRecebedorEscola.setAtivo(status);
	}

}
