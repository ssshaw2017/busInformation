package cluster;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import beans.NetpackGps;

/**
 * @author lzl
 * @version 创建时间：2018年8月20日
 * @comment 删除终点类，里面包括删除终点方法deleteEnds和获取终点几何中心点方法getEnds，计算时间差方法timeDiff
 */
public class DelEndPoints {

    DelStartPoints delStartPoints = new DelStartPoints();// 引用删除起点类中的聚类方法cluster
    List<NetpackGps> delEndGps = new ArrayList<NetpackGps>();// 已删除终点集
    List<NetpackGps> endPoints = new LinkedList<>();// 终点集
    List<NetpackGps> endPoint = new LinkedList<>();// 几何中心点
    // 删除终点方法deleteEnds

    public List<NetpackGps> deleteEnds(List<NetpackGps> points) throws ParseException {
        System.out.println("删除终点开始");
        int k = 1;
        for (NetpackGps point : points) {
            if (k == points.size()) {// 该条件判断当前点是否为数组最后一个点，如果是，直接返回delEndGps
                return delEndGps;
                // 如果两个点的时间差小于等于12（分钟）*60=1200s的话，就把前一个点添加到已删除终点集
            } else if (timeDiff(points.get(k).getDatetime(), points.get(k - 1).getDatetime()) <= 1200) {
                delEndGps.add(point);
            }
            k = k + 1;
        }
        System.out.println("删除终点结束");
        return delEndGps;
    }

    // 获得终点几何中心点方法getEndPoint
    public List<NetpackGps> getEnds(List<NetpackGps> points) throws ParseException {
        int k = 1;
        System.out.println("获取终点开始");
        for (NetpackGps point : points) {
            if (k == points.size()) {
                endPoints.add(point);// 该条件判断当前点是否为数组最后一个点，如果是，endPoints添加该点
                // 如果两个点的时间差大于12（分钟）*60=1200s的话，就在终点里面添加point
            } else if (timeDiff(points.get(k).getDatetime(), points.get(k - 1).getDatetime()) > 1200) {
                endPoints.add(point);
            }
            k = k + 1;
        }
        System.out.println("终点聚类开始");
        delStartPoints.cluster(endPoints);// 这里的聚类方法调用了删除起点坐标里面的聚类方法cluster
        System.out.println("输出几何中心点开始");
        endPoint = delStartPoints.getStartpoint(endPoints);// 这里的获取几何中心点方法调用了删除起点坐标里面的获取几何中心点方法getStartPoint
        System.out.println("获取终点结束");
        return endPoint;
    }

    // 计算两个点时间差方法timeDiff
    public long timeDiff(String startTime, String endTime) throws ParseException {
        // 看自己的时间格式选择对应的转换对象
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 转换成date类型
        Date start = sdf.parse(startTime);
        Date end = sdf.parse(endTime);
        // 获取毫秒数
        Long startLong = start.getTime();
        Long endLong = end.getTime();
        // 计算时间差,单位秒
        Long s = (endLong - startLong) / 1000;
        return s;
    }

}
