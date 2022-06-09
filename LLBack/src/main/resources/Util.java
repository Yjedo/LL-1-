public class Util {

    public static boolean isInArray(String[] strings, String s){
        for(String item : strings){
            if (s.equals(item)){
                return true;
            }
        }
        return false;
    }
}
