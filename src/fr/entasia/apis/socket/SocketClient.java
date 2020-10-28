package fr.entasia.apis.socket;


import fr.entasia.apis.utils.ServerUtils;
import fr.entasia.errors.LibraryException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;


public class SocketClient {

	public static Logger logger = Logger.getLogger("SocketClient");
	public static boolean isConnected = false;
	public static boolean firstStartDone = false; // pour savoir dans quel Thead on est
	public static boolean run = true;
	public static ArrayList<SocketEvent> eventListeners = new ArrayList<>();
	public static java.net.Socket serverSocket = null;
	public static BufferedReader in;
	public static PrintWriter out;
	public static Thread whileThread;

	protected static String host;
	protected static int port;

	public static boolean init() {

		whileThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try{
					startSocket();
				}catch(Throwable e){
					errorHandler(e);
				}
			}
			public void startSocket() {
				String msg;
				while (run) {
					if (isConnected) {
						try {
							msg = in.readLine();
							if (msg == null) throw new IOException("Disconnected from server");
						} catch (IOException e) {
							errorHandler(e);
							continue;
						}

						String[] arg = msg.split(" ");
						String signature = arg[0];
						arg = Arrays.copyOfRange(arg, 1, arg.length);
						if (SocketSecurity.verifyMsg(String.join(" ", arg), signature)) {
							String key = arg[0];
							arg = Arrays.copyOfRange(arg, 1, arg.length);
							for (SocketEvent se : eventListeners) {
								if (se.key.equals(key)) {
									try {
										se.onEvent(arg);
									} catch (Throwable e) {
										e.printStackTrace();
										logger.info("Erreur détectée dans un event ! Contenu du paquet : " + msg);
									}
								}
							}
						} else {
							errorHandler(new LibraryException("Invalid message signature : " + msg));
						}
					} else {
						try {
							Thread.sleep(250);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		});
		whileThread.start();

		if(connect())return true;
		else{
			run = false;
			return false;
		}
	}

	public static boolean connect() {
		try {
			logger.info("Connexion au serveur..");
			serverSocket = new Socket();
			serverSocket.connect(new InetSocketAddress(host, port), 10000);
			logger.info("Connecté au serveur !");
			out = new PrintWriter(serverSocket.getOutputStream());
			in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
			isConnected = true;
			sendData("log "+ ServerUtils.serverName);
			out.flush();
			firstStartDone = true;
		} catch (Exception e) {
			errorHandler(e);
			isConnected = false;
			return false;
		}
		return true;
	}


	public static void sendData(String str){
		if(isConnected){
			String signature = SocketSecurity.signMsg(str);
			out.println(signature+" "+str);
			out.flush();
		}
	}

	public static void addListener(SocketEvent evt){
		eventListeners.add(evt);
	}

	private static void errorHandler(Throwable e) {
		isConnected = false;
		if(serverSocket!=null){
			try {
				serverSocket.close();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		}
		if(e.getMessage().equalsIgnoreCase("Disconnected from server")) {
			logger.info("Déconnecté du serveur !");
		} else if (e.getMessage().contains("Connection refused")) {
			logger.info("Connexion au serveur refusée !");
		}else{
			logger.info("Une erreur inconnue est survenue !");
			e.printStackTrace();
		}

		if(firstStartDone){
			logger.info("Tentative de reconnexion dans 5 secondes...");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException ignore) {
				run = false;
				return;
			}
			connect();
		}else whileThread.interrupt();
	}
}