package pl.hubertkuch.thesis.account.dto;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.hubertkuch.thesis.account.Account;

@Mapper
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper( AccountMapper .class );

    AccountDTO accountToDTO(Account account);
}
