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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Recupera2 extends AppCompatActivity {

    private ProgressBar Waiting_Recupera;
    private Button Concluir;
    private EditText Pass;
    private EditText Pass2;
    FirebaseFirestore firebaseDatabase;
    DocumentReference atualiza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recupera_pass2);

        Waiting_Recupera = findViewById(R.id.esperar_recupera2);
        Concluir = findViewById(R.id.concluir);
        Pass = findViewById(R.id.nova_pass);
        Pass2 = findViewById(R.id.nova_pass2);

        Waiting_Recupera.setVisibility(View.INVISIBLE);


        Concluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validar_pass(Pass.getText().toString(), Pass2.getText().toString());
                Waiting_Recupera.setVisibility(ProgressBar.VISIBLE);

            }

        });
    }

    //Ao clicar no botão Registar

    private void validar_pass(String pass, String pass2) {

        firebaseDatabase = FirebaseFirestore.getInstance();

        Intent intent_id = getIntent();
        String user_id = intent_id.getStringExtra("id");

        atualiza = firebaseDatabase.collection("Pessoa").document(user_id);

        if (pass.equals(pass2)) {

            atualiza.update("senha", pass).addOnSuccessListener(new OnSuccessListener<Void>() {

                public void onSuccess(Void aVoid) {
                    // after the data addition is successful
                    // we are displaying a success toast message.
                    Toast.makeText(getApplicationContext(), "Palavra-passe alterada!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Recupera2.this, MainActivity.class);
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
        if(exception.getCause() != null){
            ex = exception.getCause();
        }
        createAndShowDialog(ex.getMessage(), title);
    }

}
