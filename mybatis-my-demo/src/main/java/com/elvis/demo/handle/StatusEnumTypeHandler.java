package com.elvis.demo.handle;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.elvis.demo.model.OrderState;

public class StatusEnumTypeHandler extends BaseTypeHandler<OrderState>{

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, OrderState parameter, JdbcType jdbcType)
			throws SQLException {
		// TODO Auto-generated method stub
		ps.setInt(i, parameter.ordinal());
	}

	@Override
	public OrderState getNullableResult(ResultSet rs, String columnName) throws SQLException {
		// TODO Auto-generated method stub
		int status = rs.getInt(columnName);
		return valueOf(status);
	}

	@Override
	public OrderState getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		int status = rs.getInt(columnIndex);
		return valueOf(status);
	}

	@Override
	public OrderState getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		int status = cs.getInt(columnIndex);
		return valueOf(status);
	}
	public OrderState valueOf(int status) {
		switch (status) {
		case 0:
			return OrderState.INIT;
		case 1:
			return OrderState.PAID;
		case 2:
			return OrderState.BREWING;
		case 3:
			return OrderState.BREWED;
		case 4:
			return OrderState.TAKEN;
		case 5:
			return OrderState.CANCELLED;
		default:
			return null;
		}
	}
}
