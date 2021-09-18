package Class;

import MODEL.Protocol;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;

public class ConnectFTP {


    public CustomALert aLert = new CustomALert();


    public  Boolean  ConnectFTP(FTPClient ftpClient,Protocol protocol){

        try {

            ftpClient.setDefaultTimeout(6000);
            ftpClient.connect(protocol.getHostname(), protocol.getPort());
            ftpClient.enterLocalPassiveMode();
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                DisconnectFTP(ftpClient);
                return false;
            } else {
                ftpClient.setSoTimeout(6000);
                if (!ftpClient.login(protocol.getUsername(), protocol.getPassword())) {
                    return false;
                }
                ftpClient.setDataTimeout(6000);
                return true;
            }
        } catch (IOException ex) {
            return false;
        }


    }
    private void DisconnectFTP(FTPClient ftpClient) {
        if (ftpClient != null && ftpClient.isConnected()) {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
