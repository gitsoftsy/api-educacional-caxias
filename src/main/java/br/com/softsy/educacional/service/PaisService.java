package br.com.softsy.educacional.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.PaisDTO;
import br.com.softsy.educacional.infra.exception.UniqueException;
import br.com.softsy.educacional.model.Pais;
import br.com.softsy.educacional.repository.PaisRepository;

@Service
public class PaisService {

    @Autowired
    private PaisRepository repository;

    public List<Pais> listarTudo() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public PaisDTO buscarPorId(Long id) {
        return new PaisDTO(repository.getReferenceById(id));
    }

    @Transactional
    public PaisDTO salvar(PaisDTO dto) {
        validarPais(dto.getNomePais());
        validarCodPais(dto.getCodPais());

        Pais pais = criarPaisAPartirDTO(dto);

        pais = repository.save(pais);
        return new PaisDTO(pais);
    }

    private Pais criarPaisAPartirDTO(PaisDTO dto) {
        Pais pais = new Pais();
        pais.setCodPais(dto.getCodPais());
        pais.setNomePais(dto.getNomePais());
        pais.setCodigoIso(dto.getCodigoIso());
        return pais;
    }

    @Transactional
    public PaisDTO atualizar(PaisDTO dto) {
        Pais pais = repository.getReferenceById(dto.getIdPais());
        atualizarDados(pais, dto);
        return new PaisDTO(pais);
    }

    @Transactional
    public void excluir(Long id) {
        repository.deleteById(id);
    }

    private void validarPais(String nomePais) {
        Optional<Pais> paisExistente = repository.findByNomePais(nomePais).stream().findFirst();
        if (paisExistente.isPresent()) {
            throw new UniqueException("Esse país já existe.");
        }
    }
    
    private void validarCodPais(String codPais) {
        Optional<Pais> paisExistente = repository.findByCodPais(codPais).stream().findFirst();
        if (paisExistente.isPresent()) {
            throw new UniqueException("Esse país já existe.");
        }
    }

    private void atualizarDados(Pais destino, PaisDTO origem) {
        destino.setCodPais(origem.getCodPais());
        destino.setNomePais(origem.getNomePais());
        destino.setCodigoIso(origem.getCodigoIso());
    }

}