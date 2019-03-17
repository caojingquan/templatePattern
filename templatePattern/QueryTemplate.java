package templatePattern;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

public class QueryTemplate  extends JDBCTemplate{

	public QueryTemplate(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	List<?> implementsMethod(PreparedStatement ps) throws SQLException {
		return (List)ps.executeQuery();
	}
	

}
