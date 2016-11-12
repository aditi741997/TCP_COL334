import java.net.*;
import java.util.*;
import java.lang.*;
import java.io.*;

class Sender
{
	public InetAddress receiver_IP;
	public int receiver_Port;
	public int ack_received;
	public int window;
	public int bytes_sent;

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

	ArrayDeque<Packet> q;

	public static void main(String[] args)
	{
		Sender s = new Sender();
		// Date date = new Date();
		long curr_time = System.nanoTime();
		s.q = new ArrayDeque<Packet>();
		int MSS = 1000;
		try
		{
			s.window = MSS;
			s.receiver_IP = InetAddress.getByName(args[0]);
			s.receiver_Port = Integer.parseInt(args[1]);

			DatagramSocket client_skt = new DatagramSocket(s.receiver_Port);
			DatagramSocket client_skt_rec = new DatagramSocket(1729);

			Timer t = new Timer();
			int count = 0;

			Packet p = new Packet(System.nanoTime() + Math.pow(10,9),0,s.window,count);
			String str = p.to_String();
			DatagramPacket pkt = new DatagramPacket(str.getBytes(),str.length(),s.receiver_IP,s.receiver_Port);
			s.q.add(p);
			s.bytes_sent = s.window;
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
				if (System.nanoTime() >= (Packet)(s.q.getFirst()).end_time)
				{
					s.q = new ArrayDeque();
					s.window = MSS;
					Packet p1 = Packet(System.nanoTime() + Math.pow(10,9),s.ack_received,s.window,count);
					str = p1.to_String();
					count += 1;
					DatagramSocket pkt1 = new DatagramPacket(str.getBytes(),str.length(),receiver_IP,receiver_Port);
					s.q.add(p1);
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
				client_skt_rec.setSoTimeout();
				try
				{
					client_skt_rec.receive(rec_pkt);
					// parse karlo : TODO
				}
				catch (SocketTimeoutException e)
				{
// TODO
				}
			}

		}
		catch (Exception e)
		{
			System.out.println("Couldnt parse inputs! \n");			
		}
	}
}