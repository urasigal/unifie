package zixi.yuv.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Insert folders names separeted by comma !" );
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Please enter user name : ");
        String folders = null;
        try {
        	folders = reader.readLine();
        	String arrFoldersNames[] = folders.split(",");
        	
        	if(arrFoldersNames.length > 1)
        	{
        		ArrayList<ArrayList<String>> arrayOfArrayLists = new ArrayList<>();
        		for (int i = 0; i < arrFoldersNames.length; i++) {
        			try (Stream<Path> paths = Files.walk(Paths.get(arrFoldersNames[i]))) {
        				ArrayList<String> folderFilesNames = new ArrayList<>();
        			    paths.filter(Files::isRegularFile)
        			    	.forEach(fileName -> folderFilesNames.add(fileName.toString()));
        			    arrayOfArrayLists.add(folderFilesNames);
        			} 
				}
        	}else throw new Exception("Please provide more than one folder pathes");
        	
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
