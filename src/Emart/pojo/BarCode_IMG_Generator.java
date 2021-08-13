/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Emart.pojo;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;

/**
 *
 * @author AKAN
 */
public class BarCode_IMG_Generator {
   public static void createImage(String image_name,String myString){ 
   try{
    Code128Bean code128= new Code128Bean();
    code128.setHeight(15f);//this function set the height of the bar code i have set it 15px we can set it according to our need.
    ByteArrayOutputStream baos= new ByteArrayOutputStream();
    BitmapCanvasProvider canvas=new BitmapCanvasProvider(baos,"image/x-png",300,BufferedImage.TYPE_BYTE_BINARY,false,0);
    code128.generateBarcode(canvas, myString);
    canvas.finish();
    
    String userdir=System.getProperty("user.dir");//this method will return the current directory on which this project is running and since this method works on keyvalue pair to get a value of current directory we have to pass a key which is fixed i.e. "user.dir"
       System.out.println("user dir is: "+userdir);
       FileOutputStream fos=new FileOutputStream(userdir+"/Barcode/"+image_name);//now the fos will point to this path.
       System.out.println(userdir+"//Barcode"+image_name);
       fos.write(baos.toByteArray());//this will write the image in from buffer to file and since it takes bite array we passed it so.
       fos.flush();//it is for redability we can remove it also
       fos.close();
     }
   catch(Exception e){
       System.out.println("Exception in barcode"+e.getMessage());
   }
  }
}
