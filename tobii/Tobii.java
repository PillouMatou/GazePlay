package tobii;

import gaze.SecondScreen;
import gaze.TobiiGazeListener;
import javafx.geometry.Point2D;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class Tobii {

    private static final double screenWidth = com.sun.glass.ui.Screen.getScreens().get(0).getWidth();
    private static final double screenHeight = com.sun.glass.ui.Screen.getScreens().get(0).getWidth();

    private static Point2D parseTobiiOutput(String tobiiOutput) {

        float x = 0;
        float y = 0;
        try {
            x = Float.valueOf(tobiiOutput.substring(24, 32))*(float)screenWidth;
            y = Float.valueOf(tobiiOutput.substring(34, 42))*(float)screenHeight;
        } catch (Exception e) {

            return null;
        }

        Point2D point = new Point2D(x, y);

        return point;
    }

    public static void oldExecProg(TobiiGazeListener listener) {

        Runtime runtime = Runtime.getRuntime();

        Process process = null;
        try {
            process = runtime.exec(new String[]{"C:\\Users\\schwab\\IdeaProjects\\GazePlay\\tobii\\GazePlay-tobii.exe"});
        } catch (IOException e) {

            System.err.println("No tobii connected");
            return;
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = "";

        try {
            while ((line = reader.readLine()) != null) {
                // Traitement du flux de sortie de l'application si besoin est

         //      if(i++==100)
                System.out.println(line);
                //System.out.println(parseTobiiOutput(line));
                //Point2D gazePosition = parseTobiiOutput(line);
                Point2D gazePosition = null;
                if(gazePosition!=null){

                 //   listener.onGazeUpdate(gazePosition);
                }
            }
        }catch(Exception ioe){
        ioe.printStackTrace();
        }
    }

    public static void execProgold2(TobiiGazeListener listener) {

        Runtime runtime = Runtime.getRuntime();

        Process process = null;
        try {
            process = runtime.exec(new String[]{"C:\\Users\\schwab\\IdeaProjects\\GazePlay\\tobii\\GazePlay-tobii.exe"});
        } catch (IOException e) {

            System.err.println("No tobii connected");
            return;
        }

        ReadableByteChannel standardChannel = Channels.newChannel(process.getInputStream());

        int size = 1024;

        ByteBuffer buffer = ByteBuffer.allocate( size );

        try {
            while(true) {

                standardChannel.read(buffer);
                buffer.flip();
                for (int i = 0; i < buffer.limit(); i++)
                    System.out.print((char)buffer.get());
                buffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void execProg(TobiiGazeListener listener) {
        init();

        while(true){

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(gazePosition());
        }
    }

    public static native void init();

    public static native String gazePosition();


    static{

        try {
            System.loadLibrary("tobii_stream_engine");
            System.loadLibrary("GazePlayTobiiLibrary2");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] argv){

        System.out.println(parseTobiiOutput("Gaze point: 10809754557 0.433731, 0.719170"));
     /*   System.out.println(parseTobiiOutput("Gaze point: 10809814527 0.372081, 0.670327"));
        System.out.println(parseTobiiOutput("Gaze point: 10809828278 0.373068, 0.672786"));
        System.out.println(parseTobiiOutput("Gaze point: 10809874253 0.369580, 0.700685"));
        System.out.println(parseTobiiOutput("Gaze point: 10809889523 0.371530, 0.707418"));
        System.out.println(parseTobiiOutput("Gaze point: 10817493839 INVALID"));*/

        init();

        while(true){

            System.out.println(gazePosition());
        }


    }


}
