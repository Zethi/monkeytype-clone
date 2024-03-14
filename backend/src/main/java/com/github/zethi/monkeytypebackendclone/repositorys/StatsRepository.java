package com.github.zethi.monkeytypebackendclone.repositorys;

import com.github.zethi.monkeytypebackendclone.entity.Stats;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StatsRepository extends CrudRepository<Stats, Long> {
    Optional<Stats> findByUserId(UUID uuid);
}
