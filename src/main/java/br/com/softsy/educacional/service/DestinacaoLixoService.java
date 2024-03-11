package br.com.softsy.educacional.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.softsy.educacional.model.DestinacaoLixo;
import br.com.softsy.educacional.repository.DestinacaoLixoRepository;

@Service
public class DestinacaoLixoService {

	@Autowired
	private DestinacaoLixoRepository repository;
	
	public List<DestinacaoLixo> listarTudo(){
		return repository.findAll();
	}
}
