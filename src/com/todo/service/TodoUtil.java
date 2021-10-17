package com.todo.service;

import java.util.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title, desc;
		String category, due_date;
		int is_comp, is_rep, cycle;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n"
				+ "[항목 추가]\n"
				+ "카테고리 > ");
		
		category = sc.next().trim();
		
		System.out.print("제목 > ");
		
		title = sc.next().trim();
		if (list.isDuplicate(title)) {
			System.out.printf("이미 있는 제목은 사용할 수 없습니다");
			return;
		}
		
		sc.nextLine();
		System.out.print("내용 > ");
		desc = sc.nextLine();
		
		System.out.print("D-Day를 입력하십시오 > ");
		due_date = sc.nextLine();
		
		System.out.print("완료된 항목이라면 1을 아니라면 0을 입력하십시오 > ");
		is_comp = sc.nextInt();
		
		System.out.print("반복되는 항목이라면 1을 아니라면 0을 입력하십시오 > ");
		is_rep = sc.nextInt();
		
		if(is_rep == 1) {
			System.out.print("반복 주기를 입력하십시오 > ");
			cycle = sc.nextInt();
		}else {
			cycle=0;
		}
		
		TodoItem t = new TodoItem(category, title, desc, due_date, is_comp, is_rep, cycle);
		if(list.addItem(t)>0) {
			System.out.println("추가되었습니다");
		}
	}

	public static void deleteItem(TodoList l) {
		
		System.out.print("\n"
				+ "[항목 삭제]\n"
				+ "삭제할 항목의 번호를 입력하십시오 > ");		
		
		Scanner sc = new Scanner(System.in);
		String str = sc.nextLine();
		String[] arr = str.split(" ");
		int i = 0;
		while(arr.length>i) {
			int id = Integer.parseInt(arr[i]);
			if(l.deleteItem(id)>0) {
				System.out.println(id+ "번의 삭제가 완료되었습니다");
			}
			i++;
		}
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n"
				+ "[항목 수정]\n"
				+ "수정할 항목의 번호를 입력하십시오 > ");
		
		int id = sc.nextInt();
		
		System.out.print("새 카테고리 > ");
		String new_category = sc.next().trim();
		
		System.out.print("새 제목 > ");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.printf("이미 있는 제목은 사용할 수 없습니다");
			return;
		}
		
		System.out.print("새 내용 > ");
		sc.nextLine();
		String new_description = sc.nextLine().trim();
		
		System.out.print("새 D-Day > ");
		String new_due_date = sc.nextLine().trim();
		
		System.out.print("완료된 항목이라면 1을 아니라면 0을 입력하십시오 > ");
		int is_comp = sc.nextInt();
		
		System.out.print("반복되는 항목이라면 1을 아니라면 0을 입력하십시오 > ");
		int is_rep = sc.nextInt();
		
		int cycle;
		
		if(is_rep == 0) {
			cycle=0;	
		}else {
			System.out.print("반복 주기를 입력하십시오 > ");
			cycle = sc.nextInt();
		}
		
		TodoItem t = new TodoItem(new_category, new_title, new_description, new_due_date, is_comp, is_rep, cycle);
		t.setId(id);
		if(l.editItem(t)>0){
			System.out.println(id +"번 항목이 수정되었습니다");
		}
		
	}

	public static void listAll(TodoList l, String orderby, int ordering) {
		System.out.printf("[전체 목록 %d개]\n", l.getCount());
		for(TodoItem item : l.getOrderedList(orderby, ordering)) {
			System.out.println(item.toString());
		}
	}
	
	public static void listAll(TodoList l) {
		System.out.printf("[전체 목록, 총 %d개]\n", l.getCount());
		for(TodoItem item : l.getList()) {
			System.out.println(item.toString());
		}
	}
	
	public static void find(TodoList l, String find) {
		int cnt = 0;
		for(TodoItem t : l.getList(find)) {
			System.out.println(t.toString());
			cnt++;
		}
		if(cnt==0) System.out.println("일치하는 항목이 없습니다");
		else System.out.println("\n총 "+cnt+"개의 항목을 찾았습니다");
	}
	
	public static void find_cate(TodoList l, String find) {
		int cnt=0;
		for(TodoItem t : l.getListCategories(find)) {
			System.out.println(t.toString());
			cnt++;
		}
		
		if(cnt==0) System.out.println("일치하는 항목이 없습니다");
		else System.out.println("\n총 "+cnt+"개의 항목을 찾았습니다");
	}
	
	public static void ls_cate(TodoList l) {
		int cnt = 0;
		
		for(String item : l.getCategories()) {
			System.out.print(item + " ");
			cnt++;
		}

		System.out.println("\n총 "+cnt+"개의 카테고리가 등록되어 있습니다");
	}
	
	public static void complete(TodoList l, int id) {
		if(l.complete(l, id)>0) {
			System.out.println(id+"번 항목이 완료되었습니다");
		}
	}
	
	public static void ls_comp(TodoList l) {
		int cnt = 0;
		
		for(TodoItem t : l.getListComp()) {
			System.out.println(t.toString());
			cnt++;
		}
		if(cnt==0) System.out.println("완료된 항목이 없습니다");
		else System.out.println("\n총 "+cnt+"개의 항목이 완료되었습니다");
	}
}
