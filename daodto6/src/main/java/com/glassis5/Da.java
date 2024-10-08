package com.glassis5;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Da {

	// 데이터베이스(DB) 연결을 위한 Connection 객체
	Connection con = null;

	// SQL 실행을 위한 Statement 객체
	Statement st = null;

	// 데이터베이스에 연결하는 메서드
	void connect() {

		try {
			// 고정 1: JDBC 드라이버 로드
			Class.forName(Db.DB_JDBC_DRIVER_PACKAGE_PATH);
			// 고정 2: 데이터베이스 연결설정 (url, id, pw사용)
			con = DriverManager.getConnection(Db.DB_URL, Db.DB_ID, Db.DB_PW);
			// 고정 3: Statement 객체 생성 (SQL 실행준비)
			st = con.createStatement();

		} catch (Exception e) {
			// 예외발생시 스택추적
			e.printStackTrace();
		}
	}

	// SQL 업데이트 명령을 실행하는 메서드(insert, update, delete등)
	void update(String sql) {
		try {
			st.executeUpdate(sql); // 전달된 SQL을 실행
		} catch (Exception e) {
			e.printStackTrace();// 예외발생 시 스택추적
		}
	}

	// 데이터베이스 연결을 닫는 메서드
	void close() {
		try {
			// 고정 4: Statement 객체 닫기
			st.close();
			// 고정 5: Connection 객체 닫기(DB연결해제)
			con.close();
		} catch (Exception e) {
			e.printStackTrace(); // 예외발생시 스택추적
		}
	}

}
