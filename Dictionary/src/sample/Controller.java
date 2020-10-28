package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.scene.control.Button;

import com.gtranslate.Audio;                                                   
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public class Controller extends Dictionary {

  @FXML private TextField Text;

  @FXML protected Button Xoa;

  @FXML protected Button Them;

  //Delete word
  public void pressXoa(ActionEvent event) throws IOException {
    Dialog<String> dialog = new Dialog<>();
    dialog.setTitle("Delete");
    dialog.setHeaderText("Xóa Từ");

    ButtonType deletebutton = new ButtonType("Delete", ButtonBar.ButtonData.OK_DONE);
    ButtonType cancelbutton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
    dialog.getDialogPane().getButtonTypes().addAll(deletebutton, cancelbutton);

    GridPane grid = new GridPane();
    grid.setHgap(20);
    grid.setVgap(20);
    grid.setPadding(new Insets(20, 150, 10, 10));

    TextField userName = new TextField();
    userName.setText(null);

    grid.add(new javafx.scene.control.Label("Word:"), 0, 0);
    grid.add(userName, 1, 0);

    Node deleteButton = dialog.getDialogPane().lookupButton(deletebutton);
    deleteButton.setDisable(true);

    userName
        .textProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              deleteButton.setDisable(newValue.trim().isEmpty());
            });
    dialog.getDialogPane().setContent(grid);

    dialog.setResultConverter(
        dialogButton -> {
          if (dialogButton == deletebutton) {
            //
            return userName.getText();
          }
          return null;
        });
    Optional<String> result = dialog.showAndWait();

    if (userName.getText() != null) {

      list.add(userName.getText());

      deleteData(event);
    }
  }

  // Add Word
  public void pressThem(ActionEvent event) throws IOException {

    Dialog<Pair<String, String>> dialog = new Dialog<>();
    dialog.setTitle("Add");
    dialog.setHeaderText("Thêm từ");

    ButtonType addbutton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
    ButtonType cancelbutton2 = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

    dialog.getDialogPane().getButtonTypes().addAll(addbutton, cancelbutton2);

    GridPane grid = new GridPane();
    grid.setLayoutX(500);
    grid.setLayoutY(500);
    grid.setHgap(20);
    grid.setVgap(20);
    grid.setPadding(new Insets(20, 150, 10, 10));

    TextField userName = new TextField();
    userName.setText(null);
    TextArea password = new TextArea();
    password.setText("");

    grid.add(new javafx.scene.control.Label("Word:"), 0, 0);
    grid.add(userName, 1, 0);
    grid.add(new javafx.scene.control.Label("Def:"), 0, 1);
    grid.add(password, 1, 2);

    Node loginButton = dialog.getDialogPane().lookupButton(addbutton);
    loginButton.setDisable(true);

    userName
        .textProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              loginButton.setDisable(newValue.trim().isEmpty());
            });
    dialog.getDialogPane().setContent(grid);

    dialog.setResultConverter(
        dialogButton -> {
          if (dialogButton == addbutton) {
            return new Pair<>(userName.getText(), password.getText());
          }
          return null;
        });
    Optional<Pair<String, String>> result = dialog.showAndWait();

    if (dialog.getResult() != null) {
      for (int i = 0; i < list.size(); i++) {
        if (list.get(i).equals(userName.getText())) {
          list.remove(i);
        }
      }

      list1.add(dialog.getResult());
      for (int i = 0; i < list1.size(); i++) {
        System.out.println(list1.get(i));
      }
      AddData(event);
    }
  }

  //Search word
  public void pressTimKiem(ActionEvent event) throws IOException {
    Dialog<String> dialog2 = new Dialog<>();
    dialog2.setTitle("Search");
    dialog2.setHeaderText("Tìm Từ");

    ButtonType searchbutton = new ButtonType("Search", ButtonBar.ButtonData.OK_DONE);
    ButtonType cancelbutton5 = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

    dialog2.getDialogPane().getButtonTypes().addAll(searchbutton, cancelbutton5);

    GridPane grid = new GridPane();
    grid.setHgap(20);
    grid.setVgap(20);
    grid.setPadding(new Insets(20, 150, 10, 10));

    TextField userName = new TextField();
    userName.setText(null);

    grid.add(new javafx.scene.control.Label("Word:"), 0, 0);
    grid.add(userName, 1, 0);

    Node deleteButton = dialog2.getDialogPane().lookupButton(searchbutton);
    deleteButton.setDisable(true);

    userName
        .textProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              deleteButton.setDisable(newValue.trim().isEmpty());
            });
    dialog2.getDialogPane().setContent(grid);

    dialog2.setResultConverter(
        dialogButton -> {
          if (dialogButton == searchbutton) {
            return userName.getText();
          }
          return null;
        });
    Optional<String> result = dialog2.showAndWait();
    if (userName.getText() != null) {

      wordtofind = userName.getText();

      Search(event);
    }
  }

  public void RETURN(ActionEvent event) throws IOException {

    Parent root = FXMLLoader.load(getClass().getResource("/sample/Dictionary_Main.fxml"));
    Scene scene = new Scene(root);

    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

    stage.setScene(scene);
    stage.setTitle("Dictionary");
    stage.show();

    initComponents(scene);

    readData();

    for (int i = 0; i < list.size(); i++) {
      data.remove(list.get(i));
    }
    for (int i = 0; i < list1.size(); i++) {
      Word word = new Word(list1.get(i).getKey(), list1.get(i).getValue());
      data.put(list1.get(i).getKey(), word);
    }

    loadWordList();
  }

  //Fix word
  public void pressSua(ActionEvent event) throws IOException {

    Dialog<Pair<String, String>> dialog = new Dialog<>();
    dialog.setTitle("Fix");
    dialog.setHeaderText("Sửa từ");

    ButtonType fixbutton = new ButtonType("Update", ButtonBar.ButtonData.OK_DONE);
    ButtonType cancelbutton4 = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

    dialog.getDialogPane().getButtonTypes().addAll(fixbutton, cancelbutton4);

    GridPane grid = new GridPane();
    grid.setLayoutX(500);
    grid.setLayoutY(500);
    grid.setHgap(20);
    grid.setVgap(20);
    grid.setPadding(new Insets(20, 150, 10, 10));

    TextField word = new TextField();
    word.setText(null);
    TextArea def = new TextArea();
    def.setText("");

    grid.add(new javafx.scene.control.Label("Word:"), 0, 0);
    grid.add(word, 1, 0);
    grid.add(new javafx.scene.control.Label("Def:"), 0, 1);
    grid.add(def, 1, 2);

    Node loginButton = dialog.getDialogPane().lookupButton(fixbutton);
    loginButton.setDisable(true);

    word
        .textProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              loginButton.setDisable(newValue.trim().isEmpty());
            });
    dialog.getDialogPane().setContent(grid);

    dialog.setResultConverter(
        dialogButton -> {
          if (dialogButton == fixbutton) {
            return new Pair<>(word.getText(), def.getText());
          }
          return null;
        });
    Optional<Pair<String, String>> result = dialog.showAndWait();

    if (dialog.getResult() != null) {

      list2.add(dialog.getResult());

      FixData(event);
    }
  }

  // Delete the searching word
  public void pressXoa1(ActionEvent event) throws IOException {
    if (iftodoanything != null) {
      list.add(iftodoanything);
      deleteData(event);
    }
  }

  // Fix Def of searching word
  public void pressFix2(ActionEvent event) throws IOException {

    Dialog<Pair<String, String>> dialog = new Dialog<>();
    dialog.setTitle("Fix");
    dialog.setHeaderText("Sửa từ");

    ButtonType fixbutton = new ButtonType("Update", ButtonBar.ButtonData.OK_DONE);
    ButtonType cancelbutton4 = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

    dialog.getDialogPane().getButtonTypes().addAll(fixbutton, cancelbutton4);

    GridPane grid = new GridPane();

    grid.setHgap(20);
    grid.setVgap(20);
    grid.setPadding(new Insets(20, 150, 10, 10));

    TextArea password = new TextArea();
    password.setText("");

    grid.add(new javafx.scene.control.Label("Def:"), 0, 1);
    grid.add(password, 1, 2);

    Node loginButton = dialog.getDialogPane().lookupButton(fixbutton);
    loginButton.setDisable(true);

    password
        .textProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              loginButton.setDisable(newValue.trim().isEmpty());
            });
    dialog.getDialogPane().setContent(grid);

    dialog.setResultConverter(
        dialogButton -> {
          if (dialogButton == fixbutton) {
            return new Pair<>(iftodoanything, password.getText());
          }
          return null;
        });
    Optional<Pair<String, String>> result = dialog.showAndWait();

    if (dialog.getResult() != null) {

      list2.add(dialog.getResult());

      FixData(event);
    }
  }

  public void write(ActionEvent event) throws IOException {

    String s = Text.getText();
    char[] arg = s.toCharArray();
    int i = s.length();

    try {
      updateList(arg, i, event);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  //Text to Speech
  public void texttoString() throws  Exception {
    if(!iftodoanything.isEmpty()) {
      Audio audio = Audio.getInstance();
      String s = iftodoanything + "&client=tw-ob";
      InputStream sound = audio.getAudio(s,"en");
      audio.play(sound);
    } else {
      System.out.print("No");
    }


  }

}

