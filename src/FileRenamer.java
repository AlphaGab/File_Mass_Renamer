import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FileRenamer extends JFrame {

    private final JLabel prefixlbl = new JLabel("Prefix");
    private JTextField prefixField;
    private JButton AddFilebtn,Clearbtn,replacePrefixbtn,addPrefixbtn;
    private List<File> renamedFileNames = new ArrayList<>();
    private List<File> selectedFileNames = new ArrayList<>();
    private JTextArea fileNameArea = new JTextArea(30,40);
    private JScrollPane scrollPane = new JScrollPane(fileNameArea);

    public FileRenamer(){
        setTitle("File Renamer");
        setSize(500,650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        FlowLayout flowLayout = new FlowLayout();

        setLayout(flowLayout);
        add(prefixlbl);
        prefixField = new JTextField(20);

        addPrefixbtn = new JButton("Add Prefix");
        replacePrefixbtn = new JButton("Replace Prefix");
        AddFilebtn = new JButton("Add File");
        Clearbtn = new JButton("Clear");
        fileNameArea.setEditable(false);
        add(prefixlbl);
        add(prefixField);
        add(addPrefixbtn);
        add(replacePrefixbtn);
        add(AddFilebtn);
        add(Clearbtn);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        getContentPane().add(scrollPane);
        AddFilebtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addFiles();


            }
        });
        Clearbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearTextArea();
            }
        });
        addPrefixbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rename();
               refreshTextArea();
            }
        });
        replacePrefixbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                replace();
                refreshTextArea();
            }
        });

        setVisible(true);
    }
    private void updateFileNameTextArea(){
        fileNameArea.setText("");
        for (File file : selectedFileNames) {
            fileNameArea.append(file + "\n");
        }

    }

    private void refreshTextArea(){
            fileNameArea.setText("");
        for (File file : renamedFileNames) {
            fileNameArea.append(file + "\n");
        }
        }


    private void addFiles(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true);
        int returnValue = fileChooser.showOpenDialog(null);
        if(returnValue == JFileChooser.APPROVE_OPTION){
            for(File file : fileChooser.getSelectedFiles()){
                selectedFileNames.add(file);
            }

        updateFileNameTextArea();
        }
    }
    private void clearTextArea(){
        fileNameArea.setText("");
        renamedFileNames.clear();
        selectedFileNames.clear();
    }
    private void rename(){
        String prefix = prefixField.getText().trim();
        if(!prefix.isEmpty()){
            for(int i = 0 ; i < selectedFileNames.size(); i++){
                File file = selectedFileNames.get(i);
                String oldName = file.getName();
                String newName = prefix+"_" + oldName;
                File newFileNamed = new File(file.getParent(),newName);
                if(file.renameTo(newFileNamed)){
                    renamedFileNames.add(newFileNamed);

                }
                else {
                    JOptionPane.showMessageDialog(null, "Error renaming " + oldName);
                }

            }
            selectedFileNames.clear();

        }
    }
    //Use to replace Names
    private void replace(){
        String prefix = prefixField.getText().trim();
        for(int i = 0 ; i< selectedFileNames.size(); i++){
            File file = selectedFileNames.get(i);
            String oldName = file.getName();
            String substringAfterFirstUnderscore = "";
            String newName = "";
            int firstUnderscoreIndex = oldName.indexOf('_');
            if (firstUnderscoreIndex != -1 && firstUnderscoreIndex < oldName.length() - 1) {
               substringAfterFirstUnderscore = oldName.substring(firstUnderscoreIndex + 1); //get the string beside _
                newName = prefix + "_"+substringAfterFirstUnderscore;

            } else {
                rename();
            }
            File newFileNamed = new File(file.getParent(),newName);
            if(file.renameTo(newFileNamed)){
                renamedFileNames.add(newFileNamed);

            }
            else {
                JOptionPane.showMessageDialog(null, "Error renaming " + oldName);
            }

        }
        selectedFileNames.clear();
    }



    public static void main(String[]args){
        new FileRenamer();
    }


}
