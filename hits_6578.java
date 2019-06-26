// SAKETAN PATIL    cs610 6578 prp

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;

public class hits_6578 {

	int iterations;
	int initVal;
	String input_graph_file;
	boolean display_iteration;
	boolean large_graph;
	double errorRate;
	int total_vertices;
	int total_edges;
	double [][] adjMatrix;
	double [][] adjMatrixT;
	double [] calcAuthority;
	double [] curScaledAuthority;
	double [] preScaledAuthority;
	double [] calcHub;
	double [] curscaledHub;
	double [] preScaledHub;
    static final int Threshold_Vertices = 10;
	static final DecimalFormat format_output = new DecimalFormat ("#0.0000000");

    public hits_6578 () {
	}


    public void setcalcHub(double[] calcHub) {
		this.calcHub = calcHub;
	}

	public double[] getcurscaledHub() {
		return curscaledHub;
	}

	public void setcurscaledHub(double[] curscaledHub) {
		this.curscaledHub = curscaledHub;
	}

	public double[] getpreScaledHub() {
		return preScaledHub;
	}

	public void setpreScaledHub(double[] preScaledHub) {
		this.preScaledHub = preScaledHub;
	}

	public void setIterations(int iterations) 
	{
		this.iterations = iterations;
	}
	
	public int getIterations() 
	{
		return iterations;
	}

	public void setinitVal(int initVal) 
	{
		this.initVal = initVal;
	}

	public int getinitVal() 
	{
		return initVal;
	}

	public void setinput_graph_file(String input_graph_file) 
	{
		this.input_graph_file = input_graph_file;
	}

	public String getinput_graph_file() 
	{
		return input_graph_file;
	}

	public boolean display_iteration() 
	{
		return display_iteration;
	}

	public void setIterated(boolean display_iteration) 
	{
		this.display_iteration = display_iteration;
	}

	public boolean large_graph() 
	{
		return large_graph;
	}

	public void setLargeGraph(boolean large_graph) 
	{
		this.large_graph = large_graph;
	}

	public void setErrorRate(double errorRate) 
	{
		this.errorRate = errorRate;
	}

	public double getErrorRate() 
	{
		return errorRate;
	}

	public void settotal_vertices(int total_vertices) 
	{
		this.total_vertices = total_vertices;
	}

	public int gettotal_vertices() 
	{
		return total_vertices;
	}

	public void settotal_edges(int total_edges) 
	{
		this.total_edges = total_edges;
	}

    public int gettotal_edges() 
	{
		return total_edges;
	}

	public void setadjMatrix(double[][] adjMatrix) 
	{
		this.adjMatrix = adjMatrix;
	}

	public double[][] getadjMatrix() {
		return adjMatrix;
	}

	public double[][] getadjMatrixT() {
		return adjMatrixT;
	}

	public void setadjMatrixT(double[][] adjMatrixT) {
		this.adjMatrixT = adjMatrixT;
	}

	public double[] getcalcAuthority() {
		return calcAuthority;
	}

	public void setcalcAuthority(double[] calcAuthority) {
		this.calcAuthority = calcAuthority;
	}

	public double[] getcurScaledAuthority() {
		return curScaledAuthority;
	}

	public void setcurScaledAuthority(double[] curScaledAuthority) {
		this.curScaledAuthority = curScaledAuthority;
	}

	public double[] getpreScaledAuthority() {
		return preScaledAuthority;
	}

	public void setpreScaledAuthority(double[] preScaledAuthority) {
		this.preScaledAuthority = preScaledAuthority;
	}

	public double[] getcalcHub() {
		return calcHub;
	}

	public static void main (String[] args) {

        // Reading an input data and check if they are in correct form.
		if (args.length != 3) {
			System.err.println ("Check Input command. Program requires exactly 3 arguments.");
			return;
		}
        hits_6578 obj = new hits_6578 ();
		obj.setIterations (Integer.parseInt (args[0]));
		obj.setinitVal (Integer.parseInt (args[1]));
		obj.setinput_graph_file (args[2]);
		Path fileName = Paths.get ("./" + obj.getinput_graph_file ());
		try {
			Charset charset = Charset.forName ("US-ASCII");
			String line = null;
			boolean line1 = true;
			int fileVal1;
			int fileVal2;
			BufferedReader bufferedReader = Files.newBufferedReader (fileName, charset);
            int count=0;
			while ((line = bufferedReader.readLine ()) != null) {
				if (!line.trim ().equalsIgnoreCase("")) {
					String [] nodeValues = line.split (" ");
					
					if (nodeValues.length != 2) // Reading 2 values from a line of a file.
					{
						System.err.println ("Input Graph file not in correct format. Each line should have exactly 2 values");
						System.exit (0);
					}
					fileVal1 = Integer.parseInt (nodeValues [0]);
					fileVal2 = Integer.parseInt (nodeValues [1]);
                    count++;
					if (line1) {
						obj.settotal_vertices (fileVal1);
						obj.settotal_edges (fileVal2);
						int totalVertices = obj.gettotal_vertices ();
						if (totalVertices > Threshold_Vertices) {
							obj.setIterations (0);
							obj.setinitVal (-1);
							obj.setLargeGraph (true);
						}
						if (obj.getIterations () > 0) {
		    				obj.setIterated (true);
						} else {
							obj.setIterated (false);
							if (obj.getIterations () == 0) {
								obj.setErrorRate (Math.pow (10, -5));
							} else {
								obj.setErrorRate (Math.pow (10, obj.getIterations ()));
							}
						}
						obj.setadjMatrix (new double [totalVertices][totalVertices]);
						obj.setadjMatrixT (new double [totalVertices][totalVertices]);
						obj.setcalcAuthority (new double [totalVertices]);
						obj.setcurScaledAuthority (new double [totalVertices]);
						obj.setpreScaledAuthority (new double [totalVertices]);
						obj.setcalcHub (new double [totalVertices]);
						obj.setcurscaledHub (new double [totalVertices]);
						obj.setpreScaledHub (new double [totalVertices]);
						line1 = false;
					} else {
						// Check if input graph file is in proper format with respect to edges and vertices to avoid exceptions.
						if (fileVal1 >= obj.gettotal_vertices () || fileVal2 >= obj.gettotal_vertices ()) {
							System.err.println ("Error in File. Vertex number is invalid ");
							System.exit (0);
						} 
                        else if(count-1>obj.gettotal_edges ()){
                            System.err.println ("Error in File. Number of edges not equal to remaining lines");
							System.exit (0);
                            }
                            else {
							obj.adjMatrix [fileVal1][fileVal2] = 1d;
							obj.adjMatrixT [fileVal2][fileVal1] = 1d;						
						}
					}
				}
			}
			
			int totalVertices = obj.gettotal_vertices ();
			double value = 0d;
			switch (obj.getinitVal ()) {
		    	case 0 :
			    	value = 0d; // initialize base case values to 0.0
			    	break;
    			case 1 :
		    		value = 1d; // initialize base case values to 1.0
			    	break;
		    	case -1 :
			    	value = (double) (1d/ (double) totalVertices); // initialize base case values to 1/(number of vertices)
			    	break;
	    		case -2 :
			    	value = (double) (1d/ (double) Math.sqrt (totalVertices)); // initialize base case values to 1/sqrt(number of vertices)
			    	break;
		    	default :
		    		System.err.println ("Base case value should be either 0,1,-1,-2");
					System.exit(0);
			    	break;
			}
	    	for (int i = 0; i < totalVertices; i++) {
		    	obj.calcAuthority [i] = value;
		    	obj.calcHub [i] = value;
		    	obj.curScaledAuthority [i] = value;
		    	obj.curscaledHub [i] = value;
	    	}

			obj.HitsAlgo_6578 (obj);
		} catch (FileNotFoundException e)
        {
            System.err.println ("Check input file. Check if the input graph file is  in the same folder of program file.");
		    
		} 
        catch (IOException e) 
        {
            System.err.println ("Check Graph file.");
		    
		}
	}
	
	private void HitsAlgo_6578 (hits_6578 obj) {
		int iteration_count = 0;
		boolean Base_display = true;
		boolean Converged = true;
        
		// Check for iterative case, Error rate case or large graph case and run algorithm and print values accordingly.
		if (obj.display_iteration ()) {
			for (int i = 0; i <= obj.getIterations (); i++) {
				String print_string = "";		//Iterative Case: Run program for given number of fixed iterations.
				if (Base_display) {
					Base_display = false;
					print_string = "Base";
				} else {
					print_string = "Iter";
					int totalVertices = obj.gettotal_vertices (); 
					double comp;
					double [][] matrixTranspose = obj.getadjMatrixT ();
					double [] calcHub = obj.getcalcHub ();
					double [] newAuth = new double [totalVertices];
					for (int v = 0; v < totalVertices; v++) {
						comp = 0d;
						for (int w = 0; w < totalVertices; w++) {
							comp = comp + matrixTranspose[v][w] * calcHub[w];
						}
						newAuth[v] = comp;
					}
					obj.setcalcAuthority (newAuth);
					totalVertices = obj.gettotal_vertices (); 
					double [][] matrix = obj.getadjMatrix ();
					double [] calcAuthority = obj.getcalcAuthority ();
					double [] newHub = new double [totalVertices];
					for (int v = 0; v < totalVertices; v++) {
						comp = 0d;
						for (int w = 0; w < totalVertices; w++) {
							comp = comp + matrix[v][w] * calcAuthority[w];
						}
						newHub[v] = comp;
					}
        			obj.setcalcHub (newHub);
					
					totalVertices = obj.gettotal_vertices (); 
					double thv = 0d;
					double tav = 0d;
					calcAuthority = obj.getcalcAuthority ();
					calcHub = obj.getcalcHub ();
					double [] curScaledAuthority = obj.getcurScaledAuthority ();
					double [] curscaledHub = obj.getcurscaledHub ();
					for (int v = 0; v < totalVertices; v++) {
						tav = tav + (calcAuthority[v] * calcAuthority[v]);
						thv = thv + (calcHub[v] * calcHub[v]);
					}
					tav = (double) Math.sqrt (tav);
					thv = (double) Math.sqrt (thv);
					for (int v = 0; v < totalVertices; v++) {
						curScaledAuthority[v] = calcAuthority[v] / tav;
						curscaledHub[v] = calcHub[v] / thv;
					}
					obj.setcurScaledAuthority (curScaledAuthority);
					obj.setcurscaledHub (curscaledHub);
					}
				if (i < 10) {
					print_string = print_string + "    :  " + i + " :";	
				} else if (i < 100) {
					print_string = print_string + "    : " + i + " :";
				} else {
					print_string = print_string + "    :" + i + " :";
				}
				for (int j = 0; j < obj.gettotal_vertices(); j++) {
					print_string = print_string +
		                "A/H[" + j + "]=" +
		                format_output.format (Math.round (obj.curScaledAuthority[j] * 10000000d) / 10000000d) + "/" +
		                format_output.format (Math.round (obj.curscaledHub[j] * 10000000d) / 10000000d) + " ";
				}
				System.out.println (print_string);
			}
		} else {
            do {
            	Converged = true;		// Convergence should be checked for Error Rate case and Large Graph case.
				String print_string = "";
				if (Base_display) {
					Base_display = false;
					print_string = "Base";
				} else {
					print_string = "Iter";
					int totalVertices = obj.gettotal_vertices (); 
					double comp;
					double [][] matrixTranspose = obj.getadjMatrixT ();
					double [] calcHub = obj.getcalcHub ();
					double [] newAuth = new double [totalVertices];
					for (int v = 0; v < totalVertices; v++) {
						comp = 0d;
						for (int w = 0; w < totalVertices; w++) {
							comp = comp + matrixTranspose[v][w] * calcHub[w];
						}
						newAuth[v] = comp;
					}
					obj.setcalcAuthority (newAuth);
					totalVertices = obj.gettotal_vertices (); 
					double [][] matrix = obj.getadjMatrix ();
					double [] calcAuthority = obj.getcalcAuthority ();
					double [] newHub = new double [totalVertices];
					for (int v = 0; v < totalVertices; v++) {
						comp = 0d;
						for (int w = 0; w < totalVertices; w++) {
							comp = comp + matrix[v][w] * calcAuthority[w];
						}
						newHub[v] = comp;
					}
        			obj.setcalcHub (newHub);
					totalVertices = obj.gettotal_vertices (); 
					double thv = 0.0;
					double tav = 0.0;
					calcAuthority = obj.getcalcAuthority ();
					calcHub = obj.getcalcHub ();
					double [] curScaledAuthority = obj.getcurScaledAuthority ();
					double [] curscaledHub = obj.getcurscaledHub ();
					for (int v = 0; v < totalVertices; v++) {
						tav = tav + (calcAuthority[v] * calcAuthority[v]);
						thv = thv + (calcHub[v] * calcHub[v]);
					}
					tav = (double) Math.sqrt (tav);
					thv = (double) Math.sqrt (thv);
					for (int v = 0; v < totalVertices; v++) {
						curScaledAuthority[v] = calcAuthority[v] / tav;
						curscaledHub[v] = calcHub[v] / thv;
					}
					obj.setcurScaledAuthority (curScaledAuthority);
					obj.setcurscaledHub (curscaledHub);
					
				}
				if (iteration_count < 10) {
					print_string = print_string + "    :  " + iteration_count + "  :";	
				} else if (iteration_count < 100) {
					print_string = print_string + "    : " + iteration_count + " :";
				} else {
					print_string = print_string + "    :" + iteration_count + " :";
				}
				for (int i = 0; i < obj.gettotal_vertices (); i++) {
					double oldAutValue = obj.preScaledAuthority[i];
					double autValue = obj.curScaledAuthority[i];
					double old_HubValue = obj.preScaledHub[i];
					double calcHub = obj.curscaledHub[i];
					if ((Math.abs (oldAutValue - autValue) > obj.getErrorRate ()) || (Math.abs (old_HubValue - calcHub) > obj.getErrorRate ())) {
						Converged = false;
						break;
					}
				}
				if (obj.large_graph ()) {		//check for a large graph and print values in short.
					if (Converged) {
						print_string = "Iter    :  " + iteration_count;
						System.out.println (print_string);
						for (int k = 0; k < 4; k++) 
                        {
							print_string = "A/H[" + k + "]=" +
					                format_output.format (Math.round (obj.curScaledAuthority[k] * 10000000D) / 10000000D) + "/" +
					                format_output.format (Math.round (obj.curscaledHub[k] * 10000000D) / 10000000D) + " ";
							System.out.println (print_string);
						}
						System.out.println ("... other verices ommited");
					}
				} else {
					for (int j = 0; j < obj.gettotal_vertices(); j++) 
                    {
						print_string = print_string +
			                "A/H[" + j + "]=" +
			                format_output.format (Math.round (obj.curScaledAuthority[j] * 10000000D) / 10000000D) + "/" +
			                format_output.format (Math.round (obj.curscaledHub[j] * 10000000D) / 10000000D) + " ";
					}
					System.out.println (print_string);
				}
				for (int j = 0; j < obj.gettotal_vertices(); j++) 
                {
					obj.preScaledAuthority[j] = obj.curScaledAuthority[j];
					obj.preScaledHub[j] = obj.curscaledHub[j];
				}
				iteration_count++;
			} while (!Converged);
		}
	}	
}