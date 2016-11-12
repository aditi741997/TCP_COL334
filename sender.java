import java.net.*;
import java.util.*;
import java.lang.*;
import java.io.*;

class Sender
{
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



	public static void main(String[] args)
	{
		// Date date = new Date();
		InetAddress receiver_IP;
		int receiver_Port;
		int ack_received;
		int window;
		int bytes_sent;
		ArrayDeque<Packet> q;
		ArrayDeque<String> rq;

		long curr_time = System.nanoTime();
		q = new ArrayDeque<Packet>();
		int MSS = 1000;
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