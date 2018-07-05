  public static JSONArray getDataFromFile(String fileName){
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        String Path="E:\\data\\" + fileName+ ".json";
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
            //    System.out.println(deleteString);
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
      //  System.out.println(jsonObject);
        return jsonArray;
    }
    public static void insertTable(String tableName,String insertWhat,String value){
        try {
            System.out.println("Inserting records into the table "+tableName+"....");
            statement = connection.createStatement();
            String sql = "insert into "+tableName+"("+value+")"+" values "+insertWhat;
            statement.executeUpdate(sql);
            System.out.println("Inserted records into the table successfully...");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //insert into student_info(stuName,stuAge) values('zhanghua',13),('zhanghua',14),('zhanghua',15);
    public static  void insert(String tableName,JSONArray jsonArray,String insertName){
        String [] value = insertName.split(",");
        String valueString = new String();
        for(int d = 0;d<value.length-1;d++){
            valueString = valueString+value[d]+",";
        }
        valueString = valueString+value[value.length-1];
        String insertString = new String();
        for (int j =0;j<jsonArray.size()-1;j++) {
            insertString = "(";
            for (int i = 0; i < value.length - 1; i++) {
                insertString = insertString + "'" + jsonArray.getJSONObject(j).getString(value[i]).replace('\'','’') + "',";
            }

//            if (tableName == "song") {
//                if (!(jsonArray.getJSONObject(j).getString("song_id").isEmpty())){
//
//                }
//            }
            insertString = insertString + "'" + jsonArray.getJSONObject(j).getString(value[value.length - 1]).replace('\'','’') + "')";
           insertTable(tableName, insertString, valueString);
        }

      //System.out.println(valueString);
       // System.out.println(insertString);
      // System.out.println(jsonArray.size());
     //   insertTable(tableName,insertString,valueString);

    }

    public static  void  main(String[]args){
        //System.out.println(getDataFromFile("album"));
        setConnection("dai_music","root","gaobaiqiqiu98");
       // getDataFromFile("singer");
        //insert("singer", getDataFromFile("singer"),"singer_name,singer_introduction,singer_photo");
       // insert("album", getDataFromFile("album"),"album_name,album_introduction,album_photo");
        insert("song", getDataFromFile("song"),"song_name,song_singer,punish_time,song_album,song_photo,song_lyric");
    }
