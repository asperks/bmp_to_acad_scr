import java.io.*;
import java.awt.image.*;
import javax.imageio.*;

import sun.security.ssl.Debug;

public class Main {

   public static void main(String[] args) {
      String sFileDir = "c:/BmpToScr";
      
      File folder = new File(sFileDir);
      File[] listOfFiles = folder.listFiles(); 
      
      for (int i = 0; i < listOfFiles.length; i++) {
         if (listOfFiles[i].isFile()) {
            String f = listOfFiles[i].getPath();
            if (f.endsWith(".Bmp") || f.endsWith(".Bmp")) {
               // f is a filename in the directory that needs to be processed.
               processBmp(f);
            }
         }
      }
   }
   
   
   private static void processBmp(String sFile) {
      System.out.println("Processing : " + sFile);
      
      File f = new File(sFile);
      System.out.println("File : " + f.toString());
      String sFileOut = sFile.substring(0, sFile.length() - 4) + ".scr";
      
      try {
         FileWriter fstream = new FileWriter(sFileOut);
         BufferedWriter out = new BufferedWriter(fstream);
         
         BufferedImage bi = ImageIO.read(f);

         int rgb = bi.getRGB(1, 1);
         int w = bi.getWidth(null);
         int h = bi.getHeight(null);
         
         System.out.println(w + " x " + h);
         
         for (int ih = 0; ih < h; ih++) {
            int bStart = -2;
            
            for (int iw = 0; iw < w; iw++) {
               int ival = bi.getRGB(iw, ih);
               if (ival != -1) {
                  if (bStart == -2) {
                     //  a colour is starting
                     bStart = iw;
                  }
               } else {
                  if (bStart != -2) {
                     //  a line is ending.
                     boolean bEnd = true;
                     
                     if ((iw - bStart) < 1) {
                        bEnd = false;
                     }
                     
                     if (bEnd == true) {
                        // write out the line
                        out.write(new String("line " + bStart + "," + (h - ih) + " " + (iw - 1) + "," + (h - ih) + " "));
                        out.newLine();
                        bStart = -2;
                     }
                  }
               }
            }
         }

         out.close();

      } catch (IOException e) {
         System.out.println(e.toString());
      }
      
   }

}
