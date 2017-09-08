import java.awt.Checkbox;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TodoList {

	private static ArrayList<JCheckBox> boxList;
	static JPanel panel;
	
	public static void main(String[] args) {
		final LinkedList<Task> todoList = new LinkedList<Task>();
		boxList  = new ArrayList<JCheckBox>();
		todoList.add(new Task ("T1"));
		todoList.add(new Task ("T2"));

		todoList.add(new Task ("T3"));
		JFrame frame = new JFrame("TODO List");
		frame.setSize(800, 600);
		frame.setVisible(true);
		panel = new JPanel();
		panel.setLayout(new GridLayout(0,1));
		frame.add(panel);
		JButton addTask = new JButton("Add A New Task");
		JButton removeTask = new JButton("Remove A Task");
		JButton outTask = new JButton("Make a outputfile");
		addTask.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				final JFrame newTaskFrame = new JFrame("New Task");
				newTaskFrame.setSize(800, 600);
				newTaskFrame.setLayout(new GridLayout(0, 1));
				final TextField textField = new TextField(20);
				newTaskFrame.add(textField);
				JButton enterTextButton = new JButton("Enter");
				newTaskFrame.add(enterTextButton);
				enterTextButton.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {

						final String newText = textField.getText();
						Task curTask = new Task(newText);
						todoList.add(curTask);
						TodoList.addNewTaskPanel(curTask);

						newTaskFrame.setVisible(false);
						System.out.print(newText);
					}

					
					
				});
				newTaskFrame.setVisible(true);				
			}
			
		});

		removeTask.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Iterator<Task> listIter = todoList.iterator();
				while(listIter.hasNext()){
					Task temp = (Task)listIter.next();
					if(temp.isSelected){
						todoList.remove(temp);
						panel.remove(temp.taskPanel);
					}
				}
			}
			
		});
		outTask.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				File outFile = new File ("output.txt");
			    FileWriter fWriter = null;
				try {
					fWriter = new FileWriter (outFile);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			    PrintWriter pWriter = new PrintWriter (fWriter);
			    Iterator taskIter = todoList.iterator();
				while(taskIter.hasNext()){
					Task temp = (Task)taskIter.next();
					String out = temp.getString() + "|" + temp.getTime();//this chunk of code about output file is from 
																		 //"https://www.cs.utexas.edu/~mitra/csSummer2015/cs312/lectures/fileIO.html"
					pWriter.println (out);
				}
				pWriter.close();
				
				
				
			}
			
		});
		panel.add(addTask);
		panel.add(removeTask);
		panel.add(outTask);
		Iterator todoIter = todoList.iterator();
		while(todoIter.hasNext()){
			final Task cur = (Task) todoIter.next();
			addNewTaskPanel(cur);
			
		}
	}

	protected static void addNewTaskPanel(Task curTask) {
		JPanel taskPanel = new JPanel();
		curTask.taskPanel = taskPanel;
		final JTextPane timeText = new JTextPane();
		final Task cur = curTask;
		String curStr = cur.getString();
		final JCheckBox box = new JCheckBox(curStr);
		boxList.add(box);
		//box.setSize(1, 2);
		taskPanel.add(box);
		taskPanel.add(timeText);
		panel.add(taskPanel);
		timeText.setText("0");
		final Timer timer = new Timer(1000, new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String timeVal = String.valueOf((Integer.valueOf(timeText.getText()) + 1));
				timeText.setText(timeVal);
				cur.setTime(timeVal);
				
			}
			
		});
		box.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				if(box.isSelected()){
					cur.isSelected = true;
					TodoList.checkCheckBox(box);
					timer.start();
				} else {
					timer.stop();
					cur.isSelected = false;
				}
				
			}
			
		});
		//timer.start();
		
	}

	protected static void checkCheckBox(JCheckBox box) {
		for(JCheckBox i : boxList){
			if(i != box){
				i.setSelected(false);
			}
		}
		
	}

}
