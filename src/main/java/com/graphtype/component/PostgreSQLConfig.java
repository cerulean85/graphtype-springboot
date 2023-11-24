//package com.graphtype.component;
//
//import com.graphtype.model.Article;
//import com.graphtype.model.ArticleResourceVO;
//import com.graphtype.repository.ArticleResourceDAO;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.stereotype.Component;
//
//import javax.sql.DataSource;
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.List;
//
//@Component
//public class PostgreSQLConfig implements ApplicationRunner {
//
////    private final DataSource dataSource;
////    private final JdbcTemplate jdbcTemplate;
//
//    @Autowired
//    ArticleResourceDAO articleResourceDAO;
//
////    public PostgreSQLConfig(DataSource dataSource) {
////        this.dataSource = dataSource;
////    }
////    public PostgreSQLConfig(DataSource dataSource,
////                            JdbcTemplate jdbcTemplate) {
////        this.dataSource = dataSource;
////        this.jdbcTemplate = jdbcTemplate;
////    }
//
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
////        try (Connection connection = dataSource.getConnection()){
////            System.out.println("[프뚜] > dataSource Class > " + dataSource.getClass());
////            System.out.println("[프뚜] > URL > " + connection.getMetaData().getURL());
////            System.out.println("[프뚜] > userName > " + connection.getMetaData().getUserName());
////        }
//
//        List<ArticleResourceVO> list = articleResourceDAO.getArticleResourceList();
////        int count = 0;
////        String sql = "SELECT * FROM resource_management";
////        List<ArticleResourceVO> list = jdbcTemplate.query(sql, new RowMapper<ArticleResourceVO>() {
////
////            @Override
////            public ArticleResourceVO mapRow(ResultSet rs, int rowNum) throws SQLException {
////                ArticleResourceVO vo = new ArticleResourceVO();
////                vo.setResourceId(rs.getString("resource_id"));
////                vo.setArticleId(rs.getString("article_id"));
////                vo.setUpdatedAt((rs.getString("updated_at")));
////                return vo;
////            }
////        });
//    }
//}