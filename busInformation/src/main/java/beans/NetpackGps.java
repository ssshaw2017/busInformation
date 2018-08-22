package beans;

import java.math.BigInteger;

import javax.jdo.annotations.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author lzl
 * @version 创建时间：${date} ${time}
 * @comment netpack_gps1表实体类
 */
@Entity
@Table(name = "netpack_gps2")
public class NetpackGps {
    /*
     * GPS采集时间
     */
    @Column(name = " gps_datetime ")
    private String gps_datetime;
    /*
     * 车辆NO
     */
    @Column(name = " bus_no ")
    private BigInteger bus_no;
    /*
     * 线路号
     */
    @Column(name = " line_no ")
    private BigInteger line_no;
    /*
     * 支线路号
     */
    @Column(name = " line_subno ")
    private BigInteger line_subno;
    /*
     * 上一经过站序号
     */
    @Column(name = " busstop_serial ")
    private BigInteger busstop_serial;
    /*
     * 经度
     */
    @Column(name = " geo_l ")
    private double geo_l;
    /*
     * 纬度
     */
    @Column(name = " geo_b ")
    private double geo_b;
    /*
     * 速度
     */
    @Column(name = " speed ")
    private double speed;
    /*
     * 角度（角度值，单位：度）
     */
    @Column(name = " angle ")
    private Integer angle;
    /*
     * 上下行
     */
    @Column(name = " updown ")
    private Integer updown;
    /*
     * GPS公里
     */
    @Column(name = " distance_sum ")
    private double distance_sum;
    /*
     * 是否有效, 0 有效 1无效
     */
    @Column(name = " valid ")
    private Integer valid;
    /*
     * 入库时间
     */
    @Column(name = " datetime ")
    private String datetime;
    /*
     * 主状态×10+子状态 (车辆状)
     */
    @Column(name = " svr_state ")
    private int svr_state;
    /*
     * 线路名称
     */
    @Column(name = " line_name ")
    private String line_name;
    /*
     * 站点编号
     */
    @Column(name = " busstop_no ")
    private Integer busstop_no;
    /*
     * 站点名称
     */
    @Column(name = " busstop_name ")
    private String busstop_name;
    /*
     * 站点类型 1:发车场站2:起始站点 3 中间站 4终点站 5 终点站场站
     */
    @Column(name = " busstop_type ")
    private Integer busstop_type;
    /*
     * 站点经度
     */
    @Column(name = " busstop_lng ")
    private double busstop_lng;
    /*
     * 站点纬度
     */
    @Column(name = " busstop_lat ")
    private double busstop_lat;
    /*
     * 是否是核心点
     */
    private boolean isKey;
    /*
     * 是否访问过
     */
    private boolean isVisited;
    /*
     * 聚类id
     */
    private int clusterId;

    public String getGps_datetime() {
        return gps_datetime;
    }

    public void setGps_datetime(String gps_datetime) {
        this.gps_datetime = gps_datetime;
    }

    public BigInteger getBus_no() {
        return bus_no;
    }

    public void setBus_no(BigInteger bus_no) {
        this.bus_no = bus_no;
    }

    public BigInteger getLine_no() {
        return line_no;
    }

    public void setLine_no(BigInteger line_no) {
        this.line_no = line_no;
    }

    public BigInteger getLine_subno() {
        return line_subno;
    }

    public void setLine_subno(BigInteger line_subno) {
        this.line_subno = line_subno;
    }

    public BigInteger getBusstop_serial() {
        return busstop_serial;
    }

    public void setBusstop_serial(BigInteger busstop_serial) {
        this.busstop_serial = busstop_serial;
    }

    public double getGeo_l() {
        return geo_l;
    }

    public void setGeo_l(double geo_l) {
        this.geo_l = geo_l;
    }

    public double getGeo_b() {
        return geo_b;
    }

    public void setGeo_b(double geo_b) {
        this.geo_b = geo_b;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public Integer getAngle() {
        return angle;
    }

    public void setAngle(Integer angle) {
        this.angle = angle;
    }

    public Integer getUpdown() {
        return updown;
    }

    public void setUpdown(Integer updown) {
        this.updown = updown;
    }

    public double getDistance_sum() {
        return distance_sum;
    }

    public void setDistance_sum(double distance_sum) {
        this.distance_sum = distance_sum;
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public int getSvr_state() {
        return svr_state;
    }

    public void setSvr_state(int svr_state) {
        this.svr_state = svr_state;
    }

    public String getLine_name() {
        return line_name;
    }

    public void setLine_name(String line_name) {
        this.line_name = line_name;
    }

    public Integer getBusstop_no() {
        return busstop_no;
    }

    public void setBusstop_no(Integer busstop_no) {
        this.busstop_no = busstop_no;
    }

    public String getBusstop_name() {
        return busstop_name;
    }

    public void setBusstop_name(String busstop_name) {
        this.busstop_name = busstop_name;
    }

    public Integer getBusstop_type() {
        return busstop_type;
    }

    public void setBusstop_type(Integer busstop_type) {
        this.busstop_type = busstop_type;
    }

    public double getBusstop_lng() {
        return busstop_lng;
    }

    public void setBusstop_lng(double busstop_lng) {
        this.busstop_lng = busstop_lng;
    }

    public double getBusstop_lat() {
        return busstop_lat;
    }

    public void setBusstop_lat(double busstop_lat) {
        this.busstop_lat = busstop_lat;
    }

    public boolean isKey() {
        return isKey;
    }

    public void setKey(boolean isKey) {
        this.isKey = isKey;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean isVisited) {
        this.isVisited = isVisited;
    }

    public int getClusterId() {
        return clusterId;
    }

    public void setClusterId(int clusterId) {
        this.clusterId = clusterId;
    }

    public NetpackGps(String gps_datetime, BigInteger bus_no, BigInteger line_no, BigInteger line_subno,
            BigInteger busstop_serial, double geo_l, double geo_b, double speed, Integer angle, Integer updown,
            double distance_sum, Integer valid, String datetime, int svr_state, String line_name, Integer busstop_no,
            String busstop_name, Integer busstop_type, double busstop_lng, double busstop_lat, boolean isKey,
            boolean isVisited, int clusterId) {
        super();
        this.gps_datetime = gps_datetime;
        this.bus_no = bus_no;
        this.line_no = line_no;
        this.line_subno = line_subno;
        this.busstop_serial = busstop_serial;
        this.geo_l = geo_l;
        this.geo_b = geo_b;
        this.speed = speed;
        this.angle = angle;
        this.updown = updown;
        this.distance_sum = distance_sum;
        this.valid = valid;
        this.datetime = datetime;
        this.svr_state = svr_state;
        this.line_name = line_name;
        this.busstop_no = busstop_no;
        this.busstop_name = busstop_name;
        this.busstop_type = busstop_type;
        this.busstop_lng = busstop_lng;
        this.busstop_lat = busstop_lat;
        this.isKey = isKey;
        this.isVisited = isVisited;
        this.clusterId = clusterId;
    }

    public NetpackGps() {
        super();
    }

}
