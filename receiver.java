import java.net.*;
import java.util.*;
import java.lang.*;
import java.io.*;

class receiver
{
	private int Ack_number;
	int Port;

	receiver(int port)
	{
		Port = port;
		Ack_number = 0;
	}

	void updateAck()
	{
        byte[] receive_buff = new byte[1300];
        DatagramPacket rec_pkt = new DatagramPacket(receive_buff,receive_buff.length);
		DatagramSocket rec = new DatagramSocket(Port);

		while (true)
		{
			// receive, send back an ACK pkt
		}
	}
	
	public static void main(String[] args)
	{
		r.updateAck();
	}
}