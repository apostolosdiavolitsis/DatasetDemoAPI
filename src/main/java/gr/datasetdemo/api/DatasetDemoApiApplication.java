package gr.datasetdemo.api;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	@PostMapping(value = "/datasets")
	public ResponseEntity<Object> createDataset(@RequestBody Dataset dataset){
		
		
		Connection conn = null;
		try {
			//Get Connection
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/datasetdemo?serverTimezone=UTC", "apostolos", "8lfvl94fubBu");
						
			//Add Payload to the database
			Payload payload = dataset.getPayload();
			
			//Set blob to a test value as i was not able to send it via JSON
			payload.setBlob("Test".getBytes());

			String sql = "INSERT INTO Payload VALUES (?,?,?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, payload.getId());
			pstmt.setString(2, payload.getCreatDate());
			pstmt.setBinaryStream(3, new ByteArrayInputStream(payload.getBlob()));
			pstmt.executeUpdate();
			
			//Add Dataset to the database
			sql = "INSERT INTO Dataset VALUES (?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dataset.getId());
			pstmt.setString(2, dataset.getName());
			pstmt.setString(3, dataset.getDescription());
			pstmt.setInt(4, payload.getId());
			pstmt.executeUpdate();
			
			//Close connection
			conn.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} 
		
		return new ResponseEntity<>("Dataset created successfully", HttpStatus.CREATED);
	}
	
	
	
}
