import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class LinkedListReader {
    static public void readFileToLinkedList (File file, LinkedList<Notebook> is_notebooks, JTable table) {
        String s = getStringFromFile(file);
        s = s.substring(1,s.length()-3);

        String[] lines = s.split(",");
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].charAt(0)==' '){
                lines[i] = lines[i].substring(1,lines[i].length());
            }
            System.out.println(lines[i]);
            Notebook n = fromString(lines[i]);
            if (n != null) {
                is_notebooks.add(n);
                table.updateUI();
            }
        }
    }
    static private Notebook fromString(String s){
        String[] elements = new String[4];
        for (int i = 0; i < 4; i++) elements[i] = "";
        int pos = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ' ') {
                pos++;
                i++;
            }
            if (pos == 4) return null;
            elements[pos] += s.charAt(i);
        }
        for (int i = 0; i < 4; i++)
            elements[i] = elements[i].replaceAll("[\\x00-\\x1F]", "");//очистка от управляющих символов для корректного парсинга
        try {
            double f = Double.parseDouble(elements[2]);
            double r = Double.parseDouble(elements[3]);
            return new Notebook(elements[0], elements[1], f, r);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    static private String getStringFromFile(File file) {
        String s = "";
        try {
            Scanner in = new Scanner(file);
            while (in.hasNext())
                s += in.nextLine() + "\r\n";
            in.close();
        } catch (FileNotFoundException e) {
            System.out.print("Файл не найден!");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Параметры командной строки некорректны!");
        }
        return s;
    }
}
