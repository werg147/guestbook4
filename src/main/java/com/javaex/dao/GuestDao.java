package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.GuestVo;

@Repository
public class GuestDao {

	//필드
	@Autowired
	private DataSource dataSource;
	
	// 0. import java.sql.*;
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	// DB접속
	private void getConnection() {
		try {
			
			conn = dataSource.getConnection();

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	// 자원정리
	private void close() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	// 리스트
	public List<GuestVo> getGuestList() {

		List<GuestVo> guestList = new ArrayList<GuestVo>();

		getConnection();

		try {
			String query = "";
			query += " select no, ";
			query += "        name, ";
			query += "        password, ";
			query += "        content, ";
			query += "        reg_date ";
			query += " from guestbook ";
			query += " order by no desc ";

			pstmt = conn.prepareStatement(query);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				String password = rs.getString("password");
				String content = rs.getString("content");
				String regDate = rs.getString("reg_date");

				GuestVo vo = new GuestVo(no, name, password, content, regDate);
				guestList.add(vo);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();

		return guestList;
	}

	// 등록
	public int guestInsert(GuestVo guestVo) {

		getConnection();

		int count = 0;

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " insert into guestbook ";
			query += " values(seq_no.nextval, ?, ?, ?, sysdate) ";

			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, guestVo.getName());
			pstmt.setString(2, guestVo.getPassword());
			pstmt.setString(3, guestVo.getContent());

			count = pstmt.executeUpdate();

			// 4.결과처리
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();

		return count;
	}

	// 삭제
	public int guestDelete(GuestVo guestVo) { //delete 비밀번호 비교 추가

		getConnection();

		int count = 0;

		try {

			String query = "";
			query += " delete from guestbook ";
			query += " where no = ? ";
			query += " and password = ? ";

			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, guestVo.getNo() );
			pstmt.setString(2, guestVo.getPassword());

			count = pstmt.executeUpdate();
			// System.out.println("삭제" + count);

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();

		return count;
	}
	
	//1명 정보 가져오기
	public GuestVo getGuest(int no) {
		
		getConnection();
		
		GuestVo guestVo = null;
		
		try {
			String query = "";
			query += " select no, ";
			query += "        name, ";
			query += "        password, ";
			query += "        content, ";
			query += "        reg_date ";
			query += " from guestbook ";
			query += " where no = ? ";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				int No = rs.getInt("no");
				String name = rs.getString("name");
				String passWord = rs.getString("password");
				String content = rs.getString("content");
				String regDate = rs.getString("reg_date");

				GuestVo vo = new GuestVo(No, name, passWord, content, regDate);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		
		return guestVo;
	}

}
