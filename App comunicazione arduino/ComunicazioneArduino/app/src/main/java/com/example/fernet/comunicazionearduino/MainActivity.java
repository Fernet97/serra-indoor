package com.example.fernet.comunicazionearduino;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    private EditText conip;
    private EditText arduinoRisp;

    private GridLayout grid;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private String messFromArduino;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        conip = findViewById(R.id.conip);
        conip.setText("easy-fut5al.homepc.it");

        arduinoRisp = findViewById(R.id.risparduino);
    }




    public void connectToServer(View v) throws IOException {

        AsyncTaskRunner runner = new AsyncTaskRunner();
        runner.execute(conip.getText().toString());

    }



    public void spedisciAlSever(View v) throws IOException {

        AsyncTaskRunnerSend runner2 = new AsyncTaskRunnerSend();
        Button b = (Button) v;
        runner2.execute(b.getText().toString());

    }




    private class AsyncTaskRunnerSend extends AsyncTask<String, String, String> {

        private String resp;

        @Override
        protected String doInBackground(String... params) {

            try {
                out.writeObject(params[0]);

                messFromArduino = (String) in.readObject();
                System.out.println("Messaggui da arduino:"+messFromArduino);


            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(String result) {
            arduinoRisp.setText(messFromArduino);

        }


        @Override
        protected void onPreExecute() {
        }


        @Override
        protected void onProgressUpdate(String... text) {
        }
    }






    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;

        @Override
        protected String doInBackground(String... params) {
            Socket socket = null;
            try {

                    String hostname = "easy-fut5al.homepc.it";
                    InetAddress addr = InetAddress.getByName(hostname);
                    String ip =  addr.toString().substring(addr.toString().indexOf('/')+1);

                    socket = new Socket(ip,8080);


                   out = new ObjectOutputStream(socket.getOutputStream());
                   in = new ObjectInputStream(socket.getInputStream());
                   out.writeObject("[Client][123-234-357-1112]");
                } catch (UnknownHostException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            // out.writeObject(mess.getText().toString());

            return null;
        }


        @Override
        protected void onPostExecute(String result) {
            // Connessione effettuata
            Toast.makeText(getApplicationContext(), "Connessione effettuata!!!", Toast.LENGTH_LONG).show();
        }


        @Override
        protected void onPreExecute() {
        }


        @Override
        protected void onProgressUpdate(String... text) {
        }
    }
}
