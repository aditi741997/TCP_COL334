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

	class Packet{
		Date end_time;
		int start_num;
		int length;
		int id;
	}

	ArrayDeque<Packet> q;

	public static void main(String[] args)
	{
		Sender s = new Sender();
		s.q = new ArrayDeque<Packet>();
		int MSS = 1000;
		try
		{
			s.receiver_IP = InetAddress.getByName(args[0]);
			s.receiver_Port = Integer.parseInt(args[1]);			
		}
		catch (Exception e)
		{
			System.out.println("Couldnt parse inputs! \n");			
		}

		while (s.ack_received < 100000)
		{

		}
	}
}