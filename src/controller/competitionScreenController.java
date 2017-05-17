package controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.animation.PathTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class competitionScreenController implements Initializable {
	String gameCode;
	String gameType;
	boolean isDBConnected;
	Connection conn;
	String currDate;
	String result="";
	ParticipantsInfromation official;
	ArrayList<ParticipantsInfromation> athletes;
	@FXML TextArea gameInfo = new TextArea();


	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
	}
	
	public void setGameRequirements(String gameCode, boolean isDBConnected, Connection conn,ParticipantsInfromation official, ArrayList<ParticipantsInfromation> athletes,String gameType ){
		this.gameCode =gameCode;
		this.isDBConnected =isDBConnected;
		this.conn =conn;
		this.official = official;
		this.athletes = athletes;
		this.gameType = gameType;
	}

    public Pane runTheGame(Pane pane){
    	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Date date = new Date();
        currDate = dateFormat.format(date);
        result="Game code: "+gameCode+"\n"+"Official ID: "+official.getID()+"\n"+"Date: "+currDate+"\n\n";
        result = result+"  ID  "+"    "+"Time"+"    "+"Score"+"\n";
    
        for(ParticipantsInfromation athlete :athletes){
            int time = compete();
            ((AthletesInformation)athlete).setTime(time);
        }
        
        Collections.sort(athletes, new Comparator<ParticipantsInfromation>() {
            @Override public int compare(ParticipantsInfromation athlete1, ParticipantsInfromation athlete2) {
                return (athlete1.getTime() - athlete2.getTime()); 
            }
        });
        
        gameInfo.setText(result);
        setScores();
                
        if(isDBConnected)
        	SaveResultToDB();
        else
        	SaveResultToTextFile();
        return animation(pane);
    }
	public Pane animation(Pane pane){

		int animeDuration = 0;
		 if(gameType == "swimmer")
			 animeDuration = 30;
	        else if(gameType == "sprinter")
	        	animeDuration = 200;
	        else
	        	animeDuration = 10;
		int y = 50;

		for(int i=0; i<athletes.size();i++){

			final int index = i;
			Rectangle rectangle = new Rectangle(0, 0, 25, 30);
			rectangle.setFill(Color.ORANGE);
			Line line = new Line(10, y, 400, y);
			pane.getChildren().add(rectangle);
			pane.getChildren().add(line);
			PathTransition path = new PathTransition();
			path.setDuration(Duration.millis(animeDuration*athletes.get(i).getTime()));
			path.setOnFinished(e-> message(index));
			path.setPath(line);
			path.setNode(rectangle);
			y = y+50;
			path.play();			
			
		}

		return pane;
	}
	public int compete(){
        Random random = new Random();
        if(gameType == "swimmer")
        	return random.nextInt(200-100+1)+100;// *30
        else if(gameType == "sprinter")
        	return random.nextInt(20-10+1)+10;// *200
        else
        	return random.nextInt(800-500+1)+500;// *10
    }
    public void setScores(){ 
        
        ((AthletesInformation)athletes.get(0)).setScore(5);
        ((AthletesInformation)athletes.get(1)).setScore(2);
        ((AthletesInformation)athletes.get(2)).setScore(1);
    }
    public void SaveResultToTextFile(){
    	String result = gameCode+", "+official.getID()+", "+currDate+"\n";
    	 for(int i =0; i<athletes.size();i++){
         	int score = 0;
         	if(i==0)
         		score = 5;
         	else if(i==1)
         		score =2;
         	else if (i==2)
         		score = 1;
         	else
         		score = 0;
             result = result +athletes.get(i).getID()+",    "+athletes.get(i).getTime()+",    "+score+"\n";
         }
    	result=result+"\n\n";
    	File fileName = new File("gameResults.txt");
        try {
        	BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
        	writer.write(result);
        	writer.close();
    }catch(IOException e){
       
    }
    }
    public void SaveResultToDB(){
		try {
			String athletesResult="";
	    	 for(int i =0; i<3;i++){
	          	int score = 0;
	          	if(i==0)
	          		score = 5;
	          	else if(i==1)
	          		score =2;
	          	else if (i==2)
	          		score = 1;
	          	else
	          		score = 0;
	          	athletesResult = athletesResult +athletes.get(i).getID()+","+athletes.get(i).getTime()+","+score+",";
	          }
			PreparedStatement preState  = conn.prepareStatement("insert into gameResults(gameID, officialID, date, athletesResults) values(?,?,?,?)");
			preState.setString(1, gameCode);
			preState.setString(2, official.getID());
			preState.setString(3, currDate);
			preState.setString(4, athletesResult);
			preState.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
    	
    }
    public void message(int index){
    	int score;
					if(index == 0)
			    		score =5;
			    	else if(index == 1)
			    		score =2;
			    	else if(index == 2)
			    		score =1;
			    	else
			    		score =0;

    	result = result +athletes.get(index).getID()+",    "+athletes.get(index).getTime()+",      "+score+"\n";
			gameInfo.setText(result);
    }


}
