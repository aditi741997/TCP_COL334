import java.net.*;
import java.util.*;
import java.lang.*;
import java.io.*;

class Packet{
	long end_time;
	int start_num;
	int length;
	int id;

	Packet(long ctime, int start, int len, int i)
	{
		end_time = ctime;
		start_num = start;
		length = len;
		id = i;
	}

	String to_String()
	{
		String s = "";
		s += start_num + " " + length + " " + id;
		return s;
	}
}


class Sender
{
	public static void main(String[] args)
	{
		InetAddress receiver_IP;
		int receiver_Port;
		int ack_received;
		Integer window;
		int bytes_sent;
		int MSS = 1000;

		ArrayDeque<Packet> packet_q = new ArrayDeque<Packet>();
		ArrayDeque<String> receive_q = new ArrayDeque<String>();

		SendThread sender = new SendThread(packet_q, receive_q, window);
		RecThread receiver = new RecThread(packet_q, receive_q, window);

		sender.start();
		receiver.start();

		long curr_time = System.nanoTime();
		try
		{
			window = MSS;
			receiver_IP = InetAddress.getByName(args[0]);
			receiver_Port = Integer.parseInt(args[1]);

			DatagramSocket client_skt = new DatagramSocket(receiver_Port);
			DatagramSocket client_skt_rec = new DatagramSocket(1729);

			Timer t = new Timer();
			int count = 0;

			Packet p = new Packet(System.nanoTime() + Math.pow(10,9),0,window,count);
			String str = p.to_String();
			DatagramPacket pkt = new DatagramPacket(str.getBytes(),str.length(),receiver_IP,receiver_Port);
			q.add(p);
			bytes_sent = window;
			try
			{
				client_skt.send(pkt);
			}
			catch(Exception e)
			{
				System.out.println("Error in client while sending \n");
			}

			count += 1;

			byte[] receive_buff = new byte[p];
			DatagramPacket rec_pkt = new DatagramPacket(receive_buff,p);



			while (true)
			{
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
		catch (Exception e)
		{
			System.out.println("Couldnt parse inputs! \n");			
		}
	}
}