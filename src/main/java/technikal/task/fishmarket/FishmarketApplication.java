package technikal.task.fishmarket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import technikal.task.fishmarket.models.Fish;
import technikal.task.fishmarket.services.FishRepository;

import java.sql.Timestamp;
import java.util.Date;

@SpringBootApplication
public class FishmarketApplication {

	public static void main(String[] args) {
		SpringApplication.run(FishmarketApplication.class, args);
	}

}
