package afterRefactor.domain;

import java.io.Serializable;

public final class Guest implements Serializable{
	private static final long serialVersionUID = 1L;
	private final String name;
	private final String contact;
	private final String gender;
	
	public Guest(String name, String contact, String gender) {
		super();
		this.name = name;
		this.contact = contact;
		this.gender = gender;
	}
	
	public String name() {return name;}
	public String contact() {return contact;}
	public String gender() {return gender;}
}
