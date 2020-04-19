import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    Socket socket;
    public void init() throws IOException {
        int porta = 9999;
        String host = "localhost";
        this.socket = new Socket(host,porta);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    escutar();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    escrever();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void escutar() throws IOException {
        Scanner entrada = new Scanner(this.socket.getInputStream());
        while(entrada.hasNextLine()){
            String mensagem = entrada.nextLine();
            System.out.println("Nova mensagem: "+mensagem);
        }
    }


    public void escrever() throws IOException {

        PrintStream saida = new PrintStream(this.socket.getOutputStream());

        Scanner input = new Scanner(System.in);

        System.out.println("Digite seu id: ");
        String meuid = input.nextLine();

        System.out.println("Digite o id de destino");
        String idDestino = input.nextLine();
        // meuid:'id_ira_fica_aqui'
        saida.println("meuid:"+meuid);

        String mensagem = "";

        while(!mensagem.contains("sair")){
            mensagem = idDestino+":";

            System.out.println("Digite sua mensagem: ");
            mensagem += input.nextLine();
            // idDestino:mensagem
            saida.println(mensagem);
        }

    }

    public static void main(String args[]) throws IOException {
        new Cliente().init();
    }
}
