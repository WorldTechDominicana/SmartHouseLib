package smarthouselib.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author cameri
 * @since 6/24/13
 */
public class Client
{
  Socket socket = null;
  PrintWriter out = null;
  BufferedReader in = null;
  private String host;
  private int port;

  public Client(String host, int port)
  {
    this.port = port;
    this.host = host;
  }

  public void connectServer() throws UnknownHostException, IOException
  {
    socket = new Socket(host, port);
    out = new PrintWriter(socket.getOutputStream(), true);
    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
  }

  public String receiveMessage()
  {
    try
    {
      return in.readLine();
    } catch (IOException ex)
    {
      return null;
    }
  }

  public void sendMessage(String message)
  {
    out.println(message);
  }

  public void disconnect()
  {
    try
    {
      in.close();
      out.close();
      socket.close();
    } catch (IOException e)
    {
      System.err.println("Failed to disconnect");
      System.exit(1);
    }
  }

  @Override
  public String toString()
  {
    return "Client{" +
      "host='" + host + '\'' +
      ", port=" + port +
      '}';
  }
}
