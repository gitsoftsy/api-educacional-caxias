package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CargoProfessorDTO;
import br.com.softsy.educacional.infra.exception.UniqueException;
import br.com.softsy.educacional.model.CargoProfessor;
import br.com.softsy.educacional.repository.CargoProfessorRepository;

@Service
public class CargoProfessorService {

    @Autowired
    private CargoProfessorRepository repository;

    public List<CargoProfessor> listarTudo() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public CargoProfessorDTO buscarPorId(Long id) {
        return new CargoProfessorDTO(repository.getReferenceById(id));
    }

    @Transactional
    public CargoProfessorDTO salvar(CargoProfessorDTO dto) {
        validarCargoProfessor(dto.getCargoProfessor());

        CargoProfessor cargo = criarCargoAPartirDTO(dto);

        cargo = repository.save(cargo);
        return new CargoProfessorDTO(cargo);
    }

    private CargoProfessor criarCargoAPartirDTO(CargoProfessorDTO dto) {
        CargoProfessor cargo = new CargoProfessor();
        cargo.setCargoProfessor(dto.getCargoProfessor());
        cargo.setDataCadastro(LocalDateTime.now());
        cargo.setAtivo('S');
        return cargo;
    }

    @Transactional
    public CargoProfessorDTO atualizar(CargoProfessorDTO dto) {
        CargoProfessor cargo = repository.getReferenceById(dto.getIdCargoProfessor());
        atualizaDados(cargo, dto);
        return new CargoProfessorDTO(cargo);
    }

    @Transactional
    public void ativaDesativa(char status, Long idCargoProfessor) {
        CargoProfessor cargo = repository.getReferenceById(idCargoProfessor);
        cargo.setAtivo(status);
    }

    private void validarCargoProfessor(String cargoProfessor) {
        Optional<CargoProfessor> cargoExistente = repository.findByCargoProfessor(cargoProfessor).stream().findFirst();
        if (cargoExistente.isPresent()) {
            throw new UniqueException("Esse cargo já existe.");
        }
    }

    private void atualizaDados(CargoProfessor destino, CargoProfessorDTO origem) {
        destino.setCargoProfessor(origem.getCargoProfessor());
    }
}
