package in.co.rays.proj4.bean;

import java.util.Date;

public class InventoryBean extends BaseBean {

	private long id;
	private String supplierName;
	private Date dob;
	private long quantity;
	private String product;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	@Override
	public String getKey() {

		return id + "";
	}

	@Override
	public String getValue() {

		return supplierName;
	}
}
