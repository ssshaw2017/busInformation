package dp;

import java.util.List;

import beans.NetpackGps;
import cluster.DelStartPoints;

/**
 * @author lzl
 * @version 创建时间：2018年8月21日 上午10:42:36
 * @comment Douglas-Peucker算法实现
 */
public class DouglasPeucker {
    DelStartPoints delStartPoints = new DelStartPoints();

    public List<NetpackGps> TrajCompressC(List<NetpackGps> startGps, List<NetpackGps> netpackGps,
            List<NetpackGps> dpedPoints, int start, int end, double DMax) {
        if (start < end) {// 递归进行的条件
            double maxDist = 0;// 最大距离
            int cur_pt = 0;// 当前下标
            for (int i = start + 1; i < end; i++) {
                double curDist = distToSegment(startGps.get(0), startGps.get(1), netpackGps.get(i));// 当前点到对应线段的距离
                if (curDist > maxDist) {
                    maxDist = curDist;
                    cur_pt = i;
                } // 求出最大距离及最大距离对应点的下标
            }
            // 若当前最大距离大于最大距离误差
            if (maxDist >= DMax) {
                dpedPoints.add(netpackGps.get(cur_pt));// 将当前点加入到过滤数组中
                // 将原来的线段以当前点为中心拆成两段，分别进行递归处理
                TrajCompressC(startGps, netpackGps, dpedPoints, start, cur_pt, DMax);
                TrajCompressC(startGps, netpackGps, dpedPoints, cur_pt, end, DMax);
            }
        }
        return dpedPoints;
    }

    /**
     * 函数功能：使用三角形面积（使用海伦公式求得）相等方法计算点pX到点pA和pB所确定的直线的距离
     * 
     * @param pA：起始点
     * @param pB：结束点
     * @param pX：第三个点
     * @return distance：点pX到pA和pB所在直线的距离
     */
    public double distToSegment(NetpackGps pA, NetpackGps pB, NetpackGps pX) {
        double a = Math.abs(delStartPoints.euclideanDistance(pA, pB));
        double b = Math.abs(delStartPoints.euclideanDistance(pA, pX));
        double c = Math.abs(delStartPoints.euclideanDistance(pB, pX));
        double p = (a + b + c) / 2.0;
        double s = Math.sqrt(Math.abs(p * (p - a) * (p - b) * (p - c)));
        double d = s * 2.0 / a;
        return d;
    }
}
