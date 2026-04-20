package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_boot extends SuperObject{
	public OBJ_boot() {
		 name = "Boots";
		 try {
			 image = ImageIO.read(getClass().getResourceAsStream("/objects/boots_blue.png"));
			
		 }
		 catch(IOException e) {
			 e.printStackTrace();
		 }
}
}
