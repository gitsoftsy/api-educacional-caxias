package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.RacaDTO;
import br.com.softsy.educacional.infra.exception.UniqueException;
import br.com.softsy.educacional.model.Raca;
import br.com.softsy.educacional.repository.RacaRepository;

@Service
public class RacaService {

    @Autowired
    private RacaRepository racaRepository;

    public List<Raca> listarTudo() {
        return racaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public RacaDTO buscarPorId(Long id) {
        return new RacaDTO(racaRepository.getReferenceById(id));
    }

    @Transactional
    public RacaDTO salvar(RacaDTO dto) {
        validarRaca(dto.getRaca());

        Raca raca = criarRacaAPartirDTO(dto);

        raca = racaRepository.save(raca);
        return new RacaDTO(raca);
    }

    private Raca criarRacaAPartirDTO(RacaDTO dto) {
        Raca raca = new Raca();
        BeanUtils.copyProperties(dto, raca, "idRaca", "ativo", "dataCadastro");
        raca.setAtivo('S');
        raca.setDataCadastro(LocalDateTime.now());
        return raca;
    }

    @Transactional
    public RacaDTO atualizar(RacaDTO dto) {
        Raca raca = racaRepository.getReferenceById(dto.getIdRaca());
        atualizaDados(raca, dto);
        return new RacaDTO(raca);
    }

    @Transactional
    public void ativaDesativa(char status, Long idRaca) {
        Raca raca = racaRepository.getReferenceById(idRaca);
        raca.setAtivo(status);
    }

    private void validarRaca(String raca) {
        Optional<Raca> racaExistente = racaRepository.findByRaca(raca).stream().findFirst();
        if (racaExistente.isPresent()) {
            throw new UniqueException("Essa raça já existe.");
        }
    }

    private void atualizaDados(Raca destino, RacaDTO origem) {
        BeanUtils.copyProperties(origem, destino, "idRaca", "ativo", "dataCadastro");
    }
}
