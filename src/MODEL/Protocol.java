package MODEL;

public class Protocol {
    private String Method;
    private String Hostname;
    private int Port;
    private String Username;
    private String Password;

    public Protocol() {
    }

    public Protocol(String method, String hostname, int port, String username, String password) {
        Method = method;
        Hostname = hostname;
        Port = port;
        Username = username;
        Password = password;
    }

    public String getMethod() {
        return Method;
    }

    public void setMethod(String method) {
        Method = method;
    }

    public String getHostname() {
        return Hostname;
    }

    public void setHostname(String hostname) {
        Hostname = hostname;
    }

    public int getPort() {
        return Port;
    }

    public void setPort(int port) {
        Port = port;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    @Override
    public String toString() {
        return Method + " : " + Username +"@"+ Hostname;
    }
}
