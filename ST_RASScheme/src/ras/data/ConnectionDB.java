package ras.data;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
// Deprecated
import org.hibernate.service.ServiceRegistryBuilder;

import ras.security.EncryptDecrypt;

@SuppressWarnings("deprecation")
public class ConnectionDB {
	private static final String xmlFile = "hibernate.cfg.xml";
	private Configuration config = null;
	private ServiceRegistry servReg = null;
	private SessionFactory sessionFact = null;
	private Session session = null;
	private Query query = null;
	private EncryptDecrypt ede = null;
	private Transaction tx = null;
	
	public ConnectionDB() {
		config = new Configuration();
		ede = new EncryptDecrypt();
		config.configure(xmlFile);
		String password = ede.decryptMsg(config.getProperties().getProperty("connection.password"));
		config.setProperty("hibernate.connection.password", password);
		servReg = new ServiceRegistryBuilder().applySettings(config.getProperties()).build();
		sessionFact = config.buildSessionFactory(servReg);
		session = sessionFact.openSession();
	}
	
	public List<Object> selectGenericQuery(String queryDB, Object id){
		//query = session.createQuery("from sra_classRAS where cra_idClassification = :id");
		//initiateDB();
		query = session.createQuery(queryDB);
		if(id != null)
			query.setParameter("id", id);
		@SuppressWarnings("unchecked")
		List<Object> list = query.list();
		shutdown();
		return list;
	}
	
	public int insertGenericQuery(Object data){
		Integer idTransaction = 0;
		try{
			tx = session.beginTransaction();
			idTransaction = (Integer) session.save(data);
			if(idTransaction == null)
				throw new IllegalArgumentException("A problem occurred when "+data.getClass().getName()+" was been stored");
			tx.commit();
		} catch (HibernateException e){
			if(tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			shutdown();
		}
		return idTransaction;
	}
	
	public int insertGenericQuery(ST_RASTest rasTest, ST_RASTestTripCommand tripCommand, List<ST_RASTestContingencies> contingencies){
		Integer idTransaction = 0;
		Integer idAuxTransaction = 0;
		try{
			tx = session.beginTransaction();
			idTransaction = (Integer) session.save(rasTest);
			idAuxTransaction = (Integer) session.save(tripCommand);
			if(idAuxTransaction != null){
				for(ST_RASTestContingencies contingency:contingencies){
					idAuxTransaction = (Integer) session.save(contingency);
					if(idAuxTransaction == null)
						throw new IllegalArgumentException("A problem occured when CONTINGENCY data of a RAS Test was been stored");
				}
			}
			else{
				idTransaction = 0;
				throw new IllegalArgumentException("A problem occured when TRIP COMMANDA data of a RAS Test was been stored");
			}
				
			tx.commit();
		} catch (HibernateException e){
			if(tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			shutdown();
		}
		return idTransaction;
	}
	
	public boolean updateGenericQuery(ST_RASTest data, int id){
		boolean flagUpdate = true;
		tx = null;
		try{
			tx = session.beginTransaction();
			//data = (ST_RASTest)session.get(ST_RASTest.class, id);
			session.update(data);
			tx.commit();
		} catch (HibernateException e) {
			if(tx != null)
				tx.rollback();
			System.out.println(ras.interfaces.Utilities.separator3+"ConnectionDB err: "+e.getMessage());
			e.printStackTrace();
			flagUpdate = false;
		} finally {
			shutdown();
		}
		return flagUpdate;
	}
	
	public boolean updateGenericQuery(Object data, int id){
		boolean flagUpdate = true;
		tx = null;
		try{
			tx = session.beginTransaction();
			//data = (ST_RASTest)session.get(ST_RASTest.class, id);
			session.update(data);
			tx.commit();
		} catch (HibernateException e) {
			if(tx != null)
				tx.rollback();
			System.out.println(ras.interfaces.Utilities.separator3+"ConnectionDB err: "+e.getMessage());
			e.printStackTrace();
			flagUpdate = false;
		} finally {
			shutdown();
		}
		return flagUpdate;
	}
	
	/* Method to UPDATE salary for an employee
	   public void updateEmployee(Integer EmployeeID, int salary ){
	      Session session = factory.openSession();
	      Transaction tx = null;
	      try{
	         tx = session.beginTransaction();
	         Employee employee = 
	                    (Employee)session.get(Employee.class, EmployeeID); 
	         employee.setSalary( salary );
			 session.update(employee); 
	         tx.commit();
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         session.close(); 
	      }
	   }
	   */
	
	public void shutdown(){
		session.close();
		sessionFact.close();
	}
	/*
	@SuppressWarnings("null")
	public static void main(String[] args){
		ConnectionDB db = new ConnectionDB();
		//db.initiateDB();
		List<Object> listTest = null;
		listTest = db.selectGenericQuery("from sra_classRAS",null);
		listTest = null;
		listTest = db.selectGenericQuery("from sra_RASSchemes", null);
		listTest = null;
		System.out.println(listTest.size());
		//System.out.println();
		
	}
	*/
}
