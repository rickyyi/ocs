package cn.hf.manage.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import cn.hf.manage.pojo.HttpResult;

/**
 * 说明：HttpClient工具类
 * @author XiJie
 * @version 1.0
 * @date 2017年10月16日
 */
public class HttpClientUtil {

	/**
	 * 说明：get访问
	 * @param url：请求的url带参数：http://192.168.30.29:8080/wordSegmentation-engine/api/searchWord.msp?sentence=怒金
	 *            请求的url不带参数：http://192.168.30.29:8080/wordSegmentation-engine/api/searchWord.msp
	 * @return
	 * @throws Exception
	 * @author XiJie
	 * @time：2017年10月16日 下午3:37:46
	 */
	public static HttpResult doGet(String url){
		
		// 构建连接池管理器对象
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        //设置整个连接池最大连接数
        cm.setMaxTotal(200);
        // 设置每个主机地址的并发数
        cm.setDefaultMaxPerRoute(200);

        // 构建请求配置信息
        RequestConfig config = RequestConfig.custom().setConnectTimeout(10000)  //与服务器连接超时时间：httpclient会创建一个异步线程用以创建socket连接，此处设置该socket的连接超时时间  
                .setConnectionRequestTimeout(10000) //从连接池中获取连接的超时时间  
                .setSocketTimeout(10 * 1000) // 数据传输的最长时间
                .setStaleConnectionCheckEnabled(true) // 提交请求前测试连接是否可用
                .build();

        // 构建HttpClient对象,把连接池、请求配置对象作为参数
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm)
                .setDefaultRequestConfig(config).build();
		
		
		// 创建Httpclient对象,相当于打开了浏览器
        // CloseableHttpClient httpClient = HttpClients.createDefault();
        
        // 创建HttpGet请求，相当于在浏览器输入地址
     	HttpGet httpGet = new HttpGet(url);
     	CloseableHttpResponse response = null;
		try {
			// 执行请求，相当于敲完地址后按下回车。获取响应
			response = httpClient.execute(httpGet);
			Integer code = response.getStatusLine().getStatusCode();
			String data = EntityUtils.toString(response.getEntity(), "UTF-8");
			HttpResult httpResult = new HttpResult(code, data);
            return httpResult;
		}  catch (Exception e) {
			e.printStackTrace();
		}  finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	
	/**
	 * 无参的doPost请求
	 * @param url    
http://192.168.30.29:8080/wordSegmentation-engine/api/searchWord.msp?serviceid=1111&taskid=1234567890&phonenumber=13013131313
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static HttpResult doPost(String url) {
		
		// 构建连接池管理器对象
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        // 设置最大连接数
        cm.setMaxTotal(200);
        // 设置每个主机地址的并发数
        cm.setDefaultMaxPerRoute(20);

        // 构建请求配置信息
        RequestConfig config = RequestConfig.custom().setConnectTimeout(10000)  //与服务器连接超时时间：httpclient会创建一个异步线程用以创建socket连接，此处设置该socket的连接超时时间  
                .setConnectionRequestTimeout(2000) //从连接池中获取连接的超时时间  
                .setSocketTimeout(10 * 1000) // 数据传输的最长时间
                .setStaleConnectionCheckEnabled(true) // 提交请求前测试连接是否可用
                .build();

        // 构建HttpClient对象,把连接池、请求配置对象作为参数
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm)
                .setDefaultRequestConfig(config).build();
		
		// 创建Httpclient对象,相当于打开了浏览器 
//		CloseableHttpClient httpClient = HttpClients.createDefault();
        // 创建http POST请求  输入地址
        HttpPost httpPost = new HttpPost(url);
        
        //模拟浏览器
//        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
        CloseableHttpResponse response = null;
        try {
            // 执行请求   敲回车
            response = httpClient.execute(httpPost);
            //工具类不应该添加对结果集的逻辑判断，应该交给调用者自己来判断
            Integer code = response.getStatusLine().getStatusCode();
            String data = EntityUtils.toString(response.getEntity(), "UTF-8");
			HttpResult httpResult = new HttpResult(code, data);
            return httpResult;
        }  catch (Exception e) {
			e.printStackTrace();
		}  finally {
            if (response != null) {
                try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
        }
		return null;
	}
	
	/**
	 * 说明：post方式
	 * @param condition：查询条件
	 * @param url：查询的url：http://192.168.30.29:8080/wordSegmentation-engine/api/searchWord.msp
	 * @return
	 * @throws Exception
	 * @author XiJie
	 * @time：2017年10月16日 下午3:40:34
	 */
	public static String doPost(String condition, String url) throws Exception {
		CloseableHttpClient httpClient = HttpClients.createDefault();
        // 创建http POST请求
        HttpPost httpPost = new HttpPost(url);
        // 设置请求头信息   格式
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        if (condition != null) {
            // 设置参数
            List<NameValuePair> parameters = new ArrayList<NameValuePair>(0);
            parameters.add(new BasicNameValuePair("sentence", condition));
            // 构造一个form表单式的实体                                                        
            @SuppressWarnings("deprecation")
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters,HTTP.UTF_8);
            // 将请求实体设置到httpPost对象中
            httpPost.setEntity(formEntity);
        }

        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = httpClient.execute(httpPost);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                String content = EntityUtils.toString(response.getEntity(), "UTF-8");
                return content;
            }
        } finally {
            if (response != null) {
                response.close();
            }
        }
		return null;
    }
	
	
	
}
