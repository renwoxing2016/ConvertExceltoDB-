
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.pipeline.ResultItemsCollectorPipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.utils.FilePersistentBase;

import org.apache.http.annotation.ThreadSafe;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.io.IOException;

/*
@ThreadSafe
public class ImgPipeline extends FilePersistentBase implements Pipeline {
        private Logger logger = LoggerFactory.getLogger(getClass());
        public ImgPipeline() {
            setPath("F:\\temp\\share2");
        }

        public ImgPipeline(String path) {
            setPath(path);
        }

        public String getsubstringfromXtoEnd(String yuanstring,int x){
            return yuanstring.substring(yuanstring.length()-x,yuanstring.length());
        }
        @Override
        public void process(ResultItems resultItems, Task task) {
            String fileStorePath = this.path;
            try {
                    String imgShortNameNew="(http://album.sina.com.cn/pic/)|(jpg)";
                    CloseableHttpClient httpclient = HttpClients.createDefault();

                    for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
                        String strkey = entry.getKey();
                        if (strkey.equals("img")) {
                            if (entry.getValue() instanceof List) {

                                List listOne = (List) entry.getValue();
                                List<String> list = new ArrayList<String>();

                                for (int i = 0; i < listOne.size(); i++) {
                                    list.add((String) listOne.get(i));
                                }

                                for (int i = 1; i < list.size(); i++) {
                                    //StringBuffer sb = new StringBuffer();
                                    //StringBuffer imgFileNameNewYuan = sb.append(fileStorePath)
                                    //       .append(list.get(0)) //此处提取文件夹名，即之前采集的标题名
                                    //        .append("\\");
                                    //这里先判断文件夹名是否存在，不存在则建立相应文件夹
                                    //Path target = Paths.get(imgFileNameNewYuan.toString());
                                    //if (!Files.isReadable(target)) {
                                    //    Files.createDirectory(target);
                                    //}

                                    String extName = "jpg";//com.google.common.io.Files.getFileExtension(list.get(i));

                                    StringBuffer imgFileNameNew = new StringBuffer();
                                    imgFileNameNew.append(getsubstringfromXtoEnd(list.get(i),21))
                                            .append(".")
                                            .append(extName);

                                    //这里通过httpclient下载之前抓取到的图片网址，并放在对应的文件中
                                    HttpGet httpget = new HttpGet(list.get(i));
                                    HttpResponse response = httpclient.execute(httpget);
                                    HttpEntity entity = response.getEntity();
                                    InputStream in = entity.getContent();

                                    File file = new File(imgFileNameNew.toString());
                                    try {
                                        FileOutputStream fout = new FileOutputStream(file);
                                        int l = -1;
                                        byte[] tmp = new byte[1024];
                                        while ((l = in.read(tmp)) != -1) {
                                            fout.write(tmp, 0, l);
                                        }
                                        fout.flush();
                                        fout.close();
                                    } finally {
                                        in.close();
                                    }
                                }
                            } else {
                                System.out.println(entry.getKey() + ":\t" + entry.getValue());
                            }
                        }
                    }
                    httpclient.close();
            } catch (IOException e) {
                     logger.warn("write file error", e);
            }
        }
}
*/

/**
 * Created by neusoft on 2016/4/22.
 */
public class BlogPP implements PageProcessor {

      // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
      private Site site = Site.me().setRetryTimes(3).setSleepTime(2000);

      @Override      public Site getSite() {
        return site;
      }

    //from sourceString List, fing all not same's String
    public List<String> getNotSameStringFromList(List<String> sourceList){
        List<String> destList = new ArrayList<String>();
        if(sourceList.size() <= 0) {
            return destList;
        }
        System.out.println("sourceList:");
        destList.add(sourceList.get(0));
        System.out.println(destList.get(0));
        for(int i=1; i<sourceList.size();i++)
        {
            boolean bFlag = false;
            //System.out.println(sourceList.get(i));
            for(int j=0; j<destList.size();j++)
            {
                if(sourceList.get(i).equals(destList.get(j))){
                    bFlag = true;
                    break;
                }
            }
            if(true == bFlag){//not add into deststring
            }else {//add i String into deststring
                destList.add(sourceList.get(i));
                System.out.println("dest list size:"+destList.size());
            }
        }
        return destList;
    }


    //--------------abb blog-----------------------
      @Override
      // process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
      public void process(Page page) {
        // 部分二：定义如何抽取页面信息，并保存下来
        System.out.println("page println 1");
        //page.putField("author", page.getUrl().regex("https://github\\.com/(\\w+)/.*").toString());
        page.putField("author", page.getUrl().toString());
        System.out.println(page.getUrl().toString());
        //page.putField("name", page.getHtml().xpath("//img[@class='SG_icon SG_icon111']/a/text()").toString());
        String imgRegex = "http://photo.blog.sina.com.cn/.*";
        String nextpageRegex = "http://blog.sina.com.cn/s/blog_\\w+.html";

        //List<String> links = page.getHtml().links().all();
        List<String> imglinks = page.getHtml().links().regex(imgRegex).all();
        page.putField("img", imglinks);

        List<String> requests = page.getHtml().links().regex(nextpageRegex).all();
        List<String> requests2 = getNotSameStringFromList(requests);

        page.putField("nextpage", requests2);
        //if (page.getResultItems().get("name") == null) {
        //  //skip this page
        //  page.setSkip(true);
        //}
        //page.putField("readme", page.getHtml().xpath("//div[@id='start-of-content']/tidyText()"));
        System.out.println("page println 2");
        // 部分三：从页面发现后续的url地址来抓取
       // page.addTargetRequests(page.getHtml().links().regex("(http://blog.sina.com.cn/s/blog_\\w+.html)").all());
      }
      //-------------------------------------

    /*/-----------test--------------------------
    @Override
    // process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
    public void process(Page page) {
        // 部分二：定义如何抽取页面信息，并保存下来
        page.putField("author", page.getUrl().toString());
        page.putField("name", page.getHtml().xpath("//ul[@class='languages']/li/a/text()").toString());
        if (page.getResultItems().get("name") == null) {
            //skip this page
            page.setSkip(true);
        }
        //page.putField("readme", page.getHtml().xpath("//div[@id='start-of-content']/tidyText()"));
        System.out.println("page println");
        // 部分三：从页面发现后续的url地址来抓取
        page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/\\w+/\\w+)").all());
    }
    //-------------------------------------*/
    /*/-------------video------------------------
    @Override
    // process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
    public void process(Page page) {
        // 部分二：定义如何抽取页面信息，并保存下来
        page.putField("author", page.getUrl().toString());
        page.putField("name", page.getHtml().xpath("//ul[@class='languages']/li/a/text()").toString());
        if (page.getResultItems().get("name") == null) {
            //skip this page
            page.setSkip(true);
        }
        //page.putField("readme", page.getHtml().xpath("//div[@id='start-of-content']/tidyText()"));
        System.out.println("page println");
        // 部分三：从页面发现后续的url地址来抓取
        page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/\\w+/\\w+)").all());
    }
    //-------------------------------------*/







      public static void main(String[] args) {
        System.out.println("test bolg crawler");
        /*/---------test-----------------------
        Spider.create(new BlogPP())
        //    .addPipeline(new FilePipeline("F:\\temp\\"))
        //    .test("http://webmagic.io/docs/");
            //从"https://github.com/code4craft"开始抓
            .addUrl("https://github.com/code4craft")
            //设置Scheduler，使用Redis来管理URL队列
          //  .setScheduler(new RedisScheduler("localhost"))
          //  .addPipeline(new JsonFilePipeline("F:\\temp\\bolgline.json"))
          //  .addPipeline(new ConsolePipeline())	//打印到控制台
            .addPipeline(new FilePipeline("F:\\temp\\"))	//保存到文件夹
            //开启5个线程抓取
            .thread(5)
            //启动爬虫
            .run();
         //--------------------------------*/

         /*/-------video-------------------------
          Spider.create(new BlogPP())
          //.addUrl("http://webmagic.io/docs/")
          .addUrl("http://www.1905.com/video/?fr=home_menu_video")
          // .addPipeline(new JsonFilePipeline("F:\\temp\\bolgline.json"))
          // .addPipeline(new ConsolePipeline())	//打印到控制台
             .addPipeline(new FilePipeline("F:\\temp\\share2"))	//保存到文件夹
             //开启5个线程抓取
             //.thread(5)
             //启动爬虫
             .run();
          //--------------------------------*/

          //得到表格中所有的数据
          //List<EarthQuakeEntity> listExcel=ExcelService.getAllByExcel("F:\\Users\\neusoft\\Crawler\\test\\eqList2016_06_28-2010-1-1.xls");
          List<EarthQuakeEntity> listExcel=ExcelService.getAllByExcel("eqList2016_06_28-2010-1-11.xls");

          //for(int i=0; i<listExcel.size(); i++){
          //    System.out.println(listExcel.get(i).toString());
          //}
          //System.out.println("excel servic ok");

          DBConnect dbQuake = new DBConnect();
          dbQuake.createdb("QuakeDB");
          dbQuake.createTable("QuakeTable");
          dbQuake.insertToTable(listExcel);
          System.out.println("table insert value ok");

         /*
         //-------abb bolog-------------------------
          Spider.create(new BlogPP())
          //.addUrl("http://10.10.109.208:8080/IATools/")
          .addUrl("http://blog.sina.com.cn/abbroboticsirb120/")
          // .addPipeline(new JsonFilePipeline("F:\\temp\\bolgline.json"))
          // .addPipeline(new ConsolePipeline())	//打印到控制台
             .addPipeline(new FilePipeline("F:\\temp\\share2"))	//保存到文件夹
             //开启5个线程抓取
             .thread(5)
             //启动爬虫
             .run();
          //--------------------------------
          */
      }

}
