package templatePattern;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public abstract class JDBCTemplate {
	private DataSource dataSource;

	public JDBCTemplate(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public Object templateMethod(String sql,Object[] values,RowMapper<?> rm){
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try{
			//先获取连接
			conn = this.dataSource.getConnection();
			//创建执行计划
			ps = conn.prepareStatement(sql);
			//设置参数并且执行语句集
			Object obj = this.executeQuery(ps,values); 
			//处理结果集
			if(needParseResult())return this.parseResult(obj,rm);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.closeSomething(conn,ps,rs);
		}
		return null;
	}
	
	//实现的具体的操作
	abstract Object implementsMethod(PreparedStatement ps) throws Exception;
	
	final Object parseResult(Object obj,RowMapper<?> rm) throws SQLException {
		if(obj instanceof ResultSet){
			List<Object> list = new ArrayList<Object>(); 
			int index = 0;
			ResultSet rs = (ResultSet)obj;
			while(rs.next()){
				list.add(rm.mapRow(rs,index++));
			}
			return list;
		} else if(obj instanceof Integer){
			return (Integer)obj;
		} else {
			return null;
		}
	}

	/**
	 * 钩子方法，来实现模板的微调，默认需要处理结果集
	 * @return
	 */
	protected boolean needParseResult(){
		return true;
	}
	
	//将参数赋值给执行计划中的占位符，然后获取返回值
	 final Object executeQuery(PreparedStatement ps,Object[] values) throws Exception{
		 if(null != values){
			 for(int i=0;i<values.length;i++){
				 ps.setObject(i+1, values[i]);
			 }
		 }
		 Object obj = implementsMethod(ps);
		return obj;
	}
	 
	 final void closeSomething(Connection conn,PreparedStatement ps,ResultSet rs){
		 try{
			 if(null != conn)conn.close();
			 if(null != ps)ps.close();
			 if(null != rs)rs.close();
		 }catch(Exception e){
			 e.printStackTrace();
		 }
	 }

}
