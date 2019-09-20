package gr.datasetdemo.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DatasetDemoApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DatasetDemoApiApplication.class, args);
	}

	@RequestMapping(value = "/")
	public String info() {
		return "This is a demo REST API for saving datasets and payloads at a MySQL database";
	}
}
