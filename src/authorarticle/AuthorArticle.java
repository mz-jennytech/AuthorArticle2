/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package authorarticle;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



/**
 *
 * @author jennifer.okosisi
 */
public class AuthorArticle {

    /**
     * @param args the command line arguments
     */
        
    static ArrayList<String> Username = new ArrayList<String>();

    static ArrayList<String>  Submitted = new ArrayList<String>();

    static ArrayList<String> UpdatedAt = new ArrayList<String>();

    static ArrayList<Integer> SubmissionCount = new ArrayList<Integer>();

    static ArrayList<Integer> CommentCount = new ArrayList<Integer>();

    static ArrayList<Integer> CreatedAt = new ArrayList<Integer>();

     static int a;
    
    public static void main(String[] args) throws Exception{

       
       Scanner scan = new Scanner(System.in);

  
        System.out.println("The total number of pages with results");

        a = scan.nextInt();
        
        AuthorArticle author = new AuthorArticle();
          
        System.out.println("Testing - Send HTTP GET request");

        author.sendGet();

      
        
      System.out.println("list of most active authors according to a set threshold");

        int threshold = scan.nextInt();

        System.out.println("The submission count as the criteria " + threshold + " = " + getUsernames(threshold));

      
        System.out.println("Author with the highest comment count " + getUsernameWithHigestComment());


        System.out.println("Usernames sorted by record date");

        int threshold2 = scan.nextInt();



        System.out.println("The list of the authors sorted by when their record was created according to a set threshold " + threshold2 + " = " + getUsernamesSortedByRecordDate(threshold2));

    }
    
    public static List<String> getUsernames(int threshold) {

        for (int i = 0; i < SubmissionCount.size(); i++) {

            if (SubmissionCount.get(i) >= threshold) {

                    Submitted.add(Username.get(i) + " - " + SubmissionCount.get(i).toString());

            }

        }

        return Submitted;

    }



    public static String getUsernameWithHigestComment() {

        return Collections.max(CommentCount).toString();

    }



    public static List<String> getUsernamesSortedByRecordDate(int threshold) {

        Collections.sort(CreatedAt);

        for (int i = 0; i < CreatedAt.size(); i++) {

            if (CreatedAt.get(i) >= threshold) {

                UpdatedAt.add(Username.get(i) + " - " + CreatedAt.get(i).toString());

            }

        }

        return UpdatedAt;



    }


        
    
     
 private void sendGet() throws Exception {

        String url = "https://jsonmock.hackerrank.com/api/article_users/search?page=";



        for (int i = 1; i <= a; i++) {



            HttpURLConnection httpClient = (HttpURLConnection) new URL(url + i).openConnection();



            //setting GET method

            httpClient.setRequestMethod("GET");



            int responseCode = httpClient.getResponseCode();

            System.out.println("Sending GET request ");
              
             // print status code
            System.out.println("Response statusCode " + responseCode);


            try (BufferedReader in = new BufferedReader(

                    new InputStreamReader(httpClient.getInputStream()))) {



                StringBuilder resp = new StringBuilder();

                String line;



                while ((line = in.readLine()) != null) {

                    resp.append(line);

                }

                System.out.println(resp.toString());



                JSONObject jsonObject = new JSONObject(resp.toString());
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int k = 0; k < jsonArray.length(); k++) {
                    
                    
                    
                    JSONObject jsonObject2 = jsonArray.getJSONObject(k);
                    
                    
                    
                    String username = jsonObject2.getString("username");
                    
                    
                    int submissionCount = jsonObject2.getInt("submission_count");
                    
                    
                    
                    int commentCount = jsonObject2.getInt("comment_count");
                    
                    
                    
                    int createdAt = jsonObject2.getInt("created_at");
                    
                    
                    
                    Username.add(username);
                    
                    
                    
                    SubmissionCount.add(submissionCount);
                    
                    
                    
                    CommentCount.add(commentCount);
                    
                    
                    
                    CreatedAt.add(createdAt);
               
                }
                } catch (JSONException e) {

                    e.printStackTrace();

                }
        }
    }
}
