package daoTest;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;

import beans.NetpackGps;
import cluster.DelStartPoints;
import dao.DAO;

/**
 * @author lzl
 * @version 创建时间：2018年8月20日
 * @comment 道格拉斯抽稀测试类
 */
public class TestDp {
    private static Logger log = Logger.getLogger(TestDp.class);
    private DAO dao = new DAO();

    // 以下测试方法是从数据库查询后的结果（不只一条记录）给相应的Javabean的属性赋值，并在控制台显示出多条打印结果
    @Test
    public void testquerylist() {
        String sql = "select ng.datetime as datetime，ng.geo_l as geo_l,ng.geo_b as geo_b from netpack_gps2 ng where ng.datetime like '2018-01-11%' and ng.bus_no=43";
        log.info("----------------查询列表开始---------------------------------------");
        List<NetpackGps> netpackGps = new ArrayList<NetpackGps>();
        List<NetpackGps> delStartGps = new ArrayList<NetpackGps>();
        // 查询
        netpackGps = (List<NetpackGps>) dao.querylist(NetpackGps.class, sql);
        // 删除起点
        DelStartPoints del = new DelStartPoints();
        delStartGps = del.delStartPoints(netpackGps);
        // 删除终点
        // 遍历输出查询结果
        for (NetpackGps netpackgps : netpackGps) {
            System.out.println("入库时间为：" + netpackgps.getDatetime());
            System.out.println(netpackGps.size());
        }

    }
}
