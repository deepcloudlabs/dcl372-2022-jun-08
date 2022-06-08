package com.example.lottery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// 1. @Primary
// 2  @Qualifier
// 3. Meta-annotation -> @Qualifier -> Enum
// 4. Injection all implementations into List<> or Map<>
// 5. @ConditionalOnXYZ -> @ConditionalOnProperty, ...
// 6. http://binkurt.blogspot.com/2013/10/spring-catsna-kendi-injector-snfmz.html
@SpringBootApplication
public class LotteryApplication {

	public static void main(String[] args) {
		SpringApplication.run(LotteryApplication.class, args);
	}

}
