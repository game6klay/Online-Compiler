/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servletPack;

import java.io.*;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import JavaLib.*;
import javax.servlet.http.HttpServletResponse;
import tc_Pack.ServerAssignments;
import tc_Pack.SingleServer;
import tc_Pack.SingleUser;
import tc_Pack.UserDB;

/**
 *
 *
 */
public class GetServerAddress extends HttpServlet {

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
        
        ServerAssignments sa = new ServerAssignments();
        SingleServer ss = new SingleServer();
        SingleUser su = new SingleUser();

        // read calling user
        try {
            ObjectInputStream in = new ObjectInputStream(request.getInputStream());
            su = (SingleUser)in.readObject();
            in.close();
        }catch(Exception e) {
            System.out.println("Exception: " + e);
        }

        // load assignment
        try
        {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(CentralServerPathSettings.centralServerPath + "\\server.assignments"));
            sa = (ServerAssignments)in.readObject();
            in.close();
        }catch(Exception e) {
            System.out.println("Exception: " + e);
        }
        
        // find server with least load
        if(sa.servers.size() > 0) {
            int minLoad = sa.servers.get(0).loadCount;
            int minIndex = 0;
            for(int i=1;i<sa.servers.size();i++) {
                if(sa.servers.get(i).loadCount < minLoad) {
                    minLoad = sa.servers.get(i).loadCount;
                    minIndex = i;
                }
            }
            ss = sa.servers.get(minIndex);
            ss.loadCount++;
            Calendar c = Calendar.getInstance();
            String dt = "" + c.get(c.YEAR) + "-" + (1+c.get(c.MONTH)) + "-" + c.get(c.DATE) + " " + c.get(c.HOUR_OF_DAY) + ":" + c.get(c.MINUTE) + ":" + c.get(c.SECOND);
            ss.log.add("REQUEST FOR " + su.userID + " SCHEDULED AT: " + dt);
            
            updateServerAssignments(sa);
        }
        
        ss.log.clear();
        
        try {
            ObjectOutputStream out = new ObjectOutputStream(response.getOutputStream());
            out.writeObject(ss);
            out.close();
        }catch(Exception e) {
            System.out.println("Exception: " + e);
        }
        
    }
    
    public void updateServerAssignments(ServerAssignments sa) {
        try {
            ObjectOutputStream out1 = new ObjectOutputStream(new FileOutputStream(CentralServerPathSettings.centralServerPath + "\\server.assignments"));
            out1.writeObject(sa);
            out1.close();
        }catch(Exception e) {
            System.out.println("Error Writing. " + e);
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
