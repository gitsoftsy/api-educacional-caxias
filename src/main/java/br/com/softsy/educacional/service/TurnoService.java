package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.TurnoDTO;
import br.com.softsy.educacional.model.Turno;
import br.com.softsy.educacional.repository.TurnoRepository;

@Service
public class TurnoService {

    @Autowired
    private TurnoRepository repository;

    public List<TurnoDTO> listarTudo() {
        List<Turno> turnos = repository.findAll();
        return turnos.stream()
                .map(TurnoDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TurnoDTO buscarPorId(Long id) {
        Turno turno = repository.getReferenceById(id);
        return new TurnoDTO(turno);
    }

    @Transactional
    public TurnoDTO salvar(TurnoDTO dto) {
        Turno turno = criarTurnoAPartirDTO(dto);
        turno = repository.save(turno);
        return new TurnoDTO(turno);
    }

    @Transactional
    public TurnoDTO atualizar(TurnoDTO dto) {
        Turno turno = repository.getReferenceById(dto.getIdTurno());
        atualizarDados(turno, dto);
        return new TurnoDTO(turno);
    }

    @Transactional
    public void excluir(Long id) {
        repository.deleteById(id);
    }

    private Turno criarTurnoAPartirDTO(TurnoDTO dto) {
        Turno turno = new Turno();
        BeanUtils.copyProperties(dto, turno, "idTurno", "dataCadastro");
        turno.setDataCadastro(LocalDateTime.now());
        return turno;
    }

    private void atualizarDados(Turno destino, TurnoDTO origem) {
        BeanUtils.copyProperties(origem, destino, "idTurno", "dataCadastro");
    }
}