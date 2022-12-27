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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Registar extends AppCompatActivity {

    private ProgressBar Waiting_Registo;
    private Button Regista;
    private EditText Email;
    private EditText Pass;
    private EditText Pass2;
    private EditText Data_Aniversario;
    FirebaseFirestore firebaseDatabase;
    CollectionReference insere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registar);

        Waiting_Registo = findViewById(R.id.esperar_registo);
        Regista = findViewById(R.id.entrar);
        Email = findViewById(R.id.email);
        Pass = findViewById(R.id.pass);
        Pass2 = findViewById(R.id.pass2);
        Data_Aniversario = findViewById(R.id.data_aniversario);

        Waiting_Registo.setVisibility(View.INVISIBLE);


        Regista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regista(Email.getText().toString(), Pass.getText().toString(), Pass2.getText().toString(), Data_Aniversario.getText().toString());
                Waiting_Registo.setVisibility(ProgressBar.VISIBLE);

            }

        });

    }

    //Ao clicar no botão Registar

    private void regista(String user, String pass, String pass2, String data) {

        firebaseDatabase = FirebaseFirestore.getInstance();

        insere = firebaseDatabase.collection("Pessoa");

        Pessoa utilizador = new Pessoa(user, pass, data);

            if (pass.equals(pass2)) {

                insere.add(utilizador).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {

                    public void onSuccess(DocumentReference documentReference) {
                        // after the data addition is successful
                        // we are displaying a success toast message.
                        Toast.makeText(getApplicationContext(), "Utilizador registado!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(Registar.this, MainActivity.class);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // this method is called when the data addition process is failed.
                        // displaying a toast message when data addition is failed.
                        createAndShowDialog(new Exception("Erro ao criar a aplicação! Por favor verifique o URL."), "Erro");
                    }
                });

            } else {

                Toast.makeText(this, "As palavras-passe não coincidem", Toast.LENGTH_LONG).show();

                Pass2.setText("");

            }
    }

    private void createAndShowDialog(final String message, final String title) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(message);
        builder.setTitle(title);
        builder.create().show();
    }

    private void createAndShowDialog(Exception exception, String title) {
        Throwable ex = exception;
        if (exception.getCause() != null) {
            ex = exception.getCause();
        }
        createAndShowDialog(ex.getMessage(), title);
    }

}