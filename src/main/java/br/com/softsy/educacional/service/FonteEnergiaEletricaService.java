package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.FonteEnergiaEletricaDTO;
import br.com.softsy.educacional.infra.exception.UniqueException;
import br.com.softsy.educacional.model.FonteEnergiaEletrica;
import br.com.softsy.educacional.repository.FonteEnergiaEletricaRepository;

@Service
public class FonteEnergiaEletricaService {

	@Autowired
	private FonteEnergiaEletricaRepository repository;
	
	public List<FonteEnergiaEletrica> listarTudo(){
		return repository.findAll();
	}
	
	@Transactional(readOnly = true)
	public FonteEnergiaEletricaDTO buscarPorId(Long id){
		return new FonteEnergiaEletricaDTO(repository.getReferenceById(id));
	}
	
	@Transactional
	public FonteEnergiaEletricaDTO salvar(FonteEnergiaEletricaDTO dto) {
		validarFonteEnergiaEletrica(dto.getFonteEnergiaEletrica());
		
		FonteEnergiaEletrica fonteEnergia = criarEnergiaAPartirDTO(dto);
		
		fonteEnergia = repository.save(fonteEnergia);
		return new FonteEnergiaEletricaDTO(fonteEnergia);
	}
	
	private FonteEnergiaEletrica criarEnergiaAPartirDTO(FonteEnergiaEletricaDTO dto) {
		FonteEnergiaEletrica fonteEnergia = new FonteEnergiaEletrica();
		BeanUtils.copyProperties(dto, fonteEnergia, "idFonteEnergiaEletrica", "ativo", "dataCadastro");
		fonteEnergia.setAtivo('S');
		fonteEnergia.setDataCadastro(LocalDateTime.now());
		return fonteEnergia;
	}
	
	@Transactional
	public FonteEnergiaEletricaDTO atualizar(FonteEnergiaEletricaDTO dto) {
		FonteEnergiaEletrica fonteEnergia = repository.getReferenceById(dto.getIdFonteEnergiaEletrica());
		atualizaDados(fonteEnergia, dto);
		return new FonteEnergiaEletricaDTO(fonteEnergia);
	}
	
	@Transactional
	public void ativaDesativa(char status, Long idFonteEnergiaEletrica) {
		FonteEnergiaEletrica fonteEnergia = repository.getReferenceById(idFonteEnergiaEletrica);
		fonteEnergia.setAtivo(status);
	}
	
	private void validarFonteEnergiaEletrica(String fonteEnergiaEletrica) {
		Optional<FonteEnergiaEletrica> fonteExistente = repository.findByFonteEnergiaEletrica(fonteEnergiaEletrica).stream().findFirst();
		if(fonteExistente.isPresent()) {
			throw new UniqueException("Essa fonte já existe.");
		}
	}
	
	private void atualizaDados(FonteEnergiaEletrica destino, FonteEnergiaEletricaDTO origem) {
		BeanUtils.copyProperties(origem, destino, "idFonteEnergiaEletrica", "ativo", "dataCadastro");
		
	}
	
}
