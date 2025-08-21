package pl.hubertkuch.thesis.application.dto;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.hubertkuch.thesis.application.Application;

@Mapper
public interface AppMapper {
    AppMapper INSTANCE = Mappers.getMapper(AppMapper.class);

    AppDTO toDto(Application application);
}
