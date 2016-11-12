	class RecThread extends Thread
	{
		client_skt_rec.setSoTimeout();
		try
		{
			client_skt_rec.receive(rec_pkt);
			// parse karlo : TODO
			// packet -> string 
			// 1st pkt 3, rec ack 5 -> all pkts till 5 rec : 
		}
		catch (SocketTimeoutException e)
		{
// TODO
			// 	q = new ArrayDeque();
			// 	window = MSS;

		}

	}