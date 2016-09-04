package com.mati.stateful;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.PreRemove;

@Stateful(name = "Nums")
public class Numbers implements INumbers{

	private List<Integer> nums;

	@PostConstruct
	public void initiate(){
		this.nums = new ArrayList<Integer>();
		System.out.println("in initiate @PostCunstruct");
	}

	public void addNum(int num) {
		this.nums.add(num);
		System.out.println(nums);
	}

	public int getTotal() {
		int total = getSumOfNums();
		System.out.println("in getTotal");
		System.out.println(total);
		return total;
	}

	public int[] getNums() {
		int[] nums = getIntArray();
		System.out.println("in getNums");
		return nums;
	}

	@PreRemove
	private void setNull(){
		this.nums = null;
	}

	@Remove
	public void exit() {
	}

	private int[] getIntArray() {
		int[] nums = new int[this.nums.size()];
		for (int i = 0; i < this.nums.size(); i++) {
			nums[i] = this.nums.get(i);
		}
		return nums;
	}

	private int getSumOfNums() {
		int sum = 0 ;
		for (Integer num : nums) {
			sum =sum + num;
		}	
		
		return sum;
	}

}
