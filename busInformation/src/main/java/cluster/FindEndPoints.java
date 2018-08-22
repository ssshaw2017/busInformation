package cluster;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.io.IOUtils;

public class FindEndPoints {
    DBSCANClutering cluster = new DBSCANClutering();

    private List<DataPoint> initData() {
        List<DataPoint> points = new LinkedList<>();
        InputStream in = null;
        BufferedReader br = null;
        try {
            File file = new File("C:\\Users\\Administrator\\Desktop\\test3.txt");
            in = new FileInputStream(file);
            br = new BufferedReader(new InputStreamReader(in));
            String line = br.readLine();
            while (null != line && !"".equals(line)) {
                StringTokenizer tokenizer = new StringTokenizer(line, ",");
                double x = Double.parseDouble(tokenizer.nextToken());
                double y = Double.parseDouble(tokenizer.nextToken());
                // double z = Double.parseDouble(tokenizer.nextToken());
                double[] dimensioin = { x, y };
                String z = tokenizer.nextToken();
                String f = tokenizer.nextToken();
                z = z.replace(":", "");
                z = z.substring(z.length() - 6, z.length());
                long longCreateTime = Long.parseLong(z);
                points.add(new DataPoint(dimensioin, x + "-" + y, false, false, 1, longCreateTime, f));
                line = br.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(br);
        }
        return points;
    }

    // 打印出所有的终点
    public List<DataPoint> print(List<DataPoint> points) {
        int k = 1;
        List<DataPoint> endPoints = new LinkedList<>();
        for (DataPoint point : points) {
            if (k == points.size()) {
                endPoints.add(point);
            } else if (points.get(k).getCreateTime() - points.get(k - 1).getCreateTime() > 4100) {
                endPoints.add(point);
            }
            k = k + 1;
        }
        for (DataPoint point : endPoints) {
            System.out.println(point.getDimensioin()[0] + "," + point.getDimensioin()[1] + "," + point.getCreateTime());
        }
        System.out.println("终点聚类开始");
        cluster.cluster(endPoints);
        System.out.println("输出几何中心点开始");
        cluster.print2(endPoints);
        return endPoints;
    }

    private void build2() {
        List<DataPoint> points = initData();
        // List<DataPoint> endPoints = new LinkedList<>();
        long start = System.nanoTime();
        System.out.println(System.nanoTime() - start);
        System.out.println("输出所有点开始");
        print(points);// 输出所有点
        /*
         * System.out.println("输出聚类点开始"); clustering.cluster(endPoints);
         */
        /*
         * System.out.println("输出几何中心点开始"); clustering.print2(endPoints);
         */
        // findMax(points);输出重合最多的点
    }

    public static void main(String[] args) {
        FindEndPoints builder = new FindEndPoints();
        builder.build2();
    }
}
