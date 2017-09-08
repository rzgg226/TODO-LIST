import javax.swing.JPanel;

public class Task {
	private String name;
	private String time;
	Boolean isSelected;
	JPanel taskPanel;
	
	public Task(String name){
		this.name = name; 
		this.isSelected = false;
	}
	
	public String getString(){
		return this.name;
	}
	
	public String getTime(){
		return this.time;
	}
	
	public void setTime(String time){
		this.time = time;
	}
}
