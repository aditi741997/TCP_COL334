import java.net.*;
import java.util.*;
import java.lang.*;
import java.io.*;

class RecThread extends Thread{
	private Thread t;
	ArrayDeque<Packet> packet_q;
	ArrayDeque<String> receive_q;
	Integer window;

	RecThread(ArrayDeque<Packet> packet_q, ArrayDeque<String> receive_q, Integer window){
		this.packet_q = packet_q;
		this.receive_q = receive_q;
		this.window = window;
	}

	public void start(){
		if(t == null){
			t = new Thread(this, "RecThread");
			t.start();
		}
	}

	public void run(){
		byte[] buffer = new byte[1000];
		String receive_data;
		DatagramSocket socket_receive = new DatagramSocket(7777);
		DatagramPacket packet_receive = new DatagramPacket(buffer, 1000);
		
		while(true){
			int time = 1000;
			if(!packet_q.isEmpty()) time = packet_q.get(0).end_time;
			// set socket timeout
			// to be done!
			socket_receive.setSoTimeout(time);
			try{
				socket_receive.receive(packet_receive);
				receive_data = new String(packet_receive.getData(), 0, packet_receive.getLength());
				synchronized(receive_q){
					receive_q.add(receive_data);
				}
			}
			catch(SocketTimeoutException e){
				synchronized(packet_q) {
					packet_q.clear();
				}
				synchronized(window) {
					window = 1000;
				}
			}
		}
	}
}