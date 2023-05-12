//package definition
package Client;

/**
 *
 * @author meika
 */

 //component imports
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

//begin ClientAppController class

public class ClientAppController {
  
    // Labels, Tables and Buttons
    @FXML
    private TextField txtFireId;
    @FXML
    private TextField txtDroneId;
    @FXML
    private Label lblMessage;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnClose;
    @FXML
    private Button btnClear;
    @FXML
    private TableView<QueryResult> tableView;
    
    // code to call the RESTful Web Services client and retrieve the query results
    // populate the TableView with the query results
    public void queryDatabase() {

    }
    
    // code to hold the query result data
    public class QueryResult {
        
    }
    
    @FXML 
    private void btnClearlAction(){
        eraseAll();
    }
    
    @FXML
    private void btnCloseAction() throws IOException{
        closeScreen();
    }
    private void closeScreen() {
        Stage aStage = (Stage) btnClose.getScene().getWindow();
        aStage.close();
       
    }
    private void eraseAll(){
        txtFireId.setText("");
        txtDroneId.setText("");
    }    
  }

  //end ClientAppController class
