package com.elvis.demo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class SNTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LinkedList<String> list = iniSNByfile("/static/devids.csv");
		System.out.println(list.size());
	}

	public static LinkedList<String> iniSNByfile(String pathname) {
		FileReader fr = null;
		BufferedReader br = null;
		LinkedList<String> list = new LinkedList<>();
		try {
			fr = new FileReader(pathname);
			br = new BufferedReader(fr);
			String tempString = null;
			while ((tempString = br.readLine()) != null) {
				list.add(tempString);
			}
			return list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			try {
				if (fr != null) {
					fr.close();
				}
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

}
