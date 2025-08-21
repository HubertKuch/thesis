package pl.hubertkuch.thesis.application.dto;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.hubertkuch.thesis.application.Application;

@Mapper
public interface ApplicationMapper {
    ApplicationMapper INSTANCE = Mappers.getMapper(ApplicationMapper.class);

    ApplicationDTO toDto(Application application);
}
