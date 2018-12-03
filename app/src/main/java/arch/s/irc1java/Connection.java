package arch.s.irc1java;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Connection {

    String host;
    int port;
    String nick;
    String password;
    boolean auth_nickserv;
    List<String> autojoin;
    String script;

    BufferedReader in;
    PrintWriter out;

    MessageCallback listener;
    boolean running;

    public Connection(String host, int port, String nick, String password,
                      boolean auth_nickserv, String autojoin[], String script,
                      MessageCallback listener) {
        this.host = host;
        this.port = port;
        this.nick = nick;
        this.password = password;
        this.auth_nickserv = auth_nickserv;
        this.autojoin = Arrays.asList(autojoin);
        this.script = script;
        this.listener = listener;

        running = false;
    }

    public void start() throws IOException {
        running = true;

        Socket socket = new Socket(host, port);
        try {
            out = new PrintWriter(new BufferedWriter
                    (new OutputStreamWriter
                            (socket.getOutputStream())),
                    true);
            in = new BufferedReader(new InputStreamReader
                    (socket.getInputStream()));
            if (!password.isEmpty() && !auth_nickserv)
                send("PASS " + password);
            send("NICK " + nick);
            send("USER sphone 0 0 :sphone");
            if (!password.isEmpty() && auth_nickserv)
                send("PRIVMSG NickServ :IDENTIFY " + password);
            if (!script.isEmpty())
                send(script);
            for (String channel : autojoin)
                send("JOIN " + channel);

            while (running) {
                String message = in.readLine();
                if (message != null)
                    listener.rcv(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.flush();
            out.close();
            in.close();
            socket.close();
        }
    }

    public void stop() {
        running = false;
    }

    public void send(String message) {
        out.println(message);
    }

    public interface MessageCallback {
        void rcv(String message);
    }
}
