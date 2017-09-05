package codenet.bean;

public class DatasetBean {

	private int id;
	private String name;
	private String filename;
	private String shortdetail;
	private String detail;
	private String contributor;
	private String reference;
	private int dowloads;
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
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getContributor() {
		return contributor;
	}
	public void setContributor(String contributor) {
		this.contributor = contributor;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public int getDowloads() {
		return dowloads;
	}
	public void setDowloads(int dowloads) {
		this.dowloads = dowloads;
	}
	
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public String getShortdetail() {
		return shortdetail;
	}
	public void setShortdetail(String shortdetail) {
		this.shortdetail = shortdetail;
	}
	@Override
	public boolean equals(Object obj) {
		return ((DatasetBean)obj).id==id;
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id=").append(id).append('\n');
		sb.append("name=").append(name).append('\n');
		sb.append("detail=").append(detail).append('\n');
		sb.append("contributor=").append(contributor).append('\n');
		sb.append("reference=").append(reference).append('\n');
		sb.append("dowloads=").append(dowloads).append('\n');
		return sb.toString();
	}
	
	
	
}
