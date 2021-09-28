package calculator.calculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
@SuppressWarnings("exports") public class App extends Application {


	 
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("primary.fxml"));//刷取
        primaryStage.setTitle("計算機");//軟體名稱
        primaryStage.setResizable(false);//鎖住大小
        primaryStage.setScene(new Scene(root, 257, 361));//給予大小
        primaryStage.show();

    }
    
 
 
    public static void main(String[] args) {
      launch(args); 
 
    }
    
    
}