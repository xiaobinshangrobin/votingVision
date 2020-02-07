import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Try {
	//public static final String WRITE_EXTERNAL_STORAGE;
	public static void main(String[] args) {
		
		JetBuilder jb = new JetBuilder();
		double [] jet= jb.buildJet("image/data/20191207_010957.jpg");
		
		int dimension = 20000; //set the jet dimension 
		
		double [][] trainJets = new double [100][dimension];  //initialize empty array to store training jets 
		int [] trainLabels = new int [100];                   //initialize empty array to store labels for training jets
		double [][] testJets = new double [54][dimension];    //initialize empty array to testing jets
		int [] testLabels = new int [54];                     //initialize empty array to store labels for testing jets
		                                                      //(Their size can be changed for other split ratio; now is 100:54 )
		
		long start = System.nanoTime();   //record the start time
		
		final File folder = new File("image/data");   //The folder that stores the image files
		
		int i = 0;
		for (final File fileEntry : folder.listFiles()) //This loop transfers all images in the folder to jet,
		{   String name = fileEntry.getName();          //finds their labels in the labels folder, 
		   if(!(name.equals(".DS_Store")))              //and saves them into arrays initialized above
			{
			   if(i<trainJets.length) //save into the training jets
			   {
				   trainJets[i]= Arrays.copyOfRange(jb.buildJet("image/data/"+ name), jet.length-dimension, jet.length); //build a jet for the image and then truncate it to the given dimension. Keep the last 2 entries in jet. 
				   
				   trainLabels[i]=jb.getLabel1("image/labels/"+ name.substring(0, 15) +".json"); //read json file in the labels folder to get the label for the image
			   }
			   else //save into the testing jets
			   {
				   testJets[i-trainJets.length]= Arrays.copyOfRange(jb.buildJet("image/data/"+ name), jet.length-dimension, jet.length);
				   testLabels[i-trainJets.length]=jb.getLabel1("image/labels/"+ name.substring(0, 15) +".json");
			   }
			   i++;
			}
		}
		System.out.println(i);                                                          //print total number of images
		System.out.println("true labels of testing jets" + Arrays.toString(testLabels));//print true testing labels
		
		votingVisionPCA pca = new votingVisionPCA(4, trainJets[0].length, 1000000000); //initialize the classifier
		pca.train( trainJets, trainLabels);                                            //Train the classifier with the training jet
		System.out.println( "accuracy:"+pca.evaluate(testJets, testLabels));           //print the accuracy on the testing jets
		long end = System.nanoTime();                                                  //record the ending time
		System.out.println("running time:" + (end-start)/(1000000));                   //print the running time in millisecond
		 
	    
	  
	   
	 	}

}
