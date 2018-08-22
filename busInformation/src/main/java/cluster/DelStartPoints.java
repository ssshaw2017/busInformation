package cluster;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import beans.NetpackGps;

/**
 * @author lzl
 * @version 创建时间：2018年8月20日 上午12:02:36
 * @comment 删除起点坐标，里面包括删除起点方法delStartPoints，获得起点几何中心点方法getStartPoint，计算两点之间距离euclideanDistance，获取当前点邻居obtainNeighbors
 *          聚类方法mergeCluster和cluster
 */
public class DelStartPoints {
    public static int clusterIdMax = 0;// 最大的clusterId值
    public static int clusterIdMost = 0;// 最多的clusterId值
    public static double delRadius = 60;// 删除起点半径
    public static double Epislon = 30;// 聚类半径
    public static int MinPts = 3;// 密度、最小点个数
    List<NetpackGps> delStartGps = new ArrayList<NetpackGps>();// 已删除起点的数据集
    List<NetpackGps> startGps = new ArrayList<NetpackGps>();// 起点几何中心点

    // 定义方法删除起点delStartPoint
    public List<NetpackGps> delStartPoints(List<NetpackGps> netpackGps) {
        // 遍历所有点，给每个点都赋值：非核心点，和未访问过
        for (NetpackGps netpackGps2 : netpackGps) {
            netpackGps2.setKey(false);
            netpackGps2.setVisited(false);
        }
        // 对netpackGps所有点进行聚类
        cluster(netpackGps);
        // 起点周围点
        deleteSurroundings(netpackGps);
        return delStartGps;
    }

    // 计算两个点的经纬度距离转化成米
    public static double EARTH_RADIUS = 6371.393;

    public static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    public double euclideanDistance(NetpackGps a, NetpackGps b) {
        double distance = 0.0;
        double radLat1 = rad(a.getGeo_l());
        double radLat2 = rad(b.getGeo_l());
        double m = radLat1 - radLat2;
        double n = rad(a.getGeo_b()) - rad(b.getGeo_b());
        distance = 2 * Math.asin(Math.sqrt(
                Math.pow(Math.sin(m / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(n / 2), 2)));
        distance = distance * EARTH_RADIUS;
        distance = Math.round(distance * 1000);
        return distance;
    }

    // 获取当前点的邻居
    public List<NetpackGps> obtainNeighbors(NetpackGps current, List<NetpackGps> points) {
        List<NetpackGps> neighbors = new LinkedList<>();
        for (NetpackGps point : points) {
            double distance = euclideanDistance(current, point);
            if (distance <= Epislon) {
                neighbors.add(point);
            }
        }
        return neighbors;
    }

    // 聚类方法
    public void mergeCluster(NetpackGps point, List<NetpackGps> neighbors, int clusterId, List<NetpackGps> points) {
        point.setClusterId(clusterId);

        while (!neighbors.isEmpty()) {
            NetpackGps neighbor = neighbors.get(0);
            if (!neighbor.isVisited()) {
                neighbor.setVisited(true);
                List<NetpackGps> nneighbors = obtainNeighbors(neighbor, points);
                if (nneighbors.size() > MinPts) {
                    for (NetpackGps nneighbor : nneighbors) {
                        if (!neighbors.contains(nneighbor)) {
                            neighbors.add(nneighbor);
                        }
                    }
                }
            }
            // 未被聚类的点归入当前聚类中
            if (neighbor.getClusterId() <= 0) {
                neighbor.setClusterId(clusterId);
            }
            neighbors.remove(neighbor);
        }
    }

    // 调用聚类方法进行聚类
    public List<NetpackGps> cluster(List<NetpackGps> points) {
        // clusterId初始为0表示未分类,分类后设置为一个正数,如果设置为-1表示噪声
        int clusterId = 0;
        // 遍历所有的点
        for (NetpackGps point : points) {
            // 遍历过就跳过
            if (point.isVisited()) {
                continue;
            }
            point.setVisited(true);
            // 找到其邻域所有点
            List<NetpackGps> neighbors = obtainNeighbors(point, points);
            // 邻域中的点大于MinPts，则为核心点
            if (neighbors.size() >= MinPts) {
                // 满足核心对象条件的点创建一个新簇
                clusterId = point.getClusterId() <= 0 ? (++clusterId) : point.getClusterId();
                // 处理该簇内所有未被标记为已访问的点，对簇进行扩展
                mergeCluster(point, neighbors, clusterId, points);
                if (clusterId > clusterIdMax) {
                    clusterIdMax = clusterId;
                }
            } else {
                // 未满足核心对象条件的点暂时当作噪声处理
                if (point.getClusterId() <= 0) {
                    point.setClusterId(-1);
                }
            }
        }
        return points;
    }

    // 打印几何中心点周围点的方法deleteSurroundings
    public List<NetpackGps> deleteSurroundings(List<NetpackGps> points) {
        int t = 0;
        double distance2 = 0;
        double[] big = new double[2];
        NetpackGps netpackGpsMost = new NetpackGps();
        // DecimalFormat df = new DecimalFormat("###.######");//格式化平均坐标值
        // 分别对clusterid从1到最大值求几何中心
        for (int i = 1; i < clusterIdMax + 1; i++) {
            double sumx = 0;
            double sumy = 0;
            int k = 0;
            // 遍历所有的点,把所有clusterid=i的点，先求出x，y的和sumx，sumy，k的作用是计算出有多少个clusterid=i的点
            for (NetpackGps point : points) {
                if (point.getClusterId() == i) {
                    sumx = point.getGeo_l() + sumx;
                    sumy = point.getGeo_b() + sumy;
                    k = k + 1;
                }
            }
            // 求平均值
            sumx = sumx / k;
            sumy = sumy / k;
            // 转换成小数点前三位，小数点后六位的数字

            if (k > t) {
                t = k;
                big[0] = sumx;
                big[1] = sumy;
                clusterIdMax = i;
                netpackGpsMost.setGeo_l(sumx);
                netpackGpsMost.setGeo_l(sumy);
            }
            // 输出（i ， sumx ，sumy）
            // System.out.println(i + "," + df.format(sumx) + "," +
            // df.format(sumy));
        }
        // System.out.println("重合点最多的点是：" + df.format(big[0]) + "+" +
        // df.format(big[1]) + "总数为：" + t);
        // 去除起点附近点
        for (NetpackGps netpackGps : points) {
            distance2 = euclideanDistance(netpackGps, netpackGpsMost);
            if (distance2 > delRadius) {
                delStartGps.add(netpackGps);
            }
        }
        return delStartGps;
    }

    // 获得几何中心点的方法getStartPoint
    public List<NetpackGps> getStartpoint(List<NetpackGps> points) {
        int t = 0;
        double[] big = new double[2];
        // 分别对clusterid从1到最大值求几何中心
        for (int i = 1; i < clusterIdMax + 1; i++) {
            double sumx = 0;
            double sumy = 0;
            int k = 0;
            // 遍历所有的点,把所有clusterid=i的点，先求出x，y的和sumx，sumy，k的作用是计算出有多少个clusterid=i的点
            for (NetpackGps point : points) {
                if (point.getClusterId() == i) {
                    sumx = point.getGeo_l() + sumx;
                    sumy = point.getGeo_b() + sumy;
                    k = k + 1;
                }
            }
            // 求平均值
            sumx = sumx / k;
            sumy = sumy / k;

            if (k > t) {
                t = k;
                big[0] = sumx;
                big[1] = sumy;
                clusterIdMax = i;
                startGps.get(0).setGeo_l(sumx);
                startGps.get(0).setGeo_l(sumy);
            }
        }
        return startGps;
    }
}
