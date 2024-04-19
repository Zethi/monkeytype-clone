package com.github.zethi.monkeytypebackendclone.services;

import com.github.zethi.monkeytypebackendclone.entity.Stats;
import com.github.zethi.monkeytypebackendclone.repositorys.StatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class StatService {

    private final StatsRepository statsRepository;

    @Autowired
    public StatService(StatsRepository statsRepository) {
        this.statsRepository = statsRepository;
    }

    public void save(Stats stats) {
        this.statsRepository.save(stats);
    }

    public Optional<Stats> findById(long id) {
        return this.statsRepository.findById(id);
    }

    public Optional<Stats> findByUserUUID(UUID uuid) {
        return this.statsRepository.findByUserId(uuid);
    }
}
