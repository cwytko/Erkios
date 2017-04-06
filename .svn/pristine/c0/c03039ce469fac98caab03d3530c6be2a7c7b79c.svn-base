package ras.gui;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class TestJFrame extends JFrame {
    private static final long serialVersionUID = 1L;

    public static void main(String[] args) {
        TestJFrame testJFrame = new TestJFrame();

        List<String> columns = new ArrayList<String>();
        List<String[]> values = new ArrayList<String[]>();

        columns.add("col1");
        columns.add("col2");
        columns.add("col3");

        for (int i = 0; i < 100; i++) {
            values.add(new String[] {"val"+i+" col1","val"+i+" col2","val"+i+" col3"});
        }

        TableModel tableModel = new DefaultTableModel(values.toArray(new Object[][] {}), columns.toArray());
        JTable table = new JTable(tableModel);
        testJFrame.setLayout(new BorderLayout());
        testJFrame.add(new JScrollPane(table), BorderLayout.CENTER);

        testJFrame.add(table.getTableHeader(), BorderLayout.NORTH);

        testJFrame.setVisible(true);
        testJFrame.setSize(200,200);
    }

}
