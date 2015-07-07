package it.algos.web.lib;

import it.algos.web.Application;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.vaadin.server.Resource;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;

public class LibResource {

	
	/**
	 * Ritorna una immagine dalle risorse come byte array.
	 * <p>
	 * @param name nome dell'immagine
	 * @return il corrispondente byte array.
	 */
	public static byte[] getImgBytes(String name){
		byte[] bytes = null;
		Path path = Paths.get(Application.getImgFolderPath().toString(), name);
		
		try {
			bytes = Files.readAllBytes(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return bytes;
	}

	/**
	 * Ritorna una immagine dalle risorse come resource.
	 * <p>
	 * @param name nome dell'immagine
	 * @return la Resource corrispondente.
	 */
	public static Resource getImgResource(String name){
		Resource res = null;
		byte[] bytes = getImgBytes(name);
		if (bytes!=null) {
			if (bytes.length > 0) {
				res = getStreamResource(bytes);
			}
		}
		return res;
	}
	
	/**
	 * Create a StreamResource form a byte array
	 * <p>
	 * @param bytes the byte array
	 * @return the StreamResource
	 */
	@SuppressWarnings("serial")
	public static StreamResource getStreamResource(final byte[] bytes){
		StreamResource resource = null;
		
	    // Create a stream source returning the data from the byte array
	    StreamSource streamSource = new StreamSource(){
			@Override
			public InputStream getStream() {
				return new ByteArrayInputStream(bytes);
			}
	    };
	    
	    // create a StreamResource as data source for the image
	    // the name of the stream must be changed or the browser cache will not update the image
	    String resName = ""+System.currentTimeMillis();
	    resource = new StreamResource(streamSource, resName);

	    return resource;
	}

}
