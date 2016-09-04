package com.mati.stateful;

import javax.ejb.Local;

@Local
public interface INumbers {
	public void addNum(int num);
	public int getTotal();
	public int[] getNums();
	public void exit();

}
