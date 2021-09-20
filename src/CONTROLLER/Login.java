package CONTROLLER;

import Class.CacheFile;
import Class.CustomALert;
import Class.ConnectFTP;
import MODEL.Protocol;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.commons.net.ftp.FTPClient;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Login implements Initializable {

    @FXML
    public ListView lv_workspace;
    public ComboBox cbo_Session;
    public ComboBox cbo_portocol;
    public TextField txt_hostname;
    public Spinner txt_port;
    public PasswordField txt_password;
    public TextField txt_username;
    public Button btn_save;
    public Button btn_login;
    public Button btn_close;
    public Button btn_help;
    public ProgressBar Pg_Login;
    public ImageView img_success;
    public ImageView img_fail;
    public Label lb_nof;

    ArrayList<Protocol> protocols = new ArrayList<>();
    CacheFile  cacheFile = new CacheFile();
    CustomALert  aLert = new CustomALert();
    ConnectFTP connectFTP = new ConnectFTP();
    FTPClient ftpClient = new FTPClient();
    int protocol_index ;
    int flag_mode=0;


    @FXML
    void SaveSession(ActionEvent e){
        if(!txt_hostname.getText().equals("")&&!txt_username.getText().equals("")&&!txt_password.getText().equals("")){
            if(flag_mode ==0){
                Protocol protocol = new Protocol(
                        cbo_portocol.getSelectionModel().getSelectedItem().toString(),
                        txt_hostname.getText(),
                        Integer.parseInt(txt_port.getValue().toString()),
                        txt_username.getText(),
                        txt_password.getText()
                );
                protocols.add(protocol);
            }
            if (flag_mode == 1 && protocol_index != -1 ){
                Protocol protocol = protocols.get(protocol_index);
                protocol.setMethod(cbo_portocol.getSelectionModel().getSelectedItem().toString());
                protocol.setHostname(txt_hostname.getText());
                protocol.setPort(Integer.parseInt(txt_port.getValue().toString()));
                protocol.setUsername(txt_username.getText());
                protocol.setPassword( txt_password.getText());

            }
            SaveCache(0);
            flag_mode =0;
        }
    }

    public void SaveCache(int mode){
        txt_hostname.clear();
        txt_password.clear();
        txt_username.clear();
        cacheFile.CreateCache();
        if(cacheFile.WriteCache(protocols)){
            if (mode==0)
                aLert.Infor_Alert("Save workspace success!");
            if(mode ==1)
                aLert.Infor_Alert("Close workspace success!");
        }

        InitItemWorkspace();
    }

    public void OpenMain(ActionEvent event,FTPClient ftpClient){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../GUI/Main.fxml"));
            Parent root = loader.load();
            Main main = loader.getController();
            main.InitFTPClient(ftpClient);
            Stage stage = new Stage();
            stage.setScene(new Scene(root,1480,900));
            stage.setTitle("FTP Client");
            stage.show();
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void Login(ActionEvent event){
        Protocol protocol = (Protocol) lv_workspace.getSelectionModel().getSelectedItem();
        if(protocol!=null){
            Pg_Login.setVisible(true);
            lb_nof.setText("Connecting to server FTP!");
            Runnable  run = new Runnable() {
                @Override
                public void run() {
                    if(connectFTP.ConnectFTP(ftpClient,protocol)){
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                img_success.setVisible(true);
                                img_fail.setVisible(false);
                                lb_nof.setText("Login successfully!");
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        OpenMain(event,ftpClient);
                                    }
                                });
                            }
                        });
                    }
                    else {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                img_fail.setVisible(true);
                                img_success.setVisible(false);
                                lb_nof.setText("Connect to server failed!");
                            }
                        });
                    }
                }
            };
            new Thread(run).start();
        }
    }

    public void CloseWorkspace(ActionEvent event){

        if (protocol_index!=-1){
            Protocol  protocol = protocols.get(protocol_index);
            if(aLert.Confirm_Alert("Are you sure want to close workspace "+ protocol.toString())) {
                protocols.remove(protocol);
                SaveCache(1);
            }
        }
        flag_mode =0;
        protocol_index = -1;
    }

    public void SelectProtocol(MouseEvent mouseEvent) {

        Protocol protocol = (Protocol) lv_workspace.getSelectionModel().getSelectedItem();
        if(protocol!=null){
            protocol_index = lv_workspace.getSelectionModel().getSelectedIndex();
            flag_mode = 1;
            txt_hostname.setText(protocol.getHostname());
            txt_port.getValueFactory().setValue(protocol.getPort());
            txt_username.setText(protocol.getUsername());
            txt_password.setText(protocol.getPassword());
            Pg_Login.setVisible(false);
            img_fail.setVisible(false);
            img_success.setVisible(false);
            lb_nof.setText("");
        }

    }

    private  void  InitItemWorkspace(){
        protocols = cacheFile.ReadCache();
        lv_workspace.setItems(FXCollections.observableList(protocols));
    }

    private  void InitItemProtocol(){
        ArrayList<String> list = new ArrayList<String>();
        list.add("FTP");
        list.add("SFTP");
        list.add("SCP");
        cbo_portocol.setItems(FXCollections.observableList(list));
        cbo_portocol.getSelectionModel().select(0);
    }

    private void InitItemSession(){
        ArrayList<String> list = new ArrayList<String>();
        list.add("New session");
        list.add("Import session");
        list.add("Export session");
        cbo_Session.setItems(FXCollections.observableList(list));
        cbo_Session.getSelectionModel().select(0);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        InitItemSession();
        InitItemProtocol();
        InitItemWorkspace();
        txt_port.setEditable(true);
        txt_port.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(21,1023,1));

    }


}
