package smarthouselib.net;

import smarthouselib.net.dto.Command;
import smarthouselib.net.dto.CommandType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author cameri
 * @since 7/6/13
 */
public class ServerChildThread extends java.lang.Thread
{

  private PrintWriter writer;
  private BufferedReader reader;
  private Socket socket;
  private Server server;

  public PrintWriter getWriter()
  {
    return writer;
  }

  protected void setWriter(PrintWriter writer)
  {
    this.writer = writer;
  }

  public BufferedReader getReader()
  {
    return reader;
  }

  protected void setReader(BufferedReader reader)
  {
    this.reader = reader;
  }

  public Socket getSocket()
  {
    return socket;
  }

  protected void setSocket(Socket socket)
  {
    this.socket = socket;
  }

  public Server getServer()
  {
    return server;
  }

  protected void setServer(Server server)
  {
    this.server = server;
  }

  public ServerChildThread(Server server, Socket socket) throws IOException
  {
    super();
    setServer(server);
    setSocket(socket);
    setWriter(new PrintWriter(getSocket().getOutputStream(), true));
    setReader(new BufferedReader(new InputStreamReader(getSocket().getInputStream()), 1024));
  }

  public void run()
  {
    String inputLine;
    Command c;
    try
    {
      while (getSocket().isConnected())
      {
        while (getReader().ready())
        {
          inputLine = getReader().readLine();
          c = Command.deserialize(inputLine);
          if (c == null)
          {
            System.out.println("Invalid message received: " + inputLine);
          } else if (Parse(c))
          {
            System.out.println("Message handled: " + inputLine);
          } else
          {
            System.out.println("Unhandled message: " + inputLine);
          }
        }
      }
    } catch (IOException e)
    {

    }

    try
    {
      getWriter().close();
      getReader().close();
      getSocket().close();
    } catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  private boolean Parse(Command command)
  {
    switch (command.getType())
    {
      case Ping:
        return OnPing(command);
      case PingReply:
        return OnPingReply(command);
      case Shutdown:
        return OnShutdown(command);
      case Quit:
        return OnQuit(command);
      case Nack:
      case Ack:
      case Unknown:
      default:
        return false;
    }
  }

  private boolean sendCommand(Command command)
  {
    getWriter().write(Command.serialize(command));
    return true;
  }

  private boolean OnPing(Command command)
  {
    // send ping back
    Command reply = new Command();
    reply.setType(CommandType.PingReply);
    reply.setPayload(command.getPayload());
    this.sendCommand(reply);
    return true;
  }

  private boolean OnPingReply(Command command)
  {
    // Does not need ACK or anything
    //Command reply = new Command();
    //reply.setType(CommandType.Unknown);
    //this.sendCommand(m);
    return true;
  }

  private boolean OnShutdown(Command m)
  {
    Command reply = new Command();
    reply.setType(CommandType.Ack);
    reply.setPayload(null);
    this.sendCommand(m);
    return true;
  }

  private boolean OnQuit(Command m)
  {
    Command reply = new Command();
    reply.setType(CommandType.Ack);
    reply.setPayload(null);
    this.sendCommand(m);
    return true;
  }


}
