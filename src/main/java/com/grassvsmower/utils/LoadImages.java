package com.grassvsmower.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Component;

@Component("loadImages")
public class LoadImages {
	
	 // File representing the folder that you select using a FileChooser
    static final File dir = new File("C:\\Users\\Yovanotti\\Desktop\\images\\6\\");
    
 // array of supported extensions (use a List if you prefer)
    static final String[] EXTENSIONS = new String[]{
        "gif", "jpg", "png", "bmp" // and other formats you need
    };
    // filter to identify images based on their extensions
    static final FilenameFilter IMAGE_FILTER = new FilenameFilter() {

        @Override
        public boolean accept(final File dir, final String name) {
            for (final String ext : EXTENSIONS) {
                if (name.endsWith("." + ext)) {
                    return (true);
                }
            }
            return (false);
        }
    };
    
    public List<byte[]> show () {
    	
    	List<byte[]> retval = new ArrayList<>();

        if (dir.isDirectory()) { // make sure it's a directory
            for (final File f : dir.listFiles(IMAGE_FILTER)) {
                BufferedImage img = null;

                try {
                	
                	/*
                	 *  Path path = Paths.get("path/to/file");
						byte[] data = Files.readAllBytes(path);
                	 * */
                	
                	Path path = Paths.get(f.getAbsolutePath());
                	byte[] data = Files.readAllBytes(path);
                	
                	retval.add(data);
                	
//                    img = ImageIO.read(f);
//
//                    System.out.println("image: " + f.getName());
//                    System.out.println(" width : " + img.getWidth());
//                    System.out.println(" height: " + img.getHeight());
//                    System.out.println(" size  : " + f.length());
                } catch (final IOException e) {
                    // handle errors here
                }
            }
        }
        
        return retval;
    }

}
