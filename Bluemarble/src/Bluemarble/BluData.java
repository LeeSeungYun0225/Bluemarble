package Bluemarble;

//import java.util.*;
import javax.swing.*;
import java.awt.*;

public class BluData // ���� ��Ͽ� ���� ���� 
{
	private String m_cityname ; // ������ �̸��� ���� 
	private int m_owner; // ������ �����ڰ� ��������  
	private int m_basePrice;//�⺻ ����� 
	private int m_prePrice;//���� ����� 
	private int m_level; // ������ ���� ��� 
	private JLabel m_ownerLabel; // �����ڸ� ��Ÿ���� �̹����� ���� �� 
	private ImageIcon m_ownerImage; // ������ ��Ÿ���� �̹����� ���� �̹��������� ��ü 
	private JLabel m_levelLabel; // ������ ����� ��Ÿ���� �̹����� ���� �� 
	private ImageIcon m_levelImage; // ������ ����� ��Ÿ���� �̹����� ���� �̹��������� ��ü 
	
	
	public BluData(String cityname, int owner, int price, int level) // ������, �ʱ�ȭ�ÿ� Ȥ��, ���� ���Ž�, ��� ���׷��̵� ����� ��Ȳ���� ȣ���Ѵ� . 
	{
		m_ownerLabel = new JLabel();
		m_levelLabel = new JLabel();
		m_cityname = cityname;
		m_owner = owner;
		m_basePrice = price;
		m_prePrice = price;
		m_level = level;
	}
	
	
	
	
	public void setOwnerImage(int player)
	{

		m_ownerImage = new ImageIcon("Owner"+player + ".jpg");

	}
	public void setLevelImage(int level)
	{
		m_levelImage = new ImageIcon("Lv" + level + ".png");
	}
	public void setLevelLabel()
	{
		m_levelLabel.setIcon(m_levelImage);
	}
	public ImageIcon getOwnerImage()
	{
		return m_ownerImage;
	}
	public ImageIcon getLevelImage()
	{
		return m_levelImage;
	}
	public JLabel getOwnerLabel()
	{
		return m_ownerLabel;
	}
	public JLabel getLevelLabel()
	{
		return m_levelLabel;
	}


	public String getCity()
	{
		return m_cityname;
	}
	public int getLevel()
	{
		return m_level;
	}
	public int getBasePrice()
	{
		return m_basePrice;
	}
	public int getPrePrice()
	{
		return m_prePrice;
	}
	public void setPrePrice(int preprice)
	{
		m_prePrice = preprice;
	}
	public void setOwner(int owner)
	{
		m_owner = owner;
	}
	public void setLevel(int level)
	{
		m_level = level;
	}
	
	public int getOwner()
	{
		return m_owner;
	}
}