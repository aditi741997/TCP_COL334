	class SendThread extends Thread
	{
		
		private Thread t;

		public void run()
		{
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

	}
