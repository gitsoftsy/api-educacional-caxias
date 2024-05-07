package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CategoriaEscolaPrivadaDTO;
import br.com.softsy.educacional.model.CategoriaEscolaPrivada;
import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.repository.CategoriaEscolaPrivadaRepository;
import br.com.softsy.educacional.repository.ContaRepository;

@Service
public class CategoriaEscolaPrivadaService {

	@Autowired
	private CategoriaEscolaPrivadaRepository repository;

	@Autowired
	private ContaRepository contaRepository;

	@Transactional(readOnly = true)
	public List<CategoriaEscolaPrivadaDTO> buscarPorIdConta(Long id) {
		List<CategoriaEscolaPrivada> categoriaEscolaPrivada = repository.findByConta_IdConta(id)
				.orElseThrow(() -> new IllegalArgumentException("Erro ao buscar categoriaEscolaPrivada por id de conta"));
		return categoriaEscolaPrivada.stream().map(CategoriaEscolaPrivadaDTO::new).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public CategoriaEscolaPrivadaDTO buscarPorId(Long id) {
		return new CategoriaEscolaPrivadaDTO(repository.getReferenceById(id));
	}

	@Transactional
	public CategoriaEscolaPrivadaDTO salvar(CategoriaEscolaPrivadaDTO dto) {

		CategoriaEscolaPrivada categoria = criarCategoriaAPartirDTO(dto);

		categoria = repository.save(categoria);
		return new CategoriaEscolaPrivadaDTO(categoria);
	}

	private CategoriaEscolaPrivada criarCategoriaAPartirDTO(CategoriaEscolaPrivadaDTO dto) {
		CategoriaEscolaPrivada categoria = new CategoriaEscolaPrivada();
		Conta conta = contaRepository.findById(dto.getContaId())
				.orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
		BeanUtils.copyProperties(dto, categoria, "idCategoriaEscolaPrivada", "ativo", "dataCadastro");
		categoria.setConta(conta);
		categoria.setDataCadastro(LocalDateTime.now());
		categoria.setAtivo('S');
		return categoria;
	}

	@Transactional
	public void ativaDesativa(char status, Long idCategoriaEscolaPrivada) {
		CategoriaEscolaPrivada categoria = repository.getReferenceById(idCategoriaEscolaPrivada);
		categoria.setAtivo(status);
	}

	@Transactional
	public CategoriaEscolaPrivadaDTO atualizar(CategoriaEscolaPrivadaDTO dto) {
		CategoriaEscolaPrivada categoria = repository.getReferenceById(dto.getIdCategoriaEscolaPrivada());
		atualizaDados(categoria, dto);
		return new CategoriaEscolaPrivadaDTO(categoria);
	}

	private void atualizaDados(CategoriaEscolaPrivada destino, CategoriaEscolaPrivadaDTO origem) {
		BeanUtils.copyProperties(origem, destino, "idCategoriaEscolaPrivada", "ativo", "dataCadastro");
		Conta conta = contaRepository.findById(origem.getContaId())
				.orElseThrow(() -> new IllegalArgumentException("Dependência administrativa não encontrada"));
		destino.setConta(conta);
	}
}