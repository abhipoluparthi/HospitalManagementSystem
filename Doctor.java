package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctor {
	private Connection connection;

	
	public void Doctor(Connection connection  ) 
	{
		this.connection=connection;
		
	}
	
	
	public void viewDoctors()
	{
		String Query="select * from Doctors";
		
		try {
			PreparedStatement preparedStatement=connection.prepareStatement(Query);
			ResultSet resultSet=preparedStatement.executeQuery();
			System.out.println("Doctors:");
			System.out.println("+-----------+-----------------+---------------------+");
	        System.out.println("| Doctor Id | Name            | Specialization      |");
	        System.out.println("+-----------+-----------------+---------------------+");
	        while(resultSet.next())
	        {
	        	int id=resultSet.getInt("id");
	        	String name=resultSet.getString("name");
	        	String specialization=resultSet.getString("specialization");
				System.out.printf("|%-12s|%-18s|%-22s| \n",id,name,specialization);
	            System.out.println("+-----------+-----------------+---------------------+");
	        	
	        }
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public boolean getDoctorId(int Id)
	{
		String Query="SEK"
				+ "SELECT * FROM DOCTORSs WHERE Id=?";
		
		try
		{
			PreparedStatement preparedStatement=connection.prepareStatement(Query);
			preparedStatement.setInt(1, Id);
			ResultSet resultSet=preparedStatement.executeQuery();
			
			if(resultSet.next())
			{
				return true;
			
			}
			else
			{
				return false;
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}

}
