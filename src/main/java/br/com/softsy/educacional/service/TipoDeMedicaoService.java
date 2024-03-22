package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.TipoDeMedicaoDTO;
import br.com.softsy.educacional.infra.exception.UniqueException;
import br.com.softsy.educacional.model.TipoDeMedicao;
import br.com.softsy.educacional.repository.TipoDeMedicaoRepository;

@Service
public class TipoDeMedicaoService {

    @Autowired
    private TipoDeMedicaoRepository repository;

    public List<TipoDeMedicao> listarTudo() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public TipoDeMedicaoDTO buscarPorId(Long id) {
        return new TipoDeMedicaoDTO(repository.getReferenceById(id));
    }

    @Transactional
    public TipoDeMedicaoDTO salvar(TipoDeMedicaoDTO dto) {
        validarTipoMedicao(dto.getTipoMedicao());

        TipoDeMedicao tipoMedicao = criarTipoMedicaoAPartirDTO(dto);

        tipoMedicao = repository.save(tipoMedicao);
        return new TipoDeMedicaoDTO(tipoMedicao);
    }

    private TipoDeMedicao criarTipoMedicaoAPartirDTO(TipoDeMedicaoDTO dto) {
        TipoDeMedicao tipoMedicao = new TipoDeMedicao();
        tipoMedicao.setTipoMedicao(dto.getTipoMedicao());
        tipoMedicao.setDataCadastro(LocalDateTime.now());
        return tipoMedicao;
    }
    
    @Transactional
    public void excluir(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public TipoDeMedicaoDTO atualizar(TipoDeMedicaoDTO dto) {
        TipoDeMedicao tipoMedicao = repository.getReferenceById(dto.getIdTipoMedicao());
        atualizaDados(tipoMedicao, dto);
        return new TipoDeMedicaoDTO(tipoMedicao);
    }

    private void validarTipoMedicao(String tipoMedicao) {
        Optional<TipoDeMedicao> tipoMedicaoExistente = repository.findByTipoMedicao(tipoMedicao).stream().findFirst();
        if (tipoMedicaoExistente.isPresent()) {
            throw new UniqueException("Esse tipo de medição já existe.");
        }
    }

    private void atualizaDados(TipoDeMedicao destino, TipoDeMedicaoDTO origem) {
        BeanUtils.copyProperties(origem, destino, "dataCadastro", "idTipoMedicao");
    }
}
