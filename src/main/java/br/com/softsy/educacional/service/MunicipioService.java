package br.com.softsy.educacional.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CadastroMunicipioDTO;
import br.com.softsy.educacional.dto.CursoDTO;
import br.com.softsy.educacional.dto.MunicipioDTO;
import br.com.softsy.educacional.model.Curso;
import br.com.softsy.educacional.model.Municipio;
import br.com.softsy.educacional.model.Uf;
import br.com.softsy.educacional.repository.MunicipioRepository;
import br.com.softsy.educacional.repository.UfRepository;

@Service
public class MunicipioService {

	@Autowired
	private MunicipioRepository repository;

	@Autowired
	private UfRepository ufRepository;

	public List<MunicipioDTO> listarTudo() {
		List<Municipio> municipio = repository.findAll();

		return municipio.stream().map(MunicipioDTO::new).collect(Collectors.toList());
	}
	
    @Transactional(readOnly = true)
    public List<MunicipioDTO> buscarPorIdUf(Long idUf) {
        List<Municipio> municipio = repository.findByUf_IdUf(idUf)
                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar munícipio por ID do UF"));
        return municipio.stream()
                .map(MunicipioDTO::new)
                .collect(Collectors.toList());
    }

	@Transactional(readOnly = true)
	public MunicipioDTO buscarPorId(Long id) {
		return new MunicipioDTO(repository.getReferenceById(id));
	}

	@Transactional
	public CadastroMunicipioDTO salvar(CadastroMunicipioDTO dto) {

		Municipio municipio = criarMunicipioAPartirDTO(dto);

		municipio = repository.save(municipio);
		return new CadastroMunicipioDTO(municipio);
	}

	@Transactional
	public MunicipioDTO atualizar(CadastroMunicipioDTO dto) {
		Municipio municipio = repository.getReferenceById(dto.getIdMunicipio());
		atualizaDados(municipio, dto);
		return new MunicipioDTO(municipio);
	}

	private Municipio criarMunicipioAPartirDTO(CadastroMunicipioDTO dto) {
		Municipio municipio = new Municipio();
		Uf uf = ufRepository.findById(dto.getUfId())
				.orElseThrow(() -> new IllegalArgumentException("UF não encontrada"));
		municipio.setUf(uf);

		BeanUtils.copyProperties(dto, municipio, "idMunicipio", "idUf");
		return municipio;

	}

	private void atualizaDados(Municipio destino, CadastroMunicipioDTO origem) {
		BeanUtils.copyProperties(origem, destino, "idMunicipio", "idUf");
		
		destino.setUf(ufRepository.findById(origem.getUfId())
				.orElseThrow(() -> new IllegalArgumentException("Uf não encontrado")));

	}

	@Transactional(readOnly = true)
	public List<MunicipioDTO> buscarPorIdMunicipio(Long id) {
		List<Municipio> municipioTratamento = repository.findByUf_IdUf(id)
				.orElseThrow(() -> new IllegalArgumentException("Erro ao buscar o Termo por id de uf"));
		return municipioTratamento.stream().map(MunicipioDTO::new).collect(Collectors.toList());
	}

	@Transactional
	public void remover(Long id) {
		repository.deleteById(id);
	}

}
