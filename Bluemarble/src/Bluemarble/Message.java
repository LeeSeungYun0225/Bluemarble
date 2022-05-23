package Bluemarble;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Message extends JPanel// 도시에 도착할 때 마다 도시에 대한 정보를 출력하기 위한 클래스 
{
JPanel MPanel; 
JLabel MLabel1,MLabel2,MLabel3;
private ActionListener btn;
private JButton answerbtn;



	Message() 
	{
	setLayout(null);
	//setUndecorated(true);
	setPreferredSize(new Dimension(450,280));
	setLocation(1180, 62);
	
	MPanel= new JPanel();
	MPanel.setLayout(null);
	MPanel.setBounds(0,0,450, 280);
	MPanel.setBackground(Color.BLACK);

	//getContentPane().add(MPanel);
	add(MPanel);
	//setAlwaysOnTop(true);
	//pack();
	setVisible(false);
	btn = new ActionListener()
	{
		public void actionPerformed(ActionEvent event)
		{
			if(event.getSource() == answerbtn)
			{
				setVisible(false);
				MPanel.removeAll();
			}
		}
	};
	

	}
void Display(String mes1)
{
	close();
		MLabel1 = new JLabel(mes1);
		MLabel1.setBounds(100,-50,500, 200);
		MLabel1.setFont(new Font("돋움",Font.BOLD, 16)); 
		MLabel1.setForeground(Color.WHITE);
		answerbtn = new JButton(new ImageIcon("ok.png"));
		answerbtn.setBounds(150,150 , 100, 50);
		answerbtn.addActionListener(btn);
		MPanel.add(answerbtn);
		MPanel.add(MLabel1);
		setVisible(true);

}
void Display(String mes1,String mes2)
{
	close();
		MLabel1 = new JLabel(mes1);
		MLabel1.setBounds(100,-50,500, 200);
		MLabel1.setFont(new Font("돋움",Font.BOLD, 16)); 
		MLabel1.setForeground(Color.WHITE);
		MLabel2 = new JLabel(mes2);
		MLabel2.setBounds(100,0,500, 200);
		MLabel2.setFont(new Font("돋움",Font.BOLD, 16));
		MLabel2.setForeground(Color.WHITE);
		answerbtn = new JButton(new ImageIcon("ok.png"));
		answerbtn.setBounds(150,150 , 100, 50);
		answerbtn.addActionListener(btn);
		MPanel.add(answerbtn);
		MPanel.add(MLabel1);
		MPanel.add(MLabel2);
		setVisible(true);

}
void Display(String mes1,String mes2, String mes3)
{
	close();
	MLabel1 = new JLabel(mes1);
	MLabel1.setBounds(100,-50,500, 200);
	MLabel1.setFont(new Font("돋움",Font.BOLD, 16)); 
	MLabel1.setForeground(Color.WHITE);
	MLabel2 = new JLabel(mes2);
	MLabel2.setBounds(100,0,500, 200);
	MLabel2.setFont(new Font("돋움",Font.BOLD, 16));
	MLabel2.setForeground(Color.WHITE);
	MLabel3 = new JLabel(mes3);
	MLabel3.setBounds(100,50,500, 200);
	MLabel3.setFont(new Font("돋움",Font.BOLD, 16)); 
	MLabel3.setForeground(Color.WHITE);
	answerbtn = new JButton(new ImageIcon("ok.png"));
	answerbtn.setBounds(150,200 , 100, 50);
	answerbtn.addActionListener(btn);
	MPanel.add(answerbtn);
	MPanel.add(MLabel1);
	MPanel.add(MLabel2);
	MPanel.add(MLabel3);
	setVisible(true);

}
void close()
{
	setVisible(false);
	MPanel.removeAll();
}


}