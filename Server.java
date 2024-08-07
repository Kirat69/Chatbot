import java.io.*;
import java.net.*;

class Server{

	ServerSocket server;
	Socket socket;
	BufferedReader br;
	PrintWriter out;

	public Server(){
		
		try{
			server=new ServerSocket(6969);
			System.out.println("Server is ready to connect");
			System.out.println("Waiting for Signal");
			socket=server.accept();

			br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
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
				String msg=br.readLine();
				if(msg.equals("exit")){
				System.out.println("Client Stopped Interaction !..");	
				socket.close();
				break;	
				}
 
				System.out.println("CLient:"+msg);
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
		System.out.println("This is a Server");
		new Server();
	}
}
