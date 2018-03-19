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
        			    	.forEach(fileName -> { 	
        			    		String wholePathString = fileName.toString();
        			    		String splittedPath[] = wholePathString.split("\\\\");
        			    		folderFilesNames.add(splittedPath[splittedPath.length-1]);
        			    	});
        			    arrayOfArrayLists.add(folderFilesNames);
        			} 
				}
        		
        		ArrayList<String> relativeArrayOfFileNames = arrayOfArrayLists.get(0);
        		ArrayList<String> onlyFilesNames = new ArrayList<>();
        		for (int i = 0; i < relativeArrayOfFileNames.size(); i++) {
        			String fileNameTocompare = relativeArrayOfFileNames.get(i);
					for(int j = 1; j < arrayOfArrayLists.size(); j++)
					{
						if(arrayOfArrayLists.get(j).contains(fileNameTocompare))
						{
							onlyFilesNames.add(fileNameTocompare);
						}
					}
				}
        		
        		for(int i = 0; i < arrayOfArrayLists.size(); i++)
        		{
        			String longName = arrayOfArrayLists.get(i).get(0);
        			
        			int pos = longName.lastIndexOf("\\\\");
        	        Path path = Paths.get(longName.substring(0, pos) + "\\merged");
        	        Files.createFile(path);
        			
        		}
        		
        		
        		
        		System.out.println("Done");
        	}else throw new Exception("Please provide more than one folder pathes");
        	
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
