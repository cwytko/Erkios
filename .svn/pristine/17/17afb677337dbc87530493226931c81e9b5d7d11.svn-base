package ras.ssl;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.rmi.server.RMIServerSocketFactory;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocketFactory;

public class RMISSLServerSocketFactory implements RMIServerSocketFactory{
	
	private SSLServerSocketFactory ssf;
	
	public SSLServerSocketFactory getSsf() {
		return ssf;
	}

	private void setSsf(SSLServerSocketFactory ssf) {
		this.ssf = ssf;
	}

	public RMISSLServerSocketFactory() throws Exception{
		try{
			SSLContext sco;
			KeyManagerFactory kmf;
			KeyStore kst;
			//gridstat125@RAS#
			char[] passphrase = "gridstat125@RAS#".toCharArray();
			kst = KeyStore.getInstance("JKS");
			
			kst.load(new FileInputStream("keystore"), passphrase);
			
			kmf = KeyManagerFactory.getInstance("SunX509");
			kmf.init(kst, passphrase);
			
			sco = SSLContext.getInstance("TLS");
			sco.init(kmf.getKeyManagers(), null, null);
			setSsf(sco.getServerSocketFactory());
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public ServerSocket createServerSocket(int port) throws IOException {
		// TODO Auto-generated method stub
		return getSsf().createServerSocket(port);
	}
	
	@Override
	public int hashCode(){
		return getClass().hashCode();
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj == this){
			return true;
		} else if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		return true;
	}
}
