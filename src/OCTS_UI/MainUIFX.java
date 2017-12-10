/*===============================================================================================================================
        CLASS Name:    MainUIFX
        CREATED BY:    Raghavendran Ramasubramanian
        DATE CREATED:  Nov 2017
        DESCRIPTION:   Main class to open the UI of the application                    
        PARAMETERS:                                                                  
        RETURNS:      
        COMMENTS:                                     
        Modification Log:
        Date                             Initials                                                Modification
        
-------------         ------------    ------------------------------------------------------------------------------------------------------------------------------*/

package OCTS_UI;

import java.text.SimpleDateFormat;
import java.util.Date;

import Common_Utility.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
public class MainUIFX extends Application {

	@SuppressWarnings("static-access")
	@Override
	public void start(Stage primaryStage) {

		// Create the FXMLLoader
		FXMLLoader loader = new FXMLLoader();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hhmmss");
        System.setProperty("current.date", dateFormat.format(new Date()));
		
		try {

			Parent root = loader.load(getClass().getResource("/SplashScreen.fxml"));

			// Create the Scene

			Scene scene = new Scene(root);

			// Set the Scene to the Stage
			primaryStage.setScene(scene);

			primaryStage.getIcons().add(new Image(MainUIFX.class.getResourceAsStream("/cloud.png")));
			
			// Set the Title to the Stage
			primaryStage.setTitle("Oracle Cloud Test Automation Suite");

			// Disable resize option
			primaryStage.setResizable(false);

			
			
			// Display the Stage
			primaryStage.show();

			// Thread.sleep(7000);

			// primaryStage.getScene().getWindow().hide();
		} catch (Exception e) {
			System.out.println(e);
			primaryStage.close();
		}
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void stop() {
		Platform.exit();
		System.exit(0);
	}

}
