import java.util.Arrays;

public class votingVisionPCA{//This is the class for the classifier
	
	 public double [][]dictionary;
	 public int threshold;
	
     public votingVisionPCA(int numberOfClass, int numberOfFeatures, int thresholdIn)
	{ //constructor for the classifier
      //input how many classes to classify, 
      //how many features does each jet have,and
      //the threshold for a image to be not classifiable. 
		dictionary = new double  [numberOfClass][numberOfFeatures];
		threshold = thresholdIn;
	}
	
	 public void train(double [][] jetsForTrain, int [] labels)//Ideal training examples should be balanced
	 { 
		 for(int i=0; i<dictionary.length; i++)
		 {   int count = 0;
			 for(int j=0; j<jetsForTrain.length; j++)
			 {
				  if (labels[j]==i)
					  {
					  dictionary[i] = arrayMath.arraySum(dictionary[i], jetsForTrain[j]);
					  count++;
					  }	  
			 }
			 dictionary[i] = arrayMath.arrayDivision(dictionary[i], count );
		 }
		 
	 }
	 
	 public double predict(double[]jet) 
	 {   //calculate the distance from the input jet to each reference jet in the dictionary,
	     //test if the closest distance is in a reasonable range(if not output -1), and output the class of the closest reference jet.
		 double [] result = new double [dictionary.length];
		 for(int i=0; i<dictionary.length; i++)
		 {
			 result[i] =0;
			 for(int j=0; j<jet.length; j++) 
			 {
				 result[i] = result[i]+Math.pow(jet[j]-dictionary[i][j], 2);
			 }
				 
		 }
		 double [] possibleClass = arrayMath.arrayMinValueAndIndex(result);
		 if(Math.sqrt(possibleClass[0])<=threshold)
			 {return possibleClass[1]; }
		 else
		 { return -1;}
	 }
	 
	 public double evaluate(double[][]jets, int[]labels) //evaluate the classifier's accuracy on the testing jets
	 {   
		 int count = 0;
		 double [] results = new double [jets.length];
		 for(int i=0; i<jets.length; i++)
		 {   double pre = predict(jets[i]);
		     results[i] = pre;
			 if(pre == labels[i])
			 {
				 count++;
			 }	 
		 }
		 System.out.println("predictions on testing jets:"+ Arrays.toString(results));//print the prediction results on the testing jets
		 return count/(double)jets.length; 
	 }

}





