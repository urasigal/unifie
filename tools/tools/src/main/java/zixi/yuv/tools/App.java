package zixi.yuv.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
        		
        		for(int i = 0; i < arrFoldersNames.length; i++)
        		{
        			String longName = arrFoldersNames[i];
        			
        			int pos = longName.lastIndexOf("\\");
        	        Path path = Paths.get(longName.substring(0, pos) + "\\merged");
        	        Files.createFile(path);
        	        
        	        OutputStream out = new FileOutputStream(new File(path.toString()), true);
        	        
        	        try (Stream<Path> paths = Files.walk(Paths.get(arrFoldersNames[i]))) {
        			    paths.filter(Files::isRegularFile)
        			    	.forEach(fileName -> {
        			    		String wholePathString = fileName.toString();
        			    		String splittedPath[] = wholePathString.split("\\\\");
        			    		
        			    	
        			    		if(onlyFilesNames.contains(splittedPath[splittedPath.length-1]))
        			    		{ 
        			    			try {
	        			    			byte[] buf = new byte[1024];
										InputStream in = new FileInputStream(new File(wholePathString));
										int b = 0;
								        while ( (b = in.read(buf)) >= 0) {
								            out.write(buf, 0, b);
								            out.flush();
								        }
								        out.close();
								        in.close();
        			    			} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
        			    			// Append to file 
        			    		}
        			    		
        			    	});
        			} 
        			
        		}
        		
 
        		System.out.println("Done");
        	}else throw new Exception("Please provide more than one folder pathes");
        	
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
