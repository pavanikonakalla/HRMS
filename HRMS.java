import java.sql.*;
import java.util.Scanner;

public class HRMS {
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        try { createTables(); } catch(Exception e){ e.printStackTrace(); }
        while(true){
            System.out.println("\n==== HRMS TERMINAL ====");
            System.out.println("1. Create new Organization");
            System.out.println("2. Login to Organization");
            System.out.println("3. Exit");
            System.out.print("Choice: ");
            int ch = sc.nextInt(); sc.nextLine();
            if(ch==1) createOrg();
            else if(ch==2) loginMenu();
            else break;
        }
    }
    static void createTables(){
        try(Connection con = DBConnection.getConnection(); Statement st = con.createStatement()){
            st.execute("CREATE DATABASE IF NOT EXISTS hrms_db");
            st.execute("USE hrms_db");
            st.execute("CREATE TABLE IF NOT EXISTS organizations (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(100), address VARCHAR(255), pan VARCHAR(20), gst VARCHAR(20), dept VARCHAR(50), email VARCHAR(100) UNIQUE, password VARCHAR(100), empCount INT)");
            System.out.println("Database connected!");
        }catch(Exception e){ System.out.println("DB Error: "+e.getMessage()); }
    }
    static void createOrg(){
        try{
            System.out.print("Organization name: "); String name = sc.nextLine();
            System.out.print("Address: "); String addr = sc.nextLine();
            System.out.print("PAN: "); String pan = sc.nextLine();
            System.out.print("GST: "); String gst = sc.nextLine();
            System.out.print("Department: "); String dept = sc.nextLine();
            System.out.print("Email (for login): "); String email = sc.nextLine();
            System.out.print("Password: "); String pass = sc.nextLine();
            System.out.print("Employees count: "); int count = sc.nextInt(); sc.nextLine();
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("INSERT INTO organizations(name,address,pan,gst,dept,email,password,empCount) VALUES(?,?,?,?,?,?,?,?)");
            ps.setString(1,name); ps.setString(2,addr); ps.setString(3,pan); ps.setString(4,gst); ps.setString(5,dept); ps.setString(6,email); ps.setString(7,pass); ps.setInt(8,count);
            ps.executeUpdate();
            System.out.println(">> Organization Created & Saved in MySQL!");
        }catch(Exception e){ System.out.println(e.getMessage()); }
    }
    static void loginMenu(){
        while(true){
            System.out.println("\n--- Login Menu ---");
            System.out.println("1. Enter email and password");
            System.out.println("2. Forget password");
            System.out.println("3. Back to main menu");
            System.out.print("Choice: ");
            int ch = sc.nextInt(); sc.nextLine();
            if(ch==1){ if(login()) return; }
            else if(ch==2) forgetPassword();
            else return;
        }
    }
    static boolean login(){
        try{
            System.out.print("Enter email: "); String email = sc.nextLine();
            System.out.print("Enter password: "); String pass = sc.nextLine();
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM organizations WHERE email=? AND password=?");
            ps.setString(1,email); ps.setString(2,pass);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                System.out.println("\nWelcome to "+rs.getString("name"));
                dashboard(rs.getString("name"));
                return true;
            } else System.out.println("Invalid credentials!");
        }catch(Exception e){ System.out.println(e.getMessage()); }
        return false;
    }
    static void forgetPassword(){
        try{
            System.out.print("Enter email: "); String email = sc.nextLine();
            System.out.print("Enter new password: "); String np = sc.nextLine();
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("UPDATE organizations SET password=? WHERE email=?");
            ps.setString(1,np); ps.setString(2,email);
            if(ps.executeUpdate()>0) System.out.println("Password updated!");
            else System.out.println("Email not found!");
        }catch(Exception e){ System.out.println(e.getMessage()); }
    }

        // Feature: Student Search - Added in feature/student_search branch
    static void searchStudent(){
        System.out.println("--- Student Search ---");
        System.out.print("Enter student name to search: ");
        String name = sc.nextLine();
        System.out.println("Searching for: " + name + " ... Feature in progress!");
    }

    static void dashboard(String orgName){
        while(true){
            System.out.println("\n=== "+orgName+" Dashboard ===");
            System.out.println("1. Employee -> add, view, update, delete");
            System.out.println("2. Attendance -> create, view, update, delete");
            System.out.println("3. Salary -> create, update, delete, view");
            System.out.println("4. Notice/Holiday -> create, view, delete, update");
            System.out.println("5. Logout");
            System.out.print("Choice: ");
            int ch = sc.nextInt(); sc.nextLine();
            if(ch==5) return;
            else System.out.println(">> Module "+ch+" we will add next!");
        }
    }
}