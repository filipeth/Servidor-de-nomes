import java.io.*;
import java.net.*;
public class Servidor implements Runnable{
	Socket socket;
	ObjectOutputStream enviaMensagem;
	ObjectInputStream recebeMensagem;

	
	public Servidor(Socket socket) throws IOException {
		this.socket= socket;
		this.enviaMensagem = new ObjectOutputStream(socket.getOutputStream());
		this.recebeMensagem = new ObjectInputStream(socket.getInputStream());
	}
	
	public static void main(String[] args) throws Exception {
		ServerSocket welcomeSocket = null;
		String ipServidor = null;
		ObjectOutputStream enviaMensagem1;
		ObjectInputStream recebeMensagem1;
		try {
			welcomeSocket = new ServerSocket(5566); 
			//O ENDEREÇO DE IP DO SERVIDOR DE NOMES DEVE SER INSERIO AQUI
			ipServidor = "192.168.43.133";
		
				
			//INICIANDO COMUNICACAO COM O SERVIDOR DE NOMES PORTA 5566
			Socket socketCliente = 	new Socket(ipServidor, 5567);
			if (socketCliente.isConnected());
				System.out.println("Servidor Conectado a Servidor de Nomes");
			System.out.println("ONLINE");
			recebeMensagem1 = new ObjectInputStream(socketCliente.getInputStream());
			enviaMensagem1 = new ObjectOutputStream(socketCliente.getOutputStream());
			enviaMensagem1.writeObject(ipServidor); //ENVIA PARA SERVIDOR DE NOMES ENDERECO ATUAL
			ipServidor = (String)recebeMensagem1.readObject(); //RECEBE CONFIRMACAO
			socketCliente.close(); 			
			System.out.println(ipServidor);
			
			//ACEITA A CONECAO COM O CLIENTE PORTA 5566
			while(true){
				Socket connectionSocket = welcomeSocket.accept(); 
				System.out.println("cliente conectado");
				new Thread(new Servidor(connectionSocket)).start();
			}
	      } catch(IOException ioe) { 
	         System.out.println("Nao conseguiu criar servidor na porta 5566. Saindo.");
	         System.exit(-1);
	      } 


	}
	
	
	public void run(){
		System.out.println("Conexao com cliente feita com sucesso");
		try{
			while (true){


				//LE O NUMERO ENVIADO PELO CLIENTE NA PORTA 5566
				int num = (Integer) recebeMensagem.readObject();
				if(num!=0){
					System.out.println("\n NUMERO INFORMADO PELO CLIENTE: " + num);
					num = fibonacci(num);
					System.out.println("\n ENVIANDO PARA CLIENTE RESULTADO: " + num);
					//ENVIA NA PORTA 5566 O RESULTADO DA OPERAÇÃO
					enviaMensagem.writeObject(num);
					Thread.yield();

				}
			}
		}catch (IOException ie) { } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//METODO PARA CALCULAR O FIBONACCI
	public static int fibonacci(int num) {
		if (num < 2) {
			return num;
		} else {
			return fibonacci(num - 1) + fibonacci(num - 2);
		}
	}
		
}