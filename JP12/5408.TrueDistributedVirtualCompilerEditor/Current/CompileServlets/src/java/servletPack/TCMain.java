/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servletPack;

import JavaLib.LoadForm;
import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import JavaLib.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tc_Pack.CodeOb;
import tc_Pack.CompilerServerSettings;

/**
 *
 * 
 */
public class TCMain extends HttpServlet {

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
        CodeOb co = new CodeOb();
        long t1, t2;
        
        try {
            ObjectInputStream in = new ObjectInputStream(request.getInputStream());
            co = (CodeOb)in.readObject();
            in.close();
        }catch(Exception e) {
            System.out.println("Exception: " + e);
        }
        
        if(co.userID.equals("")) {
            co.returnString  = "User Not Specified!";
            co.toDo = -1;
        }
        
        // read admin db for reading compiler settings.
        // default compiler paths
        String javaCompilerDir = "C:\\Program Files\\Java\\jdk1.6.0_16\\bin\\";
        String cCompilerDir = "C:\\Program Files\\Microsoft Visual Studio\\VC98\\Bin\\";
        try {
            ObjectInputStream in1 = new ObjectInputStream(new FileInputStream(CompilerServerPathSettings.compilerServerPath + "\\compile-server.settings"));
            CompilerServerSettings settings  = (CompilerServerSettings)in1.readObject();
            in1.close();
        
            javaCompilerDir = settings.javaCompilerDir;
            cCompilerDir = settings.cCompilerDir;
        }catch(Exception e) { 
            System.out.println("Error Reading AdminDB.DAT: " + e);
        }
        
        // flush old directory
        removeDirectory(co.userID);
        // re-create directory...
        createDirectory(co.userID);
        
        // process request
        switch(co.toDo) {
            case 3: // compile java
            {
                try {

                    // write java code
                    String fname = CompilerServerPathSettings.compilerServerPath + "\\CodeDB\\" + co.userID + "\\" + co.mainClass + ".java";
                    BufferedWriter bw = new BufferedWriter(new FileWriter(fname));
                    bw.write(co.code);
                    bw.close();
                    
                    // compile
                    fname = CompilerServerPathSettings.compilerServerPath + "\\CodeDB\\" + co.userID + "\\" + co.mainClass + ".java";
                    File codeFile = new File(fname);
                    String parentDir = codeFile.getParent();

                    ArrayList <String> cmd = new ArrayList <String> ();
                    cmd.add(javaCompilerDir + "javac.exe");
                    cmd.add("-classpath");
                    cmd.add(parentDir);
                    cmd.add("-sourcepath");
                    cmd.add(parentDir);
                    cmd.add("-d");
                    cmd.add(parentDir);
                    cmd.add("-verbose");
                    cmd.add(fname);
                    
                    t1 = Calendar.getInstance().getTimeInMillis();
                    
                    ProcessBuilder pb = new ProcessBuilder(cmd);
                    Process p = pb.start();

                    String res = "";
                    int temp;
                    BufferedInputStream in2 = new BufferedInputStream(p.getErrorStream());
                    while( (temp=in2.read()) != -1) {
                        res += (char)temp;
                    }
                    
                    t2 = Calendar.getInstance().getTimeInMillis();
                    co.compileTime = t2 - t1;
                    
                    co.returnString = res;
                    co.toDo = 301; // compiled & result returned...
                }catch(Exception e) {
                    co.returnString = "Exception: " + e;
                    co.toDo = 302; // error
                }
            }
            break;
            case 13: // compile cpp
            {
                try {
                    // write cpp code file
                    String fname = CompilerServerPathSettings.compilerServerPath + "\\CodeDB\\" + co.userID + "\\" + co.mainClass + ".cpp";
                    BufferedWriter bw = new BufferedWriter(new FileWriter(fname));
                    bw.write(co.code);
                    bw.close();
                    
                    // compile
                    fname = CompilerServerPathSettings.compilerServerPath + "\\CodeDB\\" + co.userID + "\\" + co.mainClass + ".cpp";
                    File codeFile = new File(fname);
                    String parentDir = codeFile.getParent();
                    String exeName = fname;
                    while(!exeName.endsWith(".")) { // wait till dot
                        exeName = exeName.substring(0,exeName.length()-1);
                    }
                    exeName += "exe";

                    ArrayList <String> cmd = new ArrayList <String> ();
                    cmd.add(cCompilerDir + "cl.exe");
                    cmd.add("/I");
                    cmd.add(parentDir);
                    cmd.add("\"" + fname + "\"");
                    cmd.add("/Fe\"" + exeName + "\"");

                    t1 = Calendar.getInstance().getTimeInMillis();
                    ProcessBuilder pb = new ProcessBuilder(cmd);
                    pb.directory(new File(parentDir));
                    Process p = pb.start();

                    String res = "";
                    int temp;
                    BufferedInputStream in2 = new BufferedInputStream(p.getErrorStream());
                    while( (temp=in2.read()) != -1) {
                        res += (char)temp;
                    }
                    res += "\n";
                    in2 = new BufferedInputStream(p.getInputStream());
                    while( (temp=in2.read()) != -1) {
                        res += (char)temp;
                    }
                    t2 = Calendar.getInstance().getTimeInMillis();
                    co.compileTime = t2 - t1;
                    
                    co.returnString = res;
                    co.toDo = 1301;
                }catch(Exception e) {
                    co.returnString = "Exception: " + e;
                    co.toDo = 1302;
                }
            }
            break;
            case 4: // execute java - take name from client side
            {
                // re-compile before execution every time
                try {
                    // write java code
                    String fname = CompilerServerPathSettings.compilerServerPath + "\\CodeDB\\" + co.userID + "\\" + co.mainClass + ".java";
                    BufferedWriter bw = new BufferedWriter(new FileWriter(fname));
                    bw.write(co.code);
                    bw.close();
                    
                    // compile
                    fname = CompilerServerPathSettings.compilerServerPath + "\\CodeDB\\" + co.userID + "\\" + co.mainClass + ".java";
                    File codeFile = new File(fname);
                    String parentDir = codeFile.getParent();
                    ArrayList <String> cmd = new ArrayList <String> ();
                    cmd.add(javaCompilerDir + "javac.exe");
                    cmd.add("-classpath");
                    cmd.add(parentDir);
                    cmd.add("-sourcepath");
                    cmd.add(parentDir);
                    cmd.add("-d");
                    cmd.add(parentDir);
                    cmd.add("-verbose");
                    cmd.add(fname);
                    
                    ProcessBuilder pb = new ProcessBuilder(cmd);
                    Process p = pb.start();
                    String res = "";
                    int temp;
                    BufferedInputStream in2 = new BufferedInputStream(p.getErrorStream());
                    while( (temp=in2.read()) != -1) {
                        res += (char)temp;
                    }
                

                    // execute code begins...
                    fname = CompilerServerPathSettings.compilerServerPath + "\\CodeDB\\" + co.userID + "\\" + co.mainClass + ".java";
                    codeFile = new File(fname);
                    parentDir = codeFile.getParent();
                    String fileName = codeFile.getName();
                    String className = fileName;
                    while(!className.endsWith(".")) { // wait till dot
                        className = className.substring(0,className.length()-1);
                    }
                    className = className.substring(0,className.length()-1); // remove last dot too

                    cmd = new ArrayList<String> ();
                    cmd.add("java");
                    cmd.add("-classpath");
                    cmd.add(parentDir);
                    cmd.add("-Djava.library.path="+parentDir);
                    cmd.add(className);

                    t1 = Calendar.getInstance().getTimeInMillis();
                    pb = new ProcessBuilder(cmd);
                    p = pb.start();
                    res = "Program Standard Error Stream:\n";
                    in2 = new BufferedInputStream(p.getErrorStream());
                    while( (temp=in2.read()) != -1) {
                        res += (char)temp;
                    }
                    res += "\n\nProgram Standard Output Stream:\n";
                    in2 = new BufferedInputStream(p.getInputStream());
                    while( (temp=in2.read()) != -1) {
                        res += (char)temp;
                    }
                    t2 = Calendar.getInstance().getTimeInMillis();
                    co.executionTime = t2 - t1;
                    co.returnString = res;
                    co.toDo = 401;
                }catch(Exception e) {
                    co.returnString = "Exception: " + e;
                    co.toDo = 402;
                }
            }
            break;
            case 14: // execute cpp - take name from client side
            {
             
                // recompile before execution
                try {
                    // write cpp code file
                    String fname = CompilerServerPathSettings.compilerServerPath + "\\CodeDB\\" + co.userID + "\\" + co.mainClass + ".cpp";
                    BufferedWriter bw = new BufferedWriter(new FileWriter(fname));
                    bw.write(co.code);
                    bw.close();
                    
                    // compile
                    fname = CompilerServerPathSettings.compilerServerPath + "\\CodeDB\\" + co.userID + "\\" + co.mainClass + ".cpp";
                    File codeFile = new File(fname);
                    String parentDir = codeFile.getParent();
                    String exeName = fname;
                    while(!exeName.endsWith(".")) { // wait till dot
                        exeName = exeName.substring(0,exeName.length()-1);
                    }
                    exeName += "exe";

                    ArrayList <String> cmd = new ArrayList <String> ();
                    cmd.add(cCompilerDir + "cl.exe");
                    cmd.add("/I");
                    cmd.add(parentDir);
                    cmd.add("\"" + fname + "\"");
                    cmd.add("/Fe\"" + exeName + "\"");

                    t1 = Calendar.getInstance().getTimeInMillis();
                    ProcessBuilder pb = new ProcessBuilder(cmd);
                    pb.directory(new File(parentDir));
                    Process p = pb.start();

                    String res = "";
                    int temp;
                    BufferedInputStream in2 = new BufferedInputStream(p.getErrorStream());
                    while( (temp=in2.read()) != -1) {
                        res += (char)temp;
                    }
                    res += "\n";
                    in2 = new BufferedInputStream(p.getInputStream());
                    while( (temp=in2.read()) != -1) {
                        res += (char)temp;
                    }

                    // now execute
                    fname = CompilerServerPathSettings.compilerServerPath + "\\CodeDB\\" + co.userID + "\\" + co.mainClass + ".cpp";
                    codeFile = new File(fname);
                    parentDir = codeFile.getParent();
                    exeName = fname;
                    while(!exeName.endsWith(".")) { // wait till dot
                        exeName = exeName.substring(0,exeName.length()-1);
                    }
                    exeName += "exe";

                    t1 = Calendar.getInstance().getTimeInMillis();
                    pb = new ProcessBuilder(exeName);
                    pb.directory(new File(parentDir));
                    p = pb.start();

                    res = "Program Standard Error Stream:\n";
                    in2 = new BufferedInputStream(p.getErrorStream());
                    while( (temp=in2.read()) != -1) {
                        res += (char)temp;
                    }
                    res += "\n\nProgram Standard Output Stream:\n";
                    in2 = new BufferedInputStream(p.getInputStream());
                    while( (temp=in2.read()) != -1) {
                        res += (char)temp;
                    }
                    t2 = Calendar.getInstance().getTimeInMillis();
                    co.executionTime = t2 - t1;
                    
                    co.returnString = res;
                    co.toDo = 1401; // ok
                }catch(Exception e) {
                    co.returnString = "Exception: " + e;
                    co.toDo = 1402; // exception
                }
            }
            break;
        }
        
        //send back to client...
        try
        {
            ObjectOutputStream out = new ObjectOutputStream(response.getOutputStream());
            out.writeObject(co);
            out.close();
        }
        catch(Exception e)
        {
            System.out.println("Exception: " + e);
        }        
    }
    
    public void createDirectory(String userID) {
        System.out.println("Creating Directory For Client: " + userID);
        File f = new File(CompilerServerPathSettings.compilerServerPath + "\\CodeDB\\" + userID);
        try {
            f.mkdir();
        }catch(Exception e) {
            ;
        }
        // delete all previous files if already exists
        try {
            File files[] = f.listFiles();
            for(File file: files) {
                try {
                    file.delete();
                }catch(Exception e) {
                    System.out.println("Error Deleting File: " + e);
                }
            }
        }catch(Exception e) {
            ;
        }
                
    }    
    
    public void removeDirectory(String userID) {
        System.out.println("Removing For Client: " + userID);
        File f = new File(CompilerServerPathSettings.compilerServerPath + "\\CodeDB\\" + userID);
        if(!f.exists()) {
            return;
        }
        
        // delete all internal files
        try {
            File files[] = f.listFiles();
            for(File file: files) {
                try {
                    file.delete();
                }catch(Exception e) {
                    System.out.println("Error Deleting File: " + e);
                }
            }
        }catch(Exception e) {
            ;
        }
        
        try {
            f.delete();
        }catch(Exception e) {
            ;
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
