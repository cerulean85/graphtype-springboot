package com.graphtype.service;


import com.graphtype.component.AwsS3Uploader;
import com.graphtype.model.*;
import com.graphtype.repository.ArticleRepository;
import com.graphtype.repository.ArticleResourceDAO;
import com.graphtype.repository.BotBoardDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private BotBoardDAO botBoardDAO;

    @Autowired
    private ArticleResourceDAO articleResourceDAO;

    public Mono<Article> getItem(String author, String articleId) {
        return Mono.just(articleRepository.getItem(author, articleId));
    }

    public Mono<Article> getFeedback(String author, String articleId, String type) {
        BotBoardVO bot = botBoardDAO.getBotState(articleId, type);
        String state = (bot == null) ? "error" : bot.getState();

        Article article = new Article();
        article.setState(state);

        try {
            Article resArticle = getItem(author, articleId).block();
            article.setFeedback(resArticle.getFeedback());
        } catch (Exception e) {
            article.setFeedback(new ArrayList<FeedbackItem>());
        }

        return Mono.just(article);
    }

    public Mono<Article> createItem(Article article) {
        Mono<Article> res = Mono.just(articleRepository.createItem(article));
        Article resultArticle = res.block();

        assert resultArticle != null;
        List<InventoryItem> inventory = resultArticle.getInventory();
        List<File> imageSrcList = getResources(resultArticle, inventory);
        if (imageSrcList.size() > 0)
            articleResourceDAO.insertArticleResource(imageSrcList);

        BotBoardVO botBoardVO = new BotBoardVO();
        botBoardVO.setArticleId(article.getArticleId());
        botBoardVO.setState("idle");
        botBoardVO.setAuthor(article.getAuthor());

        botBoardVO.setType("chat_gpt");
        botBoardDAO.insertOrUpdateBoardItem(botBoardVO);

        botBoardVO.setType("gemini");
        botBoardDAO.insertOrUpdateBoardItem(botBoardVO);

        return res;
    }
    public Mono<ArticleResponse> getItems(String author, int pageNo) {
        return Mono.just(articleRepository.getItems(author, pageNo));
    }

    public Mono<ArticleResponse> searchItems(String searchText, int pageNo) {
        return Mono.just(articleRepository.searchItems(searchText, pageNo));
    }
    public Mono<Boolean> deleteItem(String author, String articleId, AwsS3Uploader awsS3Uploader) {
        Mono<Article> selectRes = this.getItem(author, articleId);
        Article readArticle = selectRes.block();

        assert readArticle != null;
        List<ArticleResourceVO> resources = articleResourceDAO.getArticleResourceList(articleId);
        for (ArticleResourceVO vo: resources) {
            try {
                awsS3Uploader.delete(vo.getResourceId());
            } catch(Exception e) {

            }
        }
        articleResourceDAO.deleteArticleResource(resources);

        BotBoardVO botBoardVO = new BotBoardVO();
        botBoardVO.setArticleId(articleId);
        botBoardDAO.deleteBotBoardItem(botBoardVO);

        return  Mono.just(articleRepository.deleteItem(author, articleId));
    }

    public Mono<String> updateItem(Article article) {
        return Mono.just(articleRepository.updateItem(article));
    }

    public Mono<Boolean> rechat(String articleId, String author, String botType) {
        BotBoardVO botBoardVO = new BotBoardVO();
        botBoardVO.setArticleId(articleId);
        botBoardVO.setAuthor(author);
        botBoardVO.setType(botType);
        botBoardVO.setState("wait");

        boolean result = botBoardDAO.insertOrUpdateBoardItem(botBoardVO); //botBoardDAO.updateBotBoardItemStateStop(botBoardVO);
        if (!result) return Mono.just(false);

        return Mono.just(result);
    }

    private List<File> getResources(Article resultArticle, List<InventoryItem> inventory) {
        List<File> imageSrcList = new ArrayList<>();
        for (InventoryItem item: inventory) {
            if (item.getType().equals("image")) {
                File resource = new File();
                resource.setArticleId(resultArticle.getArticleId());
                String[] temp = item.getContents().split("/");
                if (temp.length == 0)
                    continue;
                String resourceId = "images/" + temp[temp.length-1];
                resource.setResourceId(resourceId);
                imageSrcList.add(resource);
            }
        }
        return imageSrcList;
    }

    public void updateResourceMeta(File file, String articleId) {
        if (file.url == null)
            return;

        String[] temp = file.url.split("/");
        if (temp.length == 0)
            return;

        file.setResourceId("images/" + temp[temp.length-1]);
        file.setArticleId(articleId);
        List<File> files = new ArrayList<File>();
        files.add(file);
        articleResourceDAO.insertArticleResource(files);
    }
}
