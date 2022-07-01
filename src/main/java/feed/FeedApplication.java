package feed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import feed.daoimpl.UserDaoImpl;

@SpringBootApplication
public class FeedApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(FeedApplication.class, args);
		
		
	}

}
