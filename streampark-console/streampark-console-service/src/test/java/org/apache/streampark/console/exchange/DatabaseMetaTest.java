package org.apache.streampark.console.exchange;

import org.apache.streampark.console.StreamParkConsoleBootstrap;
import org.apache.streampark.console.exchange.bean.ColumnsResponse;
import org.apache.streampark.console.exchange.service.DatabaseService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
    classes = StreamParkConsoleBootstrap.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DatabaseMetaTest {

  @Autowired DatabaseService databaseService;

  @Test
  public void testGetTables() throws Exception {
    List<String> tables = databaseService.getTablesByDbId(1L);
    System.out.println(tables);
  }

  @Test
  public void testGetColumns() throws Exception {
    ColumnsResponse columnsResponse = databaseService.getColumnsByTable(1L, "t_access_token");
    System.out.println(columnsResponse);
  }
}
