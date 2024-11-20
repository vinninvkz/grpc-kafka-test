package ru.vinninvkz.mapper;

import lombok.NoArgsConstructor;
import ru.vinninvkz.dto.UsverDto;
import ru.vinninvkz.entity.Usver;

@NoArgsConstructor
public class UsverToDtoMapper {
    public static UsverDto mapToDto(Usver usver){
        return UsverDto.builder()
                .lastname(usver.getLastname())
                .build();
    }
}
