package ras.gui;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.AbstractTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.ScrollPaneConstants;

public class ClickTable {
	JTable table1;
	private JTable table;
    public static void main(String[] args) {
        new ClickTable();
    }

    public ClickTable() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                }

                List<Click> clicks = new ArrayList<>(25);
                clicks.add(new Click(620, 1028));
                clicks.add(new Click(480, 230));
                final ClickTableModel model = new ClickTableModel(clicks);

                JFrame frame = new JFrame("Testing");
                frame.setPreferredSize(new Dimension(500, 500));
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().setLayout(null);
                
                JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
                tabbedPane.setBounds(51, 118, 398, 164);
                frame.getContentPane().add(tabbedPane);
                
                JPanel panel = new JPanel();
                panel.setPreferredSize(new Dimension(100, 100));
                tabbedPane.addTab("Test", null, panel, null);
                panel.setLayout(null);
                
                JButton btnNewButton = new JButton("New button");
                btnNewButton.setBounds(111, 84, 122, 25);
                panel.add(btnNewButton);
                table1 = new JTable(model);
                table1.setAutoCreateRowSorter(true);
                table1.setPreferredScrollableViewportSize(new Dimension(500, 400));
                table1.setPreferredSize(new Dimension(1000, 80));
                table1.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.RED, Color.ORANGE, Color.BLUE, Color.GRAY));
                table1.setAlignmentY(5.0f);
                table1.setAlignmentX(5.1f);
                final JScrollPane scrollPane = new JScrollPane(table1);
                scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
                scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                scrollPane.setBounds(96, 0, 75, 70);
                panel.add(scrollPane);
                scrollPane.setPreferredSize(new Dimension(200, 130));
                scrollPane.setFocusable(false);
                scrollPane.setAlignmentY(5.0f);
                scrollPane.setAlignmentX(5.0f);
                
                JPanel panel_1 = new JPanel();
                tabbedPane.addTab("New tab", null, panel_1, null);
                panel_1.setLayout(null);
                
                table = new JTable();
                table.setPreferredSize(new Dimension(200, 100));
                table.setBounds(0, 0, 1, 1);
                
                
                final JScrollPane scrollPane_1 = new JScrollPane(table);
                scrollPane_1.setPreferredSize(new Dimension(200, 100));
                scrollPane_1.setBounds(85, 25, 191, 100);
                panel_1.add(scrollPane_1);
                
                JButton btnNewButton_1 = new JButton("New button");
                btnNewButton_1.addActionListener(new ActionListener() {
                	public void actionPerformed(ActionEvent e) {
                		List<Click> clicks = new ArrayList<>(25);
                        clicks.add(new Click(60, 10));
                        clicks.add(new Click(04, 20));
                        final ClickTableModel model = new ClickTableModel(clicks);
                		table = new JTable(model);
                		scrollPane_1.setViewportView(table);
                	}
                });
                btnNewButton_1.setBounds(276, 55, 117, 25);
                panel_1.add(btnNewButton_1);
                btnNewButton.addActionListener(new ActionListener() {
                	public void actionPerformed(ActionEvent e) {
                		table1.setVisible(false);
                		List<Click> clicks = new ArrayList<>(25);
                        clicks.add(new Click(6, 1));
                        clicks.add(new Click(4, 2));
                        final ClickTableModel model = new ClickTableModel(clicks);
                		table1 = new JTable(model);
                		scrollPane.setViewportView(table1);
                	}
                });
                
                
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    public class Click {

        private int x;
        private int y;

        public Click(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

    }

    public class ClickTableModel extends AbstractTableModel {

        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private List<Click> clicks;

        public ClickTableModel(List<Click> clicks) {
            this.clicks = new ArrayList<>(clicks);
        }

        @Override
        public int getRowCount() {
            return clicks.size();
        }

        @Override
        public int getColumnCount() {
            return 2;
        }

        @Override
        public String getColumnName(int column) {
            String name = "??";
            switch (column) {
                case 0:
                    name = "Mouse X";
                    break;
                case 1:
                    name = "Mouse Y";
                    break;
            }
            return name;
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            Class<?> type = String.class;
            switch (columnIndex) {
                case 0:
                case 1:
                    type = Integer.class;
                    break;
            }
            return type;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Click click = clicks.get(rowIndex);
            Object value = null;
            switch (columnIndex) {
                case 0:
                    value = click.getX();
                    break;
                case 1:
                    value = click.getY();
                    break;
            }
            return value;
        }            
    }        
}
