package com.ncsoft.infra.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) throws IOException {
		ServerSocket server = null;
		
		try{
			server = new ServerSocket();
			server.bind(new InetSocketAddress(9999));
		} catch(IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		while(true) {
			Socket client = null;
			InputStream in = null;
			OutputStream out = null;
			
			client = server.accept();
			in = client.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			out = client.getOutputStream();
			
			String line = null;
			StringBuffer sb = new StringBuffer();
			char[] buff = new char[20480000];
			int length = reader.read(buff);
			
			System.out.println(length + ":" + String.valueOf(buff));
		}
		

		/*
		String line = null;
		StringBuffer sb = new StringBuffer();
		char[] buff = new char[204800];
		int length = reader.read(buff);
		System.out.println(length + ":" + String.valueOf(buff));
		
		*/

//		String response = "HTTP/1.1 200\r\nContent-Length: 15\r\n\r\n{'test':'test'}";
//		out.write(response.getBytes("UTF-8"));
//		out.flush();
//		client.close();

	}

}
