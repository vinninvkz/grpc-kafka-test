package ru.vinninvkz.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.vinninvkz.dto.UsverDto;
import ru.vinninvkz.entity.Usver;
import ru.vinninvkz.mapper.UsverToDtoMapper;
import ru.vinninvkz.repo.LastnameRepo;

import java.util.Optional;

@Service
@Slf4j
public class LastnameService {
    private final LastnameRepo lastnameRepo;

    public LastnameService(LastnameRepo lastnameRepo) {
        this.lastnameRepo = lastnameRepo;
    }


    public UsverDto getUsver(int id) {

        Optional<Usver> optionalUsver = lastnameRepo.findById(id);
        Usver usver;
        if (optionalUsver.isPresent()) {
            usver = optionalUsver.get();
            log.info("Received from db usver: " + usver);
        } else throw new RuntimeException("Usver with id: " + id + " not found");
        return UsverToDtoMapper.mapToDto(usver);
    }
}
