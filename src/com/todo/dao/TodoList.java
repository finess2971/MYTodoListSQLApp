package com.todo.dao;

import java.sql.*;
import java.util.*;

import com.todo.service.*;

public class TodoList {
	private Connection con;
	private String sql;
	int cnt;

	public TodoList() {
		this.con = DBConnect.getConnection();
	}

	public int addItem(TodoItem t) {
		sql = "insert into list (title, memo, category, current_date, due_date, is_completed, is_repeated, cycle)"
				+ "values (?,?,?,?,?,?,?,?);";
		PreparedStatement pstmt;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			pstmt.setString(3, t.getCate());
			pstmt.setString(4, t.getCurrent_date());
			pstmt.setString(5, t.getDue());
			pstmt.setInt(6, t.getComp());
			pstmt.setInt(7, t.getRep());
			pstmt.setInt(8, t.getCycle());
			cnt = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return cnt;
	}

	public int deleteItem(int id) {
		sql = "delete from list where id=?;";
		PreparedStatement pstmt;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, id);
			cnt = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {	
			e.printStackTrace();
		}
		return cnt;
	}

	public int editItem(TodoItem t) {
		sql = "update list set title=?, memo=?, category=?, current_date=?, due_date=?, is_completed=?, is_repeated=?, cycle=? where id=?;";
		PreparedStatement pstmt;
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			pstmt.setString(3, t.getCate());
			pstmt.setString(4, t.getCurrent_date());
			pstmt.setString(5, t.getDue());
			pstmt.setInt(6,t.getComp());
			pstmt.setInt(7,t.getRep());
			pstmt.setInt(8,t.getCycle());
			pstmt.setInt(9,t.getId());
			cnt = pstmt.executeUpdate();
			pstmt.close();		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}
	
	public ArrayList<TodoItem> getList(String find) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		find = "%"+find+"%";
		sql = "Select * from list where title like ? or memo like ?;";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, find);
			pstmt.setString(2, find);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String cate = rs.getString("category");
				String title = rs.getString("title");
				String desc = rs.getString("memo");
				String due = rs.getString("due_date");
				String current = rs.getString("current_date");
				int is_comp = rs.getInt("is_completed");
				int is_rep = rs.getInt("is_repeated");
				int cycle = rs.getInt("cycle");
				TodoItem t = new TodoItem(cate,title,desc,due,is_comp,is_rep,cycle);
				t.setId(id);
				t.setCurrent_date(current);
				list.add(t);
			}
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getList() {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stat;
		try {
			stat = con.createStatement();
			sql = "Select * from list";
			ResultSet rs = stat.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("id");
				String cate = rs.getString("category");
				String title = rs.getString("title");
				String desc = rs.getString("memo");
				String due = rs.getString("due_date");
				String current = rs.getString("current_date");
				int is_comp = rs.getInt("is_completed");
				int is_rep = rs.getInt("is_repeated");
				int cycle = rs.getInt("cycle");
				TodoItem t = new TodoItem(cate,title,desc,due,is_comp,is_rep,cycle);
				t.setId(id);
				t.setCurrent_date(current);
				list.add(t);
			}
			stat.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public int getCount() {
		Statement stat;
		try {
			stat = con.createStatement();
			sql = "select count(id) from list;";
			ResultSet rs = stat.executeQuery(sql);
			rs.next();
			cnt = rs.getInt("count(id)");
			stat.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}
	
	public ArrayList<String> getCategories(){
		ArrayList<String> list = new ArrayList<String>();
		Statement stat;
		try {
			stat = con.createStatement();
			sql = "select distinct category from list;";
			ResultSet rs = stat.executeQuery(sql);
			while(rs.next()) {
				String cate = rs.getString("category");
				list.add(cate);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getListCategories(String find){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		sql = "Select * from list where category = ?;";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, find);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String cate = rs.getString("category");
				String title = rs.getString("title");
				String desc = rs.getString("memo");
				String due = rs.getString("due_date");
				String current = rs.getString("current_date");
				int is_comp = rs.getInt("is_completed");
				int is_rep = rs.getInt("is_repeated");
				int cycle = rs.getInt("cycle");
				TodoItem t = new TodoItem(cate,title,desc,due,is_comp,is_rep,cycle);
				t.setId(id);
				t.setCurrent_date(current);
				list.add(t);
			}
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getOrderedList(String orderby, int ordering){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stat;
		try {
			stat = con.createStatement();
			sql = "Select * from list order by "+ orderby;
			if(ordering==0)	sql = sql+" desc;";
			else sql = sql+";";
			ResultSet rs = stat.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("id");
				String cate = rs.getString("category");
				String title = rs.getString("title");
				String desc = rs.getString("memo");
				String due = rs.getString("due_date");
				String current = rs.getString("current_date");
				int is_comp = rs.getInt("is_completed");
				int is_rep = rs.getInt("is_repeated");
				int cycle = rs.getInt("cycle");
				TodoItem t = new TodoItem(cate,title,desc,due,is_comp,is_rep,cycle);
				t.setId(id);
				t.setCurrent_date(current);
				list.add(t);
			}
			stat.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public int complete(TodoList l, int id) {
		sql = "update list set is_completed = 1 where id=" + id + ";";
		PreparedStatement pstmt;
		try {
			pstmt = con.prepareStatement(sql);
			cnt = pstmt.executeUpdate();
			pstmt.close();	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		repeat(l,id);
		return cnt;
	}
	
	public ArrayList<TodoItem> getListComp(){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		sql = "Select * from list where is_completed = 1;";
		try {
			pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String cate = rs.getString("category");
				String title = rs.getString("title");
				String desc = rs.getString("memo");
				String due = rs.getString("due_date");
				String current = rs.getString("current_date");
				int is_comp = rs.getInt("is_completed");
				int is_rep = rs.getInt("is_repeated");
				int cycle = rs.getInt("cycle");
				TodoItem t = new TodoItem(cate,title,desc,due,is_comp,is_rep,cycle);
				t.setId(id);
				t.setCurrent_date(current);
				list.add(t);
			}
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public boolean isDuplicate(String title) {
		sql = "Select * from list where title like ?;";
		Statement stat;
		try {
			stat = con.createStatement();
			ResultSet rs = stat.executeQuery(sql);
			if(rs.next()) {
				stat.close();
				return true;
			}
			stat.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void repeat(TodoList l, int id) {
		sql = "select * from list where id="+id+";";
		Statement stat;
		try {
			stat = con.createStatement();
			ResultSet rs = stat.executeQuery(sql);
			while(rs.next()) {
				String cate = rs.getString("category");
				String title = rs.getString("title");
				String desc = rs.getString("memo");
				String due = rs.getString("due_date");
				int is_rep = rs.getInt("is_repeated");
				int cycle = rs.getInt("cycle");
				String[] arr=due.split("/");
				String temp = arr[2];
				arr[2] = Integer.toString(Integer.parseInt(arr[2])+cycle);
				if(is_rep==1) {		
					do {
						temp=arr[2];
						int y = Integer.parseInt(arr[0]);
						int m = Integer.parseInt(arr[1]);
						int d = Integer.parseInt(arr[2]);
						if(m==2) {
							if(Integer.parseInt(arr[0])%4==0) {
								if(d>29) {
									arr[1]="03";
									arr[2]=Integer.toString(d-29);
									m = Integer.parseInt(arr[1]);
									d = Integer.parseInt(arr[2]);
								}
							}else {
								if(d>28) {
									arr[1]="03";
									arr[2]=Integer.toString(d-28);
									m = Integer.parseInt(arr[1]);
									d = Integer.parseInt(arr[2]);
								}
							}
						}
						if((m==4||m==6)||(m==9||m==11)) {
							if(Integer.parseInt(arr[2])>30) {
								arr[1]=Integer.toString(m+1);
								arr[2]=Integer.toString(d-30);
								m = Integer.parseInt(arr[1]);
								d = Integer.parseInt(arr[2]);
							}
						}
						if(((m==1||m==3)||(m==5||m==7))||((m==8||m==10)||m==12)) {
							if(d>31) {
								arr[1]=Integer.toString(m+1);
								arr[2]=Integer.toString(d-31);
								m = Integer.parseInt(arr[1]);
								d = Integer.parseInt(arr[2]);
								if(m>12) {
									arr[0]=Integer.toString(y+1);
									arr[1]=Integer.toString(m-12);
									y = Integer.parseInt(arr[0]);
									m = Integer.parseInt(arr[1]);
								}
							}

						}
					}while(temp!=arr[2]);
					due = arr[0]+"/"+arr[1]+"/"+arr[2];
					TodoItem t = new TodoItem(cate,title,desc,due,0,is_rep,cycle);
					if(addItem(t)>0) {
						System.out.println("다음 주기의 항목이 생성되었습니다.");
					}
				}
			}
			stat.close();
		} catch (SQLException e) {	
			e.printStackTrace();
		}
		return;
	}
}
