package br.univille.dacs2022.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import br.univille.coredacs2022.entity.Procedimento;
import br.univille.dacs2022.dto.ProcedimentoDTO;

@Mapper
public interface ProcedimentoMapper {

    List<ProcedimentoDTO> mapListProcedimento(List<Procedimento> procedimento);

    List<Procedimento> mapListProcedimentoDTO(List<ProcedimentoDTO> procedimento);

    ProcedimentoDTO mapProcedimento(Procedimento procedimento);

    Procedimento mapProcedimentoDTO(ProcedimentoDTO procedimento);

}
