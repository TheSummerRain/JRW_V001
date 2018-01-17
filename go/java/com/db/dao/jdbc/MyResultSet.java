package com.db.dao.jdbc;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Time;

public class MyResultSet {

	ResultSet rset;
	Connection conn = null;

	public MyResultSet() {
	}

	public void setMyResuleSet(ResultSet Myrset) {
		rset = Myrset;
	}

	public Connection getMyConnection() {
		return conn;
	}

    public void setMyConnection(Connection Myconn) {
        conn = Myconn;
    }

	public ResultSet getMyrset() {
		return rset;
	}

	public String getString(int i) throws java.sql.SQLException {
		if (rset == null) {
			return ("");
        } else {
            return rset.getString(i);
        }
    }

	public String getString(String s) throws java.sql.SQLException {
		if (rset == null) {
			return ("");
        } else {
            return rset.getString(s);
        }
    }

	public int getInt(String s) throws java.sql.SQLException {
		if (rset == null) {
			return (-1);
        } else {
            return rset.getInt(s);
        }
    }

	public Date getDate(String s) throws java.sql.SQLException {
		return rset.getDate(s);
	}

	public Date getDate(int i) throws java.sql.SQLException {
		return rset.getDate(i);
	}

	public Time getTime(String s) throws java.sql.SQLException {
		return rset.getTime(s);
	}

    public int getType() throws java.sql.SQLException {
        return rset.getType();
    }

	public Time getTime(int i) throws java.sql.SQLException {
		return rset.getTime(i);
	}

	public int getInt(int s) throws java.sql.SQLException {
		return rset.getInt(s);
	}

    public byte[] getBytes(int i) throws java.sql.SQLException {
        return rset.getBytes(i);
    }

    public byte[] getBytes(String s) throws java.sql.SQLException {
        return rset.getBytes(s);
    }

	@SuppressWarnings("deprecation")
	public InputStream getUnicodeStream(String s) throws java.sql.SQLException {
		return rset.getUnicodeStream(s);
	}

	@SuppressWarnings("deprecation")
	public InputStream getUnicodeStream(int s) throws java.sql.SQLException {
		return rset.getUnicodeStream(s);
	}

	public double getDouble(String s) throws java.sql.SQLException {
		return rset.getDouble(s);
	}

	public double getDouble(int s) throws java.sql.SQLException {
		return rset.getDouble(s);
	}

	public float getFloat(String s) throws java.sql.SQLException {
		return rset.getFloat(s);
	}

	public float getFloat(int s) throws java.sql.SQLException {
		return rset.getFloat(s);
	}

	public boolean next() throws java.sql.SQLException {
		if (rset == null) {
			return (false);
        } else {
            return rset.next();
        }
    }

	public boolean previous() throws java.sql.SQLException {
		if (rset == null) {
			return (false);
        } else {
            return rset.previous();
        }
    }

	public void close() throws java.sql.SQLException {
        if (rset != null) {
            rset.close();
        }
    }
}
