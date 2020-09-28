package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import songObject.song;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.Scanner;

public class ListController {

	@FXML         
	ListView<String> listView;
	@FXML
	Button add;
	@FXML
	Button delete;
	@FXML
	Button edit;
	@FXML
	TextField addTitle;
	@FXML
	TextField addArtist;
	@FXML
	TextField addAlbum;
	@FXML
	TextField addYear;
	@FXML
	TextField curTitle;
	@FXML
	TextField curArtist;
	@FXML
	TextField curAlbum;
	@FXML
	TextField curYear;

	private ObservableList<String> obsList;              

	public void start(Stage mainStage) {                
		// create an ObservableList 
		// from an ArrayList  
		obsList = FXCollections.observableArrayList(                               
				"Giants",                               
				"Patriots",
				"49ers",
				"Rams",
				"Packers",
				"Colts",
				"Cowboys",
				"Broncos",
				"Vikings",
				"Dolphins",
				"Titans",
				"Seahawks",
				"Steelers",
				"Jaguars"); 

		listView.setItems(obsList); 

		// select the first item
		listView.getSelectionModel().select(0);

		// set listener for the items
		listView
		.getSelectionModel()
		.selectedIndexProperty()
		.addListener(
				(obs, oldVal, newVal) -> 
				showItemInputDialog(mainStage));

	}
	
	private void showItem(Stage mainStage) {                
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.initOwner(mainStage);
		alert.setTitle("List Item");
		alert.setHeaderText(
				"Selected list item properties");

		String content = "Index: " + 
				listView.getSelectionModel()
		.getSelectedIndex() + 
		"\nValue: " + 
		listView.getSelectionModel()
		.getSelectedItem();

		alert.setContentText(content);
		alert.showAndWait();
	}
	
	private void showItemInputDialog(Stage mainStage) {                
		String item = listView.getSelectionModel().getSelectedItem();
		int index = listView.getSelectionModel().getSelectedIndex();

		TextInputDialog dialog = new TextInputDialog(item);
		dialog.initOwner(mainStage); dialog.setTitle("List Item");
		dialog.setHeaderText("Selected Item (Index: " + index + ")");
		dialog.setContentText("Enter name: ");

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) { obsList.set(index, result.get()); }
	}
	private ArrayList<song> readFile(String file) {
		ArrayList<song> songList=new ArrayList<song>();
		Path f=Paths.get(file);
		Scanner s = null;
		try {
			s = new Scanner(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		s.useDelimiter(":|\\n"); //Use : and new line character as delimiter
		while(s.hasNext()) {
			String title=s.next();
			String artist=s.next();
			String album=s.next();
			String year=s.next();
			song temp=new song(title,artist,album,year);
			songList.add(temp);
			s.next(); //Should be new line character if formatted right;
		}
		s.close();
		Collections.sort(songList);
		return songList;
	}
	/* Takes in ArrayList of type song
	 * Will OVERWRITE any file with same name with new song list, this case "src/songs.txt"
	 * Will write in format <title>:<artist>:<album>:<year><newLineChar> to new text file
	 * Returns nothing
	 */
	private void writeFile(ArrayList<song> songs) {
		PrintWriter writer;
	  	try {
	  			File file = new File ("src/songs.txt");
	  			file.createNewFile();
	  			writer = new PrintWriter(file);
				for(song s: songs)
		    	  {
		    		  writer.print(s.getTitle()+":");
		    		  writer.print(s.getArtist()+":");
		    		  writer.print(s.getAlbum()+":");
		    		  writer.print(s.getYear());
		    		  
		    	  }
		    	 writer.close(); 
	} catch (Exception e) {
		e.printStackTrace();
		}  
	}
}
