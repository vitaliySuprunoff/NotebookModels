import sun.management.snmp.jvmmib.JVM_MANAGEMENT_MIBOidTable;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

public class NotebookTableModel extends AbstractTableModel {
    private LinkedList <Notebook>  is_notebooks;

    public NotebookTableModel (LinkedList<Notebook> notebooks){
        this.is_notebooks = notebooks;
    }

    public Class<?> getColumnClass(int columnIndex) {
        return getValueAt(0, columnIndex).getClass();
    }

    public int getColumnCount () {
        return 4;
    }

    public String getColumnName(int columnIndex) {
        switch (columnIndex){
            case 0 : return "Id";
            case 1 : return "Creator";
            case 2 : return "Frequency";
            case 3 : return "RAM";
        }
        return "";
    }

    public int getRowCount () {
        return  is_notebooks.size();
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Notebook nb = is_notebooks.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return nb.getId();
            case 1:
                return nb.getCreator();
            case 2:
                return nb.getFrequency();
            case 3 :
                return nb.getRam();
        }
        return "";
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public void setValueAt(Object value, int rowIndex, int columnIndex) {}

    public void addRow(Notebook nn) { //добавление строки в таблицу
        is_notebooks.add(nn);
        fireTableDataChanged();
        int index = is_notebooks.indexOf(nn);
        //table.setRowSelectionInterval(index, index);
    }

    public void sort (){
        Collections.sort(is_notebooks);
        fireTableDataChanged();
    }

    public boolean deleteRow(double ram) {
        Iterator<Notebook> iNot=is_notebooks.iterator();
        boolean flag=false;
        while(iNot.hasNext()) {
            if(iNot.next().getRam()==ram) {
                iNot.remove();
                flag=true;
                fireTableDataChanged();
            }
        }
        return flag;
    }

    public void updateRow(int index, Notebook nn, JTable table) {//обновление строки в таблице
        is_notebooks.set(index, nn);
        fireTableDataChanged();
        table.setRowSelectionInterval(index, index);
    }


}