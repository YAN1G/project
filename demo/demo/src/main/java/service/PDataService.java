package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import mapper.PDataDao;
import pojo.C3P0Utils;
import pojo.Data;
import pojo.Geohash;

public class PDataService {
	 private PDataDao dataDao=new PDataDao();
	 public String insert(int id,double lat,double lon) {
		 Geohash geohash=new Geohash();
			String hash=geohash.encode(lat, lon);
			Connection con = C3P0Utils.getConnection();
			// ����������
			try {
				// ������������
				if (!con.isClosed())
					System.out.println("Succeeded connecting to the Database!");

			
				// 2.����statement���������ִ��SQL��䣡��
				String sql=dataDao.insert(id, lat, lon, hash);
				PreparedStatement statement = con.prepareStatement(sql);
				statement.executeUpdate();
			} catch (Exception e) {
				System.out.println("connection fail!");
				e.printStackTrace();
			} finally {
				try {
					if (con != null)
						con.close();
				} catch (SQLException e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				}
			}

		return "success";
	 }

	 public List<Data> getList1(double minX,double minY,double maxX,double maxY){
		 Geohash geohash=new Geohash();
			String hash1=geohash.encode(minX, minY);//把经纬度坐标转为hash值
			String hash2=geohash.encode(maxX, maxY);
			Statement stmt = null;
			ResultSet rs = null;
			Connection con = null;
			List<Data> list1 = new ArrayList<>();
			try {
				con = C3P0Utils.getConnection();
				if (!con.isClosed())
					System.out.println("Succeeded connecting to the Database!");
			
				stmt = con.createStatement();
			    String sql=dataDao.getLi(hash1, hash2);
				rs = stmt.executeQuery(sql); //查询hash1和hash2之间范围的点
				while (rs.next()) { // 逐行扫描
					int id = rs.getInt("id");
					String hash = rs.getString("hash");
				    double lat=rs.getDouble("lat");
				    double lon=rs.getDouble("lon");
					Data a = new Data(id, hash, lat, lon);
					list1.add(a);
				}
			} catch (Exception e) {
				System.out.println("connection fail!");
				e.printStackTrace();
			} finally {
				try {
					if (rs != null)
						rs.close();
					if (stmt != null)
						stmt.close();
					if (con != null)
						con.close();

				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
			System.out.print(list1);
			return list1;
}

	 
	public List<Data> getList2(String hash1,String hash2){
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		List<Data> list1 = new ArrayList<>();
		try {
			con = C3P0Utils.getConnection();
			if (!con.isClosed())
				System.out.println("Succeeded connecting to the Database!");
		
			stmt = con.createStatement();
			String sql=dataDao.getLi(hash1, hash2);
			rs = stmt.executeQuery(sql); //查询hash1和hash2之间范围的点
			while (rs.next()) { // 逐行扫描
				int id = rs.getInt("id");
				String hash = rs.getString("hash");
			    double lat=rs.getDouble("lat");
			    double lon=rs.getDouble("lon");
				Data a = new Data(id, hash, lat, lon);
				list1.add(a);
			}
		} catch (Exception e) {
			System.out.println("connection fail!");
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (con != null)
					con.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		System.out.print(list1);
		return list1;
	 }
}
	 
