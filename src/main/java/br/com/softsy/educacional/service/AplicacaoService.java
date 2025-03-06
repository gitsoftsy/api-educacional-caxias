package br.com.softsy.educacional.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.AgendaDTO;
import br.com.softsy.educacional.dto.AplicacaoDTO;
import br.com.softsy.educacional.model.Agenda;
import br.com.softsy.educacional.model.Aplicacao;
import br.com.softsy.educacional.repository.AplicacaoRepository;

@Service
public class AplicacaoService {

	@Autowired
	private AplicacaoRepository repository;

	public List<AplicacaoDTO> listarTudo() {
		List<Aplicacao> aplicacoes = repository.findAllByOrderByIdAplicacaoAsc();
		return aplicacoes.stream().map(AplicacaoDTO::new).collect(Collectors.toList());
	}

}