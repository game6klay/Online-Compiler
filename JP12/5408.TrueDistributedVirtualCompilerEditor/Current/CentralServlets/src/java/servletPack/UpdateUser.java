/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servletPack;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import JavaLib.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tc_Pack.SingleUser;
import tc_Pack.UserDB;

/**
 *
 * 
 */
public class UpdateUser extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        SingleUser su = new SingleUser();
        
        try {
            ObjectInputStream in = new ObjectInputStream(request.getInputStream());
            su = (SingleUser)in.readObject();
            in.close();
        }catch(Exception e) {
            System.out.println("Exception: " + e);
        }
        
        if(su.userID.equals("")) {
            System.out.println("User Not Specified!");
        }else {
            // reading userDB for getting index of current user...
            UserDB userDB = new UserDB();
            try
            {
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(CentralServerPathSettings.centralServerPath + "\\UserDB.dat"));
                userDB = (UserDB)in.readObject();
                in.close();
            }catch(Exception e) {
                System.out.println("Exception: " + e);
            }
            int userIndex = -1;
            for(int i=0;i<userDB.list.size();i++) {
                if(userDB.list.get(i).userID.equals(su.userID)) {
                    userIndex = i;
                    break;
                }
            }

            if(userIndex==-1) {
                System.out.println("USER " + su.userID + " NOT FOUND!");
            }else {
                userDB.list.remove(userIndex);
                userDB.list.add(userIndex, su);
                try {
                    ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(CentralServerPathSettings.centralServerPath + "\\UserDB.dat"));
                    out.writeObject(userDB);
                    out.close();
                }catch(Exception e) {
                    System.out.println("Exception: " + e);
                }
            }
        }
        
        //send back to client...
        try {
            ObjectOutputStream out = new ObjectOutputStream(response.getOutputStream());
            out.writeObject(su);
            out.close();
        }catch(Exception e) {
            System.out.println("Exception: " + e);
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
        new LoadForm();
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
        new LoadForm();
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
