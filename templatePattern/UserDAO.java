package templatePattern;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

public class UserDAO extends QueryTemplate{

	public UserDAO(DataSource dataSource) {
		super(dataSource);
	}
	
	public UserInfo selectAll(){
		String sql = "select * from emp where job=?";
		UserInfo user = (UserInfo)this.templateMethod(sql, new Object[]{"工人"}, new RowMapper(){
			@Override
			public Object mapRow(ResultSet rs, int index) {
				try {
					Object obj= rs.getObject(index);
					UserInfo user = (UserInfo)obj;
					return user;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
		});
		return user;
	}
	

}
