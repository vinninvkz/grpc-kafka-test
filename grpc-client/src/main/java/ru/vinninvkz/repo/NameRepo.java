package ru.vinninvkz.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vinninvkz.entity.Usver;

@Repository
public interface NameRepo extends JpaRepository<Usver,Integer> {
}
