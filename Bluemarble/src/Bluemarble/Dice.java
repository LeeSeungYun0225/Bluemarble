package Bluemarble;

import java.util.Random;

public class Dice // 주사위를 굴릴 때 숫자를 랜덤으로 배정 
{
	private int m_dice1,m_dice2,m_resultValue;
	private Random m_rand;
	public Dice()
	{
		m_rand = new Random();
		
	}
	void roll()
	{
		m_rand.setSeed(System.currentTimeMillis());
		m_dice1 = m_rand.nextInt(BluConstants.DICE)+1;
		m_dice2 = m_rand.nextInt(BluConstants.DICE)+1;
		// 두개의 1~6 사이의 값을 갖는 랜덤값 추출하여 더하여 2~12범위의 임의의 값을 구한다 .
		m_resultValue = m_dice1+m_dice2;
	}
	
	public int getDice1()
	{
		return m_dice1;
	}

	public int getDice2()
	{
		return m_dice2;
	}
	
	public int getResultValue()
	{
		return m_resultValue;
	}
}