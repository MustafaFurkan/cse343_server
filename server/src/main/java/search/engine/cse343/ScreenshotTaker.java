import java.io.IOException;
public class ScreenshotTaker{

	public static void takeScreenshot(String url, String path){
		
		String[] c = new String[] {"/bin/bash", "-c", "phantomjs /home/canerbakar/Desktop/github.js " 
        + url + path};
		
		Process proc = null;

		try {
            proc = new ProcessBuilder(c).start();
        } catch (IOException ex) {
            System.out.printf("Problem with Process");
        }

        try{
            proc.waitFor();
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
	
	
	}
}
