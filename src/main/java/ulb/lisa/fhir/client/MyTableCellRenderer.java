/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ulb.lisa.fhir.client;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;



/**
 *
 * @author karabomagomola
 */
public class MyTableCellRenderer implements TableCellRenderer {

    private static final TableCellRenderer RENDERER = new DefaultTableCellRenderer();
    
    
    @Override
    public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
        
        Component c = RENDERER.getTableCellRendererComponent(jtable,o,bln,bln1,i,i1);
        
        
        if(i1 == 4){
        Object result = jtable.getModel().getValueAt(i,i1);
        Double totalChol = Double.parseDouble(result.toString());
        Color color = null;
        
        if(totalChol <170.0){
            
            color = Color.RED;
        }else if(totalChol > 120.0){
        
            color = Color.PINK;
        }
            c.setForeground(color);
        }else {
        
            c.setForeground(Color.yellow);
        }
        return c;
       
        
    }
    
    
}

   
