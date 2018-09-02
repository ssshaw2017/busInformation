package convertors;

/** 
* @author lzl 
* @version 创建时间：2018年9月1日 下午2:25:43 
* @comment 
*/
import java.math.BigDecimal;

import beans.NetpackGps;

/**
 * GPS坐标内转百度坐标 1.首先确认你的GPS传过来的坐标普遍用的WGS84。但是具体格式有很多种下面的 度度.分分分分分分（ddmm.mmmm）格式的
 * gps端实际是ddmm.mmmm格式，经过小数点前移动(ddmm.mmmm/100)就变成了dd.mmmmmm格式，即 度度.分分分分分分 2
 * 要想在百度地图上表示就得把上述格式统一成度的形式。即把分除以600000 3 第2步做完，现将WGS84-->火星坐标--->百度坐标 4
 * 转换后的坐标放入下面这个链接中验证正确性 http://api.map.baidu.com/lbsapi/getpoint/index.html
 */
public class DpConvert {

    private static double pi = 3.14159265358979323846;// 圆周率
    private static double a = 6378245.0D;// WGS 长轴半径
    private static double ee = 0.00669342162296594323D;// WGS 偏心率的平方
    private static double x_pi = 3.14159265358979324 * 3000.0 / 180.0;

    /**
     * 中国坐标内
     * 
     * @param lat
     * @param lon
     * @return
     */
    public static boolean outofChina(double lat, double lon) {
        if (lon < 72.004 || lon > 137.8347)
            return true;
        if (lat < 0.8293 || lat > 55.8271)
            return true;
        return false;
    }

    public static NetpackGps wgs84Tobd09(NetpackGps netpackGps) {
        double dLat = transformLat(netpackGps.getGeo_l() - 105.0, netpackGps.getGeo_b() - 35.0);
        double dLon = transformLon(netpackGps.getGeo_l() - 105.0, netpackGps.getGeo_b() - 35.0);
        double radLat = netpackGps.getGeo_b() / 180.0 * pi;
        double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
        double mgLat = netpackGps.getGeo_b() + dLat;
        double mgLon = netpackGps.getGeo_l() + dLon;
        double z = Math.sqrt(mgLon * mgLon + mgLat * mgLat) + 0.00002 * Math.sin(mgLat * x_pi);
        double theta = Math.atan2(mgLat, mgLon) + 0.000003 * Math.cos(mgLon * x_pi);
        double lontitude = z * Math.cos(theta) + 0.0065;
        double latitude = z * Math.sin(theta) + 0.006;
        netpackGps.setGeo_l(lontitude);
        netpackGps.setGeo_b(latitude);
        return netpackGps;
    }

    // gcj02-84
    /*
     * public Map<String, Double> gcj2Towgs(double lon, double lat) {
     * Map<String, Double> localHashMap = new HashMap<String, Double>(); double
     * lontitude = lon - (((Double) wgs84Togcj2(lon,
     * lat).get("lon")).doubleValue() - lon); double latitude = (lat -
     * (((Double) (wgs84Togcj2(lon, lat)).get("lat")).doubleValue() - lat));
     * localHashMap.put("lon", lontitude); localHashMap.put("lat", latitude);
     * return localHashMap; }
     */

    // gcj02-BD09
    /*
     * public static Map<String, Double> gcj2ToBD09(double gg_lon, double
     * gg_lat) {
     * 
     * Map<String, Double> localHashMap = new HashMap<String, Double>(); double
     * x = gg_lon, y = gg_lat; double z = Math.sqrt(x * x + y * y) + 0.00002 *
     * Math.sin(y * x_pi); double theta = Math.atan2(y, x) + 0.000003 *
     * Math.cos(x * x_pi);
     * 
     * double lontitude = z * Math.cos(theta) + 0.0065; double latitude = z *
     * Math.sin(theta) + 0.006;
     * 
     * localHashMap.put("lon", lontitude); localHashMap.put("lat", latitude);
     * return localHashMap;
     * 
     * }
     */
    // 转换横坐标
    public static double transformLat(double x, double y) {
        double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(y * pi) + 40.0 * Math.sin(y / 3.0 * pi)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(y / 12.0 * pi) + 320 * Math.sin(y * pi / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    // 转换纵坐标
    public static double transformLon(double x, double y) {
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(x * pi) + 40.0 * Math.sin(x / 3.0 * pi)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(x / 12.0 * pi) + 300.0 * Math.sin(x / 30.0 * pi)) * 2.0 / 3.0;
        return ret;
    }

    /**
     * 把经纬度坐标的度表示方式转换成小树表示方式 例：39°312051' ---> 39 + 312051/600000
     * 
     * @param strPiont
     * @return
     */
    public static double convertSit(String strPiont) {

        StringBuffer sbPoint = new StringBuffer();

        // 经纬度整数部分
        String beforSecond = strPiont.substring(0, strPiont.indexOf("."));
        // 经纬度小数部分
        String afterSecond = strPiont.substring(strPiont.indexOf(".") + 1);

        BigDecimal s60000 = new BigDecimal("600000");

        // 经纬度小数点后面组装
        sbPoint.append(beforSecond);
        BigDecimal second01 = new BigDecimal(afterSecond);

        sbPoint.append(second01.divide(s60000, 6, BigDecimal.ROUND_HALF_DOWN).toString().substring(1));
        Double dobleData = new Double(sbPoint.toString());

        return dobleData.doubleValue();

    }

    public static void main(String args[]) {

        // double x = convertSit("114.549545");
        // double y = convertSit("38.043149");
        NetpackGps netpackGps = new NetpackGps();
        netpackGps.setGeo_l(114.549545);
        netpackGps.setGeo_b(38.043149);
        double x = 114.549545;
        double y = 38.043149;
        System.out.println("x:" + x + "  " + "y:" + y);
        // 转百度坐标
        NetpackGps localHashMap = (NetpackGps) wgs84Tobd09(netpackGps);
        System.out.println("百度坐标" + localHashMap.getGeo_l() + "," + localHashMap.getGeo_b());
    }

}
