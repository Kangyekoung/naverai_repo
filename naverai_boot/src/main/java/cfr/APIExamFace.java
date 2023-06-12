package cfr;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import com.example.ai.MyNaverInform;
/* json {}: 객체 []:배열 객체
 * {
 * "info":
 * {"size":{"width":549,"height":600},"faceCount":1},
 * "faces":[
	 * 	{
	 * 		"celebrity":{"value":"박민영","confidence":0.36902}
	 * 	}
 * 	]
 * }
 * 
 */

/*
 * {"info":{"size":{"width":640,"height":407},"faceCount":7},
 * "faces":[
 * {"celebrity":{"value":"유겸","confidence":0.01}},
 * {"celebrity":{"value":"엄현경","confidence":0.090767}},
 * {"celebrity":{"value":"유진","confidence":0.0626379}},
 * {"celebrity":{"value":"현승민","confidence":0.139208}},
 * {"celebrity":{"value":"정은지","confidence":0.384913}},
 * {"celebrity":{"value":"김태형","confidence":0.01}},
 * {"celebrity":{"value":"이일민","confidence":0.0708282}}
 * 
 * ]}
 * */

// info.faceCount - 연예인 수
// faces[0].celebrity.value - 첫번째 닮은 연예인이름
// faces[0].celebrity.confidence - 첫번째 닮은 연예인 정보(0-1)
// 네이버 얼굴인식 API 예제
public class APIExamFace {

    public static void main(String[] args) {

        StringBuffer reqStr = new StringBuffer();
        String clientId = MyNaverInform.clientID;//애플리케이션 클라이언트 아이디값";
        String clientSecret = MyNaverInform.secret;//애플리케이션 클라이언트 시크릿값";

        try {
            String paramName = "image"; // 파라미터명은 image로 지정
            String imgFile = MyNaverInform.path + "bangtan7.jpg";
            File uploadFile = new File(imgFile);
            String apiURL = "https://naveropenapi.apigw.ntruss.com/vision/v1/celebrity"; // 유명인 얼굴 인식
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setDoInput(true);
            // multipart request
            String boundary = "---" + System.currentTimeMillis() + "---";
            con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
            con.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);
            OutputStream outputStream = con.getOutputStream();
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, "UTF-8"), true);
            String LINE_FEED = "\r\n";
            // file 추가
            String fileName = uploadFile.getName();
            writer.append("--" + boundary).append(LINE_FEED);
            writer.append("Content-Disposition: form-data; name=\"" + paramName + "\"; filename=\"" + fileName + "\"").append(LINE_FEED);
            writer.append("Content-Type: "  + URLConnection.guessContentTypeFromName(fileName)).append(LINE_FEED);
            writer.append(LINE_FEED);
            writer.flush();
            FileInputStream inputStream = new FileInputStream(uploadFile);
            byte[] buffer = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
            inputStream.close();
            writer.append(LINE_FEED).flush();
            writer.append("--" + boundary + "--").append(LINE_FEED);
            writer.close();
            BufferedReader br = null;
            int responseCode = con.getResponseCode();
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 오류 발생
                System.out.println("error!!!!!!! responseCode= " + responseCode);
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            }
            String inputLine;
            if(br != null) {
                StringBuffer response = new StringBuffer();
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                br.close();
                System.out.println(response.toString()); //콘솔 출력 결과
            } else {
                System.out.println("error !!!");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
