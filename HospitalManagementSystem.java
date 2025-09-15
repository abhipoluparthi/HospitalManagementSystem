package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class HospitalManagementSystem
{
	
	private static final String url="jdbc:mysql://localhost:3306/managementhospital";
	private static final String username="root";
	private static final String password="Abhishek@2003";

	public static void main(String[] args)
	{
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		try
		{
			Connection connection = DriverManager.getConnection(url,username,password);
			Scanner scanner = new Scanner(System.in);
			Patient patient=new Patient(connection,scanner); 
			Doctor doctor = new Doctor();
			while(true)
			{
				System.out.println("HOSPITAL MANAGEMENT SYSTEM");
				System.out.println("1.Add Patients");
				System.out.println("2. View Patients");
				System.out.println("3. View Doctors");
				System.out.println("4. Book Appointment");
				System.out.println("5. Exit");
				System.out.println("Enter your choice:");
				
				int choice=scanner.nextInt();
				
				switch(choice)
				{
				case 1:	
					// Add Patient
					patient.addPatient();
					System.out.println();
				case 2:
					// View Patient
					patient.viewPatient();
					System.out.println();
				case 3:
					// View Doctors
					doctor.viewDoctors();
				    System.out.println();
				case 4:
					// Book  Appointment
					bookAppointment(patient, doctor, connection, scanner);
					System.out.println();
				case 5:
					// retunr;
					
				default:
					System.out.println("Enter Valid choice!!!");
				}
             }
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}

	}
	
	public static void bookAppointment(Patient patient, Doctor doctor, Connection connection, Scanner scanner) {
	    System.out.print("Enter Patient Id: ");
	    int patientId = scanner.nextInt();

	    System.out.print("Enter Doctor Id: ");
	    int doctorId = scanner.nextInt();

	    System.out.print("Enter appointment date (YYYY-MM-DD): ");
	    String appointmentDate = scanner.next();

	    // Assuming getPatientId() and getDoctorId() return true if they exist
	    if (patient.getPatientId(patientId) && doctor.getDoctorId(doctorId)) {
	        if (checkDoctorAvailability(doctorId, appointmentDate, connection)) {
	            String appointmentQuery = "INSERT INTO appointments (patient_id, doctor_id, appointment_date) VALUES (?, ?, ?)";

	            try (PreparedStatement preparedStatement = connection.prepareStatement(appointmentQuery)) {
	                preparedStatement.setInt(1, patientId);
	                preparedStatement.setInt(2, doctorId);
	                preparedStatement.setString(3, appointmentDate);

	                int rowsAffected = preparedStatement.executeUpdate();

	                if (rowsAffected > 0) {
	                    System.out.println("Appointment booked successfully!");
	                } else {
	                    System.out.println("Failed to book appointment!");
	                }
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        } else {
	            System.out.println("Doctor is not available on this date!");
	        }
	    } else {
	        System.out.println("Doctor or Patient ID does not exist!");
	    }
	}

	
	public static boolean checkDoctorAvailability(int doctorId, String appointmentDate, Connection connection) {
	    String query = "SELECT COUNT(*) FROM appointment WHERE doctor_id = ? AND appointment_date = ?";
	    
	    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        preparedStatement.setInt(1, doctorId);
	        preparedStatement.setString(2, appointmentDate);
	        
	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
	            if (resultSet.next()) {
	                int count = resultSet.getInt(1);
	                return count == 0;  // true if doctor is available
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    // Default return if no data or exception
	    return false;
	}

}
