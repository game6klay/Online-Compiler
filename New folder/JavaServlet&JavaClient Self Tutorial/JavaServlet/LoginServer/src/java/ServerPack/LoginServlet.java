/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerPack;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.StringTokenizer;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    boolean flag;
    String idPassword;
    String userName, password;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            ObjectInputStream in = new ObjectInputStream(request.getInputStream());
            idPassword = (String) in.readObject();  //reading the object sent from client the idpassword will be comma serperated.
                                                    //i.e if user sends abc,pass the "abc" will be username and "pass" will be password
            in.close();
            flag = false;
            System.out.println("READ OK");
        } catch (Exception e) {
            System.out.println("ERROR IN READING" + e);
        }

        try {
            StringTokenizer st = new StringTokenizer(idPassword, ",");  // here we will be sepearting user name and password using 
            //StringTokenizer class. 
            if (st.hasMoreTokens()) {
                userName = st.nextElement().toString();
                password = st.nextToken().toString();
                if (userName.equals("user") && password.equals("password")) {    //check if the received username and password is same as 
                    flag = true;                                                            // required username and password.
                }
            }

        } catch (Exception e) {
            System.out.println("Error Fetching data : " + e);
        }

        try {
            // write object to file...
            ObjectOutputStream obOut = new ObjectOutputStream(response.getOutputStream());
            obOut.writeObject(flag);  //sending response back to client if login successfull then true else false.
            obOut.close();
        } catch (Exception e) {
            System.out.println("ERROR IN WRITEING" + e);
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
