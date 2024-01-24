package com.graphtype.repository;

import com.graphtype.model.RssVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import static com.graphtype.etc.DateTimeUtil.convertUtc2Kr;

@Repository
public class RssDAO {
    private JdbcTemplate template;

    @Autowired
    RssDAO(DataSource dataSource) { this.template = new JdbcTemplate(dataSource); }

    @Transactional
    public List<RssVO> getMediaAll() {

        try {

            String sql = "SELECT * FROM \"rssMedia\"";
            return this.template.query(sql, new RowMapper<RssVO>() {
                @Override
                public RssVO mapRow(ResultSet rs, int rowNum) throws SQLException {
                    String mediaIdx = rs.getString("idx");
                    RssVO vo = new RssVO();
                    vo.setNo(rs.getInt("no"));
                    vo.setIdx(mediaIdx);
                    vo.setName(rs.getString("name"));
                    vo.setRssUrl(rs.getString("rss_url"));
                    vo.setFeedCount(getFeedCount(mediaIdx));
                    return vo;
                }
            });

        } catch(Exception e) {
            System.out.print(e);
            return new ArrayList<>();
        }
    }

    @Transactional
    public List<RssVO> searchMedia(String mediaName) {

        try {

            String sql = String.format("SELECT * FROM \"rssMedia\" WHERE name LIKE '%%s%'", mediaName);
            return this.template.query(sql, new RowMapper<RssVO>() {
                @Override
                public RssVO mapRow(ResultSet rs, int rowNum) throws SQLException {
                    String mediaIdx = rs.getString("idx");
                    RssVO vo = new RssVO();
                    vo.setNo(rs.getInt("no"));
                    vo.setIdx(mediaIdx);
                    vo.setName(rs.getString("name"));
                    vo.setRssUrl(rs.getString("rss_url"));
                    vo.setFeedCount(getFeedCount(mediaIdx));
                    return vo;
                }
            });

        } catch(Exception e) {
            System.out.print(e);
            return new ArrayList<>();
        }
    }

    @Transactional
    public List<RssVO> getFeedAll() {
        try {

            String sql = """
                 SELECT
                    rf.*,
                    rm.name media_name
                 FROM \"rssFeed\" rf
                 join \"rssMedia\" rm on rf.media_idx = rm.idx
            """;
            return this.template.query(sql, new RowMapper<RssVO>() {
                @Override
                public RssVO mapRow(ResultSet rs, int rowNum) throws SQLException {
                    RssVO vo = new RssVO();
                    vo.setNo(rs.getInt("no"));
                    vo.setIdx(rs.getString("media_idx"));
                    vo.setTitle(rs.getString("title"));
                    vo.setLink(rs.getString("link"));
                    vo.setUpdatedAt(rs.getString("updated_at"));
                    vo.setName(rs.getString("media_name"));
                    return vo;
                }
            });

        } catch(Exception e) {
            System.out.print(e);
            return new ArrayList<>();
        }
    }

    @Transactional
    public List<RssVO> getFeedOfMedia(String mediaIdx) {
        try {

            String sql = String.format("""
                 SELECT
                    rf.*,
                    rm.name media_name
                 FROM \"rssFeed\" rf
                 JOIN \"rssMedia\" rm on rf.media_idx = rm.idx
                 WHERE rf.media_idx = '%s' 
            """, mediaIdx);
            return this.template.query(sql, new RowMapper<RssVO>() {
                @Override
                public RssVO mapRow(ResultSet rs, int rowNum) throws SQLException {
                    RssVO vo = new RssVO();
                    vo.setNo(rs.getInt("no"));
                    vo.setIdx(rs.getString("media_idx"));
                    vo.setTitle(rs.getString("title"));
                    vo.setLink(rs.getString("link"));
                    vo.setUpdatedAt(rs.getString("updated_at"));
                    vo.setName(rs.getString("media_name"));
                    return vo;
                }
            });

        } catch(Exception e) {
            System.out.print(e);
            return new ArrayList<>();
        }
    }

    public int getFeedCount(String mediaIdx) {
        String sql = String.format("SELECT COUNT(*) FROM \"rssFeed\" WHERE media_idx = '%s'", mediaIdx);
        return this.template.queryForObject(sql, Integer.class);
    }
}
