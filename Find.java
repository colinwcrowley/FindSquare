import java.io.*;
import java.util.Scanner;

class Find {
   public static void main(String[] args) {
      String filename = "berlin.pgm";
      String newFilename = "berlin-out2.pgm";

      int[][] image = readPGM(filename);
      int width = image[0].length;
      int height = image.length;

      int[][] answers = new int[height][width];

      long millis = System.currentTimeMillis();
      int bottemRightCorner = find(image, answers);
      System.out.println("time: " + (System.currentTimeMillis()-millis));

      int y = bottemRightCorner/width;
      int x = bottemRightCorner%width;
      int sideLength = answers[y][x];

      //image[y][x] = 255;
      for (int i=y; i>=y-sideLength; i--) {
         image[i][x] = 255;
         image[i][x-sideLength] = 255;
      }
      for (int i=x; i>=x-sideLength; i--) {
         image[y][i] = 255;
         image[y-sideLength][i] = 255;
      }
      System.out.println(y + " " + x);
      System.out.println(answers[y][x]);
      writePGM(image, newFilename);
      /*print(board);
      System.out.println();
      print(answers);*/
   }

   static int find(int[][] image, int[][] answers)  {
      for (int i=0; i<answers.length; i++) {
         if(image[i][0]<=50) {
            answers[i][0] = 1;
         }
         else {
            answers[i][0] = 0;
         }
      }
      for (int i=0; i<answers[0].length; i++) {
         if(image[0][i]<=50) {
            answers[0][i] = 1;
         }
         else {
            answers[0][i] = 0;
         }
      }
      int maxI = 0;
      int maxJ = 0;
      int max = answers[0][0];
      for (int i=1; i<answers.length; i++) {
         for (int j=1; j<answers[0].length; j++) {
            if(image[i][j]<=50 && image[i-1][j]<=50 && image[i][j-1]<=50
                && image[i-1][j-1]<=50) {
               answers[i][j] = 1+min(answers[i-1][j], answers[i][j-1],
                answers[i-1][j-1]);
            }
            else if (image[i][j]<=50) {
               answers[i][j] = 1;
            }
            else {
               answers[i][j] = 0;
            }

            if(answers[i][j]>max) {
               maxI = i;
               maxJ = j;
               max = answers[i][j];
            }
         }
      }
      return (maxI)*image[0].length + maxJ;
   }

   static int min(int a, int b, int c) {
      int min = a;
      if(b<min) min = b;
      if(c<min) min = c;
      return min;
   }

   static void print(int[][] array) {
      for (int i=0; i<array.length; i++) {
         for (int j=0; j<array[0].length; j++) {
            System.out.print(array[i][j]+ " ");
         }
         System.out.println();
      }
   }

   static int[][] readPGM(String filename) {
      Scanner s = null;
      try {
         s = new Scanner(new File(filename));
      } catch (Exception e) {
         e.printStackTrace();
      }
      s.nextLine();
      s.nextLine();
      int width = s.nextInt();
      int height = s.nextInt();
      s.nextLine();
      int[][] image = new int[height][width];
      for (int i=0; i<height; i++) {
         for (int j=0; j<width; j++) {
            image[i][j] = s.nextInt();
         }
      }
      return image;
   }

   static void writePGM(int[][] image, String filename) {
      PrintWriter pw = null;
      try {
         pw = new PrintWriter(filename);
      } catch (Exception e) {
         e.printStackTrace();
      }

      int width = image[0].length;
      int height = image.length;

      String header = "P2\n# unnecesary line";
      pw.println(header);
      pw.println(width + " " + height);
      pw.println("255");
      for (int i=0; i<height; i++) {
         for (int j=0; j<width; j++) {
            pw.print(image[i][j]+" ");
            if(j%20 == 19) {
               pw.println();
            }
         }
      }
      pw.close();
   }


}
