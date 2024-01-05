package com.graphtype.repository;

import com.graphtype.model.BotBoardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BotBoardDAO {

    private JdbcTemplate template;
    @Autowired
    BotBoardDAO(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Transactional
    public BotBoardVO getBotState(String articleId) {
        try{
            String sql = "SELECT * FROM botboard WHERE article_id = '" + articleId + "' ORDER BY idx DESC LIMIT 1";
            List<BotBoardVO> list = this.template.query(sql, new RowMapper<BotBoardVO>() {
                @Override
                public BotBoardVO mapRow(ResultSet rs, int rowNum) throws SQLException {
                    BotBoardVO vo = new BotBoardVO();
                    vo.setIdx(rs.getInt("idx"));
                    vo.setArticleId(rs.getString("article_id"));
                    vo.setState(rs.getString("state"));
                    vo.setCreatedAt(rs.getTimestamp("created_at"));
                    vo.setUpdatedAt(rs.getTimestamp("updated_at"));
                    vo.setType(rs.getString("type"));
                    return vo;
                }
            });
            if (list.size() == 0)
                return null;
            return list.get(0);
        } catch(Exception ex) {
            return null;
        }
    }

    @Transactional
    public boolean insertBotBoardItem(BotBoardVO vo) {
        try{
            String sql = "INSERT INTO BotBoard(author, article_id, state, type) VALUES(?, ?, ?, ?)";
            this.template.update(sql, vo.getAuthor(), vo.getArticleId(), vo.getState(), vo.getType());
            return true;
        } catch(Exception ex) {
            return false;
        }
    }

    @Transactional
    public boolean updateBotBoardItem(BotBoardVO vo) {
        try {
            String sql = "UPDATE BotBoard SET state = ? WHERE article_id = ? AND type = ?";
            this.template.update(sql, vo.getState(), vo.getArticleId(), vo.getType());
            return true;
        } catch(Exception ex) {
            return false;
        }
    }

    @Transactional
    public boolean updateBotBoardItemStateStop(BotBoardVO vo) {
        try {
            String sql1 = "UPDATE BotBoard SET state = ? WHERE article_id = ? AND type = ?";
            this.template.update(sql1, vo.getState(), vo.getArticleId(), vo.getType());
            return true;
        } catch(Exception ex) {
            return false;
        }
    }

    @Transactional
    public boolean deleteBotBoardItem(BotBoardVO vo) {
        try {
            String sql = "DELETE FROM BotBoard WHERE article_id = ?";
            this.template. update(sql, vo.getArticleId());
            return true;
        } catch(Exception ex) {
            return false;
        }
    }

    @Transactional
    public boolean insertOrUpdateBoardItem(BotBoardVO vo) {
        try {
            String sql = String.format("SELECT COUNT(*) BotBoard WHERE article_id = '%s' AND type = '%s'", vo.getArticleId(), vo.getType());
            int rowCount = this.template.queryForObject(sql, Integer.class);
            if (rowCount == 0) {
                insertBotBoardItem(vo);
            } else {
                updateBotBoardItem(vo);
            }
            return true;
        } catch(Exception ex) {
            return false;
        }
    }
}
