package gy.mf.info.control.transfer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bing.ma on 2017/5/3.
 */

public class CollectionUtils {

    public static boolean isEmpty(ArrayList arr) {
        if (arr == null) {
            return true;
        }
        if (arr.size() < 1) {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(Map arr) {
        if (arr == null) {
            return true;
        }
        if (arr.size() < 1) {
            return true;
        }
        return false;
    }

    public static boolean noEmpty(ArrayList arr) {
        if (arr == null) {
            return false;
        }
        if (arr.size() < 1) {
            return false;
        }
        return true;
    }

    public static boolean noEmpty(Map arr) {
        if (arr == null) {
            return false;
        }
        if (arr.size() < 1) {
            return false;
        }
        return true;
    }


    public static <T> ArrayList<T> HM2SaveList(
            ArrayList<String> arrayList_s, HashMap<String, T> pointRangeHashMap) {
        ArrayList<T> inspectionValuesList = new ArrayList<T>();
        for (int i = 0; i < arrayList_s.size(); i++) {
            T inspectionValues = pointRangeHashMap.get(arrayList_s.get(i));
            if (inspectionValues != null) {

                inspectionValuesList.add(inspectionValues);
            }
        }
        return inspectionValuesList;
    }



    public static ArrayList<String> stringToList(String fieldDBValues) {
        if (StringUtils.isEmpty(fieldDBValues)) {
            return null;
        } else {
            String[] split = fieldDBValues.split(",");
            return new ArrayList<String>(Arrays.asList(split));
        }
    }

    public static <T> ArrayList<T> copyfoByIndex(int index, ArrayList<T> arrayList) {


        if (isEmpty(arrayList)) {
            return null;
        }
        ArrayList<T> arrCopy = new ArrayList<>();
        for (int i = index; i < arrayList.size(); i++) {
            T t = arrayList.get(i);
            arrCopy.add(t);
        }
        return arrCopy;
    }

    public static <E, T> void ff(HashMap<E, T> hashMap) {

//        Iterator iter = map.entrySet().iterator();
//        while (iter.hasNext()) {
//            Object next = iter.next();
//           /* Map.Entry entry = (Map.Entry) iter.next();
//            E key = entry.getKey();
////            */T val = entry.getValue();
//        }
    }


}
