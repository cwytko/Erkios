package ras.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;

public class TestButtonTask {

    public static void main(String[] args) {

        final JFrame frame = new JFrame("Test");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        final JButton task = new JButton("Test");
        task.setBounds(5, 5, 100, 50);
        final JLabel lbltaks = new JLabel("MESSAGE");
        lbltaks.setBounds(10, 10, 10, 5);
        task.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                task.setText("Working...");
                task.setEnabled(false);

                SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

                    @Override
                    protected Void doInBackground() throws Exception {
                        try {
                        	int count = 0;
                        	while(count<10){
                        		Thread.sleep(3 * 1000);
                        		lbltaks.setText(Integer.toString(count++));
                        	}	
                        } catch (InterruptedException ex) {
                            Logger.getLogger(TestButtonTask.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        return null;
                    }                    
                };

                worker.addPropertyChangeListener(new PropertyChangeListener() {
                    @Override
                    public void propertyChange(PropertyChangeEvent evt) {
                        System.out.println("Event " + evt + " name" + evt.getPropertyName() + " value " + evt.getNewValue());
                        if ("DONE".equals(evt.getNewValue().toString())) {
                            task.setEnabled(true);
                            task.setText("Test");
                        }
                    }
                });

                worker.execute();
            }
        });

        frame.getContentPane().add(task);
        frame.getContentPane().add(lbltaks);
        frame.pack();
        frame.setVisible(true);
    } //end main
} //end class
