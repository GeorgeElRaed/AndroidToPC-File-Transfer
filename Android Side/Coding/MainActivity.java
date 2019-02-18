//package Add Package Name Here;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import java.io.File;



public class MainActivity extends AppCompatActivity {
    File file ;
    String[] FileNames;
    Spinner spinner;
    File ToSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        file =Environment.getExternalStorageDirectory();
        spinner = findViewById(R.id.spinner);
        redrawSpinner();
    }

    public void Next(View v){
        File file1 = new File(file.getAbsolutePath() + "/" +spinner.getSelectedItem().toString());
        if(file1.isDirectory()){
            file = file1;
            redrawSpinner();
        }
        else{
            ToSend = file1;
            new FileSender(this,ToSend).execute("");
            Toast.makeText(this,ToSend.getName() + " Has Been Sent!!",Toast.LENGTH_SHORT).show();

        }
    }

    public void Back(View v){
        if(file.getAbsolutePath().equals("/")){
            Toast.makeText(this, "Can't Go Back Further", Toast.LENGTH_SHORT).show();    
            return;
        }
        file = file.getParentFile();
        redrawSpinner();
    }


    private void redrawSpinner(){
        File[] Files = file.listFiles();
        FileNames = new String[Files.length];
        int i =0;
        for(File f : Files) {
            FileNames[i] = f.getName();
            i++;
        }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,FileNames);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        }

       /* private void getPreviousIP(){
        try {
            String IP;
            BufferedReader bufferedReader = new BufferedReader(new FileReader("./IP.txt"));
            ((EditText)findViewById(R.id.editText)).setText((IP=bufferedReader.readLine()) == null ? "IP Adress" : IP);
            bufferedReader.close();
        }catch (FileNotFoundException e){
            ((EditText)findViewById(R.id.editText)).setText("IP Address");
        }catch (Exception e){
            e.printStackTrace();
        }
        }*/


}
