package com.example.demo;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.InputStream;

import org.dbunit.Assertion;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.h2.H2DataTypeFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class OrderApiTest {
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	void clean() {
		jdbcTemplate.execute("TRUNCATE TABLE order_info");
	}

	@Test
	void test() throws Exception {
		mockMvc.perform(post("/orders")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{
							"itemName": "ノートPC",
							"quantity": 2
						}
						"""))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.itemName").value("ノートPC"))
				.andExpect(jsonPath("$.quantity").value(2));

		try (var con = jdbcTemplate.getDataSource().getConnection()) {
			IDatabaseConnection dbUnitCon = new DatabaseConnection(con);

			DatabaseConfig config = dbUnitCon.getConfig();
			config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new H2DataTypeFactory());

			IDataSet actualDataSet = dbUnitCon.createDataSet();
			ITable actual = actualDataSet.getTable("ORDER_INFO");
			ITable actualFilter = DefaultColumnFilter.includedColumnsTable(actual,
					new String[] { "ITEM_NAME", "QUANTITY" });

			String xmlPass = "/expected2.xml";
			ITable expected = readDataSet(xmlPass).getTable("ORDER_INFO");

			Assertion.assertEquals(expected, actualFilter);
		}
	}

	private FlatXmlDataSet readDataSet(String xmlPass) throws DataSetException {
		InputStream is = getClass().getResourceAsStream(xmlPass);
		assertThat(is).isNotNull();
		return new FlatXmlDataSetBuilder().build(is);
	}
}
