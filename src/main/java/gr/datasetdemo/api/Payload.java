package gr.datasetdemo.api;

public class Payload {

	private int id;
	private String creatDate;
	private byte[] blob;
	
	public Payload() {
	}

	public Payload(int id, String creatDate, byte[] blob) {
		this.id = id;
		this.creatDate = creatDate;
		this.blob = blob;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCreatDate() {
		return creatDate;
	}

	public void setCreatDate(String creatDate) {
		this.creatDate = creatDate;
	}

	public byte[] getBlob() {
		return blob;
	}

	public void setBlob(byte[] blob) {
		this.blob = blob;
	}
	
	
	
}
