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
    static private String PATH_SCREENSHOTS = "screenshots/";

    private static void sendImage(PrintWriter out, String path){
        try {
            File file = new File(path);
            FileInputStream fileInputStreamReader = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            fileInputStreamReader.read(bytes);
            String encodedImage = new String(Base64.encodeBase64(bytes), "UTF-8");
            out.println(encodedImage);
        }catch (Exception e){
            out.println("");
        }
    }

    private static boolean isQueryValid(String query){

        if(query == null)

            return false;

        if(query.length() > 3)

            return false;

        boolean containsLetter = false, containsDigit = false;

        char[] chars = query.toCharArray();

        for(char ch : chars){

            if(Character.isDigit(ch))

                containsDigit = true;

            else if(Character.isLetter(ch)) {

                containsLetter = true;

                if (('a' <= ch && 'z' >= ch) == false)

                    return false;
            }
            else
                return false;
        }

        if(containsDigit && containsLetter)

            return false;

        return true;
    }

    public static void main(String[] args) throws  Exception{

        // init server
        UrlForest urlForest = new UrlForest();
        urlForest.readFromFile("search_engine_server.txt");

        System.out.println("Forest restored. Links: " + urlForest.getNumUrls());

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

            if(str == null || str.length() == 0 || isQueryValid(str = str.toLowerCase()) == false){

                out.println("0");
                out.flush();
                continue ;
            }

            System.out.println("Clienttan gelen: " + str);

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
            int j = i;
            int temp; //TODO name

            //send number of objects to send.

            if(i < 0 || i > results.size())

                temp = 0;

            else if(i+CONTENT_PER_PAGE < results.size())

                temp = CONTENT_PER_PAGE;

            else

                temp = results.size()-i;

            out.println(temp);

            if(temp == 0) {

                out.flush();
                continue;
            }

            for(; (i<results.size()) && i<((page-1)*CONTENT_PER_PAGE+5); ++i){

                int index = results.get(i);

                out.println(urlForest.getResult(index).getTitle());
                out.println(urlForest.getResult(index).getUrl());
            }

            out.flush();

            //send screenshots
            for(; (j<results.size()) && j<((page-1)*CONTENT_PER_PAGE+5); ++j) {

                int index = results.get(j);

                sendImage(out, PATH_SCREENSHOTS + Integer.toString(index) + ".jpg");

                out.flush();
            }
        }
        socket.close();
        serverSocket.close();
    }
}
