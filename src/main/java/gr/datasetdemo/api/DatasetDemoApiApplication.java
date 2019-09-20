package gr.datasetdemo.api;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
			pstmt.setString(2, payload.getCreateDate());
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
	
	@GetMapping(value = "/datasets")
	public ResponseEntity<Object> getDatasets(@RequestParam(required=false,defaultValue="false") boolean loadPayloads){
				
		ArrayList<Dataset> datasetList = new ArrayList<Dataset>();
						
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/datasetdemo?serverTimezone=UTC", "apostolos", "8lfvl94fubBu");
			String sql = "SELECT * FROM Dataset";
			Statement stmt = conn.createStatement(); 
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				Dataset dataset = new Dataset();
				dataset.setId(rs.getInt("id"));
				dataset.setName(rs.getString("name"));
				dataset.setDescription(rs.getString("description"));
				if(loadPayloads) {
					String sqlPayloads = "SELECT * FROM Payload WHERE id="+rs.getInt("payloadId");
					Statement stmt2 = conn.createStatement(); 
					ResultSet rs2 = stmt2.executeQuery(sqlPayloads);
					Payload payload = new Payload();
					rs2.next();
		            payload.setId(rs2.getInt("id"));
	            	payload.setCreateDate(rs2.getString("createDate"));
	            	payload.setBlob(rs2.getBinaryStream("blobData").readAllBytes());
	            	dataset.setPayload(payload);
	            	//Close ResultSet
	            	rs2.close();
	            	stmt2.close();
				}
				datasetList.add(dataset);
			}
			//Close Connection
			rs.close();
			stmt.close();
			conn.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(datasetList, HttpStatus.OK);
	}
	
	@GetMapping(value = "/datasets", params = "id")
	public ResponseEntity<Object> getDatasetById(@RequestParam(required=true) int id,@RequestParam(required=false,defaultValue="false") boolean loadPayloads){
				
		ArrayList<Dataset> datasetList = new ArrayList<Dataset>();
						
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/datasetdemo?serverTimezone=UTC", "apostolos", "8lfvl94fubBu");
			String sql = "SELECT * FROM Dataset WHERE id="+id;
			Statement stmt = conn.createStatement(); 
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				Dataset dataset = new Dataset();
				dataset.setId(rs.getInt("id"));
				dataset.setName(rs.getString("name"));
				dataset.setDescription(rs.getString("description"));
				if(loadPayloads) {
					String sqlPayloads = "SELECT * FROM Payload WHERE id="+rs.getInt("payloadId");
					Statement stmt2 = conn.createStatement(); 
					ResultSet rs2 = stmt2.executeQuery(sqlPayloads);
					Payload payload = new Payload();
					rs2.next();
		            payload.setId(rs2.getInt("id"));
	            	payload.setCreateDate(rs2.getString("createDate"));
	            	payload.setBlob(rs2.getBinaryStream("blobData").readAllBytes());
	            	dataset.setPayload(payload);
	            	//Close ResultSet
	            	rs2.close();
	            	stmt2.close();
				}
				datasetList.add(dataset);
			}
			//Close Connection
			rs.close();
			stmt.close();
			conn.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(datasetList, HttpStatus.OK);
	}
	
	@GetMapping(value = "/datasets", params = "name")
	public ResponseEntity<Object> getDatasetById(@RequestParam(required=true) String name,@RequestParam(required=false,defaultValue="false") boolean loadPayloads){
				
		ArrayList<Dataset> datasetList = new ArrayList<Dataset>();
						
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/datasetdemo?serverTimezone=UTC", "apostolos", "8lfvl94fubBu");
			String sql = "SELECT * FROM Dataset WHERE name LIKE '"+name+"'";
			Statement stmt = conn.createStatement(); 
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				Dataset dataset = new Dataset();
				dataset.setId(rs.getInt("id"));
				dataset.setName(rs.getString("name"));
				dataset.setDescription(rs.getString("description"));
				if(loadPayloads) {
					String sqlPayloads = "SELECT * FROM Payload WHERE id="+rs.getInt("payloadId");
					Statement stmt2 = conn.createStatement(); 
					ResultSet rs2 = stmt2.executeQuery(sqlPayloads);
					Payload payload = new Payload();
					rs2.next();
		            payload.setId(rs2.getInt("id"));
	            	payload.setCreateDate(rs2.getString("createDate"));
	            	payload.setBlob(rs2.getBinaryStream("blobData").readAllBytes());
	            	dataset.setPayload(payload);
	            	//Close ResultSet
	            	rs2.close();
	            	stmt2.close();
				}
				datasetList.add(dataset);
			}
			//Close Connection
			rs.close();
			stmt.close();
			conn.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(datasetList, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/datasets", params = "id")
	public ResponseEntity<Object> deleteDatasetById (@RequestParam(required=true) int id){
		
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/datasetdemo?serverTimezone=UTC", "apostolos", "8lfvl94fubBu");

			String sql = "SELECT payloadId FROM Dataset WHERE id="+id;
			Statement stmt = conn.createStatement(); 
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			
			int payloadId = rs.getInt("payloadId");
			//Delete from Dataset first
			sql = "DELETE FROM Dataset WHERE id = ?";
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            pstmt.setInt(1, id);
            
            pstmt.executeUpdate();
            
            //Delete from Payload
            sql = "DELETE FROM Payload WHERE id = ?";
            
            pstmt = conn.prepareStatement(sql);
            
            pstmt.setInt(1, payloadId);
            
            pstmt.executeUpdate();
            
            //Close Connection
            rs.close();
            stmt.close();
            pstmt.close();
            conn.close();
            
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Dataset deleted successfully", HttpStatus.OK);
	}
}
