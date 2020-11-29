package WebCrawler.Tasks;

import DB.DBManager;
import DB.DataLine;
import DB.DataType;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserInfoTask extends Task {
    private int targetMid;

    private int endMid;

    private final List<JSONObject> storage = new ArrayList<>();

    /**
     * 默认配置：从mid=1开始爬取，直到10
     */
    public UserInfoTask() {
        this.targetMid = 1;
        this.endMid = 10; // 这么少，还爬取那么慢，b站不至于再ban我吧？
    }

    @Override
    protected void saveCrawResult(Object result) {
        JSONObject res = (JSONObject) result;
        storage.add(res);

        DBManager dbManager = DBManager.getDBManager();
        DataLine line = new DataLine(targetMid, DataType.mid, res.toJSONString());
        dbManager.saveDataLine(line);
    }

    @Override
    public boolean taskFinished() {
        return targetMid == endMid;
    }

    @Override
    public String getProgressDescription() {
        return String.format("Getting mid=%d [%d/%d]", targetMid, targetMid, endMid);
    }

    @Override
    protected void toNextTarget() {
        if (taskFinished()) {
            return;
        }
        targetMid++;
    }

    @Override
    public void crawl() {
        String getUserInfoUrl = "http://api.bilibili.com/x/space/acc/info";
        JSONObject resJsonObject = null;
        try {
            Connection.Response res = Jsoup.connect(getUserInfoUrl)
                    .header("Accept", "*/*")
                    .header("Content-Type", "application/json;charset=UTF-8")
                    .timeout(10000)
                    .ignoreContentType(true)
                    .data("mid", Integer.toString(targetMid)) // 设置get方法的参数
                    .execute();
            String body = res.body();
            resJsonObject = (JSONObject) JSONObject.parse(body);
        } catch (IOException e) {
            e.printStackTrace();
            // TODO: 处理因网络状况导致的没能成功爬取到内容的情况
            // TODO 利用Jsoup.connect返回的http状态码判断是不是被ban了，返回信息给UI
        }

        saveCrawResult(resJsonObject); // 保存这一波的爬取到的数据

        toNextTarget(); // 设置爬取目标为下一个mid
    }
}
