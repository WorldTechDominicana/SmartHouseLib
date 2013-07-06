package smarthouselib.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 * @author cameri
 * @since 6/24/13
 */
public class Server
{
  private ServerSocket serverSocket;
  private boolean started;
  private Thread serverThread;
  private int clientCount = 0;

  public Server(int port) throws IOException
  {
    serverSocket = new ServerSocket(port);
  }

  public int getClientCount()
  {
    return clientCount;
  }

  public boolean isStarted()
  {
    return started;
  }

  private void setStarted(boolean started)
  {
    this.started = started;
  }

  public Thread getServerThread()
  {
    return serverThread;
  }

  private void setServerThread(Thread serverThread)
  {
    this.serverThread = serverThread;
  }

  public synchronized void setClientCount(int clientCount)
  {

    this.clientCount = clientCount;
    System.out.println("New client count: " + this.clientCount);
  }

  // This server starts on a seperate thread so you can still do other things on the server program
  public boolean start()
  {
    if (!isStarted())
    {
      setStarted(true);
      setServerThread(new Thread()
      {
        public void run()
        {
          while (Server.this.isStarted())
          {
            Socket clientSocket = null;
            try
            {
              clientSocket = serverSocket.accept();
              openClient(clientSocket);
            } catch (SocketException e)
            {
              System.err.println("Server closed.");
            } catch (IOException e)
            {
              System.err.println("Accept failed.");
            }
          }
        }
      });
      getServerThread().start();
      return true;
    }
    return false;
  }

  public boolean stop()
  {
    if (!isStarted())
      return false;
    this.setStarted(false);
    getServerThread().interrupt();
    try
    {
      serverSocket.close();
      return true;
    } catch (IOException ex)
    {
      System.err.println("Server stop failed.");
      System.exit(1);
    }
    return false;
  }

  private void openClient(final Socket socket)
  {
    try
    {

      new ServerChildThread(this, socket).start();
    } catch (IOException e)
    {
      e.printStackTrace();
    }
    /*
    new Thread() {
      public void run() {
        try {
          setClientCount(getClientCount() + 1);
          PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
          BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()),1024);
          String inputLine;

          while ((inputLine = in.readLine()) != null) {
            Command c = Command.deserialize(inputLine);
            if (c == null) {
              System.out.println("Invalid message received: " + inputLine);
              continue;
            } else if (Parse(s, )) {


            }

          }
          setClientCount(getClientCount() - 1);
          out.close();
          in.close();
          socket.close();
        } catch (IOException ex) {
          System.err.println("Client communication failure.");
        }
      }
    }.start();*/
  }


}
