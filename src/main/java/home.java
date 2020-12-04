public class home {
    String url = "";
    String user = "";
    String password = "";

    public home(){
        this.url = "jdbc:postgresql://localhost:5433/daehakgaja_dev";
        this.user = "oohyun";
        this.password = "test123";
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
