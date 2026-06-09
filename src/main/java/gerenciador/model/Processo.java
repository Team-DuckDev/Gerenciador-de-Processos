package gerenciador.model;

public class Processo {
    
    private long pid;
    private int nice;
    private String user;
    private long time; 
    private String status;
    private String command; 

    // Construtor vazio
    public Processo() {
    }

    // Construtor completo
    public Processo(long pid, int nice, String user, long time, String status, String command) {
        this.pid = pid;
        this.nice = nice;
        this.user = user;
        this.time = time;
        this.status = status;
        this.command = command;
    }

    
    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public int getNice() {
        return nice;
    }

    public void setNice(int nice) {
        this.nice = nice;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}