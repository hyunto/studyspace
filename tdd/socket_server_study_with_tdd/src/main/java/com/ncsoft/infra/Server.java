package main.java.com.ncsoft.infra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.StringTokenizer;

public class Server {

	public static void main(String[] args) throws IOException, InterruptedException {
		ServerSocket server = new ServerSocket(9999);
		Socket client = null;

		client = server.accept();
		while (true) {

			/* Input Stream */
			InputStreamReader stream = new InputStreamReader(client.getInputStream());
			BufferedReader reader = new BufferedReader(stream);

			/* Output Stream */
			OutputStream out = client.getOutputStream();
			PrintWriter writer = new PrintWriter(out);

			/* Request Message */
			StringTokenizer request = new StringTokenizer(reader.readLine(), " ");
			String method = request.nextToken();
			String[] uri = request.nextToken().split("/");
			String version = request.nextToken();

			/* Response Message */
			String response = null;
			if (method.equals("GET")) {

				/* Download */
				if (uri[1].equals("download")) {
					System.out.println("download");
					String filename = uri[2];

				}

				/* Upload */
				else if (uri[1].equals("upload")) {
					System.out.println("upload");
					String filename = uri[2];

				}

				/* Server Stop */
				else if (uri[1].equals("quit")) {
					System.out.println("quit");
					response = "HTTP/1.1 200\r\nContent-Length: 50\r\n\r\nServer will be shutdown after 3 seconds... Bye~!";
					out.write(response.getBytes("UTF-8"));
					out.flush();
					Thread.sleep(3000);
					break;

				}

				/* Unknown Request */
				else {
					System.out.println("default");
					response = "HTTP/1.1 404\r\nContent-Length: 60\r\n\r\n'Usage: http://hostname:port/[upload | download]/<filename>'";
					out.write(response.getBytes("UTF-8"));
					out.flush();

				}
			}
		}

		client.close();
		server.close();
	}

}
