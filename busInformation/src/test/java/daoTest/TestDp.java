package daoTest;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;

import beans.NetpackGps;
import cluster.DelEndPoints;
import cluster.DelStartPoints;
import convertors.DpConvert;
import dao.DAO;
import dp.DouglasPeucker;

/**
 * @author lzl
 * @version 创建时间：2018年8月20日
 * @comment 道格拉斯抽稀测试类
 */
public class TestDp {
    private static Logger log = Logger.getLogger(TestDp.class);
    public static double DMax = 500.0;
    public static int start = 0;
    private DAO dao = new DAO();

    // 以下测试方法是从数据库查询后的结果（不只一条记录）给相应的Javabean的属性赋值，并在控制台显示出多条打印结果
    @Test
    public void testquerylist() throws ParseException {
        PropertyConfigurator.configure("log4j.properties");
        String sql = "select ng.datetime as datetime,ng.geo_l as geo_l,ng.geo_b as geo_b from netpack_gps2 ng where ng.datetime "
                + "like '2018-01-11%' and ng.line_no=1 and ng.updown=1 and ng.bus_no=?";// 在这个地方输入车辆号，可选2810~2915
        String sql2 = "select distinct ng.bus_no as bus_no from netpack_gps2 ng where ng.datetime like '2018-01-11%' and ng.line_no=1 and ng.updown=1";
        log.info("----------------查询列表开始---------------------------------------");
        List<NetpackGps> netpackGps = new ArrayList<NetpackGps>();// 原始数据集
        List<NetpackGps> delStartGps = new ArrayList<NetpackGps>();// 已删除起点集
        NetpackGps startGps = new NetpackGps();// 起点几何中心点
        List<NetpackGps> delEndGps = new ArrayList<NetpackGps>();// 已删除终点集
        NetpackGps endPoint = new NetpackGps();// 终点几何中心点
        List<NetpackGps> BaiduGps = new ArrayList<NetpackGps>();// 百度坐标点
        DpConvert dpConvert = new DpConvert();
        List<NetpackGps> dpedPoints = new ArrayList<NetpackGps>();
        DelStartPoints delStart = new DelStartPoints();
        DelEndPoints delEndPoints = new DelEndPoints();
        DouglasPeucker douglasPeucker = new DouglasPeucker();
        List<NetpackGps> busList = new ArrayList<NetpackGps>();// 车辆集
        // 查询车辆集
        busList = (List<NetpackGps>) dao.querylist(NetpackGps.class, sql2);
        // 循环车辆集查询
        for (int i = 0; i < busList.size(); i++) {
            System.out.println("查询第" + i + "条车辆数据开始，车辆号为：" + busList.get(i).getBus_no());
            // 查询
            netpackGps = (List<NetpackGps>) dao.querylist(NetpackGps.class, sql, busList.get(i).getBus_no());
            System.out.println("查询到的netpackGps数据总数为:" + netpackGps.size());
            // 删除起点
            delStartGps = delStart.delStartPoints(netpackGps);// 得到删除起点后的数据集
            System.out.println("删除起点后的数据总数为:" + delStartGps.size());
            startGps = delStart.getStartpoint(netpackGps);// 得到起点的几何中心点
            System.out.println("起点的几何中心点为 " + startGps.getGeo_b() + "," + startGps.getGeo_l());
            // 删除终点
            delEndGps = delEndPoints.deleteEnds(delStartGps);// 得到删除终点后的数据集
            System.out.println("删除终点后的数据总数为：" + delEndGps.size());
            endPoint = delEndPoints.getEnds(delStartGps);// 得到终点的几何中心点
            System.out.println("终点的几何中心点为" + endPoint.getGeo_b() + "," + endPoint.getGeo_l());
            // douglas-pucker算法
            int end = delEndGps.size();
            dpedPoints = douglasPeucker.TrajCompressC(startGps, endPoint, delEndGps, dpedPoints, start, end, DMax);
            System.out.println("转化成百度后的总数为" + dpedPoints.size() + "，输出如下：");
            // 输出转化成百度坐标后的点
            for (NetpackGps point : dpedPoints) {
                dpConvert.wgs84Tobd09(point);
                BaiduGps.add(point);
                System.out.println("new BMap.Point(" + point.getGeo_l() + "," + point.getGeo_b() + "),");
            }
        }

    }
}
