package br.com.softsy.educacional.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CadastroResponsavelDTO;
import br.com.softsy.educacional.dto.CandidatoRelacionamentoDTO;
import br.com.softsy.educacional.dto.PessoaDTO;

@Service
public class PessoaCandidatoRelacionamentoService {
	
	 @Autowired
	    private PessoaService pessoaService;

	    @Autowired
	    private CandidatoRelacionamentoService candidatoRelacionamentoService;

	    @Transactional
	    public void atualizar(CadastroResponsavelDTO dto) {
	        PessoaDTO pessoaDTO = pessoaService.atualizar(dto.getPessoaDTO());
	        CandidatoRelacionamentoDTO candidatoRelacionamentoDTO = candidatoRelacionamentoService.atualizar(dto.getCandidatoRelacionamentoDTO());
	    }

}
