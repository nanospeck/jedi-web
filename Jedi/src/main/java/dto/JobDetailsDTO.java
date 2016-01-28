package dto;

public class JobDetailsDTO {

	String id = null;
	String description = null;
	String link = null;
	
	
	public JobDetailsDTO(String id, String description, String link) {
		super();
		this.id = id;
		this.description = description;
		this.link = link;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	
	
}
