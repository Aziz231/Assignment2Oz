package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

public class mainScreenController implements Initializable {
	int gameCode=0;
	String fullGameCode = "";
	String gameSelected="swimmer";
	boolean isDBConnected = false;
	Connection conn;
@FXML Button btnCreateNewGame = new Button();
@FXML Button btnDisplayAllGames = new Button();
@FXML Button btnDisplayAtheletesPoints = new Button();
@FXML Button btnReload = new Button();
@FXML RadioButton radSwimming = new RadioButton();
@FXML RadioButton radRunning = new RadioButton();
@FXML RadioButton radCycling = new RadioButton();
@FXML ToggleGroup group = new ToggleGroup();
@FXML Label lblMessage = new Label();
@FXML TableView<ParticipantsInfromation> table;
@FXML TableColumn<ParticipantsInfromation, String> ID;
@FXML TableColumn<ParticipantsInfromation, String> type;
@FXML TableColumn<ParticipantsInfromation, String> name;
@FXML TableColumn<ParticipantsInfromation, String> state;
@FXML TableColumn<ParticipantsInfromation, Integer> age;
@FXML TableColumn<ParticipantsInfromation,Boolean> checked;
ObservableList<ParticipantsInfromation> list = FXCollections.observableArrayList();

@Override
public void initialize(URL location, ResourceBundle resources) {
	ID.setCellValueFactory(new PropertyValueFactory<ParticipantsInfromation, String>("ID"));
	type.setCellValueFactory(new PropertyValueFactory<ParticipantsInfromation, String>("type"));
	name.setCellValueFactory(new PropertyValueFactory<ParticipantsInfromation, String>("name"));
	state.setCellValueFactory(new PropertyValueFactory<ParticipantsInfromation, String>("state"));
	age.setCellValueFactory(new PropertyValueFactory<ParticipantsInfromation, Integer>("age"));
	checked.setCellValueFactory(
			new Callback<CellDataFeatures<ParticipantsInfromation,Boolean>,ObservableValue<Boolean>>()
			{
			    @Override
			    public ObservableValue<Boolean> call(CellDataFeatures<ParticipantsInfromation, Boolean> value)
			    {  
			        return value.getValue().checkedProperty();
			    }   
			});
	checked.setCellFactory(column -> new CheckBoxTableCell<ParticipantsInfromation, Boolean>()); 

	radSwimming.setToggleGroup(group);
	radRunning.setToggleGroup(group);
	radCycling.setToggleGroup(group);
	radSwimming.setUserData("swimmer");
	radRunning.setUserData("sprinter");
	radCycling.setUserData("cyclist");

	
    group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
        public void changed(ObservableValue<? extends Toggle> ov,
            Toggle old_toggle, Toggle new_toggle) {
        	gameSelected = group.getSelectedToggle().getUserData().toString();
        	loadAthletes ();
        }
      });
    
	SQLiteModel sqliteModel = new SQLiteModel();
	conn = sqliteModel.getConn();
	if(conn == null){
		isDBConnected = false;
		displayMessage("Cann't find a database connection");	
	}else
		isDBConnected = sqliteModel.isDBConnected();

	if(isDBConnected){
		loadDataFromDB();
	}else{
		loadDataFromText();
	}
}
private void loadDataFromDB(){
try {
	getMaxCode();
	Statement statement = conn.createStatement();
	ResultSet result = statement.executeQuery("Select * from participantsInformation;");
	while(result.next()){
		String row = result.getString("ID")+","+result.getString("Type")+","+result.getString("name")+","+
				result.getInt("Age")+","+result.getString("State");
		prepareDataForTable(row);
	}
	
} catch (SQLException e) {
	displayMessage("Cann't read data from the database");
	loadDataFromText();
}
loadAthletes ();
}
private void loadDataFromText(){
    File fileName = new File("participants.txt");
    displayMessage("The data has been imported from “participants.txt” ");
    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
        String line;
        while((line = reader.readLine()) != null){
        	prepareDataForTable(line);
        }   
        
} catch(IOException e){
	displayMessage("Cannot find a database connection nor “participants.txt” on the local file system");
}
    loadAthletes ();
}
public void createGame() throws GameFullException, NoRefereeException, TooFewAthleteException, WrongTypeException{	
	ParticipantsInfromation official = new OficialsInformation();
	boolean isOfficialSelected = false;
	ArrayList<ParticipantsInfromation> athletesInGame = new ArrayList<ParticipantsInfromation>();
	for(ParticipantsInfromation participant:list){
		if(participant.isChecked()){
			if(participant.getType().equals("officer") && isOfficialSelected == false ){
				official = participant;
				isOfficialSelected = true;
			}else if(participant.getType().equals("officer") && isOfficialSelected == true)
				throw new WrongTypeException("Wrong Type Found: you can  assign only one referee to start a game");
			else if(participant.getType().equals(gameSelected) || participant.getType().equals("super") ){
				athletesInGame.add(participant);
			}	
		}
	}

	if(athletesInGame.size() < 4)
		throw new TooFewAthleteException("Too Few Athletes Found: the game can't have less than 4 athletes");
	else if(athletesInGame.size() > 8)
		throw new GameFullException("Game Full Found: the game can't have more than 8 athletes");
	else if(isOfficialSelected == false)
		throw new NoRefereeException("No Referee Found: you are trying to start a game without selecting a referee");
	gameCode++;
	gameCodeIncrement(gameSelected);
	try {
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/application/view_competitionScreen.fxml").openStream());
		competitionScreenController compScreen = (competitionScreenController)loader.getController(); 
		compScreen.setGameRequirements(fullGameCode, isDBConnected, conn, official, athletesInGame,gameSelected);
		root = compScreen.runTheGame(root);

		Scene scene = new Scene(root);
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Competition Screen");
		primaryStage.show();
	} catch(Exception e) {
		displayMessage(e.getMessage());
	}

}
public void actionCreateGame(ActionEvent event){
	
		try {
			createGame();
		} catch (GameFullException e){
			displayMessage(e.getMessage());
		}catch(NoRefereeException e){
			displayMessage(e.getMessage());
		}catch(TooFewAthleteException e){
			displayMessage(e.getMessage());
		}catch(WrongTypeException e){
			displayMessage(e.getMessage());
		}
	
}
private void gameCodeIncrement(String sport){
    switch (sport) {
        case "swimmer":
            fullGameCode = "S0"+gameCode;
            break;
        case "sprinter":
            fullGameCode = "R0"+gameCode;
            break;
        default:
            fullGameCode = "C0"+gameCode;
            break;
    }
    
}
public void displayAthletesPoints(ActionEvent event){
	try {
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/application/view_PointsScreen.fxml").openStream());
		athletesPointsController pointsScreen = (athletesPointsController)loader.getController(); 
		pointsScreen.setTable(list);
		Scene scene = new Scene(root);
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Competition Screen");
		primaryStage.show();
	} catch(Exception e) {
		displayMessage(e.getMessage());
	}
    
}
public void displayResults(ActionEvent event){
	try {
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/application/view_ResultScreen.fxml").openStream());
		allResultsController resultsScreen = (allResultsController)loader.getController(); 

		resultsScreen.setResluts(isDBConnected, conn);
		Scene scene = new Scene(root);
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Competition Screen");
		primaryStage.show();
	} catch(Exception e) {
		displayMessage(e.getMessage());
	}
}
public void displayMessage(String str){
	
	lblMessage.setText(str);
	Timeline timeline = new Timeline(
		    new KeyFrame(Duration.seconds(0.5), e -> {
		    	lblMessage.setVisible(false);
		    }),

		    new KeyFrame(Duration.seconds(1.0), e -> {
		    	lblMessage.setVisible(true);
		    })
			
			);
	timeline.setCycleCount(10);
	timeline.setOnFinished(e -> lblMessage.setText(""));
	timeline.play();
}
public void prepareDataForTable(String row){
	String[]  information = row.split(",");
    if(!Arrays.asList(information).contains("")){
  	  information = removeSpaceFromInformation(information);
  	  if(information[1].equals("officer")){
  		  list.add(new OficialsInformation(information[0],information[1],information[2],(Integer.parseInt(information[3])),information[4]));
  	  }else{
  		  list.add(new AthletesInformation(information[0],information[1],information[2],(Integer.parseInt(information[3])),information[4]));
  	  }
  	  
    }
}
private String[] removeSpaceFromInformation(String[] information){
	for(int i =0; i< information.length;i++){
		information[i] = information[i].replaceAll("\\s", "");
	}
	
	return information;
}
private void getMaxCode() {
	if(isDBConnected){
	Statement statement;
	try {
		statement = conn.createStatement();
		ResultSet result = statement.executeQuery("select MAX(SN) from gameResults;");
		int maxCode = result.getInt(1);
		if(maxCode != 0)
			 gameCode = maxCode;

	} catch (SQLException e) {
		displayMessage(e.getMessage());
	}
	}else{
		String max="";
		try {
			List<String> fileList = Files.readAllLines(Paths.get("gameResults.txt"));
			for(int i=0;i<fileList.size();i=i+6){
				String line32 = fileList.get(i);
				String[] lineInfo =  line32.split(",");
				max = lineInfo[0];
			}
			gameCode = Integer.parseInt(max.substring(1));
		} catch (IOException e) {
			displayMessage(e.getMessage());
		}
		
		
	}
}
private void loadAthletes (){
	ObservableList<ParticipantsInfromation> tableList = FXCollections.observableArrayList();
	for(ParticipantsInfromation participant:list){
		if(participant.getType().equals(gameSelected) || participant.getType().equals("officer") 
				|| participant.getType().equals("super")){
			tableList.add(participant);
		}
	}
	table.setItems(tableList);
}

}


