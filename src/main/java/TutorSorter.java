import java.util.*;

public class TutorSorter{
  public static void main(String[] args){
    //Get the list of tutors
    //Get the current student logged in  
  }
    
  }
  //general filter
  public ArrayList<JSONObject> filter(JSONObject student, ArrayList<JSONObject> tutorlist){
    ArrayList<JSONObject> SimilarTutors = new ArrayList<>();
    for (int i = 0; i < tutorlist.size(); i ++){}
        int check = 0;
        if(student.meetingmedium = tutorlist.get(i).meetingmedium){check++;}
        if(student.location = tutorlist.location){check++;}
        if(student.timezone = tutorlist.get(i).timezone){check++;}
        if(student.subjects = tutorlist.get(i).subjects){check++;}
        if(student.numstudents = tutorlist.get(i).numstudents){check++;}
        if(student.tutorrates = tutorlist.get(i).tutorrates){check++;}
        if (check >= 3){SimilarTutors.add(tutorlist.get(i));}
        }
        return SimilarTutors;
      }
    //more specific ones
    public ArrayList<JSONObject> timezone(JSONObject student, ArrayList<JSONObject> tutorlist){
        ArrayList<JSONObject> SimilarTutors = new ArrayList<>();
        for (int i = 0; i < tutorlist.size(); i ++){
            if(student.timezone = tutorlist.get(i).timezone){SimilarTutors.add(tutorlist.get(i));}
            }
            return SimilarTutors;
    }
    
    public ArrayList<JSONObject> subjects(JSONObject student, ArrayList<JSONObject> tutorlist){
        ArrayList<JSONObject> SimilarTutors = new ArrayList<>();
        for (int i = 0; i < tutorlist.size(); i ++){
            if(student.subjects = tutorlist.get(i).subjects){SimilarTutors.add(tutorlist.get(i));}
            }
            return SimilarTutors;
    }

    public ArrayList<JSONObject> tutorrates(JSONObject student, ArrayList<JSONObject> tutorlist){
        ArrayList<JSONObject> SimilarTutors = new ArrayList<>();
        for (int i = 0; i < tutorlist.size(); i ++){
            if(student.tutorrates = tutorlist.get(i).tutorrates){SimilarTutors.add(tutorlist.get(i));}
            }
            return SimilarTutors;
    }
  }
}

