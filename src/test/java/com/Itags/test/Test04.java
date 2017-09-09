package com.Itags.test;

import java.util.ArrayList;
import java.util.List;

public class Test04 {
	public static void main(String[] args) {
		List<String> list = new ArrayList<>();
		list.add("13");
		list.add("dv");
		String remove = list.remove(0);
		System.out.println(list.size() + remove);
	}
}
