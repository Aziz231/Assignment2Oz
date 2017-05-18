package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class athletesPointsController implements Initializable {
	@FXML
	TableView<ParticipantsInfromation> table;
	@FXML
	TableColumn<ParticipantsInfromation, String> ID;
	@FXML
	TableColumn<ParticipantsInfromation, String> name;
	@FXML
	TableColumn<ParticipantsInfromation, String> type;
	@FXML
	TableColumn<ParticipantsInfromation, Integer> score;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ID.setCellValueFactory(new PropertyValueFactory<ParticipantsInfromation, String>("ID"));
		name.setCellValueFactory(new PropertyValueFactory<ParticipantsInfromation, String>("name"));
		type.setCellValueFactory(new PropertyValueFactory<ParticipantsInfromation, String>("type"));
		score.setCellValueFactory(new PropertyValueFactory<ParticipantsInfromation, Integer>("score"));
	}

	public void setTable(ObservableList<ParticipantsInfromation> list) {
		table.setItems(list);
		table.getSortOrder().add(score);
	}

}
