package work;

//this is a simple project I created for a coding assignment
//this projects involves GUI libraries like swing, and interacting with action listeners
//this project uses constructors, inheritance, methods and every other important java concepts 
//this project also uses "this" a lot to refer to the instance variable
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEdit;



public class Texteditor extends JFrame implements ActionListener{
JTextArea textArea;
JScrollPane scrollPane;
JSpinner fontSizeSpinner;
JLabel fontLabel;
JButton fontColorButton;
JComboBox fontBox;
JMenuBar menuBar;
JMenu fileMenu;
JMenu editMenu;
JMenuItem rev;
JMenuItem redo;
JMenuItem saveItem;
JMenuItem exitItem;
UndoManager undoManager;


	Texteditor(){
		undoManager = new UndoManager();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("noter- text editor");
		this.setSize(500,500);
		this.setLayout(new FlowLayout());
		
		textArea = new JTextArea();
	
		textArea.setLineWrap(true);
		textArea.setFont(new Font("mono",Font.PLAIN,20));
		textArea.setWrapStyleWord(true);
		scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(450,450));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    fontLabel=new JLabel("Font Size:");
		fontSizeSpinner= new JSpinner();
		fontSizeSpinner.setPreferredSize(new Dimension(50,30));
		fontSizeSpinner.setValue(20);
		
		
		fontSizeSpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				textArea.setFont(new Font(textArea.getFont().getFamily(),Font.PLAIN,(int) fontSizeSpinner.getValue()));// TODO Auto-generated method stub
				
			}
			
		});
        textArea.getDocument().addUndoableEditListener((UndoableEditListener) new UndoableEditListener() {
            @Override
            public void undoableEditHappened(UndoableEditEvent e) {
                undoManager.addEdit(e.getEdit());
            }
        });
		
		fontColorButton = new JButton("Color:");
		fontColorButton.addActionListener(this);
		String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		fontBox = new JComboBox(fonts);
		fontBox.addActionListener(this);
		fontBox.setSelectedItem("Arial");
		//------------------///
		menuBar = new JMenuBar();
		fileMenu=new JMenu("File");
		editMenu=new JMenu("Edit");
	
		saveItem= new JMenuItem("Save File");
		exitItem= new JMenuItem("EXIT");
	    rev =new JMenuItem("UNDO");
	    redo =new JMenuItem("REDO");
	    rev.addActionListener(this);
	redo.addActionListener(this);
		saveItem.addActionListener(this);
		exitItem.addActionListener(this);
		
		
		
		
		editMenu.add(rev);
		editMenu.add(redo);
		fileMenu.add(saveItem);
		fileMenu.add(exitItem);
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		
		//***************///
		
		this.setJMenuBar(menuBar);
		this.add(fontLabel);
		
		this.add(fontSizeSpinner);
		this.add(fontColorButton);
		this.add(fontBox);
		this.add(scrollPane);
		this.setVisible(true);
		
	}
public void actionPerformed(ActionEvent e) {
	// colour
	if(e.getSource()==fontColorButton) {
		
		JColorChooser colorChooser = new JColorChooser();
		Color color =colorChooser.showDialog(null, "pick a color", Color.black);
	    textArea.setForeground(color);
	}
	
	//font
	if(e.getSource()==fontBox) {
		textArea.setFont(new Font((String)fontBox.getSelectedItem(),Font.PLAIN, textArea.getFont().getSize()));
		
		
		
	}
	
	
	
	
	//save item
	if(e.getSource()==saveItem) {
		JFileChooser filechooser = new JFileChooser();
		filechooser.setCurrentDirectory(new File("."));
		int response = filechooser.showSaveDialog(null);
		if (response==JFileChooser.APPROVE_OPTION)
		{
			File file;
			PrintWriter fileOut=null;
		
			file = new File(filechooser.getSelectedFile().getAbsolutePath());
		    try {
				fileOut = new PrintWriter(file);
				fileOut.println(textArea.getText());
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		    finally {
		    	fileOut.close();
		    }
		    
		}
		
	}
	//exit
	if(e.getSource()==exitItem) {
		
		System.exit(0);
		
		
	}
	//undo
	if(e.getSource()==rev) {
		
		 try {
             if (undoManager.canUndo()) {
                 undoManager.undo();
             }
         } catch (CannotUndoException ex) {
             ex.printStackTrace();
         }
		
		
	}
	//redo
	
	if(e.getSource()==redo) {
		
		try {
            if (undoManager.canRedo()) {
                undoManager.redo();
            }
        } catch (CannotUndoException ex) {
            ex.printStackTrace();
        }
    }
		
		
	}
	
	

}

