import java.io.*;
import java.net.*;
import java.util.Random;
import java.util.Vector;


public class ServidorNomes implements Runnable{
	Socket csocket;
	ObjectOutputStream enviaMensagem;
	ObjectInputStream recebeMensagem;
	Vector<String> listaServidores;


	public ServidorNomes(Socket csocket) throws IOException {
		this.csocket = csocket;
		this.enviaMensagem = new ObjectOutputStream(csocket.getOutputStream());
		this.recebeMensagem = new ObjectInputStream(csocket.getInputStream());

	}
		
	public static void main(String[] args) throws Exception{
		
		ServerSocket welcomeSocket = null;
		ServerSocket conexaoServidor = null;
		Vector<String> listaServidores = new Vector<String>();
		
		
		try {
			welcomeSocket = new ServerSocket(456); 
			conexaoServidor = new ServerSocket(5567); 
			System.out.println("ONLINE");
			
			
			while(true){			
				
				//ACEITA CONECAO COM SERVIDOR PELA PORTA 5567
				try {
					Socket socketServidor = conexaoServidor.accept();
					ObjectOutputStream enviaMensagem = new ObjectOutputStream(socketServidor.getOutputStream());
					ObjectInputStream recebeMensagem = new ObjectInputStream(socketServidor.getInputStream());
					String ip;
					ip = (String) recebeMensagem.readObject();
					enviaMensagem.writeObject(ip+" foi");
					listaServidores.addElement(ip);
					System.out.println("Servidor adicionado a lista de nomes!" +listaServidores.toString()+" Estao conectados!");
				} catch (IOException e) {
					System.out.println("Nao conseguiu criar servidor na porta 5567. Saindo.");
				}
				
				
				//ACEITA CONEXAO COM CLIENTE PELA PORTA 456
				
				Socket connectionSocket = welcomeSocket.accept(); 
				ServidorNomes teste = new ServidorNomes(connectionSocket);
				teste.setListaServidores(listaServidores);
				new Thread(teste).start();
			}
	      } catch(IOException ioe) { 
	         System.out.println("Nao conseguiu criar servidor na porta 456. Saindo.");
	         System.exit(-1);
	      } finally {
			welcomeSocket.close();
		}

	}

	public void run(){

		try{
			System.out.println("Conexao com cliente feita com sucesso");

			//GERA UM NUMERO ALEATORIO PARA DIRECIONAR PARA O OUTRO SERVIDOR
			Random gerador = new Random(); 
			int numero = gerador.nextInt(listaServidores.size());
			System.out.println(numero); // NUMERO DE SERVIDORES CONECTADOS
			String ipServidor = "192";
			ipServidor = listaServidores.get(numero);
			System.out.println(ipServidor);
			enviaMensagem.writeObject(ipServidor); //RETORNA PARA O CLIENTE O IP DO SERVIDOR FIBONACCI
			Thread.yield(); 



		}catch (IOException ie) { }
	}

	public Vector<String> getListaServidores() {
		return listaServidores;
	}

	public void setListaServidores(Vector<String> listaServidores) {
		this.listaServidores = listaServidores;
	}
	
}