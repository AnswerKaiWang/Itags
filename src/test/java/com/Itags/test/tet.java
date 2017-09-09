package com.Itags.test;

import java.util.List;
public class tet {

	public static void main(String[] args) {
		String x = new String("fdf");
		String y = x;
		x = "ss";
		if(x == y) {
			System.out.println("1");
		} else if(x.equals(y)) {
			System.out.println("2");
		}
		System.out.println(y);
		
	}
}
