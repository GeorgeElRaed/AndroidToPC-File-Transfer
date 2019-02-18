//package Add Package Name Here;

import android.os.AsyncTask;
import android.widget.EditText;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class FileSender extends AsyncTask<String,Void,Void>{

    private EditText IPedit;

    private MainActivity activity = null;

    private File ToSend;

    public FileSender(MainActivity mainActivity,File toSend){
        activity = mainActivity;
        this.ToSend = toSend;
    }

    protected Void doInBackground(String... voids) {
        Send();
        return null;
    }

    public void setToSend(File toSend) {
        ToSend = toSend;
    }

    public void Send() {
        try {
            IPedit = activity.findViewById(R.id.editText);
            String IP = IPedit.getText().toString();
            Socket socket = new Socket(IP, 2445);

            byte[] bytes = new byte[(int)ToSend.length()];

            InputStream in = new FileInputStream(ToSend);
            OutputStream out = socket.getOutputStream();

            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(out));
            bufferedWriter.write("FromMyPhone"+ToSend.getName());
            bufferedWriter.newLine();
            bufferedWriter.flush();

            out.write(bytes,0,in.read(bytes));

            socket.close();
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
