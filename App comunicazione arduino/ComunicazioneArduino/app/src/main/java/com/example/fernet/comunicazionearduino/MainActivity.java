package com.example.fernet.comunicazionearduino;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private EditText ip;
    private EditText mess;
    private static boolean flag = false;
    private ObjectOutputStream out;
    private ObjectInputStream in;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ip = findViewById(R.id.ip);
        mess = findViewById(R.id.mess);

    }




    public void spediscialserver(View v) throws IOException {

        AsyncTaskRunner runner = new AsyncTaskRunner();
        runner.execute(ip.getText().toString());

    }




    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;

        @Override
        protected String doInBackground(String... params) {
            Socket socket = null;
            try {

                if(flag == false){
                    socket = new Socket(ip.getText().toString(),8080);
                   out = new ObjectOutputStream(socket.getOutputStream());
                   in = new ObjectInputStream(socket.getInputStream());
                   out.writeObject("[Client][123-234-357-1112]");
                    flag = true;
                }

                out.writeObject(mess.getText().toString());

            }catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(String result) {

        }


        @Override
        protected void onPreExecute() {
        }


        @Override
        protected void onProgressUpdate(String... text) {
        }
    }
}
