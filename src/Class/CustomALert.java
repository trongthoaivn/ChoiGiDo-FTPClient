package Class;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class CustomALert {
    public  void  Infor_Alert(String msg){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Notification");

        // Header Text: null
        alert.setHeaderText(null);
        alert.setContentText(msg);

        alert.showAndWait();
    }
    public  void  Warning_Alert(String msg){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");

        // Header Text: null
        alert.setHeaderText(null);
        alert.setContentText(msg);

        alert.showAndWait();
    }
    public  void  Error_Alert(String msg){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");

        // Header Text: null
        alert.setHeaderText(null);
        alert.setContentText(msg);

        alert.showAndWait();
    }



    public  Boolean  Confirm_Alert(String msg){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Notification");

        // Header Text: null
        alert.setHeaderText(null);
        alert.setContentText(msg);

        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == ButtonType.OK) {
            return true;
        }
        return false;
    }
}
