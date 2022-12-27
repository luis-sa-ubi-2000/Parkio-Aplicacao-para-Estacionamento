package pt.ubi.di.pdm.login_registo;



public class Pessoa {

    public String mail;
    public String senha;
    public String data;

    public Pessoa() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Pessoa(String mail, String senha, String data) {

        this.mail = mail;
        this.senha = senha;
        this.data = data;
    }

    public String getMail(){
        return mail;
    }

    public void setMail(String mail){
        this.mail = mail;
    }

    public String getSenha(){
        return senha;
    }

    public void setSenha(String senha){
        this.senha = senha;
    }

    public String getData(){
        return data;
    }

    public void setData(String data){
        this.data = data;
    }
}
