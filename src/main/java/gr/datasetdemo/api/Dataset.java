package gr.datasetdemo.api;

public class Dataset {
	
	private int id;
	private String name;
	private String description;
	private Payload payload;
	
	public Dataset() {
	}
	
	public Dataset(int id, String name, String description, Payload payload) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.payload = payload;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Payload getPayload() {
		return payload;
	}

	public void setPayload(Payload payload) {
		this.payload = payload;
	}
	
}
