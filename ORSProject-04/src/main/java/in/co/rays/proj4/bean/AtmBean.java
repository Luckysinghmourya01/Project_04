package in.co.rays.proj4.bean;

import java.util.Date;

public class AtmBean extends BaseBean {

	private long id;
	private String location;
	private int cashAailable;
	private Date dob;
	private String remark;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	
	

	public int getCashAailable() {
		return cashAailable;
	}

	public void setCashAailable(int cashAailable) {
		this.cashAailable = cashAailable;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}

}
