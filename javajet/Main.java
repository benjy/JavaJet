package javajet;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{

		
		try
		{
			//JavaJet jl = new JavaJet();
			//jl.login(username, password, poll);
			new JavaJetGUI();
			
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
	}

}
