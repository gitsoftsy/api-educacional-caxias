package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CadastroEscolaPpciDTO;
import br.com.softsy.educacional.dto.EscolaPpciDTO;
import br.com.softsy.educacional.model.Escola;
import br.com.softsy.educacional.model.EscolaPpci;
import br.com.softsy.educacional.repository.EscolaPpciRepository;
import br.com.softsy.educacional.repository.EscolaRepository;

@Service
public class EscolaPpciService {

	@Autowired
	private EscolaPpciRepository repository;

	@Autowired
	private EscolaRepository escolaRepository;

	public List<EscolaPpciDTO> listarTudo() {
		List<EscolaPpci> escolaPpci = repository.findAll();

		return escolaPpci.stream().map(EscolaPpciDTO::new).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public EscolaPpciDTO buscarPorId(Long id) {
		return new EscolaPpciDTO(repository.getReferenceById(id));
	}

	@Transactional
	public CadastroEscolaPpciDTO salvar(CadastroEscolaPpciDTO dto) {

		EscolaPpci escolaPpci = criarEscolaPpciAPartirDTO(dto);

		escolaPpci = repository.save(escolaPpci);
		return new CadastroEscolaPpciDTO(escolaPpci);
	}

	@Transactional
	public EscolaPpciDTO atualizar(CadastroEscolaPpciDTO dto) {
		EscolaPpci escolaPpci = repository.getReferenceById(dto.getIdEscolaPpci());
		atualizaDados(escolaPpci, dto);
		return new EscolaPpciDTO(escolaPpci);
	}

	private EscolaPpci criarEscolaPpciAPartirDTO(CadastroEscolaPpciDTO dto) {
		EscolaPpci escolaPpci = new EscolaPpci();
		
		Escola escola = escolaRepository.findById(dto.getEscolaId())
				.orElseThrow(() -> new IllegalArgumentException("Escola não encontrada"));
		escolaPpci.setEscola(escola);
		escolaPpci.setDataCadastro(LocalDateTime.now());
		BeanUtils.copyProperties(dto, escolaPpci, "idEscolaPpci", "anexo", "idEscola", "dataCadastro"
				);
		escolaPpci.setAnexo(Base64.decodeBase64(dto.getAnexo()));
		return escolaPpci;

	}

	private void atualizaDados(EscolaPpci destino, CadastroEscolaPpciDTO origem) {
		BeanUtils.copyProperties(origem, destino, "idEscolaPpci", "dataCadastro", "anexo", "idEscola"
				);

		destino.setAnexo(Base64.decodeBase64(origem.getAnexo()));
		destino.setEscola(escolaRepository.findById(origem.getEscolaId())
				.orElseThrow(()-> new IllegalArgumentException("Escola não encontrada")));
	}

	@Transactional(readOnly = true)
	public List<EscolaPpciDTO> buscarPorIdEscola(Long id) {
		List<EscolaPpci> escolaTratamento = repository.findByEscola_IdEscola(id)
				.orElseThrow(() -> new IllegalArgumentException("Erro ao buscar o Termo por id de escola"));
		return escolaTratamento.stream().map(EscolaPpciDTO::new).collect(Collectors.toList());
	}
	
	@Transactional
	public void remover(Long id) {
		repository.deleteById(id);
	}

}
