package com.gic.workflow;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;


public class Database {

	private static final Logger logger = LoggerFactory.getLogger(Database.class);
	static Connection connection;
	static String schema;
	static String database;
	static String user;
	static String password;
	static String host;
	static boolean ssl= false;

	static void Initialize(String database, String user, String password, boolean ssl, String host,String schema) {
		Database.database = database;
		Database.schema = schema;
		Database.user = user;
		Database.password = password;
		Database.host = host;
		Database.ssl = ssl;
		String url = "jdbc:postgresql://" + host + "/" + database + "?user=" + user + "&password=" + password + "&ssl="
				+ ssl;
		logger.info("url:" + url);
		connection = null;
		try {
			connection = DriverManager.getConnection(url);
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			System.exit(-1);
		}
	}

	static Connection getConnection() throws SQLException {
		try {
			if (connection == null || connection.isClosed()) {
				Initialize(database, user, password, ssl, host,schema);
			}

		} catch (SQLException e) {
			e.printStackTrace();

		}
		return connection;
	}
	
	public static String getresponseJson(String candidateid) {
		String responsejson = "";
		try {
		String sql1= "Select response_json from tbl_response_detail where Candidate_id = candidateid and service_id ='freshcase' order by id desc;";
			Statement stmt = getConnection().createStatement();
			ResultSet rs = stmt.executeQuery(sql1);
			while (rs.next()) { // max 1
				responsejson = rs.getString("response_json");
				System.out.println(rs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(responsejson);
		return responsejson;
		
	}

}
