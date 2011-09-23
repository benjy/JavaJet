package javajet;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		String username = "";
		String password = "";
		int poll = 15;
		
		try
		{
			JetLogin jl = new JetLogin();
			jl.login(username, password, poll);	
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}	
	}

}
