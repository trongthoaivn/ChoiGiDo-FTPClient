package Class;

import MODEL.Protocol;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;

public class ConnectFTP {

    public FTPClient ftpClient;
    public CustomALert aLert;

    public  Boolean  ConnectFTP(Protocol protocol){
        ftpClient = new FTPClient();
        try {

            ftpClient.setDefaultTimeout(6000);
            ftpClient.connect(protocol.getHostname(), protocol.getPort());
            ftpClient.enterLocalPassiveMode();
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                DisconnectFTP();
                aLert.Error_Alert("FTP server not respond!");
                return false;

            } else {
                ftpClient.setSoTimeout(6000);
                // login ftp server
                if (!ftpClient.login(protocol.getUsername(), protocol.getPassword())) {
                    aLert.Error_Alert("Username or password is incorrect!");
                    return false;

                }
                ftpClient.setDataTimeout(6000);
                return true;
            }
        } catch (IOException ex) {
            return false;
        }


    }
    private void DisconnectFTP() {
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
