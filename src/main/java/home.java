public class home {
    String url = "";
    String user = "";
    String password = "";

    public home(){
        this.url = "jdbc:postgresql://localhost:5432/daehakgaja_dev";
        this.user = "school";
        this.password = "dv123";
    }
    public String getUrl() {
        return this.url;
    }
    public String getUser(){
        return this.user;
    }
    public String getPassword(){
        return this.password;
    }

}
