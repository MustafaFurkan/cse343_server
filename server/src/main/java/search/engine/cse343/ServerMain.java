package search.engine.cse343;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerMain {

    static private ArrayList<Integer> results = null;
    static private int page = 1; //default
    static private int CONTENT_PER_PAGE = 5;

    public static void main(String[] args){

        // init server
        UrlForest urlForest = new UrlForest();
        urlForest.readFromFile("/home/user/search_engine_server.txt");

        System.out.println("Forest restored. Links: " + urlForest.getNumUrls());

        try {
            //init socket
            ServerSocket serverSocket = new ServerSocket(9090);
            Socket socket = serverSocket.accept();

            //init streams
            PrintWriter out =
                    new PrintWriter(socket.getOutputStream());
            BufferedReader input =
                    new BufferedReader(new InputStreamReader(socket.getInputStream()));

            System.out.println("Client connected");

            while(socket.isConnected()) {

                String str = input.readLine(); //get query from client

                if (Character.isDigit(str.charAt(0))){

                    page = Integer.parseInt(str);

                }else{ //query

                    results = urlForest.getIndexOfUrls(str);

                    if(results == null){

                        out.println("0");
                        out.flush();
                        continue ;
                    }

                    page = 1;

                    out.println(results.size()); //send number of results
                }

                int i=(page-1)*CONTENT_PER_PAGE;

                //send number of objects to send.

                if(i+CONTENT_PER_PAGE < results.size())

                    out.println(CONTENT_PER_PAGE);

                else

                    out.println(results.size()-i);

                for(; i<results.size() && i<((page-1)*CONTENT_PER_PAGE+5); ++i){

                    int index = results.get(i);

                    out.println(urlForest.getResult(index).getTitle());
                    out.println(urlForest.getResult(index).getUrl());
                }

                out.flush();

            }
            socket.close();
            serverSocket.close();
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
