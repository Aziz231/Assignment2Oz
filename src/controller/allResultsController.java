package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

public class allResultsController implements Initializable  {
	@FXML ListView<String> resultList = new ListView<String>();
	Connection conn;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	    
		
	}
	
	public void setResluts(boolean isDBConnected, Connection conn){
		this.conn = conn;
		if(isDBConnected)
			getResultsFromDB();	
		else
			getResultsFromTxtFile();	
	}

	private void getResultsFromTxtFile() {
		File fileName = new File("gameResults.txt");
	    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
	        String text;
	        resultList.getItems().clear();
	        while((text = reader.readLine()) != null){
	        	String[] lineArray = text.split(",");
	        	lineArray = removeSpaceFromInformation(lineArray);
	        	if(lineArray.length == 3){
	        		if(lineArray.length == 3 && !lineArray[2].equals("0"))
	        			resultList.getItems().add(text);
	        	}else{
	        		resultList.getItems().add(text);
	        	}	
	        }    	
	} catch(IOException e){
		 resultList.getItems().clear();
		 resultList.getItems().add(("Cannot find “gameResults.txt” on the local project folder"));
	}  
	}	
		
	private void getResultsFromDB() {
		try {
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery("Select * from gameResults;");
			while(result.next()){
				String row = result.getString("gameID")+","+result.getString("officialID")+","+result.getString("date")+","+result.getString("athletesResults");
				String[]  information = row.split(",");
				for(int i = 0 ; i<information.length;i=i+3)
					resultList.getItems().add(information[i]+",    "+information[i+1]+",    "+information[i+2]);
				resultList.getItems().add("\n\n");
			}
			
		} catch (SQLException e) {
			 resultList.getItems().clear();
			 resultList.getItems().add(("Cannot import data from the database"));
		}
	}
	
	private String[] removeSpaceFromInformation(String[] information){
		for(int i =0; i< information.length;i++){
			information[i] = information[i].replaceAll("\\s", "");
		}
		return information;
	}
	 

}