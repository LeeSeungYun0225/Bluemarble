package Bluemarble;

import java.util.Random;

public class Dice // �ֻ����� ���� �� ���ڸ� �������� ���� 
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
		// �ΰ��� 1~6 ������ ���� ���� ������ �����Ͽ� ���Ͽ� 2~12������ ������ ���� ���Ѵ� .
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