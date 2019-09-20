package gr.datasetdemo.api;

public class Payload {

	private int id;
	private String createDate;
	private byte[] blob;
	
	public Payload() {
	}

	public Payload(int id, String createDate, byte[] blob) {
		this.id = id;
		this.createDate = createDate;
		this.blob = blob;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String creatDate) {
		this.createDate = creatDate;
	}

	public byte[] getBlob() {
		return blob;
	}

	public void setBlob(byte[] blob) {
		this.blob = blob;
	}
	
	
	
}
