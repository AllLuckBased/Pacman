package level2;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    public static void main(String[] args){

        new Window();

        // Only used when debugging. Change the following boolean to true to get information of all UIComponents.
        boolean debug = false;
        if(debug) {
            System.out.println("Debug is on! Check project folder for UIComponents.xml");
            Thread windowInfoDumper = new Thread(() -> {
                try {
                    while(true) {
                        File logFile = new File("UI Components.xml");
                        logFile.createNewFile();
                        FileWriter fileWriter = new FileWriter("UI Components.xml");
                        printComponentInfo(null, fileWriter); tabSpace++;
                        fileWriter.flush();

                        Thread.sleep(3000);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            windowInfoDumper.setDaemon(true);
            windowInfoDumper.start();
        }

    }

    private static int tabSpace = 1;
    private static void printComponentInfo(Container container, FileWriter fileWriter) throws IOException {
        String[] packages = container.getClass().getName().split("\\.", 0);
        String className = packages[packages.length-1];
        fileWriter.write("<" + className + "> Width: " + container.getWidth() + " Height: " + container.getHeight() + "\n");
        for(Component c : container.getComponents()) {
            if(c instanceof Container) {
                Container innerRegion = (Container) c;
                for(int i = 0; i< tabSpace; i++) fileWriter.write("    ");
                tabSpace++;
                printComponentInfo(innerRegion, fileWriter);
            }
            else {
                packages = c.getClass().getName().split("\\.", 0);
                className = packages[packages.length-1];
                for(int i = 0; i< tabSpace; i++) fileWriter.write("    ");
                fileWriter.write("<" + className + "> Width: " +
                        c.getBounds().getWidth() + " Height: " +
                        c.getBounds().getHeight() + " </" + className + ">\n");
            }
        }
        tabSpace--;
        for(int i = 0; i< tabSpace; i++) fileWriter.write("    ");
        fileWriter.write("</" + className + ">\n");
    }
}