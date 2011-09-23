package javajet;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		String username = "15515641";
		String password = "Benny141288";
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
		
		//JetLogin2 j = new JetLogin2();
		//j.makeRequest();
		
	}

}
