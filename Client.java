import java.io.*;
import java.net.*;

class Client{

	BufferedReader br1;
	PrintWriter out;
	Socket socket;

	public Client(){

		try{
		System.out.println("Sending a Request");
		socket=new Socket("127.0.0.1",6969);
		System.out.println("Request Accepted");

		br1=new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out=new PrintWriter(socket.getOutputStream());

		startReading();
		startWriting();

	}catch(Exception e){
		System.out.println(e);
	}
	}


	public void startReading(){

	Runnable r1=()->{
		System.out.println("Reader is Started");
		try{
			while(true){
				String msg=br1.readLine();
				if(msg.equals("exit")){
				System.out.println("Server Stopped Interaction !..");							
				socket.close();
				break;	
				}
 
				System.out.println("Server:"+msg);
			}

		}catch(Exception e){
			System.out.println(e);
		}
	};
	new Thread(r1).start();

	}

	public void startWriting(){

	Runnable r2=()->{
		System.out.println("Writer is Started");
		try{
		while(!socket.isClosed()){
		BufferedReader br1=new BufferedReader(new InputStreamReader(System.in));
		String content=br1.readLine();
		out.println(content);
		out.flush();

		if(content.equals("exit")){
			socket.close();
			break;
		}
	

		}
		}catch(Exception e){
			System.out.println(e);
		}
	};
	new Thread(r2).start();
	
	}
	
		

	public static void main(String args[]){
		System.out.println("this is Client !");
		new Client();
	}
}
