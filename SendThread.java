import java.net.*;
import java.util.*;
import java.lang.*;
import java.io.*;

class SendThread extends Thread{
	private Thread t;
	ArrayDeque<Packet> packet_q;
	ArrayDeque<String> receive_q;
	Integer window, bytes_sent;
	Integer count;
	InetAddress receiver_IP;
	int receiver_Port;

	SendThread(ArrayDeque<Packet> packet_q, ArrayDeque<String> receive_q, Integer window, Integer bytes_sent, InetAddress receiver_IP, int receiver_Port){
		count = 0;
		this.packet_q = packet_q;
		this.receive_q = receive_q;
		this.window = window;
		this.bytes_sent = bytes_sent;
		this.receiver_IP = receiver_IP;
		this.receiver_Port = receiver_Port;
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
				// process the receiving queue
				String receive_data = receive_q.get(0);
				String[] data = receive_data.split(" ");
				int id = Integer.parseInt(data[0]);
				int ack = Integer.parseInt(data[1]);
				// find id in packet_q: -> delete, subtract from bytes_send the size of this pkt!!!
				// start + size < ack -> delete

				for(int i = packet_q.size()-1; i>=0; --i){
					Packet pkt = packet_q.get(i);
					if(pkt.start_num + pkt.length <= ack){
						synchronized(bytes_sent){
							bytes_sent -= pkt.length;
						}
						synchronized(receive_q){
							receive_q.remove(i);
						}
						ack_received = Math.max(ack_received, ack);
					}
				}

				synchronized(receive_q){
					receive_q.remove(0);
				}
				synchronized(window){
					window += 1000*1000/window;
				}
			}
			// send packets
			int i = 1000;
			while ((window - bytes_sent) >= 1000)
			{
				Packet p1 = Packet(System.nanoTime() + Math.pow(10,9),ack_received,i,count);
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

				synchronized(bytes_sent){
					bytes_sent += i;
				}
			}
			if (window - bytes_sent > 0)
			{
				i = window - bytes_sent;
				Packet p1 = Packet(System.nanoTime() + Math.pow(10,9),ack_received,i,count);
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

				synchronized(bytes_sent){
					bytes_sent += i;
				}
			}


			// if (System.nanoTime() >= (Packet)(q.getFirst()).end_time)
			// {
			// 	q = new ArrayDeque();
			// 	window = MSS;
			// }
			// for window size remaining ->

		}
	}
}
