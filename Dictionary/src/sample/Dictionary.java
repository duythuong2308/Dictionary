package sample;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.w3c.dom.Text;

import java.io.*;
import java.util.*;

public class Dictionary extends Application {

    private static final String DATA_FILE_PATH =
            "C:\\Users\\ASUS\\IdeaProjects\\Dictionary\\src\\sample\\E_V.txt";
    private static final String FXML_FILE_PATH;

    static {
        FXML_FILE_PATH = "Dictionary_Main.fxml";
    }

    private static final String SPLITTING_CHARACTERS = "<html>";
    protected Map<String, Word> data = new HashMap<>();

    @FXML
    private ListView<String> listView;
    @FXML
    private WebView definitionView;

    protected Stage stage;
    static ArrayList<String> list = new ArrayList<String>();
    static ArrayList<Pair<String, String>> list1 = new ArrayList<>();
    static ArrayList<Pair<String, String>> list2 = new ArrayList<>();
    static String wordtofind;
    static String iftodoanything;


    @Override
    public void start(Stage primaryStage) throws Exception{
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/sample/Dictionary_Main.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Dictionary");
            primaryStage.show();

            initComponents(scene);

            readData();

            loadWordList();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public void initComponents(Scene scene) {
        this.definitionView = (WebView) scene.lookup("#definitionView");
        this.listView = (ListView<String>) scene.lookup("#listView");
        Dictionary context = this;
        this.listView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    Word selectedWord = data.get(newValue.trim());
                    String definition = selectedWord.getDef();
                    context.definitionView.getEngine().loadContent(definition, "text/html");
                    iftodoanything = selectedWord.getWord();
                    System.out.println(iftodoanything);

                }
        );
    }

    public void loadWordList() {
        ArrayList<String> sort = new ArrayList<String>(data.keySet());
        Collections.sort(sort);

        for(int i =0;i< sort.size();i++) {


            this.listView.getItems().add(sort.get(i));}

    }

    public void readData() throws IOException {
        FileReader fis = new FileReader(DATA_FILE_PATH);
        BufferedReader br = new BufferedReader(fis);
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(SPLITTING_CHARACTERS);
            String word = parts[0];
            String definition = SPLITTING_CHARACTERS + parts[1];
            Word wordObj = new Word(word, definition);
            data.put(word, wordObj);
        }
    }

    public void readDatawithword() throws IOException {
        FileReader fis = new FileReader(DATA_FILE_PATH);
        BufferedReader br = new BufferedReader(fis);
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(SPLITTING_CHARACTERS);
            String word = parts[0];
            if(word.equals(wordtofind)) {
                String definition = SPLITTING_CHARACTERS + parts[1];
                Word wordObj = new Word(word, definition);
                data.put(word, wordObj);
            }
        }
    }

    public void Fixdatawithword(String wordtofix, String datatofix) throws IOException {
        FileReader fis = new FileReader(DATA_FILE_PATH);
        BufferedReader br = new BufferedReader(fis);
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(SPLITTING_CHARACTERS);
            String word = parts[0];
            if(word.equals(wordtofix)) {
                String definition = datatofix;
                Word wordObj = new Word(word, definition);
                data.put(word, wordObj);
            }
        }
        for(int i =0;i< list1.size();i++) {
            if(list1.get(i).getKey().equals(wordtofix)) {
                Pair<String, String> update = new Pair<>(wordtofix,datatofix);
                list1.set(i, update);
            }
        }
    }

    public void ListViewUpdate(char[] args,int num) throws IOException {
        FileReader fis = new FileReader(DATA_FILE_PATH);
        BufferedReader br = new BufferedReader(fis);
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(SPLITTING_CHARACTERS);
            String word = parts[0];
            char[] arg1 = word.toCharArray();
            for(int i=0;i<args.length;i++) {
                if(arg1.length>=args.length) {
                    if (arg1[i] != args[i]) {
                        break;
                    }
                    if (i == num - 1) {
                        String definition = parts[1];
                        Word wordObj = new Word(word, definition);
                        data.put(word, wordObj);
                    }
                }

            }
        }
        for(int i =0;i< list1.size();i++) {

        }

    }


    public void deleteData(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/sample/Dictionary_Main.fxml"));
        Scene scene = new Scene(root);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(scene);
        stage.setTitle("Dictionary");
        stage.show();

        initComponents(scene);

        readData();

        for(int i =0;i < list1.size();i++) {
            Word word = new Word(list1.get(i).getKey(),list1.get(i).getValue());
            data.put(list1.get(i).getKey(),word);
        }


        for(int i=0;i < list2.size();i++) {
            Fixdatawithword(list2.get(i).getKey(),list2.get(i).getValue());
        }

        for(int i =0;i < list.size();i++) {
            data.remove(list.get(i));}

        loadWordList();

    }

    public void AddData(ActionEvent event) throws IOException {


        Parent root = FXMLLoader.load(getClass().getResource("/sample/Dictionary_Main.fxml"));
        Scene scene = new Scene(root);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(scene);
        stage.setTitle("Dictionary");
        stage.show();

        initComponents(scene);

        readData();


        for(int i =0;i < list1.size();i++) {
            Word word = new Word(list1.get(i).getKey(),list1.get(i).getValue());
            data.put(list1.get(i).getKey(),word);

        }
        for(int i=0;i < list2.size();i++) {
            Fixdatawithword(list2.get(i).getKey(),list2.get(i).getValue());
        }

        for(int i =0;i < list.size();i++) {
            data.remove(list.get(i));}

        loadWordList();

    }

    public void Search(ActionEvent event) throws IOException {


        Parent root = FXMLLoader.load(getClass().getResource("/sample/Dictionary_Main.fxml"));
        Scene scene = new Scene(root);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(scene);
        stage.setTitle("Dictionary");
        stage.show();

        initComponents(scene);

        readDatawithword();

        for(int i =0;i < list1.size();i++) {
            Word word = new Word(list1.get(i).getKey(), list1.get(i).getValue());

            if(list1.get(i).getKey().equals(wordtofind)) {
                data.put(list1.get(i).getKey(), word);
            }

        }


        for(int i=0;i < list2.size();i++) {
            Fixdatawithword(list2.get(i).getKey(),list2.get(i).getValue());
        }


        for(int i =0;i < list.size();i++) {
            data.remove(list.get(i));
        }


        loadWordList();
    }

    public void FixData(ActionEvent event) throws IOException {


        Parent root = FXMLLoader.load(getClass().getResource("/sample/Dictionary_Main.fxml"));
        Scene scene = new Scene(root);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(scene);
        stage.setTitle("Dictionary");
        stage.show();

        initComponents(scene);

        readData();

        for(int i =0;i < list1.size();i++) {
            Word word = new Word(list1.get(i).getKey(),list1.get(i).getValue());
            data.put(list1.get(i).getKey(),word);

        }

        for(int i=0;i < list2.size();i++) {
            Fixdatawithword(list2.get(i).getKey(),list2.get(i).getValue());
        }

        for(int i =0;i < list.size();i++) {
            data.remove(list.get(i));}


        loadWordList();

    }

    public static void main(String[] args) {

        Application.launch(args);
    }

    public void updateList(char[] arg, int num,ActionEvent event) throws IOException {


        Parent root = FXMLLoader.load(getClass().getResource("/sample/Dictionary_Main.fxml"));
        Scene scene = new Scene(root);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(scene);
        stage.setTitle("Dictionary");
        stage.show();

        ListViewUpdate(arg,num);

        for(int i =0;i < list1.size();i++) {
            Word word = new Word(list1.get(i).getKey(),list1.get(i).getValue());
            data.put(list1.get(i).getKey(),word);
        }

        for(int i=0;i < list2.size();i++) {
            Fixdatawithword(list2.get(i).getKey(),list2.get(i).getValue());
        }

        for(int i =0;i < list.size();i++) {
            data.remove(list.get(i));}

        initComponents(scene);

        loadWordList();

    }
}

class Word {
    private String word;
    private String def;

    public Word(String word, String def) {
        this.word = word;
        this.def = def;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDef() {
        return def;
    }

    public void setDef(String def) {
        this.def = def;
    }
}