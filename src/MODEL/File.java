package MODEL;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class File {

    public HBox CreateHbox(String cate,String filename ,String size ,String changed,String rights){
        HBox root = new HBox(5);
        root.setPadding(new Insets(2));
        root.setAlignment(Pos.CENTER_LEFT);
        ImageView imgv = new ImageView() ;
        imgv.setFitHeight(24);
        imgv.setFitWidth(24);
        switch (cate){

            case "1":   imgv.setImage(new Image("Image/icons8_folder_48px.png"));
                        break;
            case "0":   imgv.setImage(new Image("Image/icons8_file_64px.png"));
                        break;

        }
        root.getChildren().addAll(imgv, new Label(filename),new Label(size),new Label(changed),new Label(rights));
        root.getChildren().get(1).setStyle("-fx-pref-width:280px");
        root.getChildren().get(2).setStyle("-fx-pref-width:100px");
        root.getChildren().get(3).setStyle("-fx-pref-width:100px");
        return root;
    }
}
