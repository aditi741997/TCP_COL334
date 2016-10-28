import java.net.*;
import java.util.*;
import java.lang.*;
import java.io.*;

class sender
{
	private static InetAddress receiver_IP;
	private static int receiver_Port;
	public static void main(String[] args)
	{
		receiver_IP = InetAddress.getByName(args[0]);
		receiver_Port = Integer.parseInt(args[1]);
	}
}