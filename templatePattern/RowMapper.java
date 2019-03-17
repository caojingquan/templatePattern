package templatePattern;

import java.sql.ResultSet;

public interface RowMapper<T> {

	T mapRow(ResultSet rs,int index);
}
