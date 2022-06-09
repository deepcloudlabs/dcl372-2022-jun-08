package com.example.lottery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// 1. @Primary
// 2  @Qualifier
// 3. Meta-annotation -> @Qualifier -> Enum
// 4. Injection all implementations into List<> or Map<>
// 5. @ConditionalOnXYZ -> @ConditionalOnProperty, ...
// 6. http://binkurt.blogspot.com/2013/10/spring-catsna-kendi-injector-snfmz.html
// 7. @Profile({"test", "dev"})

// -Dspring.profiles.active=dev
// -Dspring.config.location=classpath:uygulama.properties
// -Dspring.config.location=file:c:/tmp/dosya.properties
// mvn clean install spring-boot:repackage
// -Dmaven.test.skip=true
// start java -Dserver.port=8200 -Dspring.profiles.active=dev -jar lottery-0.0.1.jar
@SpringBootApplication
public class LotteryApplication {

	public static void main(String[] args) {
		SpringApplication.run(LotteryApplication.class, args);
	}

}
