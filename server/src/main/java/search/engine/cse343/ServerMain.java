package search.engine.cse343;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import org.apache.commons.codec.binary.Base64;

public class ServerMain {

    static private ArrayList<Integer> results = null;
    static private int page = 1; //default
    static private int CONTENT_PER_PAGE = 5;

    private static void sendImage(PrintWriter out, String path) throws Exception{

        File file = new File(path);
        FileInputStream fileInputStreamReader = new FileInputStream(file);
        byte[] bytes = new byte[(int)file.length()];
        fileInputStreamReader.read(bytes);
        String encodedImage = new String(Base64.encodeBase64(bytes), "UTF-8");
        out.println(encodedImage);
    }

    public static void main(String[] args){

        // init server
        UrlForest urlForest = new UrlForest();
        urlForest.readFromFile("search_engine_server.txt");

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
                int temp; //TODO name

                //send number of objects to send.

                if(i+CONTENT_PER_PAGE < results.size())

                    temp = CONTENT_PER_PAGE;

                else

                    temp = results.size()-i;

                out.println(temp);

                for(; i<results.size() && i<((page-1)*CONTENT_PER_PAGE+5); ++i){

                    int index = results.get(i);

                    out.println(urlForest.getResult(index).getTitle());
                    out.println(urlForest.getResult(index).getUrl());

                    System.out.println("title " + urlForest.getResult(index).getTitle());
                    System.out.println("url " + urlForest.getResult(index).getUrl());
                }

                out.flush();

                //send screenshots
                for(int j=0; j<temp; ++j) {

                    StringBuilder path = new StringBuilder();
                    path.append("/home/user/filesendtestfolder/"); //TODO fix path
                    path.append((char)('a'+j));
                    path.append(".jpg");

                    sendImage(out, path.toString());
                    out.flush();
                }
            }
            socket.close();
            serverSocket.close();
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
