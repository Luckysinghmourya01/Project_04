package in.co.rays.proj4.bean;

public class CartBean extends BaseBean{

	private String cartCode;
	private String userName;
	private Integer totalItem;
	private String status;
	
	
	public String getCartCode() {
		return cartCode;
	}

	public void setCartCode(String cartCode) {
		this.cartCode = cartCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getTotalItem() {
		return totalItem;
	}

	public void setTotalItem(Integer totalItem) {
		this.totalItem = totalItem;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String getKey() {
		
		return id + "";
	}

	@Override
	public String getValue() {
		
		return status;
	}

}
