package mapper;
public class PDataDao {
	  public String insert(int id, double lat,double lon,String hash) {
				String sql = "insert into geo(id,lon,lat,hash) values('" + id + "','" + lon + "','" + lat + "','" + hash + "') ";	
			return sql;
	  }
	  public String getLi(String hash1,String hash2) {
		  String sql="select id,hash,lat,lon from geo where hash>='" + hash1 + "' and hash<='" + hash2 + "'";
			return sql;
		}
	  
}
