package com.glassis5;

import java.sql.ResultSet;
import java.util.ArrayList;

public class Dao extends Da {

	// (1/5) 삭제
	// 게시물 번호(no)에 해당하는 글을 삭제하는 메서드
	public void del(String no) {
		connect(); // [고정 1,2,3]:데이터베이스 연결 connect or super.connect
		// SQL DELETE 쿼리를 생성하여 특정 번호의 게시글을 삭제
		String sql = String.format("delete from %s where b_no=%s", Db.TABLE_PS_BOARD_FREE, no);
		super.update(sql);
		super.close(); // [고정 4, 5]: 데이터베이스 연결해제
	}

	// (2/5) 쓰기
	// 새로운 게시글을 작성하는 메서드
	public void write(Dto d) {
		connect(); // [고정 1,2,3] : 데이터베이스연결, connect or super.connect
		// SQL INSERT 쿼리를 사용하여 새로운 게시글 작성
		String sql = String.format("insert into %s (b_title, b_id, b_text) values ('%s', '%s', '%s')",
				Db.TABLE_PS_BOARD_FREE, d.title, d.id, d.text);
		super.update(sql);
		super.close(); // [고정 4,5]: 데이터베이스 연결해제
	}

	// (3/5) 읽기
	// 특정 게시물 번호(no)에 해당하는 글을 읽어와서 Dto 객체로 변환하는 메서드
	public Dto read(String no) {
		connect(); // [고정 1,2,3] : DB연결 , connect or super.connect
		Dto post = null;
		try {
			// SQL SELECT 쿼리를 사용하여 특정 번호의 게시물을 읽음
			String sql = String.format("select * from %s where b_no = %s", Db.TABLE_PS_BOARD_FREE, no);
			System.out.println("sql" + sql); // 디버깅용 출력
			ResultSet rs = st.executeQuery(sql);
			rs.next();
			// 게시물 정보를 Dto 객체에 담음
			post = new Dto(rs.getString("B_NO"), rs.getString("B_TITLE"), rs.getString("B_ID"),
					rs.getString("B_DATETIME"), rs.getString("B_HIT"), rs.getString("B_TEXT"),
					rs.getString("B_REPLY_COUNT"), rs.getString("B_REPLY_ORI")

			);

		} catch (Exception e) {
			e.printStackTrace(); // 오류 시 콘솔 스택출력
		}
		super.close(); // [고정 4,5]: DB연결해제
		return post;
	}

	// (4/5) 리스트
	// 게시글 리스트를 반환하는 메서드 (페이징 처리포함)
	public ArrayList<Dto> list(String page) {
		connect(); // [고정 1,2,3] : DB연결 (connect or super.connect)
		ArrayList<Dto> posts = new ArrayList<>();
		try {

			int startIndex = ((Integer.parseInt(page)) - 1) * Board.LIST_AMOUNT; // 시작 인덱스
			// SQL SELECT 쿼리를 이용해 게시물 리스트를 가져옴 (페이징처리)
			String sql = String.format("select * from %s limit %s, %s", Db.TABLE_PS_BOARD_FREE, startIndex,
					Board.LIST_AMOUNT);
			System.out.println("sql:" + sql); // 디버깅용 출력
			ResultSet rs = st.executeQuery(sql);

			while (rs.next()) {
				// 게시글 정보를 Dto 객체에 담고 list에 추가
				posts.add(new Dto(rs.getString("B_NO"), rs.getString("B_TITLE"), rs.getString("B_ID"),
						rs.getString("B_DATETIME"), rs.getString("B_HIT"), rs.getString("B_TEXT"),
						rs.getString("B_REPLY_COUNT"), rs.getString("B_REPLY_ORI")));
			}
		} catch (Exception e) {
			e.printStackTrace(); // 오류시 스택출력
		}

		super.close(); // [고정 4,5] : 데이터베이스 연결해제
		return posts;
	}

	// (5/5) 수정
	// 특정 게시물 번호(no)에 해당하는 글을 수정하는 메서드
	public void edit(Dto d, String no) {

		connect(); // [고정 1,2,3]: DB연결 (connect or super.connect)
		// SQL UPDATE 쿼리를 사용하여 특정번호의 게시글 수정
		String sql = String.format("update %s set b_title = '%s', b_text='%s' where b_no=%s", Db.TABLE_PS_BOARD_FREE,
				d.title, d.text, no);
		super.update(sql);
		super.close(); // [고정 4,5]: 데이터베이스 연결해제
	}

	// 총 글 수 구하기
	// 게시판에 있는 전체 게시글 수를 반환하는 메서드
	public int getPostCount() {

		int count = 0;
		connect(); // [고정 1,2,3]: DB연결 (connect or super.connect)

		try {
			// SQL SELECT 쿼리를 사용하여 총 게시글 수 구하기
			String sql = String.format("select count(*) from %s", Db.TABLE_PS_BOARD_FREE);
			System.out.println("sql:" + sql); // 디버깅 용 출력
			ResultSet rs = st.executeQuery(sql);
			rs.next();
			count = rs.getInt("count(*)"); // 게시글 수 저장
		} catch (Exception e) {
			e.printStackTrace();
		}

		super.close(); // [고정 4,5] : DB연결해제
		return count;
	}

	// 검색된 총 글 수 구하기
	// 검색된 게시글 수를 반환하는 메서드
	public int getSearchPostCount(String word) {
		int count = 0;
		connect(); // [고정 1,2,3]: DB연결 (connect or super.connect)
		try {
			// SQL SELECT 쿼리를 사용해 특정 키워드로 검색된 게시물 수를 구함
			String sql = String.format("select count (*) from %S where b_title like '%%%s%%'", Db.TABLE_PS_BOARD_FREE,
					word);
			System.out.println("sql:" + sql); // 디버깅용 출력
			ResultSet rs = st.executeQuery(sql);
			rs.next();
			count = rs.getInt("count(*)"); // 검색된 게시글 수 저장
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.close(); // [고정 4,5] : DB연결해제
		return count;
	}

	// 검색된 글 리스트
	// 특정 키워드로 검색된 게시글 리스트를 반환하는 메서드 (페이징처리 포함)
	public ArrayList<Dto> listSearch(String word, String page) {
		connect(); // [고정 1,2,3]: DB연결 (connect or super.connect)
		ArrayList<Dto> posts = new ArrayList<>();
		try {
			// 시작 인덱스 계산
			int startIndex = ((Integer.parseInt(page)) - 1) * Board.LIST_AMOUNT;
			// SQL SELECT 쿼리를 사용하여 특정 키워드로 검색된 게시물 리스트를 가져옴 (페이징처리)
			String sql = String.format("select * from %s where b_title like '%%%s%%' limit %s,%s",
					Db.TABLE_PS_BOARD_FREE, word, startIndex, Board.LIST_AMOUNT);
			System.out.println("sql:" + sql); // 디버깅용 출력
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				// 게시물 정보를 Dto 객체에 담고 리스트에 추가
				posts.add(new Dto(

						rs.getString("B_NO"), rs.getString("B_TITLE"), rs.getString("B_ID"), rs.getString("B_DATETIME"),
						rs.getString("B_HIT"), rs.getString("B_TEXT"), rs.getString("B_REPLY_COUNT"),
						rs.getString("B_REPLY_ORI")

				));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.close(); // [고정 4,5]: DB연결해제
		return posts;
	}

	// 총 페이지 수 구하기
	// 전체 게시글 수를 기준으로 총 페이지 수를 구하는 메서드
	public int getTotalPageCount() {
		int totalPageCount = 0;
		int count = getPostCount(); // 총 게시물 수를 가져옴

		if (count % Board.LIST_AMOUNT == 0) { // case 1: 나머지가 없는 겅우
			totalPageCount = count / Board.LIST_AMOUNT;
		} else { // case 2: 나머지가 있는 경우 (추가 페이지 필요)
			totalPageCount = count / Board.LIST_AMOUNT + 1;
		}
		return totalPageCount;
	}

	// 검색된 총 페이지 수 구하기
	// 검색된 게시글 수를 기준으로 총 페이지수를 구하는 메서드

	public int getSearchTotalPageCount(String word) {
		int totalPageCount = 0;
		int count = getSearchPostCount(word); // 검색된 게시물 수 가져옴

		if (count % Board.LIST_AMOUNT == 0) {
			// case 1: 나머지가 없는겅우
			totalPageCount = count / Board.LIST_AMOUNT;
		} else {
			// case 2: 나머지가 있는경우 (추가 페이지 필요)
			totalPageCount = count / Board.LIST_AMOUNT + 1;
		}

		return totalPageCount;
	}

}
