package pt.ubi.di.pdm.login_registo;

//Bibliotecas

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {

    //Variáveis
    private EditText Utilizador;
    private EditText Password;
    private Button Entrar;
    private CheckBox Check;
    private int cont=5;
    private ProgressBar Waiting;
    TextView Registar;
    TextView Recupera;
    FirebaseFirestore firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Utilizador = findViewById(R.id.user);
        Password = findViewById(R.id.password);
        Entrar = findViewById(R.id.entrar);
        Check = findViewById(R.id.checkBox);
        Waiting = findViewById(R.id.esperar_registo);
        Registar = findViewById(R.id.registo);
        Recupera = findViewById(R.id.recupera);

        Waiting.setVisibility(View.INVISIBLE);

        Entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validar(Utilizador.getText().toString(), Password.getText().toString());
                Waiting.setVisibility(ProgressBar.VISIBLE);

            }
        });

        Registar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Registar.class);
                startActivity(intent);

            }
        });

        Recupera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Recupera.class);
                startActivity(intent);
            }
        });

    }

    //Funções

    //Ao clicar no botão Login

    private void validar(String user, String pass){

            firebaseDatabase = FirebaseFirestore.getInstance();

            firebaseDatabase.collection("Pessoa").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {

                            if(user.equals(document.getData().get("mail")) && pass.equals(document.getData().get("senha"))){

                                Intent intent = new Intent(MainActivity.this,Log_screen.class);
                                intent.putExtra("user",Utilizador.getText().toString());
                                startActivity(intent);

                                boolean check = (Check).isChecked();

                                if (check) {

                                    Password.setText(pass);

                                } else {

                                    Password.setText("");

                                }

                            }else{

                                cont--;

                                Password.setText("");

                                Waiting.setVisibility(ProgressBar.VISIBLE);

                                Toast.makeText(MainActivity.this,"Tentativas restantes: " + cont,Toast.LENGTH_SHORT).show();

                                if(cont == 0) {

                                    Entrar.setEnabled(false);

                                    Toast.makeText(MainActivity.this, "Para teres acesso outra vez à tua conta altera a tua palavra-passe.", Toast.LENGTH_LONG).show();

                                }
                            }
                        }
                    } else {
                        createAndShowDialog(new Exception("Erro ao criar a aplicação! Por favor verifique o URL."), "Erro");
                    }
                }
            });
    }

    private void createAndShowDialog(final String message, final String title) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(message);
        builder.setTitle(title);
        builder.create().show();
    }

    private void createAndShowDialog(Exception exception, String title) {
        Throwable ex = exception;
        if(exception.getCause() != null){
            ex = exception.getCause();
        }
        createAndShowDialog(ex.getMessage(), title);
    }

}