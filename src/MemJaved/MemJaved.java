/*
 * memJaved <https://github.com/ripe3/MemJaved>
 * 
 * Copyright (C) 2017 - Luis Filipe <https://github.com/ripe3>
 * 
 * This program is free software under the terms of the 
 * GNU General Public License 3 (GPLv3) as published by
 * the Free Software Foundation.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package MemJaved;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MemJaved {
    
    String host = "localhost";
    int port = 11211;
    
    public MemJaved() {
        
    }
    
    public MemJaved(String host, int port) {
        this.host = host;
        this.port = port;
    }
    
    public boolean set(String key, int expiry, String data) {

        try {
            Socket pingSocket = null;
            PrintWriter out = null;
            BufferedReader in = null;

            try {
                pingSocket = new Socket(host, port);
                out = new PrintWriter(pingSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(pingSocket.getInputStream()));
            } catch (IOException e) {
                return false;
            }

            String re = "";            
            String cmd = "set " + key + " 0 " + expiry + " " + data.length() + "\r\n" + data + "\r\n";

            out.println(cmd);
            while (!(re = in.readLine()).equals("")) { 
                if(re.equals("STORED"))
                    break;
                
                if(re.contains("CLIENT_ERROR"))
                    throw new Exception(re.replace("CLIENT_ERROR ", ""));
                
                if(re.contains("SERVER_ERROR"))
                    throw new Exception(re.replace("SERVER_ERROR ", ""));
                
                if(re.contains("ERROR"))
                    throw new Exception("nonexistent command name");
            }

            out.close();
            in.close();
            pingSocket.close();
            
            return true;
            
        } catch (Exception ex) {
            return false;
        }

    }
    
    public String get(String key) {

        try {
            Socket pingSocket = null;
            PrintWriter out = null;
            BufferedReader in = null;

            try {
                pingSocket = new Socket("localhost", 11211);
                out = new PrintWriter(pingSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(pingSocket.getInputStream()));
            } catch (IOException e) {
                return "";
            }

            String re = "";
            
            String cmd = "get " + key;

            out.println(cmd);
            while (!(re = in.readLine()).equals("")) {
                
                if(!re.contains("VALUE") && !re.contains("END"))
                    return re;

                if(re.equals("END"))
                    break;
                
                if(re.contains("CLIENT_ERROR"))
                    throw new Exception(re.replace("CLIENT_ERROR ", ""));
                
                if(re.contains("SERVER_ERROR"))
                    throw new Exception(re.replace("SERVER_ERROR ", ""));
                
                if(re.contains("ERROR"))
                    throw new Exception("nonexistent command name");
            }

            out.close();
            in.close();
            pingSocket.close();
            
            return null;
            
        } catch (Exception ex) {
            return null;
        }

    }
    
    public boolean delete(String key) {

        try {
            Socket pingSocket = null;
            PrintWriter out = null;
            BufferedReader in = null;

            try {
                pingSocket = new Socket("localhost", 11211);
                out = new PrintWriter(pingSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(pingSocket.getInputStream()));
            } catch (IOException e) {
                return false;
            }

            String re = "";
            
            String cmd = "delete " + key;

            out.println(cmd);
            while (!(re = in.readLine()).equals("")) {
                
                if(re.contains("DELETED") || re.equals("END") || re.equals("NOT_FOUND"))
                    break;
                
                if(re.contains("CLIENT_ERROR"))
                    throw new Exception(re.replace("CLIENT_ERROR ", ""));
                
                if(re.contains("SERVER_ERROR"))
                    throw new Exception(re.replace("SERVER_ERROR ", ""));
                
                if(re.contains("ERROR"))
                    throw new Exception("nonexistent command name");
            }

            out.close();
            in.close();
            pingSocket.close();
            
            return true;
            
        } catch (Exception ex) {
            return false;
        }

    }
    
}
