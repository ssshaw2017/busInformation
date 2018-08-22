package daoTest;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;

import beans.NetpackGps;
import dao.DAO;

public class TestDao {
    private static Logger log = Logger.getLogger(TestDao.class);
    private DAO dao = new DAO();

    // 以下测试方法是测试从数据库查询一条记录给相应的Javabean的属性赋值，并在控制台显示出一条打印结果
    /*
     * @Test public void testquery() throws ClassNotFoundException,
     * SQLException, IOException { String sql =
     * "select * from trade where tradeid=?"; NetpackGps1 netpackGps1 = new
     * NetpackGps1(); netpackGps1 = (NetpackGps1) dao.query(NetpackGps1.class,
     * sql, 1); System.out.println("tradeid:" + netpackGps1.getBusstopName());
     * 
     * }
     */
    // 以下测试方法是从数据库查询后的结果（不只一条记录）给相应的Javabean的属性赋值，并在控制台显示出多条打印结果
    @Test
    public void testquerylist() {
        String sql = "select ng.datetime as datetime from netpack_gps2 ng where ng.datetime like '2018-01-11%' and ng.bus_no=43";
        log.info("----------------查询列表开始---------------------------------------");
        List<NetpackGps> netpackGps = new ArrayList<NetpackGps>();
        // 查询
        netpackGps = (List<NetpackGps>) dao.querylist(NetpackGps.class, sql);
        // 遍历输出查询结果
        for (NetpackGps netpackgps : netpackGps) {
            System.out.println("入库时间为：" + netpackgps.getDatetime());
            System.out.println(netpackGps.size());
        }

    }
}
