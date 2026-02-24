package in.co.rays.proj4.bean;

public class TicketBean extends BaseBean {

	private long id;
	private String ticketCode;
	private String tittle;
	private String assigendAgent;
	private String ticketStatus;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTicketCode() {
		return ticketCode;
	}

	public void setTicketCode(String ticketCode) {
		this.ticketCode = ticketCode;
	}

	public String getTittle() {
		return tittle;
	}

	public void setTittle(String tittle) {
		this.tittle = tittle;
	}

	public String getAssigendAgent() {
		return assigendAgent;
	}

	public void setAssigendAgent(String assigendAgent) {
		this.assigendAgent = assigendAgent;
	}

	public String getTicketStatus() {
		return ticketStatus;
	}

	public void setTicketStatus(String ticketStatus) {
		this.ticketStatus = ticketStatus;
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
