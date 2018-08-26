package daoTest;

import org.junit.Test;

import beans.NetpackGps;
import cluster.DelStartPoints;
import dp.ENPoint;
import dp.TrajectoryCompressionMain;

/**
 * @author lzl
 * @version 创建时间：2018年8月25日 下午7:49:06
 * @comment
 */
public class TestArrayList {

    private static double distance1;
    private static double distance2;

    @Test
    public void testArrayList() {
        DelStartPoints delStartPoints = new DelStartPoints();
        TrajectoryCompressionMain dp = new TrajectoryCompressionMain();
        NetpackGps a = new NetpackGps();
        NetpackGps b = new NetpackGps();
        a.setGeo_l(114.549559);
        a.setGeo_b(38.044023);
        b.setGeo_l(114.549254);
        b.setGeo_b(34.100000);
        ENPoint pA = new ENPoint();
        ENPoint pB = new ENPoint();
        pA.pe = 38.044023;
        pA.pn = 114.549559;
        pB.pe = 34.100000;
        pB.pn = 114.549254;
        distance1 = dp.geoDist(pA, pB);
        distance2 = delStartPoints.euclideanDistance(a, b);
        System.out.println(distance1);
        System.out.println(distance2);
    }
}
