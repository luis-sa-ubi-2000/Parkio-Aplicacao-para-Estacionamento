package pt.ubi.di.pdm.login_registo;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Recupera extends AppCompatActivity {

    //Variáveis
    private EditText Utilizador;
    private Button Seguinte;
    private ProgressBar Waiting_Recupera;
    FirebaseFirestore firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recupera_pass);

        Utilizador = findViewById(R.id.email_recupera);
        Seguinte = findViewById(R.id.seguinte);
        Waiting_Recupera = findViewById(R.id.esperar_recupera);

        Waiting_Recupera.setVisibility(View.INVISIBLE);

        Seguinte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validar_mail(Utilizador.getText().toString());
                Waiting_Recupera.setVisibility(ProgressBar.VISIBLE);

            }
        });

    }

    //Funções

    //Ao clicar no botão Login

    private void validar_mail(String user){

        firebaseDatabase = FirebaseFirestore.getInstance();

        firebaseDatabase.collection("Pessoa").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        if(user.equals(document.getData().get("mail"))){

                            Intent intent = new Intent(Recupera.this,Recupera2.class);
                            intent.putExtra("id",document.getId());
                            startActivity(intent);

                        }else{

                            Utilizador.setText("");

                            Waiting_Recupera.setVisibility(ProgressBar.VISIBLE);

                            Toast.makeText(Recupera.this,"Não existe nenhum mail correspondente.",Toast.LENGTH_SHORT).show();

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
