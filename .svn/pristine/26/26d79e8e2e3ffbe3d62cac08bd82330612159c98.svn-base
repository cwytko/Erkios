package ras.conexion;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class ConfigurationMachine {
	
	public final int PORTSensor = 2000;
	public final int PORTActuator = 2005;
	public final int PORTPTCT = 2010;
	public final int PORTDAC = 2015;
	public final int PORTOPWan = 2010;
	public final int PORTRAS = 2025;
	public final String hostActuator = "localhost";
	public final String hostSensor = "localhost";
	public final String hostPTCT = "localhost";
	public final String hostDAC = "localhost";
	public final String hostOPWan = "localhost";
	public final String hostRAS = "localhost";
	
	public ConfigurationMachine()
	{
		
	}
	
	public String getIpAddress()
	{
		String ip=null;
		try
		{
			Enumeration<NetworkInterface> enumNetInt = NetworkInterface.getNetworkInterfaces();
			while(enumNetInt.hasMoreElements())
			{
				NetworkInterface netInt = (NetworkInterface) enumNetInt.nextElement();
				for(Enumeration<InetAddress> enumNetLocal = netInt.getInetAddresses(); enumNetLocal.hasMoreElements();)
				{
					InetAddress ipAddress = (InetAddress)enumNetLocal.nextElement();
		            if (!ipAddress.isLoopbackAddress()) {
		                if (ipAddress instanceof Inet4Address ) {
		                    return ipAddress.getHostAddress();
		                }
		            }
				}
			}
		}
		catch(SocketException e)
		{
			System.err.println(ras.interfaces.Utilities.separator3+"ConfigurationMachine err: ipAddress-ConfigurationMachine");
			//e.printStackTrace();
		}
		return ip;
	}
	
    public int getRealPort()
    {
    	ServerSocket sSocket = null;
		int port = 0;
		try 
		{
			sSocket = new ServerSocket(0);
			port = sSocket.getLocalPort();
		} catch (IOException e) 
		{
			System.err.println(ras.interfaces.Utilities.separator3+"ConfigurationMachine err: port-ConfigurationMachine");
			e.printStackTrace();
		}
		finally
		{
            if (sSocket != null) {
                try 
                {
                	sSocket.close();
                } catch (IOException e) 
                {
                	System.err.println(ras.interfaces.Utilities.separator3+"ConfigurationMachine err: port(close)-ConfigurationMachine");
                    e.printStackTrace();
                }
            }
		}
    	return port;
    }
    
    public boolean availablePort(String ip, Integer port) 
    {
        boolean status = false;
        ServerSocket sSocket = null;
        try 
        {
            sSocket = new ServerSocket(port);
            status = true; 
        } 
        catch (IOException e) 
        {
        	System.err.println(ras.interfaces.Utilities.separator3+"ConfigurationMachine err:"+"The port "+port+" is unavailable");
        } 
        finally 
        {
            if (sSocket != null) {
                try 
                {
                	sSocket.close();
                } catch (IOException e) 
                {
                	System.err.println(ras.interfaces.Utilities.separator3+"ConfigurationMachine err: port available(close)-ConfigurationMachine");
                    e.printStackTrace();
                }
            }
        }
        return status;
    }
    
    public String getHostName(String ipAddress)
    {
    	InetAddress addr;
    	String hostName="";
		try {
			addr = InetAddress.getByName(ipAddress);
			hostName = addr.getCanonicalHostName();
			hostName = hostName.split("-")[0].toUpperCase();
		} catch (UnknownHostException e) {
			System.out.println(ras.interfaces.Utilities.separator3+"ConfigurationMachine err: convert from IpAddress "+ipAddress+" to Hostname");
			//e.printStackTrace();
		}
    	return hostName;
    }
}