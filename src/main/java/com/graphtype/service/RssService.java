package com.graphtype.service;

import com.graphtype.model.RssFeed;
import com.graphtype.model.RssMedia;
import com.graphtype.model.RssVO;
import com.graphtype.repository.RssDAO;
import com.graphtype.repository.RssFeedRepository;
import com.graphtype.repository.RssMediaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RssService {
    @Autowired
    private RssDAO rssDAO;

    @Autowired
    private RssMediaRepository rssMediaRepository;

    @Autowired
    private RssFeedRepository rssFeedRepository;

    public Mono<List<RssMedia>> getRssMediaAll() {
        List<RssMedia> res = rssMediaRepository.findAll();
        return Mono.just(res);
    }

    public Mono<List<RssMedia>> searchRssMedia(String mediaName) {
        List<RssMedia> res = rssMediaRepository.searchByNameLike(mediaName);
        return Mono.just(res);
    }

    public Mono<List<RssFeed>> getRssFeedListByMedia(String mediaIdx) {
        List<RssFeed> res = rssFeedRepository.findByMediaIdx(mediaIdx);
        return Mono.just(res);
    }

//    public Mono<List<RssVO>> getMediaAll() {
//        return Mono.just(rssDAO.getMediaAll());
//    }
//
//    public Mono<List<RssVO>> searchMedia(String mediaName) {
//        return Mono.just(rssDAO.searchMedia(mediaName));
//    }
//
//    public Mono<List<RssVO>> getFeedAll() {
//        return Mono.just(rssDAO.getFeedAll());
//    }
//
//    public Mono<List<RssVO>> getFeedOfMedia(String mediaIdx) {
//        return Mono.just(rssDAO.getFeedOfMedia(mediaIdx));
//    }
}
