package in.co.rays.proj4.bean;

import java.util.Date;

public class StudentBean extends BaseBean {

	private String firstName;
	private String lastName;
	private Date dob;
	private String gender;
	private String mobileNo;
	private String email;
	private Long collageId;
	private String collageName;
	
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getCollageId() {
		return collageId;
	}
	public void setCollageId(Long collageId) {
		this.collageId = collageId;
	}
	public String getCollageName() {
		return collageName;
	}
	public void setCollageName(String collageName) {
		this.collageName = collageName;
	}
	@Override
	public String getKey() {
		
		return id + "";
	}
	@Override
	public String getValue() {
		
		return firstName + "" + lastName;
	}
	
	
	
}
