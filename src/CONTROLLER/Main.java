package CONTROLLER;

import MODEL.File;
import Class.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class Main implements Initializable {

    @FXML
    public ListView<HBox> lv_FilesFTPServer;
    public ListView<HBox> lv_FilesFTPClient;
    public ImageView btn_backServer;
    public ImageView btn_choosefileServer;
    public ImageView btn_choosefileClient;
    public ImageView btn_backClient;
    public TextField txt_pathClient;
    public TextField txt_pathServer;
    public ContextMenu ctm_FTPServer;
    public ContextMenu ctm_FTPClient;
    public ImageView btn_addfolder;
    public ImageView btn_remove;


    CustomALert aLert = new CustomALert();
    ArrayList<HBox> hboxesSv = new ArrayList<>();
    ArrayList<HBox> hboxesCl = new ArrayList<>();
    FTPClient ftp = new FTPClient();
    String path ="c:\\";
    File f = new File();

    public void InitFTPClient (FTPClient ftpClient) throws IOException {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ftp = ftpClient;
                GetAllFileFTP();
                GetAllFileClient(path);
            }
        });
    }
    public void InitMenu(){
        MenuItem Open = new MenuItem("Open");
        MenuItem Copy = new MenuItem("Copy");
        MenuItem Cut = new MenuItem("Cut");
        MenuItem Paste = new MenuItem("Paste");
        ctm_FTPServer.getItems().add(Open);
        ctm_FTPServer.getItems().add(Copy);
        ctm_FTPServer.getItems().add(Cut);
        ctm_FTPServer.getItems().add(Paste);
    }
    private void GetAllFileClient(String path){
        java.io.File root = new java.io.File(path);
        try {
            for (java.io.File file : root.listFiles()){
                String cate = "";
                if(file.isDirectory())
                    cate = "1";
                else cate = "0";
                BasicFileAttributes attr = Files.readAttributes(file.toPath(),BasicFileAttributes.class);
                HBox hBox = f.CreateHbox(
                        cate,
                        file.getName(),
                        String.valueOf(Files.size(file.toPath()))+ " KB",
                        "",
                        ""
                );
                hboxesCl.add(hBox);
            }
            lv_FilesFTPClient.setItems(FXCollections.observableList(hboxesCl));
            txt_pathClient.setText(path);
        }catch (Exception e){
            aLert.Error_Alert(e.toString());
        }

    }

    private String FormatTime(String time) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date modificationTime = new Date();

        try {
            if(time!=null) {
                modificationTime = dateFormat.parse(time);
            }
            return dateFormat.format(modificationTime);
        } catch (ParseException ex) {
            return "";
        }
    }

    private String hasPermisionChar ( FTPFile file ){
        String Result = "";
        if (file.hasPermission(FTPFile.USER_ACCESS , FTPFile.READ_PERMISSION))
            Result+="r";
        else Result+= "-";
        if (file.hasPermission(FTPFile.USER_ACCESS , FTPFile.WRITE_PERMISSION))
            Result+="w";
        else Result+= "-";
        if (file.hasPermission(FTPFile.USER_ACCESS , FTPFile.EXECUTE_PERMISSION))
            Result+="x";
        else Result+= "-";
        return Result;
    }




    public void GetAllFileFTP(){
        try {
            //ftp.changeWorkingDirectory(path);
            FTPFile[] ftpFiles = ftp.listFiles();
            for (FTPFile file :ftpFiles){
                    HBox  hBox = f.CreateHbox(
                            String.valueOf(file.getType()),
                            file.getName(),
                            String.valueOf(file.getSize()) +" KB",
                           FormatTime(ftp.getModificationTime(file.getName())),
                            hasPermisionChar(file));
                    hboxesSv.add(hBox);
            }
            lv_FilesFTPServer.setItems(FXCollections.observableList(hboxesSv));
            txt_pathServer.setText(ftp.printWorkingDirectory());
        } catch (IOException | ParseException e) {
            aLert.Error_Alert(e.getMessage());
        }

    }

    public void OpenFolderClient(MouseEvent mouseEvent) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        java.io.File selectedDirectory = directoryChooser.showDialog(txt_pathClient.getScene().getWindow());
        if(selectedDirectory == null){
        }else{
            String choosepath = selectedDirectory.getAbsolutePath() ;
            hboxesCl.clear();
            GetAllFileClient(choosepath);
        }

    }

    public void OpenDirectoy(MouseEvent mouseEvent) {
        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
            if(mouseEvent.getClickCount() == 2){
                String foldername = ((Label) lv_FilesFTPClient.getSelectionModel().getSelectedItem().getChildren().get(1)).getText();
                java.io.File file = new java.io.File(txt_pathClient.getText() +"\\"+foldername);
                if (file.isDirectory()){
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            hboxesCl.clear();
                            GetAllFileClient(txt_pathClient.getText() +"\\"+foldername);
                        }
                    });
                }else {
                    aLert.Error_Alert("Can not open file!");
                }

            }
        }
    }

    public void BackClient(MouseEvent mouseEvent) {
        try {
            String path = txt_pathClient.getText();

            java.io.File file = new java.io.File(path.substring(0,path.lastIndexOf("\\")));
            if (file.isDirectory()||file.isAbsolute()){
                hboxesCl.clear();
                GetAllFileClient(file.getAbsolutePath());
            }

        }catch (Exception ex){

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
            InitMenu();
           
    }


    public void OpenDirectoryFTP(MouseEvent mouseEvent) throws IOException {
        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
            if(mouseEvent.getClickCount() == 2){
                String foldername = ((Label) lv_FilesFTPServer.getSelectionModel().getSelectedItem().getChildren().get(1)).getText();
                if (ftp.changeWorkingDirectory(txt_pathServer.getText() +"/"+foldername)){
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            hboxesSv.clear();
                            GetAllFileFTP();
                        }
                    });
                }else {
                    aLert.Error_Alert("Can not open file!");
                }
            }
        }
    }

    public void BackServer(MouseEvent mouseEvent) throws IOException {
        try {
            String path = txt_pathServer.getText();
            if(ftp.changeWorkingDirectory(path.substring(0,path.lastIndexOf("/")))){
                hboxesSv.clear();
                GetAllFileFTP();
            }
        }catch (Exception e){
            aLert.Error_Alert(e.toString());
        }

    }

    public void addfolderFTPServer(MouseEvent mouseEvent) {

    }

    public void removefolderFTPServer(MouseEvent mouseEvent) {

    }
}
