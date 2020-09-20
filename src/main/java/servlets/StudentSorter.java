import java.util.*;

public class StudentSorter{
  public ArrayList<JSONObject> students = new ArrayList<>();
  public static void main(String[] args){
    //Get the list of Student
    //Get the current student logged in  
  }
    
  }
  //general filter
  public ArrayList<JSONObject> filter(JSONObject student, ArrayList<JSONObject> studentlist){
    ArrayList<JSONObject> SimilarStudents = new ArrayList<>();
    for (int i = 0; i < studentlist.size(); i ++){}
        int check = 0;
        if(student.name = studentlist.get(i).name){break;}
        if(student.meetingmedium = studentlist.get(i).meetingmedium){check++;}
        if(student.location = studentlist.location){check++;}
        if(student.timezone = studentlist.get(i).timezone){check++;}
        if(student.subjects = studentlist.get(i).subjects){check++;}
        if(student.numstudents = studentlist.get(i).numstudents){check++;}
        if(student.tutorrates = studentlist.get(i).tutorrates){check++;}
        if (check >= 3){SimilarStudents.add(studentlist.get(i));}
        }
        return SimilarStudents;
      }
    //more specific ones
    public ArrayList<JSONObject> timezone(JSONObject student, ArrayList<JSONObject> studentlist){
        ArrayList<JSONObject> SimilarStudents = new ArrayList<>();
        for (int i = 0; i < studentlist.size(); i ++){
            if(student.timezone = studentlist.get(i).timezone){SimilarStudents.add(studentlist.get(i));}
            }
            return SimilarStudents;
    }
    
    public ArrayList<JSONObject> subjects(JSONObject student, ArrayList<JSONObject> studentlist){
        ArrayList<JSONObject> SimilarStudents = new ArrayList<>();
        for (int i = 0; i < studentlist.size(); i ++){
            if(student.subjects = studentlist.get(i).subjects){SimilarStudents.add(studentlist.get(i));}
            }
            return SimilarStudents;
    }

    public ArrayList<JSONObject> tutorrates(JSONObject student, ArrayList<JSONObject> studentlist){
        ArrayList<JSONObject> SimilarStudents = new ArrayList<>();
        for (int i = 0; i < studentlist.size(); i ++){
            if(student.tutorrates = studentlist.get(i).tutorrates){SimilarStudents.add(studentlist.get(i));}
            }
            return SimilarStudents;
    }
  }
}

