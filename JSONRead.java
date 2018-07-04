import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.*;
import java.sql.*;
/**
 * @author theDogKnight
 * @date : 2018.06.30  09:26
 * @email: 1123706238@qq.com
 */
public class JSON{
    private static final String DRIVER_NAME = "com.mysql.jdbc.Driver";
    private static final String URL_FORMER = "jdbc:mysql://localhost:3306/";
    public static Connection connection = null;
    public static Statement statement = null;

    /**
     * 连接数据库
     *
     * @param URL_LATTER 数据库的名称
     * @param USER_NAME  用户名
     * @param PASSWORD   用户密码
     */
    public static void setConnection(String URL_LATTER, String USER_NAME, String PASSWORD) {
        String url = URL_FORMER + URL_LATTER;
        try {
            Class.forName(DRIVER_NAME);
            //获取数据库连接
            System.out.println("Connecting to a selected database...");
            connection = DriverManager.getConnection(url, USER_NAME, PASSWORD);
            System.out.println("Connected database successfully");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查找表内字段
     *
     * @param tableName 要操作的表的名称
     * @param valueName 要操作的字段的名称
     */
    public static JSONArray searchTable(String tableName, String valueName, int limit) throws SQLException {

        JSONArray array = new JSONArray();


        try {
            statement = connection.createStatement();
            String sql = "SELECT " + valueName + " FROM " + tableName;
            System.out.println("reading database...");
            ResultSet resultSet = statement.executeQuery(sql);
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();
            while (resultSet.next()) {
                JSONObject jsonObject = new JSONObject();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = resultSetMetaData.getColumnLabel(i);
                    String word_value = resultSet.getString(columnName);
                    jsonObject.put(columnName, word_value);
                }
                array.add(jsonObject);
                System.out.println("read database successfully");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return array;
    }
    public static String getDataFromFile(String fileName){
        String remove1 = "[";
        String remove2 = "]";
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        String Path="D:\\" + fileName+ ".json";
        BufferedReader reader = null;
        String laststr = "";
        try {
            FileInputStream fileInputStream = new FileInputStream(Path);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            String deleteString = new String();
            while ((tempString = reader.readLine()) != null) {
                for (int i = 0; i < tempString.length()-1; i++) {
                    if (tempString.charAt(i) != '['&& tempString.charAt(i)!=']') {
                        deleteString += tempString.charAt(i);

                        }
                    }
                if(tempString.charAt(tempString.length()-1)!=',' && tempString.charAt(tempString.length()-1)!=']'){
                    deleteString+= tempString.charAt(tempString.length()-1);

                }
                System.out.println(deleteString);
                 jsonObject = JSONObject.fromObject(deleteString);
                 jsonArray.add(jsonObject);
                 deleteString = "";
            }
            reader.close();


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println(jsonObject);
        return laststr;
    }
    public static  void  main(String[]args){
        //System.out.println(getDataFromFile("album"));
        getDataFromFile("album");
    }
}
