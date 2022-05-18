package com.gic.workflow;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
//@Configuration(proxyBeanMethods = false)
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration. class})
public class WorkflowApplication  {  //implements CommandLineRunner
	@Value("${database}")
	static String database;
	@Value("${dbUserName}")
	static String dbUserName;
	@Value("${password}")
	static String password;
	@Value("${enableSSL}")
	static String enableSSL = "false";
	@Value("${host}")
	static String host;
	@Value("${schema}")
	static String schema;
//	static Map<String, Long> request1 = new HashMap<String, Long>();

	static ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	private static final Logger logger = LoggerFactory.getLogger(WorkflowApplication.class);

	/*
	 * @Override public void run(String... args) throws Exception { // TODO
	 * Auto-generated method stub Database.Initialize(database, dbUserName,
	 * password, Boolean.getBoolean(enableSSL), host); // request1 =
	 * Database.getresponseJson();
	 * 
	 * // logger.info("activityWorkerMap {}", request1); int delay = 0; boolean more
	 * = true; while (more) { if (processNextBatch()) { delay = 0; } else { delay =
	 * delay + 10; if (delay > 60) { delay = 10; } } more = pause(delay); }
	 * 
	 * }
	 * 
	 * private static boolean pause(int factor) { try { logger.info("sleeping for "
	 * + factor + " seconds"); TimeUnit.SECONDS.sleep(factor); } catch
	 * (InterruptedException e) { logger.error("Interrupted"); return false; }
	 * return true; }
	 */

	public static void main(String[] args) throws JsonMappingException, JsonProcessingException {
		SpringApplication.run(WorkflowApplication.class, args);
		Database.Initialize(database, dbUserName, password, Boolean.getBoolean(enableSSL), host, schema);
		System.out.println(database);
		System.out.println(dbUserName);
		String requestjson = "{\n" + "\"requestJson\":{\n" + "\"data\":[\n" + "{\n"
				+ "\"taskId\":\"ac66ef60-9f9a-42d1-a242-be5b6841ce84\",\n" + "\"taskName\":\"downloadCaseDataCTS\",\n"
				+ "\"taskSpecs\":{\n" + "\"downloadData\":{\n" + "\"DOJ\":\"03/05/2022 00:00:00\",\n"
				+ "\"Crn_Number\":\"\",\n" + "\"Excel_Name\":\"Report20220509134609\",\n"
				+ "\"Request_ID\":\"1604545\",\n" + "\"Associate_Id\":\"2164691\",\n"
				+ "\"Candidate_ID\":\"19799872\",\n" + "\"Candidate_Name\":\"Manas Ranjan Sikha\"\n" + "}\n" + "},\n"
				+ "\"taskSerialNo\":1\n" + "}\n" + "],\n" + "\"metadata\":{\n" + "\"task\":\"FreshCase\",\n"
				+ "\"attempt\":\"1\",\n" + "\"stageId\":\"468fdd68-72a3-4566-8f65-7cbfcb84c34d\",\n"
				+ "\"version\":\"1.0.0\",\n" + "\"taskDesc\":\"Download Data from Customer Portal\",\n"
				+ "\"multiTask\":\"no\",\n" + "\"processId\":\"06ac76d5-0f91-45ed-852e-310f34b10686\",\n"
				+ "\"requestId\":\"50e78094-5759-453c-b25e-29434a8a0add\",\n"
				+ "\"processName\":\"CSPi Touchless App\",\n" + "\"referenceId\":\"52392814\",\n"
				+ "\"requestDate\":\"Mon May 09 09:19:24 UTC 2022\",\n" + "\"requestType\":\"query\",\n"
				+ "\"taskGroupId\":\"22f355cf-dc1f-4b00-8f62-25fba00525b6\",\n" + "\"requestAuthToken\":\"jwt.token\"\n"
				+ "}\n" + "},\n" + "\"requestProcessorId\":201359\n" + "}";

		ObjectNode inputJson = null;
		try {
			inputJson = (ObjectNode) mapper.readTree(requestjson);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info(inputJson.toString());

		String candidateid = inputJson.get("requestJson").get("data").get(0).get("taskSpecs").get("downloadData")
				.get("Candidate_ID").asText();

		System.out.println(candidateid);
		logger.info(candidateid);
		String str = Database.getresponseJson("candidateid");
		JsonNode jsonObj = null;
		try {
			jsonObj = mapper.readTree(str);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info(jsonObj.asText());

	}

}