package com.graphtype.repository;

import com.graphtype.model.ArticleResourceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ArticleResourceDAO {

    private JdbcTemplate template;
    @Autowired
    ArticleResourceDAO(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    public List<ArticleResourceVO> getArticleResourceList() {
        String sql = "SELECT * FROM resource_management";
        List<ArticleResourceVO> list = this.template.query(sql, new RowMapper<ArticleResourceVO>() {

            @Override
            public ArticleResourceVO mapRow(ResultSet rs, int rowNum) throws SQLException {
                ArticleResourceVO vo = new ArticleResourceVO();
                vo.setResourceId(rs.getString("resource_id"));
                vo.setArticleId(rs.getString("article_id"));
                vo.setUpdatedAt((rs.getString("updated_at")));
                return vo;
            }
        });
        return list;
    }

}
