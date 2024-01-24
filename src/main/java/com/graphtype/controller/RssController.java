package com.graphtype.controller;

import com.graphtype.model.RssFeed;
import com.graphtype.model.RssMedia;
import com.graphtype.model.RssVO;
import com.graphtype.service.RssService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
public class RssController {
    @Autowired
    private RssService rssService;

//    @GetMapping("/rss/media_all")
//    public Mono<ResponseEntity<List<RssVO>>> getMediaAll() {
//        Mono<List<RssVO>> res = rssService.getMediaAll();
//        return res.map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
//    }

//    @GetMapping("/rss/media/{searchText}")
//    public Mono<ResponseEntity<List<RssVO>>> searchMedia(
//            @PathVariable("searchText") String searchText
//    ) {
//        Mono<List<RssVO>> res = rssService.searchMedia(searchText);
//        return res.map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
//    }
//
//    @GetMapping("/rss/feed_all")
//    public Mono<ResponseEntity<List<RssVO>>> getFeedAll() {
//        Mono<List<RssVO>> res = rssService.getFeedAll();
//        return res.map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
//    }
//
//    @GetMapping("/rss/feed/{mediaIdx}")
//    public Mono<ResponseEntity<List<RssVO>>> getFeedOfMedia(
//            @PathVariable("mediaIdx") String mediaIdx
//    ) {
//        Mono<List<RssVO>> res = rssService.getFeedOfMedia(mediaIdx);
//        return res.map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
//    }

    @GetMapping("/rss/media/all")
    public Mono<ResponseEntity<List<RssMedia>>> getRssMediaAll() {
        Mono<List<RssMedia>> res = rssService.getRssMediaAll();
        return res.map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/rss/media/{name}")
    public Mono<ResponseEntity<List<RssMedia>>> searchRssMedia(
            @PathVariable("name") String mediaName
    ) {
        Mono<List<RssMedia>> res = rssService.searchRssMedia(mediaName);
        return res.map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/rss/feed/{mediaIdx}")
    public Mono<ResponseEntity<List<RssFeed>>> getRssFeedListByMedia(
            @PathVariable("mediaIdx") String mediaIdx
    ) {
        Mono<List<RssFeed>> res = rssService.getRssFeedListByMedia(mediaIdx);
        return res.map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
