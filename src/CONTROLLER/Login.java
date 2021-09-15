package CONTROLLER;

import Cache.CacheFile;
import MODEL.Cbo_Item;
import MODEL.Protocol;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.awt.*;
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

    ArrayList<Protocol> protocols = new ArrayList<>();
    CacheFile  cacheFile = new CacheFile();


    @FXML
    void SaveSession(ActionEvent e){
        if(!txt_hostname.getText().equals("")&&!txt_username.getText().equals("")&&!txt_password.getText().equals("")){
            Protocol protocol = new Protocol(
                    cbo_portocol.getSelectionModel().getSelectedItem().toString(),
                    txt_hostname.getText(),
                    Integer.parseInt(txt_port.getValue().toString()),
                    txt_username.getText(),
                    txt_password.getText()
            );
            protocols.add(protocol);
            lv_workspace.setItems(FXCollections.observableList(protocols));
            txt_hostname.clear();
            txt_password.clear();
            txt_username.clear();
            cacheFile.CreateCache();
            if(cacheFile.WriteCache(protocols))
            System.out.println("success");
        }
    }

    public void SelectProtocol(MouseEvent mouseEvent) {
        Protocol protocol = (Protocol) lv_workspace.getSelectionModel().getSelectedItem();
        txt_hostname.setText(protocol.getHostname());
        txt_port.getValueFactory().setValue(protocol.getPort());
        txt_username.setText(protocol.getUsername());
        txt_password.setText(protocol.getPassword());
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
        txt_port.setEditable(true);
        txt_port.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(21,1023,1));

    }


}
