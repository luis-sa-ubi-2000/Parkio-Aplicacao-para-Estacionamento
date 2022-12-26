package com.ubi.parkio;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class NotificationFragment extends Fragment {

    Button add;
    AlertDialog dialogo;
    LinearLayout layout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notification, container, false);
        add = v.findViewById(R.id.btnaddforum);
        layout = v.findViewById(R.id.contentoreheh);
        buildDialog();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogo.show();
            }
        });
        // Inflate the layout for this fragment*/
        return v;
    }

    private void buildDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getLayoutInflater().inflate(R.layout.dialogo, null);

        final EditText mensagem = view.findViewById((R.id.edtMensagem));

        builder.setView(view);
        builder.setTitle("Mensagem").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addCard(mensagem.getText().toString());
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //dialogo.dismiss();
                    }
                });
        dialogo = builder.create();
    }

    private void addCard(String mensagem) {
        final View view = getLayoutInflater().inflate(R.layout.card, null);
        TextView mensagemView = view.findViewById(R.id.mensagem);

        mensagemView.setText(mensagem);

        layout.addView(view);
    }
}
