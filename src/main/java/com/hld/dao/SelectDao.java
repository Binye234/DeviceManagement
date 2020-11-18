package com.hld.dao;

import java.util.*;
import java.sql.ResultSet;

public interface SelectDao {
    public List select(String sql, Object... arg);
}
