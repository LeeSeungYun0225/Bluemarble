package Bluemarble;

import javax.swing.*;
import java.awt.*;

public class Main {

	public static void main(String[] args) {

		JFrame frame = new JFrame("BluMarble");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		BluePlay b = new BluePlay();
		frame.setPreferredSize(new Dimension(1400,1000));
		frame.setLocation(250, 30);
		frame.getContentPane().add(b);
		frame.pack();
		frame.setVisible(true);
		
	}

}