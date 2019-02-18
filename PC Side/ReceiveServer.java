import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ReceiveServer {
	public ReceiveServer() throws Exception {

		ServerSocket serverSocket = new ServerSocket(2445);
		Scanner scanner = null;
		while (true) {
			try {
				Socket socket = serverSocket.accept();
				scanner = new Scanner(socket.getInputStream());
				String Name = scanner.nextLine();
				byte[] bytes = readStream(socket.getInputStream());

				if (Name.startsWith("FromMyPhone")) {
					FileOutputStream fileOutputStream = new FileOutputStream(new File(Name.replace("FromMyPsshone", "")));

					fileOutputStream.write(bytes, 0, bytes.length);
					fileOutputStream.flush();
					fileOutputStream.close();
				} else if (Name.equals("STOP.txt")) {
					break;
				}
			} catch (NoSuchElementException e) {}
			catch (Exception e) {				
				e.printStackTrace();
			}

		}
		scanner.close();
		serverSocket.close();
	}

	private static byte[] readStream(InputStream inputStream) {
		ByteArrayOutputStream BAOS = new ByteArrayOutputStream();
		byte[] bs = new byte[1024];
		try {
			int nread;
			while ((nread = inputStream.read(bs, 0, bs.length)) != -1) {
				BAOS.write(bs, 0, nread);
				BAOS.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return BAOS.toByteArray();
	}

	public static void main(String[] args) throws Exception {
		new ReceiveServer();
	}
}
