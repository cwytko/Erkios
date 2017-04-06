package ras.tableModels;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import ras.data.ST_contingencies;

public class ContingenciesTableModel extends AbstractTableModel{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<ST_contingencies> contingencies;
	private String[] columnContingencies = {"Check","Contingecies"};
	
    public List<ST_contingencies> getContingencies() {
		return contingencies;
	}

	private void setContingencies(List<ST_contingencies> contingencies) {
		this.contingencies = contingencies;
	}

	public ContingenciesTableModel(List<ST_contingencies> conting) {
        //this.contingencies = new ArrayList<>(conting);
    	setContingencies(conting);
    	//this.contingencies = conting;
        //JOptionPane.showMessageDialog(frmST_RAS, String.valueOf("Value: "+contingencies.get(0).getContingency()));
    }
    
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columnContingencies.length;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return getContingencies().size();
	}
	
	@Override
    public String getColumnName(int columnIndex) {
        return columnContingencies[columnIndex];
    }
	
	@Override
    public Class<?> getColumnClass(int column) {
        switch (column) {
            case 0:
                return Boolean.class;
            case 1:
                return String.class;
        }
		return null;
    }
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
    	ST_contingencies conting = contingencies.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return conting.getCheckContingency();
            case 1:
                return conting.getContingency();
        }
        return null;
	}
	
	@Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex){
    	getContingencies().get(rowIndex).setCheckContingency((Boolean)aValue);
    	fireTableCellUpdated(rowIndex, columnIndex);
    }
    
    @SuppressWarnings("rawtypes")
	public Class[] columnTypes = new Class[] {
			Boolean.class, String.class
	};
    
    boolean[] columnEditables = new boolean[] {
		true, false
	};
    
    @Override
    public boolean isCellEditable(int row, int column) {
		return columnEditables[column];
	}
}
