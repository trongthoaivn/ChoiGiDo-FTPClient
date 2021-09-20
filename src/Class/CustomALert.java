package Class;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.util.Optional;

public class CustomALert {


    Label label = new Label("");

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

    public String InputDialog(String msg){
        TextInputDialog dialog = new TextInputDialog();

        dialog.setTitle("");
        dialog.setHeaderText(null);
        dialog.setContentText(msg);

        Optional<String> result = dialog.showAndWait();


        result.ifPresent(name -> {
            label.setText(name);
        });
        return label.getText();
    }
}
