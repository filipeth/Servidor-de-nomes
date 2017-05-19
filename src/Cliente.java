import java.io.*;
import java.net.*;
import java.util.Scanner;
public class Cliente {

	public static void main(String[] args) throws Exception {
		Scanner scanner = new Scanner(System.in);
		ObjectOutputStream enviaMensagem;
		ObjectInputStream recebeMensagem;
		String ipServidor;
		
		//INICIA UMA CONEÇAO COM O SERVIDOR NA PORTA 456 COM O HOST LOCAL
		ipServidor = "192.168.43.133"; //ENDERECO DO SERVIDOR DE NOMES
		Socket socketCliente = 	new Socket(ipServidor, 456);
		System.out.println("Conexao com Servido de Nomes realizada com sucesso \n");

		//INICIA VARIAVEIS DE ENTRADA E SAIDA DOS DADOS
		recebeMensagem = new ObjectInputStream(socketCliente.getInputStream());
		enviaMensagem = new ObjectOutputStream(socketCliente.getOutputStream());

		//RECEBE IP DO SERVIDOR DE NOMES
		ipServidor = (String)recebeMensagem.readObject();
		socketCliente.close(); 
		System.out.println(ipServidor);
	
		Socket socketCliente1 = new Socket(ipServidor, 5566);
		//OPÇAO PARA ENVIAR MENSAGEM
		recebeMensagem = new ObjectInputStream(socketCliente1.getInputStream());
		enviaMensagem = new ObjectOutputStream(socketCliente1.getOutputStream());


		System.out.println("Ola do cliente para servidor fibonacci \n");


		
		System.out.println("Digite o numero para calcular o Fibonacci: ");
		int num = scanner.nextInt();
		
		
		System.out.println("Enviando para o SERVIDOR " + num + "\n");
		//ENVIA PARA SERVIDOR O NUMERO DIGITADO PELO USUARIO
		enviaMensagem.writeObject(num);
		//LE O NUMERO ENVIADO PELO SERVIDOR COM O RESULTADO DO FIBONACCI
		num = (Integer) recebeMensagem.readObject();
		System.out.println("Recebendo do SERVIDOR " + num + "\n");
		//FECHA A CONEXAO COM O SERVIDOR
		socketCliente1.close();
		scanner.close();
	
	}
	
}
