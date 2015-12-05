package view;
import java.io.*;

public class Serialization {

   public static void serializeObject(String OutputFileName, Object theObject) throws IOException
   {
         FileOutputStream OutputFile = new FileOutputStream(OutputFileName);
         ObjectOutputStream output = new ObjectOutputStream(OutputFile);
         output.writeObject(theObject);
         output.close();
         OutputFile.close();
   }


   public static Object deSerializeObject(String theInputFileName) throws IOException, ClassNotFoundException
   {
	   	 Object theObject;
         FileInputStream InputFile = new FileInputStream(theInputFileName);
         ObjectInputStream input = new ObjectInputStream(InputFile);
         theObject = input.readObject();
         input.close();
         InputFile.close();
         return theObject;
    }
	
}
