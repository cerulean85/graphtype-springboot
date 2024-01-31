package com.graphtype.service;

import com.graphtype.model.BotArt;
import com.graphtype.model.RssMedia;
import com.graphtype.repository.BotArtRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BotArtService {
    @Autowired
    private BotArtRepository botArtRepository;

    public Mono<List<BotArt>> getBotArtAll() {
        List<BotArt> res = botArtRepository.findAll();
        return Mono.just(res);
    }
}
