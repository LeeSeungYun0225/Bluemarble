package Bluemarble;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.*;
import java.lang.Runnable;

public class BluePlay extends JPanel
{
	public BluData[][] m_board; // 보드의 칸 수 
	public Player m_playerA; // 플레이어 a 
	public Player m_playerB; // 플레이어 b 객체 
	private JButton m_diceButton;// 주사위 굴리기 버튼 
	private ActionListener m_actionListener; // 버튼에대한 처리 이벤트 
	int m_turn; //누구의 턴인지 체크하기 위한 변수 
	private int m_arriveOthers; // 다른 사람의 땅인지 확인하기 위함 
	public JButton m_buyButton,m_nonBuyButton;//구매/구매하지않음 선택 버튼 
	private JButton m_level1Button,m_level2Button,m_level3Button,m_cancelButton;  // 땅을 업그레이드 할지 취소할지 선택하기 위한ㅂ ㅓ튼 
	private int m_check;
	private JLabel m_double; // 더블일 경우 이미지 출력을 위함 
	private JLabel m_win; // 승리시 이미지 출력 
	private JLabel m_map; // 맵을 나타내기 위한 라벨 
	private JLabel m_dice1; // 주사위 1번을 보여주는 라벨 
	private JLabel m_dice2; // 주사위 2번을 보여주는 라벨 
	private JLabel m_startPass; // 출발지를 지났을때 보여줄 이미지를 담을 라벨 
	private JLabel m_worldTour; // 세계여행 이미지를 보여줄 라벨 
	private JButton m_worldTourButton; // 세계여행 버튼 
	private int m_stpCount; 
	private JLabel m_isle;  // 무인도 도착시 이미지 출력 라벨 
	private JButton m_isleButton;  // 무인도 버튼 
	private JLabel m_festival; // 축제 도착시 이미지 출력 라벨 
	private JButton m_festivalButton; 
	private JButton m_bonusButton;
	private JLabel m_bonusLabel1,m_bonusLabel2;
	private Message m_message;
	
	public BluePlay()
	{		
		m_actionListener = new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				//m.close();
				if(event.getSource() == m_diceButton) // 주사위 굴릴시에 
				{
					m_message.setVisible(false);
					m_double.setVisible(false);
					play();
					if(m_turn == BluConstants.PLAYER1)
					{
						m_dice1.setIcon(new ImageIcon(m_playerA.getDice1() +".gif")); // 주사위 이미지 출력 
						m_dice2.setIcon(new ImageIcon(m_playerA.getDice2()+".gif"));
					}
					else if(m_turn == BluConstants.PLAYER2)
					{
						m_dice1.setIcon(new ImageIcon(m_playerB.getDice1() +".gif"));
						m_dice2.setIcon(new ImageIcon(m_playerB.getDice2()+".gif"));
					}
					m_dice1.setVisible(true);
					m_dice2.setVisible(true);
					if(m_stpCount == 2)
					{
						m_stpCount--;
					}
					else if(m_stpCount == 1)
					{
						m_startPass.setVisible(false);
					}
					
				}
				else if(event.getSource() == m_buyButton) // 구매버튼 누르면 
				{
					
					if(m_turn == BluConstants.PLAYER1 && m_playerA.getMoney() > m_board[m_playerA.m_x][m_playerA.m_y].getBasePrice()) // 유저의 돈을 체크하고 구매시행 
					{
						m_board[m_playerA.m_x][m_playerA.m_y].setOwner(m_playerA.getPlayerNum()); // 유저 1의 땅으로 변경 
						m_playerA.minusMoney((int)(Math.round(m_board[m_playerA.m_x][m_playerA.m_y].getBasePrice()))); // 유저의 돈 차감 
						m_board[m_playerA.m_x][m_playerA.m_y].setOwnerImage(m_turn); // 유저의 땅이 되어 이미지를 변경해줌 
						m_board[m_playerA.m_x][m_playerA.m_y].getOwnerLabel().setIcon(m_board[m_playerA.m_x][m_playerA.m_y].getOwnerImage());
						m_playerA.getUserMoney().setText("잔액 : " + (int)m_playerA.getMoney());//유저의 돈을 보여주는 라벨에 값을 갱신 
						if(!m_playerA.CheckDouble()) // 더블이 아니면 턴을 넘김 
						{					
							turnToPlayerB();
						}					
					}
					else if(m_playerA.getMoney() < m_board[m_playerA.m_x][m_playerA.m_y].getBasePrice()) // 돈 부족시 
					{
						m_message.Display("돈이 부족해서 구매할 수 없습니다.");
					}
					else if(m_turn == BluConstants.PLAYER2 && m_playerB.getMoney() > m_board[m_playerB.m_x][m_playerB.m_y].getBasePrice())
					{
						m_board[m_playerB.m_x][m_playerB.m_y].setOwner(m_playerB.getPlayerNum());
						m_playerB.minusMoney((int)(Math.round(m_board[m_playerB.m_x][m_playerB.m_y].getBasePrice())));
						m_board[m_playerB.m_x][m_playerB.m_y].setOwnerImage(m_turn);
						m_board[m_playerB.m_x][m_playerB.m_y].getOwnerLabel().setIcon(m_board[m_playerB.m_x][m_playerB.m_y].getOwnerImage());
						m_playerB.getUserMoney().setText("잔액 : " + (int)m_playerB.getMoney());
						if(!m_playerB.CheckDouble())
						{
							turnToPlayerA();
						}
					}
					else if(m_playerB.getMoney() < m_board[m_playerB.m_x][m_playerB.m_y].getBasePrice())
					{
						
						m_message.Display("돈이 부족해서 구매할 수 없습니다.");
					}
					
					endBuyChoice();
					if(m_stpCount == 2)
					{
						m_stpCount--;
					}
					else if(m_stpCount == 1)
					{
						m_startPass.setVisible(false);
					}
				}
				else if(event.getSource() == m_nonBuyButton) // 구매안한다 누를시 
				{
					endBuyChoice();
					if(m_turn == BluConstants.PLAYER1 && !m_playerA.CheckDouble())
					{
						turnToPlayerB();
					}
					else if(m_turn == BluConstants.PLAYER2 && !m_playerB.CheckDouble() )
					{
						turnToPlayerA();
					}
					if(m_stpCount == 2)
					{
						m_stpCount--;
					}
					else if(m_stpCount == 1)
					{
						m_startPass.setVisible(false);
					}
				}
				else if(event.getSource() == m_level1Button) // 레벨1 업그레이드 버튼 누를시에 , 
				{
					if(m_turn == BluConstants.PLAYER1)
					{
						if(m_playerA.getMoney() > m_board[m_playerA.m_x][m_playerA.m_y].getBasePrice()*BluConstants.LV1FEE)//업그레이드 비용보다 돈이 많은지 체크 
						{
							m_board[m_playerA.m_x][m_playerA.m_y].setLevel(BluConstants.LEVEL1); // 블록의 레벨을 1로 설정 
							m_playerA.minusMoney((int)(Math.round(m_board[m_playerA.m_x][m_playerA.m_y].getBasePrice()*BluConstants.LV1FEE))); // 돈을 지불 
							m_board[m_playerA.m_x][m_playerA.m_y].setPrePrice(m_board[m_playerA.m_x][m_playerA.m_y].getBasePrice()*BluConstants.LV1RATE);
							m_board[m_playerA.m_x][m_playerA.m_y].setLevelImage(BluConstants.LEVEL1);
							m_board[m_playerA.m_x][m_playerA.m_y].getLevelLabel().setIcon(m_board[m_playerA.m_x][m_playerA.m_y].getLevelImage());
							m_playerA.getUserMoney().setText("잔액 : " + (int)m_playerA.getMoney());
						}
						else 
						{
							
							m_message.Display("돈이 부족해서 업그레이드를 할 수 없습니다.");
						}
						if(!m_playerA.CheckDouble())
						{
							turnToPlayerB();
						}
					}
				
					else if(m_turn == BluConstants.PLAYER2 )
					{
						if(m_playerB.getMoney() > m_board[m_playerB.m_x][m_playerB.m_y].getBasePrice()*BluConstants.LV1FEE)//업그레이드 비용보다 돈이 많은지 체크 
						{
							m_board[m_playerB.m_x][m_playerB.m_y].setLevel(BluConstants.LEVEL1);
							m_playerB.minusMoney((int)(Math.round(m_board[m_playerB.m_x][m_playerB.m_y].getBasePrice()*BluConstants.LV1FEE)));
							m_board[m_playerB.m_x][m_playerB.m_y].setPrePrice(m_board[m_playerB.m_x][m_playerB.m_y].getBasePrice()*BluConstants.LV1RATE);
							m_board[m_playerB.m_x][m_playerB.m_y].setLevelImage(BluConstants.LEVEL1);
							m_board[m_playerB.m_x][m_playerB.m_y].getLevelLabel().setIcon(m_board[m_playerB.m_x][m_playerB.m_y].getLevelImage());
							m_playerB.getUserMoney().setText("잔액 : " + (int)m_playerB.getMoney());
						}
						else 
						{
							m_message.Display("돈이 부족해서 업그레이드를 할 수 없습니다.");
						}
						if(!m_playerB.CheckDouble())
						{
							turnToPlayerA();
						}
						
					
						
					}
					endLevelUpChoice();
					if(m_stpCount == 2)
					{
						m_stpCount--;
					}
					else if(m_stpCount == 1)
					{
						m_startPass.setVisible(false);
					}
				}
				
				else if(event.getSource() == m_level2Button) // 2레벨로 업그레이드 할 시에,. 
				{
					if(m_turn == BluConstants.PLAYER1)
					{
						// 0레벨 도시를 2레벨로 업그레이드 할 때 
						if(m_board[m_playerA.m_x][m_playerA.m_y].getLevel() == BluConstants.LEVEL0 && m_board[m_playerA.m_x][m_playerA.m_y].getBasePrice()*BluConstants.LV2FEE < m_playerA.getMoney())
						{
							m_playerA.minusMoney((int)(Math.round(m_board[m_playerA.m_x][m_playerA.m_y].getBasePrice()*BluConstants.LV2FEE)));
							m_board[m_playerA.m_x][m_playerA.m_y].setLevel(BluConstants.LEVEL2);
							m_board[m_playerA.m_x][m_playerA.m_y].setPrePrice(m_board[m_playerA.m_x][m_playerA.m_y].getBasePrice()*BluConstants.LV2RATE);
							m_board[m_playerA.m_x][m_playerA.m_y].setLevelImage(BluConstants.LEVEL2);
							m_board[m_playerA.m_x][m_playerA.m_y].getLevelLabel().setIcon(m_board[m_playerA.m_x][m_playerA.m_y].getLevelImage());
							m_playerA.getUserMoney().setText("잔액 : " + (int)m_playerA.getMoney());
						}
						//1레벨 도시를 2레벨로 업그레이드 할 때 
						else if(m_board[m_playerA.m_x][m_playerA.m_y].getLevel() == BluConstants.LEVEL1 && m_board[m_playerA.m_x][m_playerA.m_y].getBasePrice()*(BluConstants.LV2FEE-BluConstants.LV1FEE) < m_playerA.getMoney())
						{
							m_playerA.minusMoney((int)(Math.round(m_board[m_playerA.m_x][m_playerA.m_y].getBasePrice()*(BluConstants.LV2FEE-BluConstants.LV1FEE))));
							m_board[m_playerA.m_x][m_playerA.m_y].setLevel(BluConstants.LEVEL2);		
							m_board[m_playerA.m_x][m_playerA.m_y].setPrePrice(m_board[m_playerA.m_x][m_playerA.m_y].getBasePrice()*BluConstants.LV2RATE);
							m_board[m_playerA.m_x][m_playerA.m_y].setLevelImage(BluConstants.LEVEL2);
							m_board[m_playerA.m_x][m_playerA.m_y].getLevelLabel().setIcon(m_board[m_playerA.m_x][m_playerA.m_y].getLevelImage());
							m_playerA.getUserMoney().setText("잔액 : " + (int)m_playerA.getMoney());
						}
						else
						{
							
							m_message.Display("돈이 부족해서 업그레이드를 할 수 없습니다.");
						}
						if(!m_playerA.CheckDouble())
						{
							turnToPlayerB();

						}
					}
					else if(m_turn == BluConstants.PLAYER2 )
					{
						if(m_board[m_playerB.m_x][m_playerB.m_y].getLevel() == BluConstants.LEVEL0 && m_board[m_playerB.m_x][m_playerB.m_y].getBasePrice()*BluConstants.LV2FEE < m_playerB.getMoney())
						{
							m_playerB.minusMoney((int)(Math.round(m_board[m_playerB.m_x][m_playerB.m_y].getBasePrice()*BluConstants.LV2FEE)));
							m_board[m_playerB.m_x][m_playerB.m_y].setLevel(BluConstants.LEVEL2);
							m_board[m_playerB.m_x][m_playerB.m_y].setPrePrice(m_board[m_playerB.m_x][m_playerB.m_y].getBasePrice()*BluConstants.LV2RATE);
							m_board[m_playerB.m_x][m_playerB.m_y].setLevelImage(BluConstants.LEVEL2);
							m_board[m_playerB.m_x][m_playerB.m_y].getLevelLabel().setIcon(m_board[m_playerB.m_x][m_playerB.m_y].getLevelImage());
							m_playerB.getUserMoney().setText("잔액 : " + (int)m_playerB.getMoney());
						}
						else if(m_board[m_playerB.m_x][m_playerB.m_y].getLevel() == BluConstants.LEVEL1 && m_board[m_playerB.m_x][m_playerB.m_y].getBasePrice()*(BluConstants.LV2FEE-BluConstants.LV1FEE) < m_playerB.getMoney())
						{
							m_playerB.minusMoney((int)(Math.round(m_board[m_playerB.m_x][m_playerB.m_y].getBasePrice()*(BluConstants.LV2FEE-BluConstants.LV1FEE))));
							m_board[m_playerB.m_x][m_playerB.m_y].setLevel(BluConstants.LEVEL2);
							m_board[m_playerB.m_x][m_playerB.m_y].setPrePrice(m_board[m_playerB.m_x][m_playerB.m_y].getBasePrice()*BluConstants.LV2RATE);
							m_board[m_playerB.m_x][m_playerB.m_y].setLevelImage(BluConstants.LEVEL2);
							m_board[m_playerB.m_x][m_playerB.m_y].getLevelLabel().setIcon(m_board[m_playerB.m_x][m_playerB.m_y].getLevelImage());
							m_playerB.getUserMoney().setText("잔액 : " + (int)m_playerB.getMoney());
						}
						else 
						{
						
							m_message.Display("돈이 부족해서 업그레이드를 할 수 없습니다.");
						}
						if(!m_playerB.CheckDouble())
						{
							turnToPlayerA();
						}
					}
					endLevelUpChoice();
					if(m_stpCount == 2)
					{
						m_stpCount--;
					}
					else if(m_stpCount == 1)
					{
						m_startPass.setVisible(false);
					}
				}
				else if(event.getSource() == m_level3Button)
				{
					if(m_turn == BluConstants.PLAYER1)//1P
					{
						if(m_board[m_playerA.m_x][m_playerA.m_y].getLevel() == BluConstants.LEVEL0 && m_board[m_playerA.m_x][m_playerA.m_y].getBasePrice()*BluConstants.LV3FEE < m_playerA.getMoney())
						{
							m_playerA.minusMoney((int)(Math.round(m_board[m_playerA.m_x][m_playerA.m_y].getBasePrice()*BluConstants.LV3FEE)));
							m_board[m_playerA.m_x][m_playerA.m_y].setLevel(BluConstants.LEVEL3);
							m_board[m_playerA.m_x][m_playerA.m_y].setPrePrice(m_board[m_playerA.m_x][m_playerA.m_y].getBasePrice()*BluConstants.LV3RATE);
							m_board[m_playerA.m_x][m_playerA.m_y].setLevelImage(3);
							m_board[m_playerA.m_x][m_playerA.m_y].setLevelLabel();
							m_playerA.getUserMoney().setText("잔액 : " + (int)m_playerA.getMoney());
						}
						else if(m_board[m_playerA.m_x][m_playerA.m_y].getLevel() == BluConstants.LEVEL1 && m_board[m_playerA.m_x][m_playerA.m_y].getBasePrice()*(BluConstants.LV3FEE-BluConstants.LV1FEE) < m_playerA.getMoney())
						{
							m_playerA.minusMoney((int)(Math.round(m_board[m_playerA.m_x][m_playerA.m_y].getBasePrice()*(BluConstants.LV3FEE-BluConstants.LV1FEE))));
							m_board[m_playerA.m_x][m_playerA.m_y].setLevel(BluConstants.LEVEL3);
							m_board[m_playerA.m_x][m_playerA.m_y].setPrePrice(m_board[m_playerA.m_x][m_playerA.m_y].getBasePrice()*BluConstants.LV3RATE);
							m_board[m_playerA.m_x][m_playerA.m_y].setLevelImage(3);
							m_board[m_playerA.m_x][m_playerA.m_y].setLevelLabel();
							m_playerA.getUserMoney().setText("잔액 : " + (int)m_playerA.getMoney());
						}
						else if(m_board[m_playerA.m_x][m_playerA.m_y].getLevel() == BluConstants.LEVEL2 && m_board[m_playerA.m_x][m_playerA.m_y].getBasePrice()*(BluConstants.LV3FEE-BluConstants.LV2FEE) < m_playerA.getMoney())
						{
							m_playerA.minusMoney((int)(Math.round(m_board[m_playerA.m_x][m_playerA.m_y].getBasePrice()*(BluConstants.LV3FEE-BluConstants.LV2FEE))));
							m_board[m_playerA.m_x][m_playerA.m_y].setLevel(BluConstants.LEVEL3);
							m_board[m_playerA.m_x][m_playerA.m_y].setPrePrice(m_board[m_playerA.m_x][m_playerA.m_y].getBasePrice()*BluConstants.LV3RATE);
							m_board[m_playerA.m_x][m_playerA.m_y].setLevelImage(3);
							m_board[m_playerA.m_x][m_playerA.m_y].setLevelLabel();
							m_playerA.getUserMoney().setText("잔액 : " + (int)m_playerA.getMoney());
						}
						else
						{
							m_message.Display("돈이 부족해서 업그레이드를 할 수 없습니다.");
						}
						if(!m_playerA.CheckDouble())
						{
							turnToPlayerB();
						}

					}
					else if(m_turn == BluConstants.PLAYER2 )//2P
					{
						if(m_board[m_playerB.m_x][m_playerB.m_y].getLevel() == BluConstants.LEVEL0 && m_board[m_playerB.m_x][m_playerB.m_y].getBasePrice()*BluConstants.LV3FEE < m_playerB.getMoney())
						{
							m_playerB.minusMoney((int)(Math.round(m_board[m_playerB.m_x][m_playerB.m_y].getBasePrice()*BluConstants.LV3FEE)));
							m_board[m_playerB.m_x][m_playerB.m_y].setLevel(BluConstants.LEVEL3);
							m_board[m_playerB.m_x][m_playerB.m_y].setPrePrice(m_board[m_playerB.m_x][m_playerB.m_y].getBasePrice()*BluConstants.LV3RATE);
							m_board[m_playerB.m_x][m_playerB.m_y].setLevelImage(3);
							m_board[m_playerB.m_x][m_playerB.m_y].setLevelLabel();
							m_playerB.getUserMoney().setText("잔액 : " + (int)m_playerB.getMoney());
						}
						else if(m_board[m_playerB.m_x][m_playerB.m_y].getLevel() == BluConstants.LEVEL1 && m_board[m_playerB.m_x][m_playerB.m_y].getBasePrice()*(BluConstants.LV3FEE-BluConstants.LV1FEE) < m_playerB.getMoney())
						{
							m_playerB.minusMoney((int)(Math.round(m_board[m_playerB.m_x][m_playerB.m_y].getBasePrice()*(BluConstants.LV3FEE-BluConstants.LV1FEE))));
							m_board[m_playerB.m_x][m_playerB.m_y].setLevel(BluConstants.LEVEL3);
							m_board[m_playerB.m_x][m_playerB.m_y].setPrePrice(m_board[m_playerB.m_x][m_playerB.m_y].getBasePrice()*BluConstants.LV3RATE);
							m_board[m_playerB.m_x][m_playerB.m_y].setLevelImage(3);
							m_board[m_playerB.m_x][m_playerB.m_y].setLevelLabel();
							m_playerB.getUserMoney().setText("잔액 : " + (int)m_playerB.getMoney());
						}
						else if(m_board[m_playerB.m_x][m_playerB.m_y].getLevel() == BluConstants.LEVEL2 && m_board[m_playerB.m_x][m_playerB.m_y].getBasePrice()*(BluConstants.LV3FEE-BluConstants.LV2FEE) < m_playerB.getMoney())
						{
							m_playerB.minusMoney((int)(Math.round(m_board[m_playerB.m_x][m_playerB.m_y].getBasePrice()*(BluConstants.LV3FEE-BluConstants.LV2FEE))));
							m_board[m_playerB.m_x][m_playerB.m_y].setLevel(BluConstants.LEVEL3);
							m_board[m_playerB.m_x][m_playerB.m_y].setPrePrice(m_board[m_playerB.m_x][m_playerB.m_y].getBasePrice()*BluConstants.LV3RATE);
							m_board[m_playerB.m_x][m_playerB.m_y].setLevelImage(3);
							m_board[m_playerB.m_x][m_playerB.m_y].setLevelLabel();
							m_playerB.getUserMoney().setText("잔액 : " + (int)m_playerB.getMoney());
						}
						else
						{
							m_message.Display("돈이 부족해서 업그레이드를 할 수 없습니다.");
						}
						if(!m_playerB.CheckDouble())
						{
							turnToPlayerA();
						}
					}
					endLevelUpChoice();
					if(m_stpCount == 2)
					{
						m_stpCount--;
					}
					else if(m_stpCount == 1)
					{
						m_startPass.setVisible(false);
					}
				}
				else if(event.getSource() == m_cancelButton)
				{
					endLevelUpChoice();
					if(m_stpCount == 2)
					{
						m_stpCount--;
					}
					else if(m_stpCount == 1)
					{
						m_startPass.setVisible(false);
					}
				}
				else if(event.getSource() == m_worldTourButton)
				{
					m_worldTourButton.setVisible(false);
					m_diceButton.setVisible(true);
					m_worldTour.setVisible(false);
				}
				else if(event.getSource() == m_isleButton)
				{
					m_isle.setVisible(false);
					m_isleButton.setVisible(false);
					m_diceButton.setVisible(true);
				}
				else if(event.getSource() == m_festivalButton)
				{
					m_festivalButton.setVisible(false);
					m_festival.setVisible(false);
					m_diceButton.setVisible(true);
				}
				else if(event.getSource() == m_bonusButton)
				{
					m_bonusLabel1.setVisible(false);
					m_bonusLabel2.setVisible(false);
					m_bonusButton.setVisible(false);
					m_diceButton.setVisible(true);
				}
			}
		};
		m_stpCount = 0;
		
		// 특수 이벤트시의 버튼, 이미지 및 위치 설정 
		m_isle = new JLabel(new ImageIcon("Isle.gif"));
		m_isleButton = new JButton(new ImageIcon("Pass.gif"));
		m_isle.setBounds(50, 300, 800, 400);
		add(m_isle);
		m_isleButton.setBounds(400,700,100,100);
		
		add(m_isleButton);
		m_isleButton.addActionListener(m_actionListener);
		m_isle.setVisible(false);
		m_isleButton.setVisible(false);
		m_worldTour = new JLabel(new ImageIcon("WorldTour.gif"));
		m_worldTour.setBounds(50,300,803,404);
		add(m_worldTour);
		m_worldTour.setVisible(false);
		m_worldTourButton = new JButton(new ImageIcon("Pass.gif"));
		m_worldTourButton.setBounds(400,700,100,100);
		add(m_worldTourButton);
		m_worldTourButton.addActionListener(m_actionListener);
		m_worldTourButton.setVisible(false);
		
		m_festival = new JLabel(new ImageIcon("Festival.gif"));
		m_festivalButton = new JButton(new ImageIcon("Pass.gif"));
		m_festival.setBounds(50, 300, 800, 400);
		add(m_festival);
		m_festivalButton.setBounds(400,700,100,100);
		add(m_festivalButton);
		m_festivalButton.addActionListener(m_actionListener);
		m_festival.setVisible(false);
		m_festivalButton.setVisible(false);
		m_startPass = new JLabel(new ImageIcon("StartPass.gif"));
		m_startPass.setBounds(225,300,500,100);
		add(m_startPass);
		m_startPass.setVisible(false);
		m_bonusLabel1 = new JLabel(new ImageIcon("Bonus.gif")); 
		m_bonusLabel2 = new JLabel(new ImageIcon("Bonus1.gif"));
		m_bonusButton = new JButton(new ImageIcon("Pass.gif"));
		m_bonusLabel1.setBounds(250,320,400,228);
		add(m_bonusLabel1);
		m_bonusLabel2.setBounds(200, 180, 500, 200);
		add(m_bonusLabel2);
		m_bonusButton.setBounds(400,550,100,100);
		add(m_bonusButton);
		m_bonusLabel1.setVisible(false);
		m_bonusLabel2.setVisible(false);
		m_bonusButton.setVisible(false);
		m_bonusButton.addActionListener(m_actionListener);
		m_dice1 = new JLabel();
		m_dice2 = new JLabel();
		m_dice1.setBounds(300,200, 100, 100);
		m_dice2.setBounds(500,200, 100,100);
		add(m_dice1);
		add(m_dice2);
		m_dice1.setVisible(false);
		m_dice2.setVisible(false);
		m_win = new JLabel();
		m_win.setBounds(200, 100,500,500);
		add(m_win);
		m_win.setVisible(false);
		
		m_double = new JLabel(new ImageIcon("Double.gif"));
		m_double.setBounds(740,600,800,200);
		add(m_double);
		m_double.setVisible(false);
		setLayout(null);
		setBounds(0,0,1400,800);
		m_arriveOthers = 0;
		m_check = 0;
		m_board = new BluData[BluConstants.H_LEN][BluConstants.V_LEN];
		m_playerA = new Player(BluConstants.PLAYER1);
		m_playerB = new Player(BluConstants.PLAYER2);
		add(m_playerA.getCharacter());
		add(m_playerB.getCharacter());
		add(m_playerA.getUserInfor());
		add(m_playerB.getUserInfor());
		add(m_playerA.getUserMoney());
		add(m_playerB.getUserMoney());
		
		initBoard();
		
		
		add(m_playerA.getBlockedLabel());
		add(m_playerB.getBlockedLabel());
		
		//Dice Button 
		m_diceButton = new JButton(new ImageIcon("Dice.gif"));
		m_diceButton.setBounds(350,550,200,100);
		m_diceButton.setBackground(null);
		m_diceButton.addActionListener(m_actionListener);
		add(m_diceButton);
		
		//Buy& nonBuy Button 
		m_buyButton = new JButton(new ImageIcon("Buy.gif"));
		m_nonBuyButton = new JButton(new ImageIcon("Pass.gif"));
		m_nonBuyButton.setBounds(450,550,100,100);
		m_buyButton.setBounds(350,550,100,100);
		m_buyButton.addActionListener(m_actionListener);
		m_nonBuyButton.addActionListener(m_actionListener);
		add(m_buyButton);
		add(m_nonBuyButton);
		m_buyButton.setVisible(false);
		m_nonBuyButton.setVisible(false);
		
		
		//Leveling Buttons
		m_level1Button = new JButton(new ImageIcon("Lv1.gif"));
		m_level2Button = new JButton(new ImageIcon("Lv2.gif"));
		m_level3Button = new JButton(new ImageIcon("Lv3.gif"));
		m_cancelButton = new JButton(new ImageIcon("Pass.gif"));
		m_level1Button.setBounds(250,550,100,100);
		m_level2Button.setBounds(350,550,100,100);
		m_level3Button.setBounds(450,550,100,100);
		m_cancelButton.setBounds(550,550,100,100);
		m_level1Button.addActionListener(m_actionListener);
		m_level2Button.addActionListener(m_actionListener);
		m_level3Button.addActionListener(m_actionListener);
		m_cancelButton.addActionListener(m_actionListener);
		add(m_level1Button);
		add(m_level2Button);
		add(m_level3Button);
		add(m_cancelButton);
		m_level1Button.setVisible(false);
		m_level2Button.setVisible(false);
		m_level3Button.setVisible(false);
		m_cancelButton.setVisible(false);
		
		
		setBounds(0,0,1000,1000);
		// 생성
		setBackground(Color.white);
		m_turn = BluConstants.PLAYER1;
		// 초기값
		
		m_map = new JLabel(new ImageIcon("Map.jpg"));
		m_map.setBounds(0,0,BluConstants.MAP_X,BluConstants.MAP_Y);
		add(m_map);
		// 맵
		
		
		 add(m_playerA.getPlayerTurnLabel());
	     add(m_playerB.getPlayerTurnLabel());
	     m_playerB.getPlayerTurnLabel().setVisible(false);
	     m_message = new Message();
	     m_message.setBounds(920,30,450,280);
	     add(m_message);

	     
	}
	
	
	void play()
	{
		if(m_turn == BluConstants.PLAYER1)
		{
			m_playerA.m_roll();
			if(m_playerA.CheckDouble() || m_playerA.getBlocked() == 0) 
			{
				m_playerA.setBlocked(0);
				m_playerA.move();
				m_playerA.m_moveBlock();
				arrive(m_playerA);
				if(m_board[m_playerA.m_x][m_playerA.m_y].getOwner()>=0)
				{
					if(m_board[m_playerA.m_x][m_playerA.m_y].getLevel()==0 && m_board[m_playerA.m_x][m_playerA.m_y].getOwner() == 0)
					{
						m_message.Display("도시  : "+m_board[m_playerA.m_x][m_playerA.m_y].getCity(),"땅 가격  : " + m_board[m_playerA.m_x][m_playerA.m_y].getBasePrice());
					}
					else if(m_board[m_playerA.m_x][m_playerA.m_y].getLevel()==3)
					{
						m_message.Display("LV3, 모든 업그레이드를 완료하였습니다.");
					}
					else if(m_board[m_playerA.m_x][m_playerA.m_y].getOwner() == 1)
					{
						m_message.Display("1lvl 업그레이드 가격 : " + m_board[m_playerA.m_x][m_playerA.m_y].getBasePrice()*BluConstants.LV1FEE,"2lvl 업그레이드 가격 : " +m_board[m_playerA.m_x][m_playerA.m_y].getBasePrice()*BluConstants.LV2FEE,"3lvl 업그레이드 가격 : " +m_board[m_playerA.m_x][m_playerA.m_y].getBasePrice()*BluConstants.LV3FEE);
					}
					
				}
				m_playerA.getBlockedLabel().setText("남은 휴식턴 : " + m_playerA.getBlocked());
			}
			else if(m_playerA.getBlocked() != 0)
			{
				m_playerA.setBlocked(m_playerA.getBlocked() - 1);
				m_playerA.getBlockedLabel().setText("남은 휴식턴 : " + m_playerA.getBlocked());
			}
			if(m_playerA.getFin() == 1)
			{
				m_stpCount = 2;
				m_startPass.setVisible(true);
				m_playerA.setMoney((int)(Math.round(m_playerA.getMoney()*1.2)));
				m_playerA.setFin(0);
				m_playerA.getUserMoney().setText("잔액 : " + (int)m_playerA.getMoney());
			}

			if(m_arriveOthers == 1)//남의땅 걸리면 
			{
				m_message.Display("도시  : "+m_board[m_playerA.m_x][m_playerA.m_y].getCity(),"통행료 : -" + m_board[m_playerA.m_x][m_playerA.m_y].getPrePrice());
				m_playerA.minusMoney((int)(Math.round(m_board[m_playerA.m_x][m_playerA.m_y].getPrePrice())));
				m_playerB.plusMoney(m_board[m_playerA.m_x][m_playerA.m_y].getPrePrice());
				m_arriveOthers = 0;
				m_playerA.getUserMoney().setText("잔액 : " + (int)m_playerA.getMoney());
				m_playerB.getUserMoney().setText("잔액 : " + (int)m_playerB.getMoney());
			}
			if(m_playerA.getMoney() <= 0)
			{
				m_win.setIcon(new ImageIcon("Player"+BluConstants.PLAYER2+"m_win.Gif"));
				m_win.setVisible(true);
				m_dice1.setVisible(false);
				m_dice2.setVisible(false);
				m_diceButton.setVisible(false);
			}
			if(m_check != 1 && !m_playerA.CheckDouble())
			{
				m_turn = BluConstants.PLAYER2;
				m_playerB.getPlayerTurnLabel().setVisible(true);
				m_playerA.getPlayerTurnLabel().setVisible(false);
			}
			else if(m_playerA.CheckDouble())
			{
				m_double.setVisible(true);
			}
		}
		else if(m_turn == BluConstants.PLAYER2)
		{
			m_playerB.m_roll();
			if(m_playerB.CheckDouble() || m_playerB.getBlocked() == 0)
			{
				
				m_playerB.setBlocked(0);
				m_playerB.move();
				m_playerB.m_moveBlock();
				arrive(m_playerB);
				if(m_board[m_playerB.m_x][m_playerB.m_y].getOwner()>=0)
				{
					if(m_board[m_playerB.m_x][m_playerB.m_y].getLevel()==0 && m_board[m_playerB.m_x][m_playerB.m_y].getOwner() == 0)
					{
						m_message.Display("도시  : "+m_board[m_playerB.m_x][m_playerB.m_y].getCity(),"땅 가격  : " + m_board[m_playerB.m_x][m_playerB.m_y].getBasePrice());
					}
					else if(m_board[m_playerB.m_x][m_playerB.m_y].getLevel()==3)
					{
						m_message.Display("LV3, 모든 업그레이드를 완료하였습니다.");
					}
					else if(m_board[m_playerB.m_x][m_playerB.m_y].getOwner() == 2)
					{
						m_message.Display("1lvl 업그레이드 가격 : " + m_board[m_playerB.m_x][m_playerB.m_y].getBasePrice()*BluConstants.LV1FEE,"2lvl 업그레이드 가격 : " +m_board[m_playerB.m_x][m_playerB.m_y].getBasePrice()*BluConstants.LV2FEE,"3lvl 업그레이드 가격 : " +m_board[m_playerB.m_x][m_playerB.m_y].getBasePrice()*BluConstants.LV3FEE);
					}
					
				}
				m_playerB.getBlockedLabel().setText("남은 휴식턴 : " + m_playerB.getBlocked());
			}
			else if(m_playerB.getBlocked() != 0)
			{
				m_playerB.setBlocked(m_playerB.getBlocked() - 1);
				m_playerB.getBlockedLabel().setText("남은 휴식턴 : " + m_playerB.getBlocked());
			}
			if(m_playerB.getFin() == 1)
			{
				m_stpCount =2;
				m_startPass.setVisible(true);
				m_playerB.setMoney((int)(Math.round(m_playerB.getMoney()*1.2)));
				m_playerB.setFin(0);
				m_playerB.getUserMoney().setText("잔액 : " + (int)m_playerB.getMoney());
			}
			if(m_arriveOthers == 1)
			{
				m_message.Display("도시  : "+m_board[m_playerB.m_x][m_playerB.m_y].getCity(),"통행료 : -" + m_board[m_playerB.m_x][m_playerB.m_y].getPrePrice());
				m_playerB.minusMoney((int)(Math.round(m_board[m_playerB.m_x][m_playerB.m_y].getBasePrice())));				
				m_playerA.plusMoney(m_board[m_playerB.m_x][m_playerB.m_y].getBasePrice());
				m_arriveOthers = 0;
				m_playerA.getUserMoney().setText("잔액 : " +(int)m_playerA.getMoney());
				m_playerB.getUserMoney().setText("잔액 : " + (int)m_playerB.getMoney());
			}
			if(m_playerB.getMoney() <= 0)
			{
				m_win.setIcon(new ImageIcon("Player"+BluConstants.PLAYER1+"m_win.gif"));
				m_win.setVisible(true);
				m_dice1.setVisible(false);
				m_dice2.setVisible(false);
				m_diceButton.setVisible(false);
			}
			if(m_check != 1 && !m_playerB.CheckDouble())
			{
				turnToPlayerA();

			}
			else if(m_playerB.CheckDouble())
			{
				m_double.setVisible(true);
			}
		}
		
	}
	
	void arrive(Player pl)//도착위치에 대한 처리 
	{
		if(m_board[pl.m_x][pl.m_y].getOwner() == pl.getPlayerNum())//내 블록 도착시
		{
			if(m_board[pl.m_x][pl.m_y].getLevel() == BluConstants.LEVEL0)
			{
				m_message.setVisible(true);
				m_level1Button.setVisible(true);
				m_level2Button.setVisible(true);
				m_level3Button.setVisible(true);
				m_cancelButton.setVisible(true);
				m_diceButton.setVisible(false);
				m_check = 1;
			}
			else if(m_board[pl.m_x][pl.m_y].getLevel() == BluConstants.LEVEL1)
			{
				m_message.setVisible(true);
				m_level2Button.setVisible(true);
				m_level3Button.setVisible(true);
				m_cancelButton.setVisible(true);
				m_diceButton.setVisible(false);
				m_check = 1;
			}
			else if(m_board[pl.m_x][pl.m_y].getLevel() == BluConstants.LEVEL2)
			{
				m_message.setVisible(true);
				m_level3Button.setVisible(true);
				m_cancelButton.setVisible(true);
				m_diceButton.setVisible(false);
				m_check = 1;
			}
			else if(m_board[pl.m_x][pl.m_y].getLevel() == BluConstants.LEVEL3)
			{
			}
		}
		else if(m_board[pl.m_x][pl.m_y].getOwner() == 0)//주인없는 블록 도착시 
		{
			m_message.setVisible(true);
			m_buyButton.setVisible(true);
			m_nonBuyButton.setVisible(true);
			m_diceButton.setVisible(false);
			m_check = 1;
		}
		else if(m_board[pl.m_x][pl.m_y].getOwner() == BluConstants.SPECIALBLOCK)//보너스
		{
			m_bonusLabel1.setVisible(true);
			m_bonusLabel2.setVisible(true);
			m_bonusButton.setVisible(true);
			m_diceButton.setVisible(false);
			pl.setMoney((int)(Math.round(pl.getMoney()*1.4)));
			pl.getUserMoney().setText("잔액 : " + (int)pl.getMoney());
		}
		else if(m_board[pl.m_x][pl.m_y].getOwner() == BluConstants.WORLDTOUR)//세계일주
		{
			m_worldTour.setVisible(true);
			m_worldTourButton.setVisible(true);
			m_diceButton.setVisible(false);
			pl.setMoney((int)(Math.round(pl.getMoney()/2)));
			pl.getUserMoney().setText("잔액 : " + (int)pl.getMoney());
		
		}
		else if(m_board[pl.m_x][pl.m_y].getOwner() == BluConstants.FESTIVAL)//축제
		{
			m_festival.setVisible(true);
			m_festivalButton.setVisible(true);
			m_diceButton.setVisible(false);
			pl.setMoney((int)(Math.round(pl.getMoney()*0.7)));
			pl.getUserMoney().setText("잔액 : " + (int)pl.getMoney());
		}
		else if(m_board[pl.m_x][pl.m_y].getOwner() == BluConstants.ISLE)//무인도 
		{
			m_isle.setVisible(true);
			m_isleButton.setVisible(true);
			m_diceButton.setVisible(false);
			pl.setBlocked(3);
			pl.getBlockedLabel().setText("남은 휴식턴 : " + pl.getBlocked());
		}
		else//다른사람 블록 도착시
		{
			m_message.setVisible(true);
			m_arriveOthers = 1;
		}
	}
	void initBoard() // 보드판 초기화, 기본적인 구매 가격과 이름을 설정함 . 
	{
		  m_board[0][0] = new BluData("start",BluConstants.STARTBLOCK,0,0);
	      m_board[1][0] = new BluData("평양",0,1000,0);
	      m_board[2][0] = new BluData("독도",0,1000,0);
	      m_board[3][0] = new BluData("베이징",0,1200,0);
	      m_board[4][0] = new BluData("싱가폴",0,1500,0);
	      m_board[5][0] = new BluData("보너스",BluConstants.SPECIALBLOCK,0,0);
	      m_board[6][0] = new BluData("방콕",0,1700,0);
	      m_board[7][0] = new BluData("마닐라",0,2000,0);
	      m_board[8][0] = new BluData("무인도",BluConstants.ISLE,0,0);
	      m_board[8][1] = new BluData("예루살렘",0,2200,0);
	      m_board[8][2] = new BluData("카이로",0,2400,0);
	      m_board[8][3] = new BluData("이스탄불",0,2600,0);
	      m_board[8][4] = new BluData("키프러스",0,2000,0);
	      m_board[8][5] = new BluData("보너스",BluConstants.SPECIALBLOCK,0,0);
	      m_board[8][6] = new BluData("바그다드",0,2800,0);
	      m_board[8][7] = new BluData("테헤란",0,3000,0);
	      m_board[8][8] = new BluData("축제",BluConstants.FESTIVAL,0,0);
	      m_board[7][8] = new BluData("취리히",0,3200,0);
	      m_board[6][8] = new BluData("프라하",0,3400,0);
	      m_board[5][8] = new BluData("시칠리아",0,3000,0);
	      m_board[4][8] = new BluData("베를린",0,3600,0);
	      m_board[3][8] = new BluData("보너스",BluConstants.SPECIALBLOCK,0,0);
	      m_board[2][8] = new BluData("비엔나",0,3800,0);
	      m_board[1][8] = new BluData("로마",0,4000,0);
	      m_board[0][8] = new BluData("세계여행",BluConstants.WORLDTOUR,0,0);
	      m_board[0][7] = new BluData("서울",0,4200,0);
	      m_board[0][6] = new BluData("도쿄",0,4400,0);
	      m_board[0][5] = new BluData("워싱턴",0,4600,0);
	      m_board[0][4] = new BluData("자메이카",0,4000,0);
	      m_board[0][3] = new BluData("보너스",BluConstants.SPECIALBLOCK,0,0);
	      m_board[0][2] = new BluData("런던",0,4800,0);
	      m_board[0][1] = new BluData("파리",0,5000,0);
	      m_board[1][0].getOwnerLabel().setBounds(175,3, 25, 25);
	      add(m_board[1][0].getOwnerLabel());
	      m_board[1][0].getLevelLabel().setBounds(213,3,25,25);
	      add(m_board[1][0].getLevelLabel());
	      m_board[2][0].getOwnerLabel().setBounds(260,3, 25, 25);
	      add(m_board[2][0].getOwnerLabel());
	      m_board[2][0].getLevelLabel().setBounds(298,3, 25, 25);
	      add(m_board[2][0].getLevelLabel());
	      m_board[3][0].getOwnerLabel().setBounds(340,3, 25, 25);
	      add(m_board[3][0].getOwnerLabel());
	      m_board[3][0].getLevelLabel().setBounds(378,3,25,25);;
	      add(m_board[3][0].getLevelLabel());
	      m_board[4][0].getOwnerLabel().setBounds(420,3, 25, 25);
	      add(m_board[4][0].getOwnerLabel());
	      m_board[4][0].getLevelLabel().setBounds(458,3,25,25);	 
	      add(m_board[4][0].getLevelLabel());
	      m_board[6][0].getOwnerLabel().setBounds(584,3, 25, 25);
	      add(m_board[6][0].getOwnerLabel());
	      m_board[6][0].getLevelLabel().setBounds(622,3,25,25);	 
	      add(m_board[6][0].getLevelLabel());
	      m_board[7][0].getOwnerLabel().setBounds(666,3, 25, 25);
	      add(m_board[7][0].getOwnerLabel());
	      m_board[7][0].getLevelLabel().setBounds(704,3,25,25);
	      add(m_board[7][0].getLevelLabel());
	      m_board[8][1].getOwnerLabel().setBounds(743,172, 25, 25);
	      add(m_board[8][1].getOwnerLabel());
	      m_board[8][1].getLevelLabel().setBounds(743,210,25,25);
	      add(m_board[8][1].getLevelLabel());
	      m_board[8][2].getOwnerLabel().setBounds(743,255, 25, 25);
	      add(m_board[8][2].getOwnerLabel());
	      m_board[8][2].getLevelLabel().setBounds(743,293,25,25);
	      add(m_board[8][2].getLevelLabel());
	      m_board[8][3].getOwnerLabel().setBounds(743,338, 25, 25);
	      add(m_board[8][3].getOwnerLabel());
	      m_board[8][3].getLevelLabel().setBounds(743,376,25,25);
	      add(m_board[8][3].getLevelLabel());
	      m_board[8][4].getOwnerLabel().setBounds(743,420, 25, 25);
	      add(m_board[8][4].getOwnerLabel());
	      m_board[8][4].getLevelLabel().setBounds(743,458, 25, 25);
	      add(m_board[8][4].getLevelLabel());
	      m_board[8][6].getOwnerLabel().setBounds(743,582, 25, 25);
	      add(m_board[8][6].getOwnerLabel());
	      m_board[8][6].getLevelLabel().setBounds(743,620,25,25);
	      add(m_board[8][6].getLevelLabel());
	      m_board[8][7].getOwnerLabel().setBounds(743,662, 25, 25);
	      add(m_board[8][7].getOwnerLabel());
	      m_board[8][7].getLevelLabel().setBounds(743,700,25,25);
	      add(m_board[8][7].getLevelLabel());
	      m_board[7][8].getOwnerLabel().setBounds(665,741, 25, 25);
	      add(m_board[7][8].getOwnerLabel());
	      m_board[7][8].getLevelLabel().setBounds(703,741,25,25);
	      add(m_board[7][8].getLevelLabel());
	      m_board[6][8].getOwnerLabel().setBounds(585,741, 25, 25);
	      add(m_board[6][8].getOwnerLabel());
	      m_board[6][8].getLevelLabel().setBounds(623,741,25,25);
	      add(m_board[6][8].getLevelLabel());
	      m_board[5][8].getOwnerLabel().setBounds(502,741, 25, 25);
	      add(m_board[5][8].getOwnerLabel());
	      m_board[5][8].getLevelLabel().setBounds(540,741, 25, 25);
	      add(m_board[5][8].getLevelLabel());
	      m_board[4][8].getOwnerLabel().setBounds(420,741, 25, 25);
	      add(m_board[4][8].getOwnerLabel());
	      m_board[4][8].getLevelLabel().setBounds(458,741,25,25);
	      add(m_board[4][8].getLevelLabel());
	      m_board[2][8].getOwnerLabel().setBounds(260,741, 25, 25);
	      add(m_board[2][8].getOwnerLabel());
	      m_board[2][8].getLevelLabel().setBounds(292,741,25,25);
	      add(m_board[2][8].getLevelLabel());
	      m_board[1][8].getOwnerLabel().setBounds(175,741, 25, 25);
	      add(m_board[1][8].getOwnerLabel());
	      m_board[1][8].getLevelLabel().setBounds(208,741,25,25);
	      add(m_board[1][8].getLevelLabel());
	      m_board[0][7].getOwnerLabel().setBounds(131,662, 25, 25);
	      add(m_board[0][7].getOwnerLabel());
	      m_board[0][7].getLevelLabel().setBounds(131,700,25,25);
	      add(m_board[0][7].getLevelLabel());
	      m_board[0][6].getOwnerLabel().setBounds(131,582, 25, 25);
	      add(m_board[0][6].getOwnerLabel());
	      m_board[0][6].getLevelLabel().setBounds(131,620,25,25);
	      add(m_board[0][6].getLevelLabel());
	      m_board[0][5].getOwnerLabel().setBounds(131,503, 25, 25);
	      add(m_board[0][5].getOwnerLabel());
	      m_board[0][5].getLevelLabel().setBounds(131,541, 25, 25);
	      add(m_board[0][5].getLevelLabel());
	      m_board[0][4].getOwnerLabel().setBounds(131,420, 25, 25);
	      add(m_board[0][4].getOwnerLabel());
	      m_board[0][4].getLevelLabel().setBounds(131,458, 25, 25);
	      add(m_board[0][4].getLevelLabel());
	      m_board[0][2].getOwnerLabel().setBounds(131,255, 25, 25);
	      add(m_board[0][2].getOwnerLabel());
	      m_board[0][2].getLevelLabel().setBounds(131,293,25,25);
	      add(m_board[0][2].getLevelLabel());
	      m_board[0][1].getOwnerLabel().setBounds(131,172, 25, 25);
	      add(m_board[0][1].getOwnerLabel());
	      m_board[0][1].getLevelLabel().setBounds(131,210,25,25);
	      add(m_board[0][1].getLevelLabel());
	      
	}
	public void turnToPlayerA()
	{
		m_turn = BluConstants.PLAYER1;
		m_playerB.getPlayerTurnLabel().setVisible(false);
		m_playerA.getPlayerTurnLabel().setVisible(true);
	}
	public void turnToPlayerB()
	{
		m_turn = BluConstants.PLAYER2;
		m_playerB.getPlayerTurnLabel().setVisible(true);
		m_playerA.getPlayerTurnLabel().setVisible(false);
	}
	public void endBuyChoice()
	{
		m_buyButton.setVisible(false);
		m_nonBuyButton.setVisible(false);
		m_diceButton.setVisible(true);
		m_check = 0;
	}
	public void endLevelUpChoice()
	{
		m_level1Button.setVisible(false);
		m_level2Button.setVisible(false);
		m_level3Button.setVisible(false);
		m_cancelButton.setVisible(false);
		m_diceButton.setVisible(true);
		m_check = 0;
	}
	
}