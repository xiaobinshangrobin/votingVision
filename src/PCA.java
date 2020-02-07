import org.apache.commons.math3.*;
import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.correlation.Covariance;

public class PCA {//This class does not work for matrices with high dimension and can not be used for dimension reduction 
	              //The dimension reduction method was changed to truncation of primitive jet 
	public RealMatrix getTransformMatrix(double [][] jets, int dimension){
		
		RealMatrix realMatrix = MatrixUtils.createRealMatrix(jets);
		Covariance covariance = new Covariance(realMatrix);
		RealMatrix covarianceMatrix = covariance.getCovarianceMatrix();
		EigenDecomposition ed = new EigenDecomposition(covarianceMatrix);
		RealMatrix eigenVecters = ed.getV();
		RealMatrix transformMatrix = eigenVecters.getSubMatrix(0, jets.length, 0, dimension);
		
		return transformMatrix;
		 
		
	}
	
	
}
