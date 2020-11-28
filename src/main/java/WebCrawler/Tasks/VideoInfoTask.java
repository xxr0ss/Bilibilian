package WebCrawler.Tasks;

public class VideoInfoTask extends Task{
    // http://api.bilibili.com/x/web-interface/view?aid=
    @Override
    public void crawl() {

    }

    @Override
    protected void toNextTarget() {

    }

    @Override
    protected void saveCrawResult(Object result) {

    }

    @Override
    public boolean taskFinished() {
        return true;
    }

    @Override
    public String getProgressDescription() {
        return null;
    }
}
