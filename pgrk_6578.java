// SAKETAN PATIL    cs610 6578 prp

import java.io.*;
import java.text.DecimalFormat;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.charset.Charset;
import java.nio.file.Files;

public class pgrk_6578 {

    int total_vertices;
	int total_edges;
	double [][] adjMatrix;
	static double [] pgRank;
	static int [] outDegree;
    int iterations;
    int initVal;
    String input_graph_file;
    boolean display_iteration;
    boolean large_graph;
	double errorRate;
    static final int Threshold_Vertices = 10;
    static final DecimalFormat format_output = new DecimalFormat ("#0.0000000");
	
    public pgrk_6578 () 
    {
	}

	public int settotalIterations () 
    {
		return iterations;
	}

	public void settotalIterations (int iterations) 
    {
		this.iterations = iterations;
	}

    public void setinitVal (int initVal) 
    {
		this.initVal = initVal;
	}

	public int getinitVal () 
    {
		return initVal;
	}

    public void setFile (String input_graph_file) 
    {
		this.input_graph_file = input_graph_file;
	}

	public String getFile () 
    {
		return input_graph_file;
	}

	public boolean display_iteration () 
    {
		return display_iteration;
	}

	public void setIterations (boolean display_iteration) 
    {
		this.display_iteration = display_iteration;
	}

	public boolean large_graph () 
    {
		return large_graph;
	}

	public void setGraphcase (boolean large_graph) 
    {
		this.large_graph = large_graph;
	}

    public void setErrate (double errorRate) 
    {
		this.errorRate = errorRate;
	}

	public double getErrrate () 
    {
		return errorRate;
	}

    public void setVertices (int total_vertices) 
    {
		this.total_vertices = total_vertices;
	}

	public int getVertices () 
    {
		return total_vertices;
	}
    
    public void setEdges (int total_edges) 
    {
		this.total_edges = total_edges;
	}

	 public int getEdges ()
	     {
	                     return total_edges;
			             }

    public void setAdjMatrix (double[][] adjMatrix) 
    {
		this.adjMatrix = adjMatrix;
	}

    public void setpgRank (double[] pgRank) 
    {
		this.pgRank = pgRank;
	}

    public void setOutdegree (int[] outDegree) 
    {
		this.outDegree = outDegree;
	}
    

	public static void main (String[] args) 
    {

        // Reading an input data and check if they are in correct form.
        pgrk_6578 obj = new pgrk_6578 ();
		if (args.length != 3) 
        {
			System.err.println ("Check Input command. Program requires exactly 3 arguments.");
			return;
		}
		obj.settotalIterations (Integer.parseInt (args[0]));
		obj.setinitVal (Integer.parseInt (args[1]));
		obj.setFile (args[2]);
        double Damping = 0.85D;
		try 
        {
			int fileVal1;
			int fileVal2;
			Path fileName = Paths.get ("./" + obj.getFile ());
            Charset charset = Charset.forName ("US-ASCII");
			String line = null;
			boolean line1 = true;
            int count=0;
			BufferedReader bufferedReader = Files.newBufferedReader (fileName, charset);
			while ((line = bufferedReader.readLine ()) != null) 
        	{
				if (!line.trim ().equalsIgnoreCase("")) 
            	{
					String [] nodeValues = line.split (" ");
					
					if (nodeValues.length != 2) //Reading 2 values from a line of a file.
                	{
						System.err.println ("Input Graph file not in correct format. Each line should have exactly 2 values");
						System.exit (0);
					}
					fileVal1 = Integer.parseInt (nodeValues [0]);
					fileVal2 = Integer.parseInt (nodeValues [1]);
                    count++;
					if (line1) 
                	{
						obj.setVertices (fileVal1);
						obj.setEdges (fileVal2);
                    	int totalVertices = obj.getVertices ();
		            	if (totalVertices > Threshold_Vertices) 
                    	{
                        	obj.settotalIterations (0);
			            	obj.setinitVal (-1);
			            	obj.setGraphcase (true);
                    	}
                    	if (obj.settotalIterations () > 0) 
                    	{
		                	obj.setIterations (true);
		            	} 
                    	else 
                    	{
			            	obj.setIterations (false);
			            	if (obj.settotalIterations () == 0) 
                        	{
				            	obj.setErrate (Math.pow (10, -5));
			            	} 
                        	else 
                        	{
				            	obj.setErrate (Math.pow (10, obj.settotalIterations ()));
			         		}
		            	}
		            	obj.setAdjMatrix (new double [totalVertices][totalVertices]);
		            	obj.setpgRank (new double [totalVertices]);
		            	obj.setOutdegree (new int [totalVertices]);
                    	line1 = false;
					} 
                	else 
                	{
						// Check if input graph file is in proper format with respect to edges and vertices to avoid exceptions.
						if (fileVal1 >= obj.getVertices () || fileVal2 >= obj.getVertices ()) 
                    	{
							System.err.println ("Error in File. Vertex number is invalid");
							System.exit (0);
						}
                        else if(count-1>obj.getEdges ()){
                            System.err.println ("Error in File. Number of edges not equal to remaining lines");
							System.exit (0);
                            }
                    	else 
                    	{
							obj.adjMatrix [fileVal1][fileVal2] = 1d;
						}
					}
				}
			}
            
			int totalVertices = obj.getVertices ();
			double value = 0d;
            
		    double [] curRank = new double [totalVertices];
		    int iteration_count = 0;
		    boolean Base_display = true;
		    boolean Converged = true;

			switch (obj.getinitVal ()) 
        	{
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
	    	for (int i = 0; i < totalVertices; i++) 
        	{
		    	obj.pgRank [i] = value;
		    	obj.outDegree [i] = 0;
            	for (int j = 0; j < totalVertices; j++) 
            	{
            		obj.outDegree [i] += obj.adjMatrix [i][j];
            	}
	    	}
			// Check for iterative case, Error rate case or large graph case and run algorithm and print values accordingly.
			if (obj.display_iteration ()) 
			{	//Iterative Case: Run program for given number of fixed iterations.
				for (int i = 0; i <= obj.settotalIterations (); i++) 
            	{
					String print_string = "";
					if (Base_display) 
                	{
						Base_display = false;
						print_string = "Base";
					} 
                	else 
                	{
						print_string = "Iter";
						for (int j = 0; j < totalVertices; j++) 
                    	{
	                    	for (int k = 0; k < totalVertices; k++) 
                        	{
	                        	if (obj.adjMatrix [k][j] == 1d) 
                            	{
	                        		double pageRankByOutDegree = (obj.pgRank[k] / obj.outDegree[k]);
	                        		curRank[j] += pageRankByOutDegree;
	                        	}
	                    	}
	                	}
	                	for (int v = 0; v < totalVertices; v++) 
                    	{
	                		curRank [v] = (1 - Damping) / totalVertices + (Damping * curRank [v]);
	                		obj.pgRank[v] = curRank[v];
	                		curRank[v] = 0d;
	                	}
					}
					if (i < 10) 
                	{
						print_string = print_string + "    :  " + i + " :";	
					} 
                	else if (i < 100) 
                	{
						print_string = print_string + "    : " + i + " :";
					} 
                	else 
                	{
						print_string = print_string + "    :" + i + " :";
					}
					for (int v = 0; v < totalVertices; v++) 
                	{
						print_string = print_string + "P[" + v + "]=" + format_output.format (Math.round (obj.pgRank[v] * 10000000D) / 10000000D) + " ";
					}
					System.out.println (print_string);
				}
			} 
        	else 
        	{
            	do 	// Convergence should be checked for Error Rate case and Large Graph case.
         		{
            		Converged = true;
					String print_string = "";
					if (Base_display) 
                	{
						Base_display = false;
						print_string = "Base";
					} 
                	else 
                	{
						print_string = "Iter";
						for (int v = 0; v < totalVertices; v++) 
                    	{
	                    	for (int w = 0; w < totalVertices; w++)
                         	{
	                        	if (obj.adjMatrix [w][v] == 1d) 
                            	{
	                        		double pageRankByOutDegree = (obj.pgRank[w] / obj.outDegree[w]);
	                        		curRank[v] += pageRankByOutDegree;
	                        	}
	                    	}
	                	}
	                	for (int v = 0; v < totalVertices; v++) 
                    	{
	                    	curRank [v] = (1 - Damping) / totalVertices + (Damping * curRank [v]);
	                	}
					}
					if (iteration_count < 10) 
                	{
						print_string = print_string + "    :  " + iteration_count + " :";	
					} 
                	else if (iteration_count < 100) 
                	{
						print_string = print_string + "    : " + iteration_count + " :";
					} 
                	else 
                	{
						print_string = print_string + "    :" + iteration_count + " :";
					}
					for (int v = 0; v < totalVertices; v++) 
                	{
		            	if (Math.abs (obj.pgRank[v] - curRank[v]) > obj.getErrrate ()) 
                    	{
		            		Converged = false;
							break;
		            	}
		        	}
					if (obj.large_graph ()) //check for a large graph and print values in short.
                	{
						if (Converged) 
                    	{
							print_string = "Iter    :  " + iteration_count;
							System.out.println (print_string);
							for (int k = 0; k < 3; k++) 
                        	{
								print_string = "P[" + k + "]=" + format_output.format (Math.round (curRank[k] * 10000000D) / 10000000D);
								System.out.println (print_string);
							}
							System.out.println ("... other vertices ommited");
						}
					} 
                	else 
                	{
						for (int v = 0; v < totalVertices; v++) 
                    	{
							if (iteration_count != 0) 
                        	{
								print_string = print_string + "P[" + v + "]=" + format_output.format (Math.round (curRank[v] * 10000000D) / 10000000D) + " ";	
							} 
                        	else 
                        	{
								print_string = print_string + "P[" + v + "]=" + format_output.format (Math.round (obj.pgRank[v] * 10000000D) / 10000000D) + " ";
							}
						}
						System.out.println (print_string);
					}
					for (int v = 0; v < totalVertices; v++) 
                	{
						if (iteration_count != 0) 
                    	{
							obj.pgRank[v] = curRank[v];	
						}
						curRank[v] = 0d;
                	}
					iteration_count++;
				} while (!Converged);
			}

		} 
        catch (FileNotFoundException e) 
        {
            System.err.println ("Check input file. Check if the input graph file is  in the same folder of program file.");		

		} 
        catch (IOException e) 
        {
            System.err.println ("Check Graph file. ");

        }
	}
}