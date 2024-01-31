package com.graphtype.controller;

import com.graphtype.model.BotArt;
import com.graphtype.model.RssMedia;
import com.graphtype.service.BotArtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
public class BotArtController {
    @Autowired
    private BotArtService botArtService;

    @GetMapping("/bot/art/all")
    public Mono<ResponseEntity<List<BotArt>>> getBotArtAll() {
        Mono<List<BotArt>> res = botArtService.getBotArtAll();
        return res.map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
