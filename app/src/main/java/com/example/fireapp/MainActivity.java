package com.example.fireapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fireapp.model.Musica;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {


    FirebaseFirestore db = FirebaseFirestore.getInstance();

    EditText name;
    EditText album;
    EditText link;
    EditText avaliacao;

    TextView nomeDel;
    TextView idDel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = (EditText) findViewById(R.id.inputName);
        album = (EditText) findViewById(R.id.inputAlbum);
        link = (EditText) findViewById(R.id.inputLink);
        avaliacao = (EditText) findViewById(R.id.inputAvalicao);

        nomeDel = (TextView) findViewById(R.id.textNameMusicToBeDeleted);
        idDel = (TextView) findViewById(R.id.textIDMusicToBeDeleted);
    }

    public void createMusica(View v) {
        Musica musica = new Musica(name.getText().toString(), album.getText().toString(), link.getText().toString(), Double.parseDouble(avaliacao.getText().toString()));

        db.collection("appMusicas")
                .add(musica)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        DocumentReference docRef = db.collection("appMusicas").document(documentReference.getId());
                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        nomeDel.setText(document.getData().get("nome").toString());
                                        idDel.setText(document.getId());
                                    } else {
                                        nomeDel.setText("Música não encontrada");
                                        idDel.setText("Música não encontrada");
                                    }
                                } else {
                                    nomeDel.setText("Falha ao recuperar a música");
                                    idDel.setText("Falha ao recuperar a música");
                                }
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    public void deleteMusica(View v) {
        db.collection("appMusicas").document(idDel.getText().toString())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Context ctx = getApplicationContext();
                        CharSequence text = "Música Deletada com Sucesso";
                        int duration = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(ctx, text, duration);
                        toast.show();
                        nomeDel.setText("");
                        idDel.setText("");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Context ctx = getApplicationContext();
                        CharSequence text = "Falha ao Deletar Música";
                        int duration = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(ctx, text, duration);
                        toast.show();
                    }
                });
    }
}