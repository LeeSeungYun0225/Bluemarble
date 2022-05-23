package Bluemarble;

import javax.swing.*;


public class Player extends Dice //플레이어가 주사위를 굴리기 위한 객체 
{
	int m_x,m_y; // 유저가 위치한 블록을 의미 
	int m_pX,m_pY;
	int m_fin;
	private int m_playerNum; // 플레이어 번호 
	private int m_money; // 플레이어의 현재 자본 
	private JLabel m_character; // 플레이어의 캐릭터를 담을 라벨 

	private JLabel m_userInfor; // 유저 상태를 나타낼 라벨 
	private JLabel m_userMoney; // 유저 자본을 나타낼 라벨 
	private int m_blocked;  // 남은 휴식 턴을 저장 
	private JLabel m_blockedLabel; // 남은 휴식턴을 보여줄 라벨 
	private ImageIcon m_playerTurnImageIcon; // 어느 플레이어의 턴인지를 나타내기 위한 이미지 
	private JLabel m_playerTurnLabel; // 플레이어의 턴을 나타내기 위한 이미지를 담을 라벨 
	
	Player(int playerNumer) // 플레이어 초기 상태 설정 
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
		m_userMoney = new JLabel("잔액 : "+m_money);
		m_userMoney.setBounds(1150, 180+150*playerNumer,100,100);
		m_userInfor = new JLabel(new ImageIcon("Player" + playerNumer + ".gif"));
		m_userInfor.setBounds(1000, 180+150*playerNumer, BluConstants.CHARACTER_X,BluConstants.CHARACTER_Y);
		m_blockedLabel = new JLabel("남은 휴식턴 : " + m_blocked);
		m_blockedLabel.setBounds(1250,180+150*playerNumer,100,100);
		m_playerTurnImageIcon = new ImageIcon("mark.png");
		m_playerTurnLabel = new JLabel();
		m_playerTurnLabel.setIcon(m_playerTurnImageIcon);
		m_playerTurnLabel.setBounds(920,180+155*playerNumer,80,80);

	}
	public void move() // 이동 메소드 
	{
		if( m_pX < BluConstants.H_LEN-1 && m_pY == 0) // 맨윗줄일때 이동시 
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

		else if( m_pX == BluConstants.H_LEN-1 && m_pY < BluConstants.V_LEN-1) //오른쪽줄
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
				m_fin= 1; //한바퀴돔
				m_x = BluConstants.H_LEN-1;
				System.out.printf("x = %d\n",m_x); 
				m_y = 0 - (BluConstants.V_LEN-1 +(m_pY-getResultValue()));
				System.out.printf("y = %d\n",m_y);
			}
			else if( m_pY - getResultValue() <= 0)
			{

				m_x = getResultValue()- m_pY; 
				m_y = 0; 
				m_fin= 1; //한바퀴돔
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
	
	public void m_moveBlock() // 캐릭터 이동을 위한 메소드 
	{
		if(m_x == 0) // 유저가 왼쪽 직선칸에 위치했을 시에 
		{
			if(m_y == 0) // 유저가 왼쪽 직선칸 & 
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
	public boolean CheckDouble() // 더블인지 체크하는 메소드 
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
		roll(); // Dice 클래스의 메소드 
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