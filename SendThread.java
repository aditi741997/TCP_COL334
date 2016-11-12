import java.net.*;
import java.util.*;
import java.lang.*;
import java.io.*;

class SendThread extends Thread{
	private Thread t;
	ArrayDeque<Packet> packet_q;
	ArrayDeque<String> receive_q;
	Integer window;

	SendThread(ArrayDeque<Packet> packet_q, ArrayDeque<String> receive_q, Integer window){
		this.packet_q = packet_q;
		this.receive_q = receive_q;
		this.window = window;
	}

	public void start(){
		if(t == null){
			t = new Thread(this, "SendThread");
			t.start();
		}
	}

	public void run(){
		DatagramSocket socket_send = new DatagramSocket(7777);
		DatagramPacket packet_receive;

		while (true){
			while(!receive_q.isEmpty()){
				String receive_data = receive_q.get(0);
				// process the receiving queue

				synchronized(receive_q){
					receive_q.remove(0);
				}
				synchronized(window){
					window += 1000*1000/window;
				}
			}
			// send packets


			// if (System.nanoTime() >= (Packet)(q.getFirst()).end_time)
			// {
			// 	q = new ArrayDeque();
			// 	window = MSS;
			// }
			for window size remaining ->
				Packet p1 = Packet(System.nanoTime() + Math.pow(10,9),ack_received,window,count);
				str = p1.to_String();
				count += 1;
				DatagramSocket pkt1 = new DatagramPacket(str.getBytes(),str.length(),receiver_IP,receiver_Port);
				q.add(p1);
				try
				{
					client_skt.send(pkt1);
					count += 1;
				}
				catch(Exception e)
				{
					System.out.println("Error in client while sending \n");
				}

		}
	}
}
