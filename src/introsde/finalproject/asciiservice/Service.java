package introsde.finalproject.asciiservice;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import java.awt.image.BufferedImage;
import java.awt.Color;
import javax.imageio.ImageIO;
import java.util.Random;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

@Path("/picture/random")
public class Service {

	static Random rand = new Random();
	static int lastIdx = 0;

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String picturePlain() {
		return randomPicture();
	}

	@GET
	@Produces(MediaType.TEXT_XML)
	public String pictureXML() {
		return "<?xml version=\"1.0\"?>\n" + "<picture>" + randomPicture() + "</picture>";
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String pictureJson() {
		return "{ 'picture': '" + randomPicture() + "' }";
	}

	private String randomPicture() {
		String picture = "";
		try{
			BufferedImage img = ImageIO.read(getPictureFile());	
			for (int i = 0; i < img.getHeight(); i++) {
	            for (int j = 0; j < img.getWidth(); j++) {
	                Color pixcol = new Color(img.getRGB(j, i));
	                double pixval = (((pixcol.getRed() * 0.30) + (pixcol.getBlue() * 0.59) + (pixcol.getGreen() * 0.11)));
	                
	                String chr = " ";
			        if (pixval >= 240) {
			            chr = " ";
			        } else if (pixval >= 210) {
			            chr = ".";
			        } else if (pixval >= 190) {
			            chr = "*";
			        } else if (pixval >= 170) {
			            chr = "+";
			        } else if (pixval >= 120) {
			            chr = "^";
			        } else if (pixval >= 110) {
			            chr = "&";
			        } else if (pixval >= 80) {
			            chr = "8";
			        } else if (pixval >= 60) {
			            chr = "#";
			        } else {
			            chr = "@";
			        }
	                picture += chr;
	            }
                picture += "\n";
        	}
		} catch(IOException e) {
		}
		return picture;
	}

	private File getPictureFile() {
		File dir = new File("img");
		File[] files = dir.listFiles(new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		        return name.toLowerCase().endsWith(".jpg");
		    }
		});
		int nextIdx = rand.nextInt(files.length);
		while(nextIdx == lastIdx) {
			nextIdx = rand.nextInt(files.length);
		}
		lastIdx = nextIdx;
		return files[nextIdx];
	}
}
