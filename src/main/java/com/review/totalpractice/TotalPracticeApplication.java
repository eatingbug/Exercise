package com.review.totalpractice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // @Component, @Configuration 가 붙은 클래스 검색 후 Spring bean 으로 등록
public class TotalPracticeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TotalPracticeApplication.class, args); // Spring 애플리케이션을 부트스트랩하고, 실행
	}

}
