package codenet.test;

import java.util.List;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import codenet.bean.DatasetBean;
import codenet.jdbc.DatasetJDBC;

public class DatasetJDBCTest {

	@Test
	public void testgetAll(){
		List<DatasetBean> ds = (List<DatasetBean>) DatasetJDBC.getAll();
//		for(DatasetBean d : ds)
//			System.out.println(d.toString());
		Gson builder = new GsonBuilder().create();
		System.out.println(builder.toJson(ds));
	}
	
	
}
