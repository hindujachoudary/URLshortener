package com.assignment.urlShortener;

import com.assignment.urlShortener.repositories.GlobalRepository;
import com.assignment.urlShortener.repositories.JdbcRepository;
import com.assignment.urlShortener.services.MyService;
import jdk.nashorn.internal.objects.Global;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@SpringBootTest
class MyServiceTests {

//	@Mock
	private GlobalRepository mockRepo;

//	@InjectMocks
	@Autowired
	private MyService myService;

	@BeforeEach
	void setMockOutputs() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getSuccessTest() {
		String longUrl = "https://www.google.co.in/ajshkjahjh8769769JIHKHkjkjbf";


	}

	@Test
	void getShortUrlNotFoundTest() {
		assertEquals("Not Found", myService.getUrl("asdf"));
	}

	@Test
	void createAndGetTest() {
		String longUrlSample = "https://www.google.com/forms/QDIYW392dwldk2nssdJDN32KN323523KSDVknsKs0";
		String shortUrlSample = myService.createUrl(longUrlSample);
		assertEquals(longUrlSample, myService.getUrl(shortUrlSample));
	}

	@Test
	void createIfNotExistsTest() {
//		when(mockRepo.getByVal("https://www.youtube.com/ak39HN2mH8")).thenReturn("Not Found");
//		when(mockRepo.get(any())).thenReturn("Not Found");
		assertEquals("Not Found", myService.getUrl("https://www.youtube.com/ak39HN2mH8"));
		String shortUrl = myService.createUrl("https://www.youtube.com/ak39HN2mH8");
		assertTrue(shortUrl.length() == 6);
		assertEquals("https://www.youtube.com/ak39HN2mH8", myService.getUrl(shortUrl));
	}

	@Test
	void createIfExistsTest() {
		String longUrlSample = "https://www.zeta.tech/about/asdkjgoskj3290NKNlk4l3nKNLHKSLMELDS";
		String shortUrlSample = myService.createUrl(longUrlSample);
		String shortUrlSample2 = myService.createUrl(longUrlSample); // Second time called for the same longUrl
		assertEquals(shortUrlSample, shortUrlSample2);
	}

	@Test
	void deleteAndGetTest() {
		String longUrlSample = "https://www.youtube.com/ak39HN2mH8YW392dwldk2nssdJDN32KN32";
		String shortUrlSample = myService.createUrl(longUrlSample);
		myService.deleteUrl(shortUrlSample);
		assertEquals("Not Found", myService.getUrl(shortUrlSample));
	}
}
