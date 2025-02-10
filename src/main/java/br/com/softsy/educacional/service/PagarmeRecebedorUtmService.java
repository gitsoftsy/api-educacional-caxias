//package br.com.softsy.educacional.service;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import br.com.softsy.educacional.dto.CadastroPagarmeRecebedorUtmDTO;
//import br.com.softsy.educacional.dto.CadastroTurmaDTO;
//import br.com.softsy.educacional.dto.PagarmeRecebedorUtmDTO;
//import br.com.softsy.educacional.dto.TurmaDTO;
//import br.com.softsy.educacional.model.Conta;
//import br.com.softsy.educacional.model.Escola;
//import br.com.softsy.educacional.model.GradeCurricular;
//import br.com.softsy.educacional.model.PagarmeRecebedorUtm;
//import br.com.softsy.educacional.model.PeriodoLetivo;
//import br.com.softsy.educacional.model.Turma;
//import br.com.softsy.educacional.model.Turno;
//import br.com.softsy.educacional.model.Utm;
//import br.com.softsy.educacional.repository.ContaRepository;
//import br.com.softsy.educacional.repository.PagarmeRecebedorUtmRepository;
//import br.com.softsy.educacional.repository.UtmRepository;
//
//@Service
//public class PagarmeRecebedorUtmService {
//
//	@Autowired
//	private PagarmeRecebedorUtmRepository pagarmeRecebedorUtmRepository;
//
//	@Autowired
//	private ContaRepository contaRepository;
//
//	@Autowired
//	private UtmRepository utmRepository;
//
//	@Transactional(readOnly = true)
//	public List<PagarmeRecebedorUtmDTO> listarTudo() {
//		List<PagarmeRecebedorUtm> recebedorUtm = pagarmeRecebedorUtmRepository.findAll();
//		return recebedorUtm.stream().map(PagarmeRecebedorUtmDTO::new).collect(Collectors.toList());
//	}
//	
//	@Transactional(readOnly = true)
//	public PagarmeRecebedorUtmDTO buscarPorId(Long id){
//		return new PagarmeRecebedorUtmDTO(pagarmeRecebedorUtmRepository.getReferenceById(id));
//	}
//
//	@Transactional
//	public PagarmeRecebedorUtmDTO salvar(CadastroPagarmeRecebedorUtmDTO dto) {
//		PagarmeRecebedorUtm recebedor = criarPagarmeRecebedorUtmAPartirDTO(dto);
//		recebedor = pagarmeRecebedorUtmRepository.save(recebedor);
//		return new PagarmeRecebedorUtmDTO(recebedor);
//	}
//
//	@Transactional
//	public PagarmeRecebedorUtmDTO atualizar(CadastroPagarmeRecebedorUtmDTO dto) {
//		PagarmeRecebedorUtm recebedor = pagarmeRecebedorUtmRepository.getReferenceById(dto.getIdPagarmeRecebedorUtm());
//		atualizaDados(recebedor, dto);
//		return new PagarmeRecebedorUtmDTO(recebedor);
//	}
//
//	public PagarmeRecebedorUtm criarPagarmeRecebedorUtmAPartirDTO(CadastroPagarmeRecebedorUtmDTO dto) {
//		if (dto.getTipoRepasse() == 'P' && dto.getValorRepasse().compareTo(new java.math.BigDecimal(100)) > 0) {
//			throw new IllegalArgumentException("O valor de repasse não pode ser maior que 100 para o tipo P.");
//		}
//
//		PagarmeRecebedorUtm pagarmeRecebedorUtm = new PagarmeRecebedorUtm();
//		Conta conta = contaRepository.findById(dto.getContaId())
//				.orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
//		Utm utm = utmRepository.findById(dto.getUtmId())
//				.orElseThrow(() -> new IllegalArgumentException("Utm não encontrada"));
//
//		pagarmeRecebedorUtm.setConta(conta);
//		pagarmeRecebedorUtm.setUtm(utm);
//		pagarmeRecebedorUtm.setDataCadastro(LocalDateTime.now());
//		pagarmeRecebedorUtm.setAtivo(dto.getAtivo());
//		pagarmeRecebedorUtm.setTipoRepasse(dto.getTipoRepasse());
//		pagarmeRecebedorUtm.setValorRepasse(dto.getValorRepasse());
//		return pagarmeRecebedorUtm;
//	}
//
//	private void atualizaDados(PagarmeRecebedorUtm destino, CadastroPagarmeRecebedorUtmDTO origem) {
//
//		Conta conta = contaRepository.findById(origem.getContaId())
//				.orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
//		Utm utm = utmRepository.findById(origem.getUtmId())
//				.orElseThrow(() -> new IllegalArgumentException("Utm não encontrada"));
//
//		destino.setConta(conta);
//		destino.setUtm(utm);
//		destino.setDataCadastro(LocalDateTime.now());
//		destino.setAtivo(origem.getAtivo());
//		destino.setTipoRepasse(origem.getTipoRepasse());
//		destino.setValorRepasse(origem.getValorRepasse());
//	}
//
//	@Transactional
//	public void ativaDesativa(char status, Long idPagarmeRecebedorUtm) {
//		PagarmeRecebedorUtm pagarmeRecebedorUtm = pagarmeRecebedorUtmRepository.findById(idPagarmeRecebedorUtm)
//				.orElseThrow(() -> new IllegalArgumentException("RecebedorUtm não encontrado"));
//
//		pagarmeRecebedorUtm.setAtivo(status);
//	}
//}
