package trainingmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import trainingmanagement.repository.UserRepository;

@SpringBootApplication
public class TrainingManagementApplication {
	public static void main(String[] args) {
		SpringApplication.run(TrainingManagementApplication.class, args);
	}
	
}
