package com.example.mapper;

import com.example.model.importstatus.ImportStatus;
import com.example.model.importstatus.ImportStatusDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ImportStatusMapper {

    ImportStatusMapper INSTANCE = Mappers.getMapper(ImportStatusMapper.class);

    ImportStatusDto fromEntity(ImportStatus importStatus);

}
