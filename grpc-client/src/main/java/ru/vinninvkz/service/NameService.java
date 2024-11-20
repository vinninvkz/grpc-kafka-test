package ru.vinninvkz.service;

import org.springframework.stereotype.Service;
import ru.vinninvkz.dto.UsverDto;
import ru.vinninvkz.entity.Usver;
import ru.vinninvkz.mapper.UsverToDtoMapper;
import ru.vinninvkz.repo.NameRepo;

@Service
public class NameService {
    private final NameRepo nameRepo;

    public NameService(NameRepo nameRepo) {
        this.nameRepo = nameRepo;
    }


    public UsverDto getUsver(int id) {

        Usver usver = nameRepo.findById(id).orElseThrow(() -> new RuntimeException("Usver with ID: " + id + " not found"));

        return UsverToDtoMapper.mapToDto(usver);
    }
}
