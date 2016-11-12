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
		Integer bytes_sent;
		int MSS = 1000;

		ArrayList<Packet> packet_q = new ArrayList<Packet>();
		ArrayList<String> receive_q = new ArrayList<String>();

		long curr_time = System.nanoTime();
		try
		{
			window = MSS;
			receiver_IP = InetAddress.getByName(args[0]);
			receiver_Port = Integer.parseInt(args[1]);

			// DatagramSocket client_skt = new DatagramSocket(receiver_Port);
			// DatagramSocket client_skt_rec = new DatagramSocket(1729);

			Timer t = new Timer();

			Packet p = new Packet(System.nanoTime() + Math.pow(10,9),0,window,0);
			String str = p.to_String();
			DatagramPacket pkt = new DatagramPacket(str.getBytes(),str.length(),receiver_IP,receiver_Port);
			packet_q.add(p);
			bytes_sent = window;
			try
			{
				client_skt.send(pkt);
			}
			catch(Exception e)
			{
				System.out.println("Error in client while sending \n");
			}

			SendThread sender = new SendThread(packet_q, receive_q, window, bytes_sent, receiver_IP, receiver_Port);
			RecThread receiver = new RecThread(packet_q, receive_q, window, bytes_sent);

			sender.start();
			receiver.start();


		}
		catch (Exception e)
		{
			System.out.println("Couldnt parse inputs! \n");			
		}
	}
}