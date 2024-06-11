package presentation;
import javax.swing.*;
import java.awt.*;

public interface Tools {
	    default public JPanel headerPanel(){
	        JPanel headerPanel = new JPanel();
	        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));  
	        headerPanel.setBackground(Color.CYAN);
	        JLabel titleLabel = new JLabel("To Do List");
	        ImageIcon scaledIcon = rescaledIcon("src/main/java/presentation/images/tdl.png",25,25);
	        JLabel iconLabel = new JLabel();
	        iconLabel.setIcon(scaledIcon); 
	        headerPanel.add(iconLabel); 
	        headerPanel.add(titleLabel);
	        return headerPanel;
	    }
	   default	public JPanel footerPanel(){
	        JPanel footerPanel = new JPanel(new FlowLayout());
	        JLabel copyright = new JLabel("© 2024 TDL. All rights reserved.");
	        footerPanel.add(copyright);
	        footerPanel.setBackground(Color.CYAN);
	        return footerPanel;
	   } 
	   default public JButton button(String label) {
		  JButton button = new JButton();
		  button.setText(label);
		   return button;
	   }
	   default public ImageIcon rescaledIcon(String path,int w,int h){
			ImageIcon icon = new ImageIcon(path);
			Image img = icon.getImage();
	        Image newImg = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
	        ImageIcon scaledIcon = new ImageIcon(newImg);
			return scaledIcon;
	   }
}
	
	

