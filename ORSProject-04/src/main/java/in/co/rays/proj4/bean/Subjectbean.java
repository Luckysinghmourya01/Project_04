package in.co.rays.proj4.bean;

public class Subjectbean extends BaseBean {

	private String name;
	private long courseId;
	private String CourseName;
	private String description;

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getCourseId() {
		return courseId;
	}

	public void setCourseId(long courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return CourseName;
	}

	public void setCourseName(String courseName) {
		CourseName = courseName;
	}

	@Override
	public String getKey() {
		
		return id + "";
	}

	@Override
	public String getValue() {
		
		return name;
	}

}
