import java.net.*;
import java.util.*;
import java.lang.*;
import java.io.*;

class Data{
	int start;
	int end;
	Data(int a, int b){
		start = a;
		end = b;
	}
};

class Receiver{

	public static void main(String[] args) throws Exception	{
		int last_receive = 0;
		int port = Integer.parseInt(args[0]);
		Receiver r = new Receiver();
		DatagramSocket socket_receive = new DatagramSocket(7777);
		DatagramSocket socket_send = new DatagramSocket(8888);
		LinkedList<Data> received_packets = new LinkedList<Data>();

		byte[] buff = new byte[1000];
		String data_receive, data_send;
		DatagramPacket packet_send;
		DatagramPacket packet_receive = new DatagramPacket(buff, 1000);

		while(true){
			socket_receive.receive(packet_receive);
			data_receive = new String(packet_receive.getData(), 0, packet_receive.getLength());
			String[] s = data_receive.split(" ");
			int start = Integer.parseInt(s[0]);
			int end = Integer.parseInt(s[1]) + start;
			int id = Integer.parseInt(s[2]);
			if(start == last_receive + 1) last_receive = end;
			else{
				Data d = new Data(start, end);
				received_packets.add(d);
			}
			boolean done = false;
			while(!done){
				boolean found = false;
				for(int i=0; i<received_packets.size(); ++i){
					Data d = received_packets.get(i);
					if(d.start == last_receive + 1){
						found = true;
						last_receive = d.end;
						received_packets.remove(i);
					}
				}
				if(!found) done = true;
			}
			data_send = id + " " + last_receive;
			packet_send = new DatagramPacket(data_send.getBytes(), data_send.length(), packet_receive.getAddress(), 8888);
			socket_send.send(packet_send);
		}
	}
}