package FileCrawler.unitTest;

import static org.junit.Assert.*;

import org.junit.Test;

import FileCrawler.Crawler.localSpider;

public class CrawlTest {

	@Test
	public void test() {
		localSpider crawler = new localSpider("E://7Diagonals");
		crawler.run();
		String[] output = localSpider.search("7d.png");
		if(output.length<1)
			fail("Failed to search for file");
		
		output = localSpider.search("forum");
		if(output.length < 1){
			fail("Fail to search for contents inside text file");
		}
	}

}
