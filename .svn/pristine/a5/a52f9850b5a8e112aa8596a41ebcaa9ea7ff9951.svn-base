package ras.gui;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import ras.clientLogic.CTSClient;
import ras.clientLogic.DBRASSchemeClient;
import ras.data.ST_RASTest;
import ras.data.ST_RASTestLink;
import ras.data.ST_RASTestSTCode;
import ras.data.ST_STCode;
import ras.data.ST_classRAS;
import ras.data.ST_componentsEES;
import ras.data.ST_contingencies;
import ras.data.ST_RASTestDisableEnableComponent;
import ras.data.ST_parametersRAS;
import ras.data.ST_rasSchemes;
import ras.data.ST_remedialActions;
import ras.data.ST_tripCommand;
import ras.data.ST_typeSignal;
import ras.interfaces.DigitalSignal;
import ras.interfaces.TestCtrlSignal;
import ras.interfaces.Utilities.DisableEnableComponents;
import ras.interfaces.Utilities.TypeActions;
import ras.interfaces.Utilities.TypeSignals;
import ras.tableModels.ContingenciesTableModel;

import java.awt.Font;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JLayeredPane;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

public class RASSchemeTest {
	//private enum TypeSignal{DigitalSignal,TestControlSignal};
	private List<ST_classRAS> listClassRAS = null;
	private ST_classRAS classRAS = null;
	private List<ST_rasSchemes> listRASSchemes = null;
	private ST_rasSchemes rasScheme = null;
	private List<ST_typeSignal> listTypeSignal = null;
	private ST_typeSignal typeSignal = null;
	private List<ST_componentsEES> listComponentsEES = null;
	private ST_componentsEES componentEES = null;
	private List<ST_STCode> listSTCodes = null;
	private ST_STCode stCode = null;
	private ST_RASTestSTCode rasTestSTCode = null;
	//private ST_parametersRAS rasParameters = null;
	//private ST_tripCommand tripCommand = null;
	//private List<ST_contingencies> listContingencies = null;
	//private String idRAS = null;
	//private ST_tripCommand tripCommandMsg = null;
	//private ST_RASTest rasTest;
	//private ST_RASTestDisableEnableComponent resultDisable = null;
	//private ST_RASTestDisableEnableComponent resultEnable = null;
	
	private DBRASSchemeClient stubDBRAS;
	private CTSClient stubCTS;
	private TestCtrlSignal testCtrlSignal = null;
	private DigitalSignal digSignal = null;
	
	private ContingenciesTableModel contingTblModel = null;
	private JFrame frmST_RAS;
	private JTextField txtPower;
	private JTextField txtCurrent;
	private JTextField txtVoltage;
	private JTextField txtFrequency;
	private JTextField txtReactivePower;
	private JTextField txtPhaseVoltage;
	//private JScrollPane scrollPane = new JScrollPane(tblContingenciesOld);
	private JButton btnCreateTestCtrlSignal;
	private JButton btnDisableActuatorSensor;
	private JButton btnEnableActuatorSensor;
	private JButton btnSendMessage;
	private JComboBox<ST_rasSchemes> cmbRASScheme;
	private JComboBox<ST_classRAS> cmbRASClass;
	private JComboBox<ST_typeSignal> cmbTypeControlSignal;
	private JComboBox<ST_STCode> cmbSTCode;
	private JComboBox<ST_componentsEES> cmbComponentTest;
	private JLabel lblStatus1 = null;
	private JLabel lblStatus2 = null;
	private JLabel lblStatus3 = null;
	private JLabel lblStatus4 = null;
	private JLabel lblStatus5 = null;
	private JLabel lblStatus6 = null;
	private JLabel lblStatus7 = null;
	private JLabel lblStatus8 = null;
	private JLabel lblStatus9 = null;
	private JLabel lblStatus10 = null;
	private JLabel lblStatus11 = null;
	private JLabel lblStatus12 = null;
	private JLabel lblStatus13 = null;
	private JLabel lblStatus14 = null;
	private JLabel lblStatus15 = null;
	private JLabel lblStatus16 = null;
	private JLabel lblStatus17 = null;
	private JLabel lblStatus18 = null;
	private JLabel lblStatus19 = null;
	private JLabel lblStatus20 = null;
	private JLabel lblStatus21 = null;
	private JLabel lblStatus22 = null;
	private JLabel lblStatus23 = null;
	private JLabel lblStatus24 = null;
	private JLabel lblStatus25 = null;
	private JLabel lblDisableEnableActuator = null;
	private JLabel lblDisableEnableSensor = null;
	private JLabel lblRASTestImg = null;
	private JLabel lblNomenclatureImg = null;
	
	private ImageIcon okImg;
	private ImageIcon wrongImg;
	private ImageIcon disableComponent;
	private ImageIcon enableComponent;
	
	private volatile Thread blinker = new Thread();
	private Boolean flagDisableEnableComponent = false;
	private Boolean flagSendMessage = false;
	private String typeAction = null;
	private int idLink = 0;
	private String queryDBRASTest = "";
	private String queryDBContingencies = "";
	private String queryDBStatusComponents = "";
	private String queryDBLinks = "";
	private String queryDBRemedialActions = "";
	private String queryDBTripCommands = "";
	private String queryDBSTCodes = "";
	private String queryDBSTCodesContingency = "";
	
	private JScrollPane spaListContingencies;
	private JTable tblListContingencies;
	private JScrollPane spaRASTest;
	private JTable tblRASTest;
	
	private JScrollPane spaContingencies;
	private JTable tblContingencies;
	private JScrollPane spaStatusComponents;
	private JTable tblStatusComponents;
	private JScrollPane spaLinks;
	private JTable tblLinks;
	private JScrollPane spaRemedialActions;
	private JTable tblRemedialActions;
	private JScrollPane spaTripCommand;
	private JTable tblTripCommand;
	private JScrollPane spaSTCode;
	private JTable tblSTCode;
	private JScrollPane spaSTCodeContingency;
	private JTable tblSTCodeContingency;
	
	private File WSULogo = null;
	private String valPath = "";
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RASSchemeTest window = new RASSchemeTest();
					window.frmST_RAS.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	
	public RASSchemeTest() {
		Boolean flagMenu = true;
		File checkFile = null;
		File okFile = null; 
		File wrongFile = null;
		File rasSchemeFile = null;
		File nomenclatureFile = null;
		File disableComponentFile = null;
		File enableComponentFile = null;
		
		try {
			stubDBRAS = new DBRASSchemeClient();
			
			valPath =  RASSchemeTest.class.getProtectionDomain().getCodeSource().getLocation().getPath();
			checkFile = new File(valPath);
			valPath = (!checkFile.getParent().toString().contains("/bin") ? valPath : checkFile.getParent().toString()+"/");
			valPath += "ras/img/";
			if(System.getProperty("os.name").startsWith("Windows"))
				//valPath = valPath.replace("/", "\\").substring(1, valPath.length());
				valPath = System.getProperty("user.dir") + File.separator + "ras" + File.separator + "img" + File.separator;
			okFile = new File(valPath+"OkImg.png");
			wrongFile = new File(valPath+"WrongImg.png");
			disableComponentFile = new File(valPath+"disableComponent.png");
			enableComponentFile = new File(valPath+"enableComponent.png");
			rasSchemeFile = new File(valPath+"RASTestImg.png");
			nomenclatureFile = new File(valPath+"NomenclatureImg.png");
			System.out.println(valPath+"RASTestImg.png"+new File(valPath+"RASTestImg.PNG").exists());
			lblRASTestImg = new JLabel("");
			lblRASTestImg.setIcon(new ImageIcon(ImageIO.read(rasSchemeFile)));
			lblNomenclatureImg = new JLabel("");
			lblNomenclatureImg.setIcon(new ImageIcon(ImageIO.read(nomenclatureFile)));
			//if(okFile.exists() && !okFile.isDirectory()) { System.out.println(okFile.getAbsolutePath()); } else System.out.println("no ok");
			//if(wrongFile.exists() && !wrongFile.isDirectory()) { System.out.println(wrongFile.getAbsolutePath()); } else System.out.println("no wrong");
			
			setOkImg(new ImageIcon(ImageIO.read(okFile)));
			setWrongImg(new ImageIcon(ImageIO.read(wrongFile)));
			setDisableComponent(new ImageIcon(ImageIO.read(disableComponentFile)));
			setEnableComponent(new ImageIcon(ImageIO.read(enableComponentFile)));
			
			queryDBRASTest = "select" + 
				" rasTest.idRASTest, clas.nameClass,"+ 
				" schemes.nameRAS, rasTest.resultTest,"+ 
				" rasTest.dateTimeBeginTest, rasTest.dateTimeEndTest,"+
				" rasTest.comment"+
				" from"+ 
				" sra_RASTest as rasTest, sra_classRAS as clas, sra_RASSchemes as schemes"+
				" where"+
				" clas.idClassification = rasTest.idClassification and"+
				" schemes.idRAS = rasTest.idRAS and"+
				" rasTest.idRASTest = :id";
			
			queryDBContingencies = " select"+ 
				" conting.idContingency, contingencies.contingency"+
				" from"+
				" sra_RASTest as rasTest,"+
				" sra_contingencies as conting,"+
				" sra_RASTestContingencies as contingencies"+
				" where"+
				" conting.idContingency = contingencies.idContingency and "+
				" rasTest.idRASTest = contingencies.idRASTest and rasTest.idRASTest = :id";
			
			queryDBStatusComponents = " select"+ 
				" scheme.nameRAS, actions.Action, decComponent.disableEnableActuator,"+
				" decComponent.datetimeActuator, decComponent.disableEnableSensor,"+
				" decComponent.datetimeSensor, decComponent.resultDisableEnableComponent,"+
				" decComponent.comment"+
				" from"+
				" sra_RASTestDisableEnableComponents as decComponent,"+
				" sra_RASTest as rasTest,"+
				" sra_RASSchemes as scheme,"+
				" sra_disableEnableAction as actions"+
				" where"+
				" decComponent.idRAS = scheme.idRAS and decComponent.idAction = actions.idAction and"+
				" rasTest.idRASTest = decComponent.idRASTest and rasTest.idRASTest = :id"+
				" order by"+
				" decComponent.datetimeActuator asc";
			
			queryDBLinks = "select"+ 
				" link.idRASTestLink, sources.shortNameComponent,"+ 
				" destinations.shortNameComponent, actions.typeAction, link.resultLink,"+ 
				" link.stateComponent, link.comment"+
				" from"+
				" sra_RASTestLink as link,"+
				" sra_RASTest as rasTest,"+
				" sra_linkEES as ees,"+
				" sra_typeAction as actions,"+
				" sra_componentsEES as sources,"+ 
				" sra_componentsEES as destinations"+
				" where"+
				" link.idLinkEES = ees.idLink and link.idTypeAction = actions.idTypeAction and"+ 
				" ees.linkSource = sources.idComponent and  ees.linkDestination = destinations.idComponent and"+
				" rasTest.idRASTest = link.idRASTest and rasTest.idRASTest = :id"+
				" order by"+
				" link.idRASTestLink asc";
			
			queryDBRemedialActions = "select"+ 
				" remed.idRemedialAction, remedial.remedialAction"+
				" from"+
				" sra_RASTest as rasTest,"+
				" sra_remedialActions as remed,"+
				" sra_RASTestRemedialActions as remedial"+
				" where"+
				" remed.idRemedialAction = remedial.idRemedialAction and"+
				" rasTest.idRASTest = remedial.idRASTest and rasTest.idRASTest = :id";
			
			queryDBTripCommands = "select"+
				" schemes.idRAS, rasTest.idRASTest, schemes.nameRAS, trip.power, trip.current,"+ 
				" trip.voltage, trip.frequency, trip.reactivePower, trip.phaseVoltage"+
				" from"+
				" sra_RASTestTripCommand as trip,"+
				" sra_RASSchemes as schemes,"+
				" sra_RASTest as rasTest"+
				" where"+
				" rasTest.idRASTest = trip.idRASTest and"+
				" rasTest.idRAS = schemes.idRAS and"+
				" rasTest.idRASTest = :id";
			
			queryDBSTCodes = "select"+
				" testCode.idRASTestSTCode, rasTest.idRASTest, stCode.codeName, component.nameComponent,"+
				" actions.typeAction, testCode.codeWordCreateMessage, testCode.codeWordDisableEnableSensor, testCode.codeWordDisableEnableActuator,"+
				" testCode.codeWordPower, testCode.codeWordCurrent,"+
				" testCode.codeWordVoltage, testCode.codeWordFrequency, testCode.codeWordReactivePower,"+
				" testCode.codeWordPhaseVoltage, parameter.nameParameter, testCode.codeWordError"+
				" from"+
				" sra_RASTestSTCode as testCode,"+
				" sra_RASTest as rasTest,"+
				" sra_STCode as stCode,"+
				" sra_componentsEES as component,"+
				" sra_typeAction as actions,"+
				" sra_parameters as parameter"+
				" where"+
				" stCode.idSTCode = testCode.idSTCode and"+
				" component.idComponent = testCode.idComponent and"+
				" actions.idTypeAction = testCode.idTypeAction and"+
				" rasTest.idRASTest = testCode.idRASTest and"+ 
				" testCode.idCodeWordParamError = parameter.idParameter and"+
				" rasTest.idRASTest = :id";
			
			queryDBSTCodesContingency = "select"+
					" testCodeContingency.idRASTestSTCodeContingency, rasTest.idRASTest, contingencies.idContingency, stCode.codeName, component.nameComponent,"+
					" actions.typeAction, testCodeContingency.codeWordContingecy, testCodeContingency.codeWordError"+
					" from"+
					" sra_RASTestSTCodeContingency as testCodeContingency,"+
					" sra_RASTest as rasTest,"+
					" sra_STCode as stCode,"+
					" sra_componentsEES as component,"+
					" sra_typeAction as actions,"+
					" sra_RASTestContingencies as contingency,"+
					" sra_contingencies as contingencies"+
					" where"+
					" stCode.idSTCode = testCodeContingency.idSTCode and"+
					" component.idComponent = testCodeContingency.idComponent and"+
					" actions.idTypeAction = testCodeContingency.idTypeAction and"+
					" rasTest.idRASTest = testCodeContingency.idRASTest and"+
					" contingency.idRASTestContingency = testCodeContingency.idContingency and"+
					" contingencies.idContingency = contingency.idContingency and"+
					" rasTest.idRASTest = :id";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(frmST_RAS, e.getMessage());
			flagMenu = false;
		}
		initialize(flagMenu);
		lblDisableEnableSensor.setIcon(getEnableComponent());
		lblDisableEnableActuator.setIcon(getEnableComponent());
		
		btnEnableActuatorSensor = new JButton("<html><center>Enable Actuator<br />& Sensor</center></html>");
		btnEnableActuatorSensor.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				ST_RASTestDisableEnableComponent resultEnable = null;
				try {
					setImgs();
					stubCTS = new CTSClient();
					typeAction = TypeActions.EnableComponent.toString();
					//runThread();
					ST_RASTestSTCode testCode = rasTestSTCode;
					rasTestSTCode = new ST_RASTestSTCode(rasTestSTCode.getIdComponent(), rasTestSTCode.getIdRASTest(),
							rasTestSTCode.getIdSTCode(), TypeActions.EnableComponent.ordinal()+1);
					
					rasTestSTCode = new ST_RASTestSTCode(rasTestSTCode, testCode.getIdCodeWordParamError(), testCode.getCodeWordCreateMessage());
					rasTestSTCode = new ST_RASTestSTCode(rasTestSTCode, testCode.getCodeWordDisableEnableSensor(), testCode.getCodeWordDisableEnableActuator());
					rasTestSTCode = new ST_RASTestSTCode(rasTestSTCode, testCode.getCodeWordError(), testCode.getIdCodeWordParamError());
					
					TestCtrlSignal testAuxCtrlSignal = new TestCtrlSignal(true, getTestCtrlSignal().getResultTestSensor(), 
							getTestCtrlSignal().getDigSignal(), getTestCtrlSignal().getRasTest(),rasTestSTCode,null);
					 
					resultEnable = stubCTS.getStubCTS().diseableEnableActuatorSensor(testAuxCtrlSignal).getRasTestDisableEnable();
					setTestCtrlSignal(new TestCtrlSignal(true,getTestCtrlSignal().getDigSignal(), null, getTestCtrlSignal().getRasTest(), 
							typeAction,typeAction, resultEnable,rasTestSTCode,getTestCtrlSignal().getListRASTestSTCodeContingency()));
					//if(getTestCtrlSignal().getRasTestDisableEnable() != null)
					//	setTestCtrlSignal(new TestCtrlSignal(true,getTestCtrlSignal().getDigSignal(), null, getTestCtrlSignal().getRasTest(), typeAction, resultEnable));
					activateIcons(typeAction, getTestCtrlSignal().getRasTest().getIdRASTest());
				} catch (RemoteException | NotBoundException | InterruptedException e) {
					// TODO Auto-generated catch block
					//System.out.println(e.toString());
					activateIcons(typeAction, getTestCtrlSignal().getRasTest().getIdRASTest());
					String message = e.getMessage();
					if(message.contains("RemoteException occurred in server thread; nested exception is:") || 
							message.contains("java.rmi.RemoteException:") ||message.contains("java.rmi.ServerException:") ||
							message.contains("java.rmi.Exception:") || message.contains("String index out of range: 99")){
						
						message = message.replaceAll("RemoteException occurred in server thread; nested exception is:", "");
						message = message.replaceAll("java.rmi.RemoteException:", "");
						message = message.replaceAll("java.rmi.ServerException:", "");
						message = message.replaceAll("java.lang.Exception:", "");
						
						String[] values = message.split("\n");
						message = "";
						for(String val : values)
							if(val.length()>5)
								message += val+"\n";
						if(message.contains("String index out of range: 99"))
							message = "CTSImpl err: TIMEOUT";
					}
					System.out.println(message);
					JOptionPane.showMessageDialog(frmST_RAS, message);
					displayTables(TypeActions.EnableComponent.toString());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println(e.toString());
					activateIcons(typeAction, getTestCtrlSignal().getRasTest().getIdRASTest());
					String message = e.getMessage();
					if(message.contains("RemoteException occurred in server thread; nested exception is:") || 
							message.contains("java.rmi.RemoteException:") ||message.contains("java.rmi.ServerException:") ||
							message.contains("java.rmi.Exception:")){
						
						message = message.replaceAll("RemoteException occurred in server thread; nested exception is:", "");
						message = message.replaceAll("java.rmi.RemoteException:", "");
						message = message.replaceAll("java.rmi.ServerException:", "");
						message = message.replaceAll("java.lang.Exception:", "");
						
						String[] values = message.split("\n");
						message = "";
						for(String val : values)
							if(val.length()>5)
								message += val+"\n";
					}
					if(message.contains("String index out of range: 99"))
						message = "CTSImpl err: TIMEOUT";
					System.out.println(message);
					JOptionPane.showMessageDialog(frmST_RAS, message);
					displayTables(TypeActions.EnableComponent.toString());
				}
				if(resultEnable != null){
					flagDisableEnableComponent = resultEnable.getResultDisableEnableComponent();
					stopThread();
					idLink = 0;
					if(flagDisableEnableComponent){
						JOptionPane.showMessageDialog(frmST_RAS, "Actuator & Sensors have been "+DisableEnableComponents.ENABLED.toString()
								+" respectively on: \n"+getTestCtrlSignal().getRasTestDisableEnable().getDatetimeActuator().toString()+" - "+
								getTestCtrlSignal().getRasTestDisableEnable().getDatetimeSensor().toString());
						btnDisableActuatorSensor.setEnabled(false);
						btnDisableActuatorSensor.setVisible(false);
						btnSendMessage.setVisible(false);
						btnSendMessage.setEnabled(false);
						btnCreateTestCtrlSignal.setVisible(false);
						btnCreateTestCtrlSignal.setEnabled(false);
						btnEnableActuatorSensor.setEnabled(false);
						btnEnableActuatorSensor.setVisible(false);
						setParameters();
						//***** UNCOMMENT
						DefaultTableModel model = new DefaultTableModel();
						tblListContingencies.setModel(model);
						displayTables(TypeActions.EnableComponent.toString());
					}
					else{
						String message = resultEnable.getDisableEnableActuator().compareTo(true) != 0 ? "Actuator has not been disabled properly" :
							resultEnable.getDisableEnableActuator().compareTo(true) == 0 && resultEnable.getDisableEnableSensor().compareTo(true) != 0 ?
									"Sensor has not been disabled properly and state Actuator has been rollbacked" : 
										"Either Actuator or Sensor has not been disabled properly";
						JOptionPane.showMessageDialog(frmST_RAS, message);
						activateIcons(typeAction, getTestCtrlSignal().getRasTest().getIdRASTest());
						
						displayTables(TypeActions.EnableComponent.toString()+"NULL");
						
						//setParameters();
						cmbRASScheme.setEnabled(false);
						cmbTypeControlSignal.setEnabled(false);
						//cmb
						//setImgs();
					}
				}
				else{
					stopThread();
					JOptionPane.showMessageDialog(frmST_RAS, "An error has occurred when an attempt to disable SENSOR and ACTUATOR was performed");
				}
					
				
			}
		});
		btnEnableActuatorSensor.setEnabled(false);
		btnEnableActuatorSensor.setVisible(false);
		btnEnableActuatorSensor.setBounds(889, 172, 159, 32);
		frmST_RAS.getContentPane().add(btnEnableActuatorSensor);
		
		JLabel lblSTCode = new JLabel("Self-Testing Code");
		lblSTCode.setBounds(204, 132, 174, 15);
		frmST_RAS.getContentPane().add(lblSTCode);
		
		cmbSTCode = new JComboBox<ST_STCode>();
		cmbSTCode.setEnabled(false);
		cmbSTCode.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent event) {
				if(event.getStateChange() == ItemEvent.SELECTED){
					stCode = cmbSTCode.getItemAt(cmbSTCode.getSelectedIndex());
					int id = cmbSTCode.getSelectedIndex();
					if(id>0){
						if(stCode != null){
							rasTestSTCode = new ST_RASTestSTCode(componentEES,null,stCode,TypeActions.CreateMessage.ordinal()+1);
							btnCreateTestCtrlSignal.setEnabled(true);
					        btnCreateTestCtrlSignal.setVisible(true);
						}
					}	
				}
			}
		});
		cmbSTCode.setRenderer(new DefaultListCellRenderer(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				if(value instanceof ST_STCode){
					ST_STCode stCode = (ST_STCode) value;
					setText(stCode.getCodeName());
				}
				return this;
			}
		});
		cmbSTCode.setBounds(198, 161, 174, 24);
		frmST_RAS.getContentPane().add(cmbSTCode);
		
		JLabel lblComponentTest = new JLabel("Component Test");
		lblComponentTest.setBounds(12, 132, 174, 15);
		frmST_RAS.getContentPane().add(lblComponentTest);
		
		cmbComponentTest = new JComboBox<ST_componentsEES>();
		cmbComponentTest.addItemListener(new ItemListener() {
			@SuppressWarnings("unchecked")
			public void itemStateChanged(ItemEvent event) {
				if(event.getStateChange() == ItemEvent.SELECTED){
					componentEES = cmbComponentTest.getItemAt(cmbComponentTest.getSelectedIndex());
					int id = cmbComponentTest.getSelectedIndex();
					if(id>0){
						if(componentEES != null){
							cmbSTCode.removeAllItems();
							try {
								listSTCodes = (List<ST_STCode>)(Object)stubDBRAS.getStubDBRAS().selectGenericQuery("from sra_STCode", null);
							} catch (Exception e) {
								// TODO: handle exception
							}
							cmbSTCode.addItem(new ST_STCode());
							for(ST_STCode stCode : listSTCodes)
								cmbSTCode.addItem(stCode);
							cmbSTCode.setEnabled(true);
						}
					}
				}
			}
		});
		cmbComponentTest.setRenderer(new DefaultListCellRenderer(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				if(value instanceof ST_componentsEES){
					ST_componentsEES component = (ST_componentsEES) value;
					setText(component.getShortNameComponent());
				}
				return this;
			}
		});
		cmbComponentTest.setEnabled(false);
		cmbComponentTest.setBounds(12, 161, 174, 24);
		frmST_RAS.getContentPane().add(cmbComponentTest);
		
		JTabbedPane tpaResults = new JTabbedPane(JTabbedPane.TOP);
		tpaResults.setBounds(416, 8, 750, 152);
		frmST_RAS.getContentPane().add(tpaResults);
		
		JPanel pnlListContingencies = new JPanel();
		tpaResults.addTab("List Contingencies", null, pnlListContingencies, null);
		pnlListContingencies.setLayout(null);
		
		tblListContingencies = new JTable();
		tblListContingencies.setAutoscrolls(false);
		tblListContingencies.setRowHeight(20);
		tblListContingencies.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tblListContingencies.setPreferredScrollableViewportSize(new Dimension(600, 200));
		//tblContingencies.setPreferredSize(new Dimension(500, 80));
		//pnlContingencies.add(table);
		
		spaListContingencies = new JScrollPane(tblListContingencies);
		spaListContingencies.setBounds(0, 0, 745, 110);
		spaListContingencies.setAutoscrolls(true);
		spaListContingencies.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		//spaContingencies.setPreferredSize(new Dimension(725, 95));
		pnlListContingencies.add(spaListContingencies);
		
		JPanel pnlRASTest = new JPanel();
		tpaResults.addTab("RAS Test", null, pnlRASTest, null);
		pnlRASTest.setLayout(null);
		
		tblRASTest = new JTable();
		tblRASTest.setAutoscrolls(false);
		tblRASTest.setRowHeight(20);
		tblRASTest.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tblRASTest.setPreferredScrollableViewportSize(new Dimension(600, 200));
		
		spaRASTest = new JScrollPane(tblRASTest);
		spaRASTest.setBounds(0, 0, 745, 110);
		spaRASTest.setAutoscrolls(true);
		spaRASTest.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		pnlRASTest.add(spaRASTest);
		
		JPanel pnlStatusComponents = new JPanel();
		tpaResults.addTab("Status Components", null, pnlStatusComponents, null);
		pnlStatusComponents.setLayout(null);
		
		tblStatusComponents = new JTable();
		tblStatusComponents.setAutoscrolls(false);
		tblStatusComponents.setRowHeight(20);
		tblStatusComponents.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tblStatusComponents.setPreferredScrollableViewportSize(new Dimension(600, 200));
		
		spaStatusComponents = new JScrollPane(tblStatusComponents);
		spaStatusComponents.setBounds(0, 0, 745, 110);
		spaStatusComponents.setAutoscrolls(true);
		spaStatusComponents.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		pnlStatusComponents.add(spaStatusComponents);
		
		JPanel pnlLinks = new JPanel();
		tpaResults.addTab("Links", null, pnlLinks, null);
		pnlLinks.setLayout(null);
		
		tblLinks = new JTable();
		tblLinks.setAutoscrolls(false);
		tblLinks.setRowHeight(20);
		tblLinks.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tblLinks.setPreferredScrollableViewportSize(new Dimension(600, 200));
		
		spaLinks = new JScrollPane(tblLinks);
		spaLinks.setBounds(0, 0, 745, 110);
		spaLinks.setAutoscrolls(true);
		spaLinks.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		pnlLinks.add(spaLinks);
		
		JPanel pnlContingencies = new JPanel();
		tpaResults.addTab("Contingencies", null, pnlContingencies, null);
		pnlContingencies.setLayout(null);
		
		tblContingencies = new JTable();
		tblContingencies.setAutoscrolls(false);
		tblContingencies.setRowHeight(20);
		tblContingencies.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tblContingencies.setPreferredScrollableViewportSize(new Dimension(600, 200));
		
		spaContingencies = new JScrollPane(tblContingencies);
		spaContingencies.setBounds(0, 0, 745, 110);
		spaContingencies.setAutoscrolls(true);
		spaContingencies.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		pnlContingencies.add(spaContingencies);
		
		JPanel pnlRemedialActions = new JPanel();
		tpaResults.addTab("Remedial Actions", null, pnlRemedialActions, null);
		pnlRemedialActions.setLayout(null);
		
		tblRemedialActions = new JTable();
		tblRemedialActions.setAutoscrolls(false);
		tblRemedialActions.setRowHeight(20);
		tblRemedialActions.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tblRemedialActions.setPreferredScrollableViewportSize(new Dimension(600, 200));
		
		spaRemedialActions = new JScrollPane(tblRemedialActions);
		spaRemedialActions.setBounds(0, 0, 745, 110);
		spaRemedialActions.setAutoscrolls(true);
		spaRemedialActions.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		pnlRemedialActions.add(spaRemedialActions);
		
		JPanel pnlTripCommand = new JPanel();
		tpaResults.addTab("Trip Command", null, pnlTripCommand, null);
		pnlTripCommand.setLayout(null);
		
		tblTripCommand = new JTable();
		tblTripCommand.setAutoscrolls(false);
		tblTripCommand.setRowHeight(20);
		tblTripCommand.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tblTripCommand.setPreferredScrollableViewportSize(new Dimension(600, 200));
		
		spaTripCommand = new JScrollPane(tblTripCommand);
		spaTripCommand.setBounds(0, 0, 745, 110);
		spaTripCommand.setAutoscrolls(true);
		spaTripCommand.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		pnlTripCommand.add(spaTripCommand);
		
		JPanel pnlSTCode = new JPanel();
		tpaResults.addTab("ST Code", null, pnlSTCode, null);
		pnlSTCode.setLayout(null);
		
		tblSTCode = new JTable();
		tblSTCode.setAutoscrolls(false);
		tblSTCode.setRowHeight(20);
		tblSTCode.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tblSTCode.setPreferredScrollableViewportSize(new Dimension(600, 200));
		
		spaSTCode = new JScrollPane(tblSTCode);
		spaSTCode.setBounds(0, 0, 745, 110);
		spaSTCode.setAutoscrolls(true);
		spaSTCode.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		pnlSTCode.add(spaSTCode);
		
		
		JPanel pnlSTCodeContingency = new JPanel();
		tpaResults.addTab("ST Code Contingency", null, pnlSTCodeContingency, null);
		pnlSTCodeContingency.setLayout(null);
		
		tblSTCodeContingency = new JTable();
		tblSTCodeContingency.setAutoscrolls(false);
		tblSTCodeContingency.setRowHeight(20);
		tblSTCodeContingency.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tblSTCodeContingency.setPreferredScrollableViewportSize(new Dimension(600, 200));
		
		spaSTCodeContingency = new JScrollPane(tblSTCodeContingency);
		spaSTCodeContingency.setBounds(0, 0, 745, 110);
		spaSTCodeContingency.setAutoscrolls(true);
		spaSTCodeContingency.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		pnlSTCodeContingency.add(spaSTCodeContingency);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Boolean flagMenu) {
		frmST_RAS = new JFrame();
		try {
			WSULogo = new File(valPath+"WSULogo.png");
			frmST_RAS.setIconImage(ImageIO.read(WSULogo));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(frmST_RAS, e1.getMessage());
		}
		frmST_RAS.setTitle("ERKIOS E2E FIELD-BASED RAS TESTING");
		frmST_RAS.getContentPane().setEnabled(false);
		
		final JLabel lblRASClass = new JLabel("RAS Classification");
		lblRASClass.setBounds(12, 12, 159, 15);

		/*CONTROL DECORATION*/
		
		cmbRASScheme = new JComboBox<ST_rasSchemes>();
		cmbRASScheme.setBounds(198, 34, 174, 23);
		cmbRASScheme.addItemListener(new ItemListener() {
			@SuppressWarnings("unchecked")
			public void itemStateChanged(ItemEvent event) {
				ST_parametersRAS rasParameters = null;
				ST_tripCommand tripCommand = null;
				
				
				List<ST_contingencies> listContingencies = null;
				if(event.getStateChange() == ItemEvent.SELECTED){
					int id = cmbRASScheme.getSelectedIndex();
					if(id>0){
						//idRAS = cmbRASScheme.getItemAt(id).getIdRAS();
						setRASScheme(cmbRASScheme.getItemAt(id));
						try {
							rasParameters = (ST_parametersRAS)(Object)stubDBRAS.getStubDBRAS().selectGenericQuery("from sra_parametersRAS where pra_idRAS = :id", getRASScheme().getIdRAS()).get(0);
							tripCommand = (ST_tripCommand)(Object)stubDBRAS.getStubDBRAS().selectGenericQuery("from sra_tripCommand where csi_idRAS = :id", getRASScheme().getIdRAS()).get(0);
							listContingencies = (List<ST_contingencies>)(Object)stubDBRAS.getStubDBRAS().selectGenericQuery("from sra_contingencies where con_idRAS = :id", getRASScheme().getIdRAS());
							
							cmbTypeControlSignal.removeAllItems();
							cmbTypeControlSignal.addItem(new ST_typeSignal());
							listTypeSignal = (List<ST_typeSignal>)(Object)stubDBRAS.getStubDBRAS().selectGenericQuery("from sra_typeSignal where tsi_nameSignal != :id order by tsi_nameSignal desc", "Analog Signal");
							for(ST_typeSignal typeSignal:listTypeSignal)
								cmbTypeControlSignal.addItem(typeSignal);
						
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						//JOptionPane.showMessageDialog(frmST_RAS, String.valueOf(listRASParameters.get(0).getIdParameter()));
						//setParameters();
						
						if(rasParameters != null){
							txtCurrent.setEnabled(rasParameters.isParamCurrent());
							txtCurrent.setText(String.valueOf(tripCommand.getCurrent()));
							txtFrequency.setEnabled(rasParameters.isParamFrequency());
							txtFrequency.setText(String.valueOf(tripCommand.getFrequency()));
							txtPhaseVoltage.setEnabled(rasParameters.isParamPhaseVoltage());
							txtPhaseVoltage.setText(String.valueOf(tripCommand.getPhaseVoltage()));
							txtPower.setEnabled(rasParameters.isParamPower());
							txtPower.setText(String.valueOf(tripCommand.getPower()));
							txtReactivePower.setEnabled(rasParameters.isParamReactivePower());
							txtReactivePower.setText(String.valueOf(tripCommand.getReactivePower()));
							txtVoltage.setEnabled(rasParameters.isParamVoltage());
							txtVoltage.setText(String.valueOf(tripCommand.getVoltage()));
							cmbTypeControlSignal.setEnabled(true);
							
							if(rasParameters.isparamContingencies()){
							
								contingTblModel = new ContingenciesTableModel(listContingencies);
								//lblRASClass.setText(String.valueOf(contingTblModel.getRowCount()));
								/*tblContingenciesOld.setModel(contingTblModel);
								
								tblContingenciesOld.getColumnModel().getColumn(0).setResizable(true);
								tblContingenciesOld.getColumnModel().getColumn(0).setPreferredWidth(25);
								tblContingenciesOld.getColumnModel().getColumn(1).setPreferredWidth(425);
								tblContingenciesOld.getTableHeader().setBackground(Color.WHITE);
								tblContingenciesOld.getTableHeader().setForeground(Color.BLUE);
					            Font Tablefont = new Font("Book Details", Font.BOLD, 12);
					            tblContingenciesOld.getTableHeader().setFont(Tablefont);
								tblContingenciesOld.setPreferredScrollableViewportSize(new Dimension(450, 400));
								tblContingenciesOld.setFillsViewportHeight(true);
								tblContingenciesOld.setVisible(true);*/
								//scrollPane  = new JScrollPane(tblContingencies);
								 
						        //Add the scroll pane to this panel.
								//tblContingencies = new JTable(contingTblModel);
								/*scrollPane.setViewportView(tblContingenciesOld);
								scrollPane.setVisible(true);
						        frmST_RAS.getContentPane().add(tblContingenciesOld);
						        frmST_RAS.getContentPane().add(scrollPane);
						        */
								tblListContingencies.setModel(contingTblModel);
								//tblContingencies.getColumnModel().getColumn(0).setResizable(true);
								tblListContingencies.getColumnModel().getColumn(0).setPreferredWidth(25);
								//tblContingencies.getColumnModel().getColumn(1).setResizable(true);
								tblListContingencies.getColumnModel().getColumn(1).setPreferredWidth(425);

								spaListContingencies.setViewportView(tblListContingencies);
							}
						}
						
					}
				}
			}
		});
		cmbRASScheme.setRenderer(new DefaultListCellRenderer(){
			 /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
	            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
	                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	                if(value instanceof ST_rasSchemes){
	                	ST_rasSchemes rasSchemes = (ST_rasSchemes) value;
	                    setText(rasSchemes.getNameRAS());
	                }
	                return this;
	            }
		}); 
		cmbRASScheme.setEnabled(false);
		cmbRASScheme.setFont(new Font("Dialog", Font.BOLD, 12));
		
		
		cmbRASClass = new JComboBox<ST_classRAS>();
		cmbRASClass.setBounds(12, 34, 174, 23);
		cmbRASClass.addItemListener(new ItemListener() {
			@SuppressWarnings("unchecked")
			public void itemStateChanged(ItemEvent event) {
				if(event.getStateChange() == ItemEvent.SELECTED){
					setParameters();
					int id = cmbRASClass.getSelectedIndex();
					cmbRASScheme.removeAllItems();
					setImgs();
					
					if(id>0){
						id = cmbRASClass.getItemAt(id).getIdClassification();
						setClassRAS(cmbRASClass.getItemAt(id));
						cmbRASScheme.addItem(new ST_rasSchemes());
						//cmbTypeControlSignal.addItem(new ST_typeSignal());
						try {
							listRASSchemes = (List<ST_rasSchemes>)(Object)stubDBRAS.getStubDBRAS().selectGenericQuery("from sra_RASSchemes where ras_idClassification = :id", id);
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						cmbRASScheme.setEnabled(true);
						for(ST_rasSchemes rasScheme:listRASSchemes)
							cmbRASScheme.addItem(rasScheme);
					}
				}
			}
		});
		cmbRASClass.setRenderer(new DefaultListCellRenderer(){
			 /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
	            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
	                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	                if(value instanceof ST_classRAS){
	                	ST_classRAS classRAS = (ST_classRAS) value;
	                    setText(classRAS.getNameClass());
	                }
	                return this;
	            }
		}); 
		cmbRASClass.setEnabled(false);
		cmbRASClass.setFont(new Font("Dialog", Font.BOLD, 12));
		
		JLabel lblRASScheme = new JLabel("Type RAS Scheme");
		lblRASScheme.setBounds(204, 12, 124, 15);
		
		JLayeredPane lyaParameters = new JLayeredPane();
		lyaParameters.setName("");
		lyaParameters.setBounds(12, 197, 312, 192);
		lyaParameters.setToolTipText("");
		lyaParameters.setEnabled(false);
		
		btnDisableActuatorSensor = new JButton("<html><center>Disable Actuator<br /> & Sensor</center></html>");
		btnDisableActuatorSensor.setVisible(false);
		btnDisableActuatorSensor.setEnabled(false);
		btnDisableActuatorSensor.setBounds(572, 172, 152, 32);
		btnDisableActuatorSensor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ST_RASTestDisableEnableComponent resultDisable = null;
				TestCtrlSignal testAuxCtrlSignal = null;
				try {
					setImgs();
					stubCTS = new CTSClient();
					typeAction = TypeActions.DisableComponent.toString();
					//runThread();
					//btnDisableActuatorSensor.setText("Working ...");
					//btnDisableActuatorSensor.setEnabled(false);
					
					rasTestSTCode = new ST_RASTestSTCode(rasTestSTCode, TypeActions.DisableComponent.ordinal()+1);
					
					testAuxCtrlSignal = new TestCtrlSignal(false, null, digSignal, getTestCtrlSignal().getRasTest(),rasTestSTCode,getTestCtrlSignal().getListRASTestSTCodeContingency());
					testAuxCtrlSignal = stubCTS.getStubCTS().diseableEnableActuatorSensor(testAuxCtrlSignal);
					//testAuxCtrlSignal = new TestCtrlSignal(resultDisable.getResultDisableEnableComponent(),getTestCtrlSignal().getDigSignal(),null, getTestCtrlSignal().getRasTest(), typeAction, typeAction, resultDisable);
					setTestCtrlSignal(testAuxCtrlSignal);
					
					resultDisable = getTestCtrlSignal().getRasTestDisableEnable();
					activateIcons(typeAction, getTestCtrlSignal().getRasTest().getIdRASTest());
				} catch (RemoteException | NotBoundException e) {
					// TODO Auto-generated catch block
					//System.out.println(e.toString());
					activateIcons(typeAction, getTestCtrlSignal().getRasTest().getIdRASTest());
					String message = e.getMessage();
					if(message.contains("RemoteException occurred in server thread; nested exception is:") || 
							message.contains("java.rmi.RemoteException:") ||message.contains("java.rmi.ServerException:") ||
							message.contains("java.rmi.Exception:") || message.contains("String index out of range: 99")){
						
						message = message.replaceAll("RemoteException occurred in server thread; nested exception is:", "");
						message = message.replaceAll("java.rmi.RemoteException:", "");
						message = message.replaceAll("java.rmi.ServerException:", "");
						message = message.replaceAll("java.lang.Exception:", "");
						
						String[] values = message.split("\n");
						message = "";
						for(String val : values)
							if(val.length()>5)
								message += val+"\n";
						
						if(message.contains("String index out of range: 99"))
							message = "CTSImpl err: TIMEOUT";
					}
					JOptionPane.showMessageDialog(frmST_RAS, message);
					displayTables(TypeActions.DisableComponent.toString());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//System.out.println(e.toString());
					activateIcons(typeAction, getTestCtrlSignal().getRasTest().getIdRASTest());
					String message = e.getMessage();
					if(message.contains("RemoteException occurred in server thread; nested exception is:") || 
							message.contains("java.rmi.RemoteException:") ||message.contains("java.rmi.ServerException:") ||
							message.contains("java.rmi.Exception:") || message.contains("String index out of range: 99")){
						
						message = message.replaceAll("RemoteException occurred in server thread; nested exception is:", "");
						message = message.replaceAll("java.rmi.RemoteException:", "");
						message = message.replaceAll("java.rmi.ServerException:", "");
						message = message.replaceAll("java.lang.Exception:", "");
						
						String[] values = message.split("\n");
						message = "";
						for(String val : values)
							if(val.length()>5)
								message += val+"\n";
						if(message.contains("String index out of range: 99"))
							message = "CTSImpl err: TIMEOUT";
					}
					JOptionPane.showMessageDialog(frmST_RAS, message);
					displayTables(TypeActions.DisableComponent.toString());
				}
				if(resultDisable != null){
					flagDisableEnableComponent = resultDisable.getResultDisableEnableComponent();
					stopThread();
					idLink = 0;
					if(flagDisableEnableComponent){
						JOptionPane.showMessageDialog(frmST_RAS, "Actuator & Sensors have been "+DisableEnableComponents.DISABLED.toString()
								+" on "+resultDisable.getDatetimeActuator().toString());
						btnDisableActuatorSensor.setEnabled(false);
						btnSendMessage.setVisible(true);
						btnSendMessage.setEnabled(true);
						btnEnableActuatorSensor.setVisible(true);
						btnEnableActuatorSensor.setEnabled(true);
						
						displayTables(TypeActions.DisableComponent.toString());
					}
					else{
						String message = resultDisable.getDisableEnableActuator().compareTo(false) != 0 ? "Actuator has not been disabled properly" :
							resultDisable.getDisableEnableActuator().compareTo(false) == 0 && resultDisable.getDisableEnableSensor().compareTo(false) != 0 ?
									"Sensor has not been disabled properly and state Actuator has been rollbacked" : 
										"Either Actuator or Sensor has not been disabled properly";
						JOptionPane.showMessageDialog(frmST_RAS, message);
						setParameters();
						cmbRASScheme.setEnabled(false);
						cmbTypeControlSignal.setEnabled(false);
						activateIcons(typeAction, getTestCtrlSignal().getRasTest().getIdRASTest());
						
						displayTables(TypeActions.DisableComponent.toString()+"NULL");
						//setImgs();
					}
				}
				else{
					stopThread();
					JOptionPane.showMessageDialog(frmST_RAS, "An error has occurred when an attempt to disable SENSOR and ACTUATOR was performed");
				}
					
				
			}
		});
		
		btnCreateTestCtrlSignal = new JButton("<html><center>Create Test<br />Control Signal</center></html>");
		btnCreateTestCtrlSignal.setVisible(false);
		btnCreateTestCtrlSignal.setFont(new Font("Dialog", Font.BOLD, 12));
		btnCreateTestCtrlSignal.setBounds(426, 172, 134, 32);
		btnCreateTestCtrlSignal.setEnabled(false);
		btnCreateTestCtrlSignal.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent arg0) {
				List<ST_remedialActions> remedialActions = new ArrayList<ST_remedialActions>();
				List<ST_contingencies> listContingenciesChecked = new ArrayList<ST_contingencies>();
				ST_RASTest rasTest = null;
				ST_tripCommand tripCommandMsg = null;
				//DigitalSignal digSignal = null;
				//TestCtrlSignal testAuxCtrlSignal = null;
				setImgs();
				if(contingTblModel != null && contingTblModel.getRowCount() > 0){
					for(ST_contingencies contingency: contingTblModel.getContingencies())
						if(contingency.getCheckContingency())
							listContingenciesChecked.add(contingency);
					
					if(!listContingenciesChecked.isEmpty()){
						try {
							remedialActions = (List<ST_remedialActions>)(Object)stubDBRAS.getStubDBRAS().selectGenericQuery("from sra_remedialActions where rac_idRAS = :id", getRASScheme().getIdRAS());
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							System.out.println(ras.interfaces.Utilities.separator3+"CreateTestCtrlSignal err:"+e.getMessage());
							JOptionPane.showMessageDialog(frmST_RAS, "Remedial Actions unavailable"+e.getMessage());
						}
						
						tripCommandMsg = new ST_tripCommand(Float.parseFloat(txtCurrent.getText()), 
								Float.parseFloat(txtFrequency.getText()), rasScheme, Float.parseFloat(txtPhaseVoltage.getText()),
								Float.parseFloat(txtPower.getText()), Float.parseFloat(txtReactivePower.getText()), Float.parseFloat(txtVoltage.getText()));
						digSignal = new DigitalSignal(getClassRAS(),listContingenciesChecked,tripCommandMsg, getRASScheme(),remedialActions);
						
						if(typeSignal.getNameSignal().replaceAll("\\s+","").equals(TypeSignals.TestControlSignal.toString())){
							TestCtrlSignal testAuxCtrlSignal = new TestCtrlSignal(null,null,digSignal,rasTestSTCode);
							setTestCtrlSignal(testAuxCtrlSignal);
							try {
								stubCTS = new CTSClient();
								testAuxCtrlSignal = stubCTS.getStubCTS().createMessage(getTestCtrlSignal());
								rasTest = testAuxCtrlSignal.getRasTest();
							} catch (RemoteException | NotBoundException | NullPointerException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								JOptionPane.showMessageDialog(frmST_RAS, e.getMessage());
								setParameters();
								displayTables(TypeActions.CreateMessage.toString());
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								JOptionPane.showMessageDialog(frmST_RAS, e.getMessage());
								displayTables(TypeActions.CreateMessage.toString());
							}
							
							if(rasTest.getIdRASTest() > 0){
								JOptionPane.showMessageDialog(frmST_RAS, "Test control signal has been created successfully with Id: "+rasTest.getIdRASTest());
								btnCreateTestCtrlSignal.setEnabled(false);
								btnDisableActuatorSensor.setVisible(true);
								btnDisableActuatorSensor.setEnabled(true);
								rasTestSTCode = testAuxCtrlSignal.getRasTestSTCode(); //new ST_RASTestSTCode(rasTestSTCode.getIdComponent(), rasTest, rasTestSTCode.getIdSTCode(),TypeActions.CreateMessage.ordinal()+1);
								
								setTestCtrlSignal(new TestCtrlSignal(null, null, digSignal, rasTest,rasTestSTCode,testAuxCtrlSignal.getListRASTestSTCodeContingency()));
								
								displayTables(TypeActions.CreateMessage.toString());
							} else
								JOptionPane.showMessageDialog(frmST_RAS, "Test control signal was not been created successfully");
						}
					} else
						JOptionPane.showMessageDialog(frmST_RAS, "Must check at most one Contingency for RAS "+rasScheme.getNameRAS());		
					//testCtrl.setDigSignal();
				}
				
			}
		});
		
		JLabel lblPower = new JLabel("Power:");
		lblPower.setBounds(12, 12, 50, 15);
		lyaParameters.add(lblPower);
		
		txtPower = new JTextField();
		
		txtPower.setEnabled(false);
		txtPower.setBounds(134, 12, 114, 19);
		lyaParameters.add(txtPower);
		txtPower.setColumns(10);
		
		JLabel lblCurrent = new JLabel("Current:");
		lblCurrent.setBounds(12, 41, 59, 15);
		lyaParameters.add(lblCurrent);
		
		txtCurrent = new JTextField();
		txtCurrent.setEnabled(false);
		txtCurrent.setColumns(10);
		txtCurrent.setBounds(134, 41, 114, 19);
		lyaParameters.add(txtCurrent);
		
		JLabel lblVoltage = new JLabel("Voltage:");
		lblVoltage.setBounds(12, 70, 60, 15);
		lyaParameters.add(lblVoltage);
		
		txtVoltage = new JTextField();
		txtVoltage.setEnabled(false);
		txtVoltage.setColumns(10);
		txtVoltage.setBounds(134, 70, 114, 19);
		lyaParameters.add(txtVoltage);
		
		JLabel lblFrequency = new JLabel("Frequency:");
		lblFrequency.setBounds(12, 99, 79, 15);
		lyaParameters.add(lblFrequency);
		
		txtFrequency = new JTextField();
		txtFrequency.setEnabled(false);
		txtFrequency.setColumns(10);
		txtFrequency.setBounds(134, 99, 114, 19);
		lyaParameters.add(txtFrequency);
		
		JLabel lblReactivePower = new JLabel("Reactive Power:");
		lblReactivePower.setBounds(12, 128, 114, 15);
		lyaParameters.add(lblReactivePower);
		
		txtReactivePower = new JTextField();
		txtReactivePower.setEnabled(false);
		txtReactivePower.setColumns(10);
		txtReactivePower.setBounds(134, 128, 114, 19);
		lyaParameters.add(txtReactivePower);
		
		JLabel lblPhaseVoltage = new JLabel("Phase Voltage:");
		lblPhaseVoltage.setBounds(12, 157, 108, 15);
		lyaParameters.add(lblPhaseVoltage);
		
		txtPhaseVoltage = new JTextField();
		txtPhaseVoltage.setEnabled(false);
		txtPhaseVoltage.setColumns(10);
		txtPhaseVoltage.setBounds(134, 157, 114, 19);
		lyaParameters.add(txtPhaseVoltage);
		frmST_RAS.getContentPane().setLayout(null);
		
		lblStatus22 = new JLabel("");
		lblStatus22.setBounds(622, 442, 28, 25);
		frmST_RAS.getContentPane().add(lblStatus22);
		frmST_RAS.getContentPane().add(lblRASClass);
		frmST_RAS.getContentPane().add(lblRASScheme);
		frmST_RAS.getContentPane().add(cmbRASScheme);
		frmST_RAS.getContentPane().add(cmbRASClass);
		frmST_RAS.getContentPane().add(lyaParameters);
		
		JLabel lblSymbolPower = new JLabel("MW");
		lblSymbolPower.setBounds(254, 12, 44, 15);
		lyaParameters.add(lblSymbolPower);
		
		JLabel lblSymbolCurrent = new JLabel("A");
		lblSymbolCurrent.setBounds(254, 41, 44, 15);
		lyaParameters.add(lblSymbolCurrent);
		
		JLabel lblSymbolVoltage = new JLabel("V");
		lblSymbolVoltage.setBounds(254, 70, 44, 15);
		lyaParameters.add(lblSymbolVoltage);
		
		JLabel lblSymbolFrequency = new JLabel("Hz");
		lblSymbolFrequency.setBounds(254, 99, 44, 15);
		lyaParameters.add(lblSymbolFrequency);
		
		JLabel lblSymbolReactivePower = new JLabel("MVAR");
		lblSymbolReactivePower.setBounds(254, 128, 44, 15);
		lyaParameters.add(lblSymbolReactivePower);
		
		JLabel lblSymbolPhaseVoltage = new JLabel("φ");
		lblSymbolPhaseVoltage.setBounds(254, 157, 44, 15);
		lyaParameters.add(lblSymbolPhaseVoltage);
		frmST_RAS.getContentPane().add(btnCreateTestCtrlSignal);
		frmST_RAS.getContentPane().add(btnDisableActuatorSensor);
		
		JLabel lblTypeControlSignal = new JLabel("Type of Control Sygnal");
		lblTypeControlSignal.setBounds(12, 69, 174, 15);
		frmST_RAS.getContentPane().add(lblTypeControlSignal);
		
		cmbTypeControlSignal = new JComboBox<ST_typeSignal>();
		cmbTypeControlSignal.addItemListener(new ItemListener() {
			@SuppressWarnings("unchecked")
			public void itemStateChanged(ItemEvent event) {
				if(event.getStateChange() == ItemEvent.SELECTED){
					int indexTypeSignal = cmbTypeControlSignal.getSelectedIndex();
					typeSignal = cmbTypeControlSignal.getItemAt(indexTypeSignal);
					//JOptionPane.showConfirmDialog(frmST_RAS, typeSignal.getNameSignal());
					if(typeSignal != null && typeSignal.getNameSignal().equals("Test Control Signal")){
						cmbComponentTest.removeAllItems();
						try {
							listComponentsEES = (List<ST_componentsEES>)(Object)stubDBRAS.getStubDBRAS().selectGenericQuery(
									"from sra_componentsEES where cee_shortNameComponent = 'OpWAN' or "
									+ "cee_shortNameComponent = 'Sensor' or cee_shortNameComponent = 'Actuator' or "
									+ "cee_shortNameComponent = 'SwitchSensor1' or cee_shortNameComponent = 'SwitchSensor2' or "
									+ "cee_shortNameComponent = 'All' or cee_shortNameComponent = 'SwitchActuator' or "
									+ "cee_shortNameComponent = 'RAS' or cee_shortNameComponent = 'None' "
									+ "order by cee_idComponent desc", null);
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						cmbComponentTest.addItem(new ST_componentsEES());
						for(ST_componentsEES componentsEES : listComponentsEES)
							cmbComponentTest.addItem(componentsEES);
						cmbComponentTest.setEnabled(true);
					}
				}
			}
		});
		cmbTypeControlSignal.setRenderer(new DefaultListCellRenderer(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				if(value instanceof ST_typeSignal){
					ST_typeSignal typeSignal = (ST_typeSignal) value;
					setText(typeSignal.getNameSignal());
				}
				return this;
			}
		});
		

		cmbTypeControlSignal.setEnabled(false);
		cmbTypeControlSignal.setBounds(12, 96, 174, 24);
		frmST_RAS.getContentPane().add(cmbTypeControlSignal);
		
		btnSendMessage = new JButton("<html><center>Send Test<br />Control Signal</center></html>");
		btnSendMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//boolean flagMessage = false;
				TestCtrlSignal testSignal = null;
				try {
					setImgs();
					stubCTS = new CTSClient();
					typeAction = TypeActions.SendMessageTest.toString();
					//runThread();
					
					setImgs();
					rasTestSTCode = new ST_RASTestSTCode(getTestCtrlSignal().getRasTestSTCode(), rasTestSTCode.getIdComponent(), 
							rasTestSTCode.getIdRASTest(), rasTestSTCode.getIdSTCode(), TypeActions.SendMessageTest.ordinal()+1);
					
					testSignal = new TestCtrlSignal(getTestCtrlSignal().getDisableEnableComponents(), getTestCtrlSignal().getResultTestSensor(), 
							getTestCtrlSignal().getResultTestActuator(), getTestCtrlSignal().getDigSignal(), 
							getTestCtrlSignal().getAnaSignal(), getTestCtrlSignal().getRasTest(), getTestCtrlSignal().getMessage(),
							getTestCtrlSignal().getTypeAction(), getTestCtrlSignal().getRasTestDisableEnable(), rasTestSTCode,
							getTestCtrlSignal().getListRASTestSTCodeContingency());
					testSignal = stubCTS.getStubCTS().sendMessage(testSignal);
					setTestCtrlSignal(testSignal);
					flagSendMessage = testSignal.getResultTestActuator() && testSignal.getResultTestSensor() ? true : false;
					activateIcons(typeAction, getTestCtrlSignal().getRasTest().getIdRASTest());
					
					if(flagSendMessage){
						JOptionPane.showMessageDialog(frmST_RAS, "Message has been sent to Sensor successfully");
						
						displayTables(TypeActions.SendMessageTest.toString());
					}
					else{
						JOptionPane.showMessageDialog(frmST_RAS, "An error occurred when Test Control Signal was sent to Sensor");
						activateIcons(typeAction, getTestCtrlSignal().getRasTest().getIdRASTest());
						
						displayTables(TypeActions.SendMessageTest.toString()+"NULL");
					}	
				} catch (RemoteException | NotBoundException e) {
					// TODO Auto-generated catch block
					//System.out.println(e.toString());
					activateIcons(typeAction, getTestCtrlSignal().getRasTest().getIdRASTest());
					String message = e.getMessage();
					
					if(e.getMessage().contains("String index out of range: 99")){
						message = "from sra_RASTestLink where rtl_idRASTest = :id and rtl_comment like '%Parameter value%'";
						try {
							ST_RASTestLink link = (ST_RASTestLink)(Object)stubDBRAS.getStubDBRAS().selectGenericQuery(message, getTestCtrlSignal().getRasTest().getIdRASTest()).get(0);
							message = link.getComment();
							message = e.getMessage().split("String index out of range: 99")[0]+message;
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							//e1.printStackTrace();
						}
					}
					
					if(e.getMessage().contains("Wrong Contingency") || e.getMessage().contains("String index out of range: 99")){
						message = "from sra_RASTestLink where rtl_idRASTest = :id and rtl_comment like '%Parameter value%' or rtl_comment like '%Wrong Contingency%'";
						try {
							ST_RASTestLink link = (ST_RASTestLink)(Object)stubDBRAS.getStubDBRAS().selectGenericQuery(message, getTestCtrlSignal().getRasTest().getIdRASTest()).get(0);
							if(link!=null && link.getIdRASTestLink()>0)
								message = e.getMessage().split("Wrong Contingency")[0]+link.getComment().substring(0, 50);
							else
								message = "CTSImpl err: TIMEOUT";
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							//e1.printStackTrace();
						}
					}
					
					if(message.contains("RemoteException occurred in server thread; nested exception is:") || 
							message.contains("java.rmi.RemoteException:") ||message.contains("java.rmi.ServerException:") ||
							message.contains("java.rmi.Exception:")){
						
						message = message.replaceAll("RemoteException occurred in server thread; nested exception is:", "");
						message = message.replaceAll("java.rmi.RemoteException:", "");
						message = message.replaceAll("java.rmi.ServerException:", "");
						message = message.replaceAll("java.lang.Exception:", "");
						
						String[] values = message.split("\n");
						message = "";
						for(String val : values)
							if(val.length()>5)
								message += val+"\n";
					}
					System.out.println(message);
					JOptionPane.showMessageDialog(frmST_RAS, message);
					displayTables(TypeActions.Error.toString());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println(e.toString());
					activateIcons(typeAction, getTestCtrlSignal().getRasTest().getIdRASTest());
					String message = e.getMessage();
					message = message.replaceAll("RemoteException occurred in server thread; nested exception is:\n", "");
					message = message.replaceAll("java.rmi.RemoteException:", "");
					message = message.replaceAll("java.rmi.ServerException:", "");
					//System.out.println(message);
					if(message.contains("String index out of range: 99"))
						message = "CTSImpl err: TIMEOUT";
					JOptionPane.showMessageDialog(frmST_RAS, message);
					displayTables(TypeActions.Error.toString());
				}
				btnSendMessage.setEnabled(false);
				stopThread();
				idLink = 0;
			}
		});
		btnSendMessage.setVisible(false);
		btnSendMessage.setEnabled(false);
		btnSendMessage.setBounds(736, 172, 141, 32);
		frmST_RAS.getContentPane().add(btnSendMessage);
		
		//lblNomenclatureImg = new JLabel("");
		//lblNomenclatureImg.setIcon(new ImageIcon(RASSchemeTest.class.getResource("/ras/img/NomenclatureImg.png")));
		lblNomenclatureImg.setBounds(12, 401, 378, 169);
		frmST_RAS.getContentPane().add(lblNomenclatureImg);
		
		lblStatus2 = new JLabel("");
		lblStatus2.setBounds(952, 511, 28, 25);
		frmST_RAS.getContentPane().add(lblStatus2);
		
		JLabel lblOk = new JLabel("");
		lblOk.setVisible(false);
		//lblOk.setIcon(new ImageIcon(RASSchemeTest.class.getResource("/ras/img/OkImg.png")));
		lblOk.setBounds(285, 241, 39, 37);
		frmST_RAS.getContentPane().add(lblOk);
		
		JLabel lblWrong = new JLabel("");
		lblWrong.setVisible(false);
		//lblWrong.setIcon(new ImageIcon(RASSchemeTest.class.getResource("/ras/img/WrongImg.png")));
		lblWrong.setBounds(285, 331, 39, 37);
		frmST_RAS.getContentPane().add(lblWrong);
		
		lblStatus4 = new JLabel("");
		lblStatus4.setBounds(952, 456, 28, 25);
		frmST_RAS.getContentPane().add(lblStatus4);
		
		lblStatus3 = new JLabel("");
		lblStatus3.setBounds(952, 383, 28, 25);
		frmST_RAS.getContentPane().add(lblStatus3);
		
		lblStatus7 = new JLabel("");
		lblStatus7.setBounds(952, 331, 28, 25);
		frmST_RAS.getContentPane().add(lblStatus7);
		
		lblStatus1 = new JLabel("");
		lblStatus1.setBounds(1021, 331, 28, 25);
		frmST_RAS.getContentPane().add(lblStatus1);
		
		lblStatus8 = new JLabel("");
		lblStatus8.setBounds(830, 425, 28, 25);
		frmST_RAS.getContentPane().add(lblStatus8);
		
		lblStatus10 = new JLabel("");
		lblStatus10.setBounds(782, 319, 28, 25);
		frmST_RAS.getContentPane().add(lblStatus10);
		
		lblStatus5 = new JLabel("");
		lblStatus5.setBounds(803, 456, 28, 25);
		frmST_RAS.getContentPane().add(lblStatus5);
		
		lblStatus9 = new JLabel("");
		lblStatus9.setBounds(720, 413, 28, 25);
		frmST_RAS.getContentPane().add(lblStatus9);
		
		lblStatus19 = new JLabel("");
		lblStatus19.setBounds(763, 490, 28, 25);
		frmST_RAS.getContentPane().add(lblStatus19);
		
		lblStatus6 = new JLabel("");
		lblStatus6.setBounds(763, 545, 28, 25);
		frmST_RAS.getContentPane().add(lblStatus6);
		
		lblDisableEnableActuator = new JLabel("");
		lblDisableEnableActuator.setBounds(495, 442, 28, 25);
		frmST_RAS.getContentPane().add(lblDisableEnableActuator);
		
		lblDisableEnableSensor = new JLabel("");
		lblDisableEnableSensor.setBounds(586, 216, 28, 25);
		frmST_RAS.getContentPane().add(lblDisableEnableSensor);
			
		lblStatus11 = new JLabel("");
		lblStatus11.setBounds(451, 319, 28, 25);
		frmST_RAS.getContentPane().add(lblStatus11);
		
		lblStatus14 = new JLabel("");
		lblStatus14.setBounds(481, 279, 28, 25);
		frmST_RAS.getContentPane().add(lblStatus14);
		
		lblStatus15 = new JLabel("");
		lblStatus15.setBounds(469, 216, 28, 25);
		frmST_RAS.getContentPane().add(lblStatus15);
		
		lblStatus16 = new JLabel("");
		lblStatus16.setBounds(543, 216, 28, 25);
		frmST_RAS.getContentPane().add(lblStatus16);
		
		lblStatus12 = new JLabel("");
		lblStatus12.setBounds(543, 306, 28, 25);
		frmST_RAS.getContentPane().add(lblStatus12);
		
		lblStatus13 = new JLabel("");
		lblStatus13.setBounds(622, 306, 28, 25);
		frmST_RAS.getContentPane().add(lblStatus13);
		
		lblStatus18 = new JLabel("");
		lblStatus18.setBounds(683, 306, 28, 25);
		frmST_RAS.getContentPane().add(lblStatus18);
		
		lblStatus17 = new JLabel("");
		lblStatus17.setBounds(626, 216, 28, 25);
		frmST_RAS.getContentPane().add(lblStatus17);
		
		lblStatus20 = new JLabel("");
		lblStatus20.setBounds(693, 479, 28, 25);
		frmST_RAS.getContentPane().add(lblStatus20);
		
		lblStatus21 = new JLabel("");
		lblStatus21.setBounds(606, 534, 28, 25);
		frmST_RAS.getContentPane().add(lblStatus21);
		
		lblStatus23 = new JLabel("");
		lblStatus23.setBounds(531, 534, 28, 25);
		frmST_RAS.getContentPane().add(lblStatus23);
		
		lblStatus24 = new JLabel("");
		lblStatus24.setBounds(558, 442, 28, 25);
		frmST_RAS.getContentPane().add(lblStatus24);
		
		lblStatus25 = new JLabel("");
		lblStatus25.setBounds(416, 534, 28, 25);
		frmST_RAS.getContentPane().add(lblStatus25);
		
		final JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnNewButton.setText("Working ...");
				btnNewButton.setEnabled(false);
				
				SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>(){
					@Override
					protected Void doInBackground() throws Exception {
						try {
							lblStatus1.setIcon(getOkImg());
							Thread.sleep(500);
							lblStatus2.setIcon(getOkImg());
							Thread.sleep(500);
							lblStatus3.setIcon(getOkImg());
							Thread.sleep(500);
							lblStatus4.setIcon(getOkImg());
							Thread.sleep(500);
							lblStatus5.setIcon(getOkImg());
							Thread.sleep(500);
							lblStatus6.setIcon(getOkImg());
							Thread.sleep(500);
							lblStatus7.setIcon(getOkImg());
							Thread.sleep(500);
							lblStatus8.setIcon(getOkImg());
							Thread.sleep(500);
							lblStatus9.setIcon(getOkImg());
							Thread.sleep(500);
							lblStatus10.setIcon(getOkImg());
							Thread.sleep(500);
							lblStatus11.setIcon(getOkImg());
							Thread.sleep(500);
							lblStatus12.setIcon(getOkImg());
							Thread.sleep(500);
							lblStatus13.setIcon(getOkImg());
							Thread.sleep(500);
							lblStatus14.setIcon(getOkImg());
							Thread.sleep(500);
							lblStatus15.setIcon(getOkImg());
							Thread.sleep(500);
							lblStatus16.setIcon(getOkImg());
							Thread.sleep(500);
							lblStatus17.setIcon(getOkImg());
							Thread.sleep(500);
							lblStatus18.setIcon(getOkImg());
							Thread.sleep(500);
							lblStatus19.setIcon(getOkImg());
							Thread.sleep(500);
							lblStatus20.setIcon(getOkImg());
							Thread.sleep(500);
							lblStatus21.setIcon(getOkImg());
							Thread.sleep(500);
							lblStatus22.setIcon(getOkImg());
							Thread.sleep(500);
							lblStatus23.setIcon(getOkImg());
							Thread.sleep(500);
							lblStatus24.setIcon(getOkImg());
							Thread.sleep(500);
							lblStatus25.setIcon(getOkImg());
							Thread.sleep(500);
							lblDisableEnableActuator.setIcon(getWrongImg());
							Thread.sleep(500);
							lblDisableEnableSensor.setIcon(getWrongImg());
						} catch (InterruptedException e){
							e.printStackTrace();
						}
						return null;
					}
				};
				
				worker.addPropertyChangeListener(new PropertyChangeListener() {
					
					@Override
					public void propertyChange(PropertyChangeEvent evt) {
						// TODO Auto-generated method stub
						System.out.println("Event "+evt + " name "+evt.getPropertyName());
						if("DONE".equals(evt.getNewValue().toString())){
							btnNewButton.setEnabled(true);
							btnNewButton.setText("Test");
						}
					}
				});
				
				worker.execute();
			}
		});
		btnNewButton.setBounds(26, 575, 117, 25);
		frmST_RAS.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("New button");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//lblStatus2.setIcon(getWrongImg());
				stopThread();
				setImgs();
			}
		});
		
		//lblRASTestImg = new JLabel("");
		//lblRASTestImg.setIcon(new ImageIcon(RASSchemeTest.class.getResource("/ras/img/RASTestImg.png")));
		lblRASTestImg.setBounds(402, 212, 760, 407);
		frmST_RAS.getContentPane().add(lblRASTestImg);
		btnNewButton_1.setBounds(155, 575, 117, 25);
		frmST_RAS.getContentPane().add(btnNewButton_1);
		
		//frmST_RAS.setBounds(100, 100, 1003, 602);
		frmST_RAS.setBounds(100, 100, 1181, 677);
		frmST_RAS.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar mebMenu = new JMenuBar();
		frmST_RAS.setJMenuBar(mebMenu);
		
		JMenu menMenu = new JMenu("Menu");
		menMenu.setEnabled(flagMenu);
		mebMenu.add(menMenu);
		
		JMenuItem mitSelftestingRas = new JMenuItem("Self-Testing RAS");
		mitSelftestingRas.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent arg0) {
				cmbRASClass.addItem(new ST_classRAS());
				try {
					listClassRAS = (List<ST_classRAS>)(Object)stubDBRAS.getStubDBRAS().selectGenericQuery("from sra_classRAS", null);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					JOptionPane.showMessageDialog(frmST_RAS, e.getMessage());
				}
				cmbRASClass.setEnabled(true);
				if(cmbRASClass.getItemCount() == 1)
					for(ST_classRAS classRAS:listClassRAS)
						cmbRASClass.addItem(classRAS);
			}
		});
		menMenu.add(mitSelftestingRas);
		
		JMenuItem mitClearSelftesting = new JMenuItem("Clear Self-Testing");
		mitClearSelftesting.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setParameters();
				cmbRASClass.setEnabled(false);
				cmbRASScheme.setEnabled(false);
				cmbTypeControlSignal.setEnabled(false);
				cmbComponentTest.setEnabled(false);
				cmbSTCode.setEnabled(false);
				setImgs();
				DefaultTableModel model = new DefaultTableModel();
				tblListContingencies.setModel(model);
			}
		});
		menMenu.add(mitClearSelftesting);
		
		JMenuItem mitExit = new JMenuItem("Exit");
		mitExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(1);
			}
		});
		menMenu.add(mitExit);
	}
	
	private void stopThread(){
		blinker = null;
	}
	
	
	public void runThread(){
		blinker =
				new Thread(new Runnable() {
					@Override
					public void run() {
						Thread thisThread = Thread.currentThread();
						//System.out.println("Check Images1");
						while(blinker == thisThread){
							try {
								activateIcons(typeAction, getTestCtrlSignal().getRasTest().getIdRASTest());
								Thread.sleep(100);
								//System.out.println("Check Images");
							} catch (Exception e) {
								// TODO: handle exception
							}
							
						}
					}
				});
				blinker.start();
	}
	
	@SuppressWarnings("unchecked")
	private void activateIcons(String action, int idRASTest){
		List<ST_RASTestLink>  listLinks = new ArrayList<>();
		String query = "";
		//boolean flagId = false;
		try {
			if(typeAction.equals(TypeActions.DisableComponent.toString()))
				query = "from sra_RASTestLink where rtl_idRASTestLink > :id and (rtl_idTypeAction = "
						+(TypeActions.DisableComponent.ordinal()+1)
						+" or rtl_idTypeAction = "+(TypeActions.EnableComponent.ordinal()+1)		
						+") and rtl_idRASTest = "+idRASTest;
			else{
				if(typeAction.equals(TypeActions.EnableComponent.toString()))
					query = "from sra_RASTestLink where rtl_idRASTestLink > :id and rtl_idTypeAction = "
							+(TypeActions.EnableComponent.ordinal()+1
							+" and rtl_idRASTest = "+idRASTest);
				else
					if(typeAction.equals(TypeActions.SendMessageTest.toString()))
						query = "from sra_RASTestLink where rtl_idRASTestLink > :id and rtl_idTypeAction = "
								+(TypeActions.SendMessageTest.ordinal()+1
								+" and rtl_idRASTest = "+idRASTest);
			}
			listLinks = (List<ST_RASTestLink>)(Object)stubDBRAS.getStubDBRAS().selectGenericQuery(query, idLink);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(frmST_RAS, e.getMessage());
			e.printStackTrace();
		}
		if(!listLinks.isEmpty())
			//System.out.println(query+"\tidRASTest:"+listLinks.get(0).getIdRASTest().getIdRASTest()+"\tidLink:"+idLink);
			for(ST_RASTestLink link : listLinks){
				
				idLink = link.getIdRASTestLink();
				//System.out.println(link.getIdLinkEES().getIdLink());
				switch (link.getIdLinkEES().getIdLink()) {
				case 1:
					lblStatus1.setIcon((link.getResultLink() ? getOkImg() : getWrongImg()));
					break;
				case 2:
					lblStatus2.setIcon((link.getResultLink() ? getOkImg() : getWrongImg()));
					break;
				case 3:
					lblStatus3.setIcon((link.getResultLink() ? getOkImg() : getWrongImg()));
					break;
				case 4:
					lblStatus4.setIcon((link.getResultLink() ? getOkImg() : getWrongImg()));
					break;
				case 5:
					lblStatus5.setIcon((link.getResultLink() ? getOkImg() : getWrongImg()));
					break;
				case 6:
					lblStatus6.setIcon((link.getResultLink() ? getOkImg() : getWrongImg()));
					break;
				case 7:
					lblStatus7.setIcon((link.getResultLink() ? getOkImg() : getWrongImg()));
					break;
				case 8:
					lblStatus8.setIcon((link.getResultLink() ? getOkImg() : getWrongImg()));
					break;
				case 9:
					lblStatus9.setIcon((link.getResultLink() ? getOkImg() : getWrongImg()));
					break;
				case 10:
					lblStatus10.setIcon((link.getResultLink() ? getOkImg() : getWrongImg()));
					break;
				case 11:
					lblStatus11.setIcon((link.getResultLink() ? getOkImg() : getWrongImg()));
					break;
				case 12:
					lblStatus12.setIcon((link.getResultLink() ? getOkImg() : getWrongImg()));
					break;
				case 13:
					lblStatus13.setIcon((link.getResultLink() ? getOkImg() : getWrongImg()));
					break;
				case 14:
					lblStatus14.setIcon((link.getResultLink() ? getOkImg() : getWrongImg()));
					break;
				case 15:
					lblStatus15.setIcon((link.getResultLink() ? getOkImg() : getWrongImg()));
					break;
				case 16:
					lblStatus16.setIcon((link.getResultLink() ? getOkImg() : getWrongImg()));
					break;
				case 17:
					lblStatus17.setIcon((link.getResultLink() ? getOkImg() : getWrongImg()));
					break;
				case 18:
					lblStatus18.setIcon((link.getResultLink() ? getOkImg() : getWrongImg()));
					break;
				case 19:
					lblStatus19.setIcon((link.getResultLink() ? getOkImg() : getWrongImg()));
					break;
				case 20:
					lblStatus20.setIcon((link.getResultLink() ? getOkImg() : getWrongImg()));
					break;
				case 21:
					lblStatus21.setIcon((link.getResultLink() ? getOkImg() : getWrongImg()));
					break;
				case 22:
					lblStatus22.setIcon((link.getResultLink() ? getOkImg() : getWrongImg()));
					break;
				case 23:
					lblStatus23.setIcon((link.getResultLink() ? getOkImg() : getWrongImg()));
					break;
				case 24:
					lblStatus24.setIcon((link.getResultLink() ? getOkImg() : getWrongImg()));
					break;
				case 25:
					lblStatus25.setIcon((link.getResultLink() ? getOkImg() : getWrongImg()));
					break;
				case 26: 
					lblDisableEnableSensor.setIcon((link.getStateComponent() ? getEnableComponent() : getDisableComponent()));
					break;
				case 27: 
					lblDisableEnableActuator.setIcon((link.getStateComponent() ? getEnableComponent() : getDisableComponent()));
					break;
				default:
					break;
				}
			}
	}
	
	private void setImgs(){
		lblStatus1.setIcon(null);
		lblStatus2.setIcon(null);
		lblStatus3.setIcon(null);
		lblStatus4.setIcon(null);
		lblStatus5.setIcon(null);
		lblStatus6.setIcon(null);
		lblStatus7.setIcon(null);
		lblStatus8.setIcon(null);
		lblStatus9.setIcon(null);
		lblStatus10.setIcon(null);
		lblStatus11.setIcon(null);
		lblStatus12.setIcon(null);
		lblStatus13.setIcon(null);
		lblStatus14.setIcon(null);
		lblStatus15.setIcon(null);
		lblStatus16.setIcon(null);
		lblStatus17.setIcon(null);
		lblStatus18.setIcon(null);
		lblStatus19.setIcon(null);
		lblStatus20.setIcon(null);
		lblStatus21.setIcon(null);
		lblStatus22.setIcon(null);
		lblStatus23.setIcon(null);
		lblStatus24.setIcon(null);
		lblStatus25.setIcon(null);
		lblDisableEnableActuator.setIcon(null);
		lblDisableEnableSensor.setIcon(null);
		
		DefaultTableModel model = new DefaultTableModel();
		//tblListContingencies.setModel(model);
		tblContingencies.setModel(model);
		tblLinks.setModel(model);
		tblRASTest.setModel(model);
		tblRemedialActions.setModel(model);
		tblStatusComponents.setModel(model);
		tblSTCode.setModel(model);
		tblSTCodeContingency.setModel(model);
		tblTripCommand.setModel(model);
	}
	
	@SuppressWarnings("unchecked")
	public void displayTables(String buttonAction){
		List<Object[]> resultdata;
		
		if(buttonAction.equals(TypeActions.EnableComponent.toString()+"NULL") || 
				buttonAction.equals(TypeActions.EnableComponent.toString()) || 
				buttonAction.equals(TypeActions.DisableComponent.toString()) || 
				buttonAction.equals(TypeActions.EnableComponent.toString()+"NULL") || 
				buttonAction.equals(TypeActions.SendMessageTest.toString()) || 
				buttonAction.equals(TypeActions.SendMessageTest.toString()+"NULL") ||
				buttonAction.equals(TypeActions.Error.toString())){
			try {
				/*LINKS*/
				resultdata = (List<Object[]>)(Object)stubDBRAS.getStubDBRAS().selectGenericQuery(queryDBLinks, getTestCtrlSignal().getRasTest().getIdRASTest());
				String []columnsLinks = {"Id Link","Source","Destination","Type Action","Result Link","State Component","Comment"};
				DefaultTableModel dtableResultLinks = new DefaultTableModel(columnsLinks, 0);
				
				for(Object[] data: resultdata)
					dtableResultLinks.addRow(data);
				tblLinks.setModel(dtableResultLinks);
				spaLinks.setViewportView(tblLinks);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(frmST_RAS, "Error displaying Links table");
				e.printStackTrace();
			}
		}
		
		if(buttonAction.equals(TypeActions.CreateMessage.toString()) || 
				buttonAction.equals(TypeActions.EnableComponent.toString()) || 
				buttonAction.equals(TypeActions.DisableComponent.toString()) || 
				buttonAction.equals(TypeActions.SendMessageTest.toString()) ||
				buttonAction.equals(TypeActions.Error.toString())){
			try {
				/*RAS TEST*/
				resultdata = (List<Object[]>)(Object)stubDBRAS.getStubDBRAS().selectGenericQuery(queryDBRASTest, getTestCtrlSignal().getRasTest().getIdRASTest());
				String []columns = {"RAS Test","RAS Classifications","Type RAS","Result Test","Begin Test","End Test","Comment"};
				DefaultTableModel dtableResultRASTest = new DefaultTableModel(columns, 0);
				//System.out.println(resultRASTest.getClass().getName());
				for(Object[] data: resultdata)
					dtableResultRASTest.addRow(data);
				tblRASTest.setModel(dtableResultRASTest);
				spaRASTest.setViewportView(tblRASTest);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(frmST_RAS, "Error displaying RAS Test table");
			}
		}
		
		if(buttonAction.equals(TypeActions.EnableComponent.toString()) || 
				buttonAction.equals(TypeActions.DisableComponent.toString()) || 
				buttonAction.equals(TypeActions.SendMessageTest.toString()) ||
				buttonAction.equals(TypeActions.Error.toString())){

			try {
				/*STATUS COMPONENTS*/
				resultdata = (List<Object[]>)(Object)stubDBRAS.getStubDBRAS().selectGenericQuery(queryDBStatusComponents, getTestCtrlSignal().getRasTest().getIdRASTest());
				String []columns = {"Name RAS","Type Action","Enable/Disable Actuator","dateTime Actuator",
						"Enable/Disable Sensor","dateTime Sensor","Result Enable/Disable","Comment"};
				DefaultTableModel dtableStatusComponent = new DefaultTableModel(columns, 0);
				
				for(Object[] data: resultdata)
					dtableStatusComponent.addRow(data);
				tblStatusComponents.setModel(dtableStatusComponent);
				spaStatusComponents.setViewportView(tblStatusComponents);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(frmST_RAS, "Error displaying Status Components table");
			}
			
			try {
				/*ST CODE*/
				resultdata = (List<Object[]>)(Object)stubDBRAS.getStubDBRAS().selectGenericQuery(queryDBSTCodes, getTestCtrlSignal().getRasTest().getIdRASTest());
				String []columnsSTCode = {"Id Code","RAS Test","Name Code","Name Component","Type Action","Create Message", "Disable/Enable Sensor",
						"Disable/Enable Actuator","Power","Current","Voltage","Frequency","Reactive Power","Phase Voltage","Parameter Error","CodeWord Error"};
				DefaultTableModel dtableResultSTCode = new DefaultTableModel(columnsSTCode, 0);
				
				for(Object[] data: resultdata)
					dtableResultSTCode.addRow(data);
				tblSTCode.setModel(dtableResultSTCode);
				spaSTCode.setViewportView(tblSTCode);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(frmST_RAS, "Error displaying ST Code table");
				e.printStackTrace();
			}
			
			try {
				/*ST CODE CONTINGENCY*/
				resultdata = (List<Object[]>)(Object)stubDBRAS.getStubDBRAS().selectGenericQuery(queryDBSTCodesContingency, getTestCtrlSignal().getRasTest().getIdRASTest());
				String []columnsSTCode = {"Id Code","RAS Test","Id Contingency","Name Code","Name Component","Type Action","Contingency","CodeWord Error"};
				DefaultTableModel dtableResultSTCodeContingency = new DefaultTableModel(columnsSTCode, 0);
				
				for(Object[] data: resultdata)
					dtableResultSTCodeContingency.addRow(data);
				tblSTCodeContingency.setModel(dtableResultSTCodeContingency);
				spaSTCodeContingency.setViewportView(tblSTCodeContingency);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(frmST_RAS, "Error displaying ST Code Contingency table");
				e.printStackTrace();
			}
		}

		if(buttonAction.equals(TypeActions.SendMessageTest.toString()) || 
				buttonAction.equals(TypeActions.Error.toString())) {
			try {
				/*TRIP COMMAND*/
				resultdata = (List<Object[]>)(Object)stubDBRAS.getStubDBRAS().selectGenericQuery(queryDBTripCommands, getTestCtrlSignal().getRasTest().getIdRASTest());
				String []columnsTripCommand = {"Id RAS","RAS Test","Name RAS","Power","Current","Voltage","Frequency","Reactive Power","Phase Voltage"};
				DefaultTableModel dtableResultTripCommand = new DefaultTableModel(columnsTripCommand, 0);
				
				for(Object[] data: resultdata)
					dtableResultTripCommand.addRow(data);
				tblTripCommand.setModel(dtableResultTripCommand);
				spaTripCommand.setViewportView(tblTripCommand);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(frmST_RAS, "Error displaying Trip Command table");
				e.printStackTrace();
			}
			
			try {
				/*REMEDIAL ACTION*/
				resultdata = (List<Object[]>)(Object)stubDBRAS.getStubDBRAS().selectGenericQuery(queryDBRemedialActions, getTestCtrlSignal().getRasTest().getIdRASTest());
				String []columnsRemedialAction = {"Id Remedial Action","Remedial Action"};
				DefaultTableModel dtableResultRemedialAction = new DefaultTableModel(columnsRemedialAction, 0);
				
				for(Object[] data: resultdata)
					dtableResultRemedialAction.addRow(data);
				tblRemedialActions.setModel(dtableResultRemedialAction);
				spaRemedialActions.setViewportView(tblRemedialActions);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(frmST_RAS, "Error displaying Remedial Actions table");
				e.printStackTrace();
			}
			
			try {
				/*CONTINGENCIES*/
				resultdata = (List<Object[]>)(Object)stubDBRAS.getStubDBRAS().selectGenericQuery(queryDBContingencies, getTestCtrlSignal().getRasTest().getIdRASTest());
				String []columnsContignecies = {"Id Contigency","Contigencies"};
				DefaultTableModel dtableResultContigencies = new DefaultTableModel(columnsContignecies, 0);
				
				for(Object[] data: resultdata)
					dtableResultContigencies.addRow(data);
				tblContingencies.setModel(dtableResultContigencies);
				spaContingencies.setViewportView(tblContingencies);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(frmST_RAS, "Error displaying Contingencies table");
				e.printStackTrace();
			}
		}
	}
	
	private void setParameters(){
		
		txtCurrent.setEnabled(false);
		txtFrequency.setEnabled(false);
		txtPhaseVoltage.setEnabled(false);
		txtPower.setEnabled(false);
		txtReactivePower.setEnabled(false);
		txtVoltage.setEnabled(false);
		
		txtCurrent.setText("");
		txtFrequency.setText("");
		txtPhaseVoltage.setText("");
		txtPower.setText("");
		txtReactivePower.setText("");
		txtVoltage.setText("");
		
		//spaContingencies.setVisible(false);
		//spaContingencies.removeAll();
		//tblContingencies.setVisible(false);
		tblListContingencies.removeAll();
		tblListContingencies.removeEditor();
		
		btnCreateTestCtrlSignal.setVisible(false);
		btnCreateTestCtrlSignal.setEnabled(false);
		btnDisableActuatorSensor.setVisible(false);
		btnDisableActuatorSensor.setEnabled(false);
		btnSendMessage.setEnabled(false);
		btnSendMessage.setVisible(false);
		
		//cmbRASClass.setEnabled(false);
		//***** COMMENT
		cmbRASScheme.setEnabled(false);
		cmbComponentTest.setEnabled(false);
		cmbSTCode.setEnabled(false);
		cmbTypeControlSignal.setEnabled(false);
	}
/*
    public ST_RASTest getRASTest() {
		return rasTest;
	}

	private void setRASTest(ST_RASTest rasTest) {
		this.rasTest = rasTest;
	}
*/
	
	private TestCtrlSignal getTestCtrlSignal() {
		return testCtrlSignal;
	}

	private void setTestCtrlSignal(TestCtrlSignal testCtrlSignal) {
		this.testCtrlSignal = testCtrlSignal;
	}

	private ST_classRAS getClassRAS() {
		return classRAS;
	}

	private void setClassRAS(ST_classRAS classRAS) {
		this.classRAS = classRAS;
	}

	private ST_rasSchemes getRASScheme() {
		return rasScheme;
	}

	private void setRASScheme(ST_rasSchemes rasScheme) {
		this.rasScheme = rasScheme;
	}

	public ImageIcon getOkImg() {
		return okImg;
	}

	private void setOkImg(ImageIcon okImg) {
		this.okImg = okImg;
	}

	public ImageIcon getWrongImg() {
		return wrongImg;
	}

	private void setWrongImg(ImageIcon wrongImg) {
		this.wrongImg = wrongImg;
	}

	public ImageIcon getDisableComponent() {
		return disableComponent;
	}

	private void setDisableComponent(ImageIcon disableComponent) {
		this.disableComponent = disableComponent;
	}

	public ImageIcon getEnableComponent() {
		return enableComponent;
	}

	private void setEnableComponent(ImageIcon enableComponent) {
		this.enableComponent = enableComponent;
	}       
}

