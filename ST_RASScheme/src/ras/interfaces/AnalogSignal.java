package ras.interfaces;

import java.io.Serializable;

public class AnalogSignal implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private float[] parameters;
	private String[] parametersCode;
	private String idSession;
	private Boolean disableEnableComponent;
	private String typeAction;
	
	public AnalogSignal(float[] parameters, String[] parametersCode, String idSession, Boolean enableDisableComponent, String typeAction){
		setParameters(parameters);
		setParametersCode(parametersCode);
		setIdSession(idSession);
		setDisableEnableComponent(enableDisableComponent);
		setTypeAction(typeAction);
	}
	
	public float[] getParameters() {
		return parameters;
	}

	private void setParameters(float[] parameters) {
		this.parameters = parameters;
	}

	public String[] getParametersCode() {
		return parametersCode;
	}
	private void setParametersCode(String[] parametersCode) {
		this.parametersCode = parametersCode;
	}
	public String getIdSession() {
		return idSession;
	}
	private void setIdSession(String idSession) {
		this.idSession = idSession;
	}

	public Boolean getDisableEnableComponent() {
		return disableEnableComponent;
	}

	private void setDisableEnableComponent(Boolean disableEnableComponent) {
		this.disableEnableComponent = disableEnableComponent;
	}

	public String getTypeAction() {
		return typeAction;
	}

	private void setTypeAction(String typeAction) {
		this.typeAction = typeAction;
	}
	
}
