package server.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import base.game.network.Login;
import base.game.network.SelectorHandler;
import base.game.network.WaitingInfo;
import base.worker.NetworkWorker;
import base.worker.Worker;

public class ServerSelectorHandler implements SelectorHandler {

	Selector connected = null;
	ServerSocketChannel listener;
	private final int MTU;

	public ServerSelectorHandler(int MTU) {
		this.MTU = MTU;
	}

	private ServerSocketChannel createServerTCPSocketChannel(int port) {
		try {
			ServerSocketChannel listener = ServerSocketChannel.open();
			listener.configureBlocking(false);
			listener.socket().bind(new InetSocketAddress(port));
			return listener;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private void disconnectAndRemove(SelectionKey key) {
		try {
			key.channel().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		key.cancel();
	}

	private ArrayList<Worker> lookForInput() {
		ArrayList<Worker> ret = new ArrayList<>();
		try {
			connected.selectNow();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Set<SelectionKey> selectedKeys = connected.selectedKeys();

		Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

		NetworkWorker input;
		SelectionKey key;

		while (keyIterator.hasNext()) {
			System.out.println("have next!");
			key = keyIterator.next();
			if (key.isReadable()) {
				try {
					input = readWorker(key);
					if (input != null) {
						ret.add(input);
					} else {
						disconnectAndRemove(key);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					disconnectAndRemove(key);
				}
			}
		}
		if (!ret.isEmpty())
			System.out.println(ret.size());
		return ret;
	}

	private NetworkWorker readWorker(SelectionKey key) throws IOException {
		ByteBuffer buf = ByteBuffer.allocateDirect(MTU);
		buf.clear();
		SocketChannel channel = (SocketChannel) key.channel();
		int numBytesRead = channel.read(buf);
		System.out.println("read: " + numBytesRead + " bytes");
		buf.flip();

		while (numBytesRead != -1) {
			if (buf.hasRemaining()) {
				switch (buf.get()) {
				case -127:
					Login l = new Login();
					if (l.read(buf)) {
						l.setKey(key);
						return l;
					} else {
						return null;
					}
				}
			} else {
				System.out.println("Read zero bytes from channel. weird uh?");
				return null;
			}
		}
		return null;
	}

	@Override
	public boolean start() throws IOException {
		// Create the selectors
		connected = Selector.open();
		listener = createServerTCPSocketChannel(9999);

		if (listener == null)
			return false;
		return true;
	}

	@Override
	public ArrayList<Worker> update() throws IOException {

		SocketChannel accept = listener.accept();

		if (accept != null) {
			accept.configureBlocking(false);
			accept.register(connected, SelectionKey.OP_READ, new WaitingInfo(System.currentTimeMillis()));
			System.out.println("accepted something");
		}

		return lookForInput();
	}

}