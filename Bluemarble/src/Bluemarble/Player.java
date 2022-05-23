package Bluemarble;

import javax.swing.*;


public class Player extends Dice //�÷��̾ �ֻ����� ������ ���� ��ü 
{
	int m_x,m_y; // ������ ��ġ�� ����� �ǹ� 
	int m_pX,m_pY;
	int m_fin;
	private int m_playerNum; // �÷��̾� ��ȣ 
	private int m_money; // �÷��̾��� ���� �ں� 
	private JLabel m_character; // �÷��̾��� ĳ���͸� ���� �� 

	private JLabel m_userInfor; // ���� ���¸� ��Ÿ�� �� 
	private JLabel m_userMoney; // ���� �ں��� ��Ÿ�� �� 
	private int m_blocked;  // ���� �޽� ���� ���� 
	private JLabel m_blockedLabel; // ���� �޽����� ������ �� 
	private ImageIcon m_playerTurnImageIcon; // ��� �÷��̾��� �������� ��Ÿ���� ���� �̹��� 
	private JLabel m_playerTurnLabel; // �÷��̾��� ���� ��Ÿ���� ���� �̹����� ���� �� 
	
	Player(int playerNumer) // �÷��̾� �ʱ� ���� ���� 
	{
		m_blocked = 0;


		m_x=0;
		m_y=0;
		m_pX=0;
		m_pY=0;
		m_fin=0;
		m_money = BluConstants.INITMONEY; 
		m_playerNum = playerNumer; 
		m_character = new JLabel(new ImageIcon("Player" + playerNumer + ".gif"));
		m_character.setBounds(BluConstants.MOVESIZE/2,BluConstants.MOVESIZE/2,BluConstants.CHARACTER_X,BluConstants.CHARACTER_Y);
		m_userMoney = new JLabel("�ܾ� : "+m_money);
		m_userMoney.setBounds(1150, 180+150*playerNumer,100,100);
		m_userInfor = new JLabel(new ImageIcon("Player" + playerNumer + ".gif"));
		m_userInfor.setBounds(1000, 180+150*playerNumer, BluConstants.CHARACTER_X,BluConstants.CHARACTER_Y);
		m_blockedLabel = new JLabel("���� �޽��� : " + m_blocked);
		m_blockedLabel.setBounds(1250,180+150*playerNumer,100,100);
		m_playerTurnImageIcon = new ImageIcon("mark.png");
		m_playerTurnLabel = new JLabel();
		m_playerTurnLabel.setIcon(m_playerTurnImageIcon);
		m_playerTurnLabel.setBounds(920,180+155*playerNumer,80,80);

	}
	public void move() // �̵� �޼ҵ� 
	{
		if( m_pX < BluConstants.H_LEN-1 && m_pY == 0) // �������϶� �̵��� 
		{
		
			if((m_pX+1)+getResultValue() >= BluConstants.V_LEN+BluConstants.H_LEN)
			{
				m_x = BluConstants.H_LEN-1 -((m_pX + getResultValue()) -((BluConstants.H_LEN-1) + (BluConstants.V_LEN-1)));
				m_y = BluConstants.V_LEN-1;	
			}
			else if( (m_pX+1) + getResultValue() > BluConstants.H_LEN)
			{
				m_x = BluConstants.H_LEN-1;    
				m_y += ( (m_pX+1) + getResultValue()) - BluConstants.H_LEN; 
			}
			else
			{
				m_x = m_pX + getResultValue(); 
				m_y = 0; 
			}
			
		}

		else if( m_pX == BluConstants.H_LEN-1 && m_pY < BluConstants.V_LEN-1) //��������
		{
			
			if( (m_pY+1) + getResultValue() >= BluConstants.V_LEN + BluConstants.H_LEN)
			{
				
				m_x= 0;
				m_y= BluConstants.V_LEN-1 -((m_pY + getResultValue()) -((BluConstants.H_LEN-1) + (BluConstants.V_LEN-1)));
			}
			else if( (m_pY+1) + getResultValue() > BluConstants.V_LEN)
			{
				m_x -= ( (m_pY+1) + getResultValue()) - BluConstants.V_LEN ; 
				m_y = BluConstants.V_LEN-1; 
			}
			else
			{

				m_x = BluConstants.H_LEN-1; 
				m_y = m_pY + getResultValue(); 
			}
		}

		else if( m_pX >0 && m_pY == BluConstants.V_LEN-1)
		{
			
			if(m_pX-getResultValue() <= 0-BluConstants.V_LEN)
			{
				m_x = 0-(BluConstants.H_LEN-1 +(m_pX - getResultValue()));
				m_y = 0;

			}
			else if( m_pX - getResultValue() < 0)
			{
				m_x = 0; 
				m_y -= getResultValue() - (m_pX); 
			}
			else
			{
				m_x = m_pX - getResultValue(); 
				m_y = BluConstants.V_LEN-1; 
			}
		}

		else if( m_pX == 0 && m_pY > 0)
		{
			if(m_pY-getResultValue() <= 0-BluConstants.H_LEN)
			{
				m_fin= 1; //�ѹ�����
				m_x = BluConstants.H_LEN-1;
				System.out.printf("x = %d\n",m_x); 
				m_y = 0 - (BluConstants.V_LEN-1 +(m_pY-getResultValue()));
				System.out.printf("y = %d\n",m_y);
			}
			else if( m_pY - getResultValue() <= 0)
			{

				m_x = getResultValue()- m_pY; 
				m_y = 0; 
				m_fin= 1; //�ѹ�����
			}
			else
			{

				m_x = 0; 
				m_y = m_pY - getResultValue(); 
				
			}
		}

		m_pX = m_x;
		m_pY = m_y;
	}
	
	public void m_moveBlock() // ĳ���� �̵��� ���� �޼ҵ� 
	{
		if(m_x == 0) // ������ ���� ����ĭ�� ��ġ���� �ÿ� 
		{
			if(m_y == 0) // ������ ���� ����ĭ & 
			{
				m_character.setLocation(BluConstants.MOVESIZE/2,BluConstants.MOVESIZE/2);
			}
			else if(m_y == BluConstants.V_LEN-1)
			{
				m_character.setLocation(BluConstants.MOVESIZE/2, BluConstants.MOVESIZE*(2+m_y));
			}
			else 
			{
				m_character.setLocation(BluConstants.MOVESIZE/2, BluConstants.MOVESIZE*(1+m_y));
			}
		}
		else if(m_x == BluConstants.H_LEN - 1)
		{
			if(m_y == 0)
			{
				m_character.setLocation(BluConstants.MOVESIZE*10-BluConstants.MOVESIZE/2, BluConstants.MOVESIZE/2);
			}
			else if(m_y == BluConstants.V_LEN -1)
			{
				m_character.setLocation(BluConstants.MOVESIZE*10-BluConstants.MOVESIZE/2, BluConstants.MOVESIZE*(2+m_y));
			}
			else 
			{
				m_character.setLocation(BluConstants.MOVESIZE*10-BluConstants.MOVESIZE/2, BluConstants.MOVESIZE*(1+m_y));
			}
		}
		else if(m_y == 0)
		{
			m_character.setLocation(BluConstants.MOVESIZE*(1+m_x), BluConstants.MOVESIZE/2);
		}
		else if(m_y == BluConstants.V_LEN- 1)
		{
			m_character.setLocation(BluConstants.MOVESIZE*(1+m_x), BluConstants.MOVESIZE*(2+m_y));
		}

	}
	public boolean CheckDouble() // �������� üũ�ϴ� �޼ҵ� 
	{
		if(this.getDice1() == this.getDice2())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void plusMoney(int money)
	{
		m_money+=money;
	}
	public void minusMoney(int mon)
	{
		m_money -= mon;
	}
	
	public void m_roll()
	{
		roll(); // Dice Ŭ������ �޼ҵ� 
	}
	
	///////////get set Methods ... ////////// 
	
	public JLabel getPlayerTurnLabel()
	{
	    return m_playerTurnLabel;
	}
	public JLabel getBlockedLabel()
	{
		return m_blockedLabel;
	}
	
	public int getBlocked()
	{
		return m_blocked;
	}
	public void setBlocked(int x)
	{
		m_blocked = x;
	}
	public JLabel getUserMoney()
	{
		return m_userMoney;
	}
	public JLabel getUserInfor()
	{
		return m_userInfor;
	}
	public int getFin()
	{
		return m_fin;
	}
	public void setFin(int x)
	{
		m_fin = x;
	}
	public JLabel getCharacter()
	{
		return m_character;
	}

	
	public double getMoney()
	{
		return m_money;
	}
	public void setMoney(int mon)
	{
		m_money = mon;
	}

	public int getPlayerNum()
	{
		return m_playerNum;
	}
	
	
	
}