package Bluemarble;

//import java.util.*;
import javax.swing.*;
import java.awt.*;

public class BluData // 맵의 블록에 대한 정의 
{
	private String m_cityname ; // 도시의 이름을 정의 
	private int m_owner; // 도시의 소유자가 누구인지  
	private int m_basePrice;//기본 통행료 
	private int m_prePrice;//현재 통행료 
	private int m_level; // 도시의 현재 등급 
	private JLabel m_ownerLabel; // 소유자를 나타내는 이미지를 담을 라벨 
	private ImageIcon m_ownerImage; // 소유자 나타내는 이미지를 담을 이미지아이콘 객체 
	private JLabel m_levelLabel; // 도시의 등급을 나타내는 이미지를 담을 라벨 
	private ImageIcon m_levelImage; // 도시의 등급을 나타내는 이미지를 담을 이미지아이콘 객체 
	
	
	public BluData(String cityname, int owner, int price, int level) // 생성자, 초기화시에 혹은, 도시 구매시, 등급 업그레이드 등등의 상황에서 호출한다 . 
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