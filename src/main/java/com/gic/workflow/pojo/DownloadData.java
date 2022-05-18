package com.gic.workflow.pojo;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DownloadData {

	@JsonProperty("DOJ")
	public String dOJ;
	@JsonProperty("Crn_Number")
	public String crn_Number;
	@JsonProperty("Excel_Name")
	public String excel_Name;
	@JsonProperty("Request_ID")
	public String request_ID;
	@JsonProperty("Associate_Id")
	public String associate_Id;
	@JsonProperty("Candidate_ID")
	public String candidate_ID;
	@JsonProperty("Candidate_Name")
	public String candidate_Name;
}
