package com.graphtype;

import com.graphtype.repository.RssMediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ApplicationTests {

//	@Autowired
//	private RssDAO rssDAO;

	@Autowired
    RssMediaRepository rssMediaRepository;

//	@BeforeEach
//	void ready() {
//		rssMedia rssMedia = new rssMedia();
//		rssMedia.setName("ㅋㅋㅋㅋ");
//		rssMediaRepository.save(rssMedia);
//	}
//
//	@Test
//	void test() {
//		List<rssMedia> mediaList = rssMediaRepository.findRssMediaBy("%ㅋ%");
//		 for (rssMedia media : mediaList)
//			 System.out.print(media.getName());
//	}

//	@Test
//	void contextLoads() {
//		List<RssVO> records = rssDAO.getMediaAll();
//		System.out.print(records);
//	}

}
