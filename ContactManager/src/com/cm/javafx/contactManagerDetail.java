package com.cm.javafx;

/**Written by Nayana Thomas for 6360.004, assignment 4, starting October 28, 2017.
NetID: nxt170630
*/

import java.util.Date;
import javafx.scene.layout.BorderPane;

/**
 * This class mainly used for all the contact related data
 */
public class contactManagerDetail extends BorderPane {
	private String fname;
	private String lname;
	private String mInit;
	private String gender;
	private Date bdate;
	private Date firstKnown;
	private String company;
	private String jobTitle;
	private String webSite;
	private String notes;
	private String mStatus;
	private int contactId;
	private int phone;

	public String getPhone() {
		return String.valueOf(phone);
	}

	public void setPhone(int phone) {
		this.phone = phone;
	}

	public int getContactId() {
		return contactId;
	}

	public void setContactId(int contactId) {
		this.contactId = contactId;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
		System.out.println("hi:" + lname);
	}

	public String getmInit() {
		return mInit;
	}

	public void setmInit(String mInit) {
		this.mInit = mInit;
		System.out.println(mInit);
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBdate() {
		return bdate;
	}

	public void setBdate(Date bdate) {
		this.bdate = bdate;
	}

	public Date getFirstKnown() {
		return firstKnown;
	}

	public void setFirstKnown(Date firstKnown) {
		this.firstKnown = firstKnown;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getmStatus() {
		return mStatus;
	}

	public void setmStatus(String mStatus) {
		this.mStatus = mStatus;
	}

	/**
	 * constructor
	 */
	public contactManagerDetail() {
		this.fname = "";
		this.lname = "";
		this.mInit = "";
		this.gender = "";
		this.bdate = new Date();
		this.mStatus = "";
		this.firstKnown = new Date();
		this.company = "";
		this.jobTitle = "";
		this.webSite = "";
		this.notes = "";
	}

	/**
	 * parameterized constructor
	 */
	public contactManagerDetail(Integer contactId, String fname, String lname, String minit, String gender, Date bdate,
			String mStatus, Date firstKnown, String company, String jobTitle, String webSite, String notes) {
		this.contactId = contactId;
		this.fname = fname;
		this.lname = lname;
		this.mInit = minit;
		this.gender = gender;
		this.bdate = bdate;
		this.mStatus = mStatus;
		this.firstKnown = firstKnown;
		this.company = company;
		this.jobTitle = jobTitle;
		this.webSite = webSite;
		this.notes = notes;
	}

}
