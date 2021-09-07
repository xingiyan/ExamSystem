import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * @version 1.0
 * @author: xingyan
 * @date: 2021/6/6 19:56
 * @desc:
 */

public class TestMapper {
    @Autowired
    TestMapper test;
//    @Test
//    public void test1() {
////        String o = "/ExamSystem_war_exploded/jsp/Sign.jsp";
////        for(String string:o.split("\\.")){
////            System.out.println(string);
////        }
////        File file = new File("E:\\test\\file\\"+"web20210312");
////        if(file.exists()){
////            for(String filezip :file.list()){
////                new File("E:\\test\\file\\"+"web20210312"+"\\"+filezip).delete();
////            }
////            file.delete();
////        }
//////        UnZipUtils.unZip("E:\\test\\file\\Test\\Test.zip","E:\\test\\testpaper\\Test","123456");
//        //excel文件路径
//
////        try {
////            //String encoding = "GBK";
////            File excel = new File("E:\\test\\file\\Test\\Test\\Test.xls");
////            if (excel.isFile() && excel.exists()) {   //判断文件是否存在
////
////                String[] split = excel.getName().split("\\.");  //.是特殊字符，需要转义！！！！！
////                Workbook wb;
////                //根据文件后缀（xls/xlsx）进行判断
////                if ("xls".equals(split[1])) {
////                    FileInputStream fis = new FileInputStream(excel);   //文件流对象
////                    wb = new HSSFWorkbook(fis);
////                } else if ("xlsx".equals(split[1])) {
////                    wb = new XSSFWorkbook(excel);
////                } else {
////                    System.out.println("文件类型错误!");
////                    return;
////                }
////
////                //开始解析
////                Sheet sheet = wb.getSheetAt(0);     //读取sheet 0
////
////                int firstRowIndex = sheet.getFirstRowNum();
////                int lastRowIndex = sheet.getLastRowNum();
////
////                for (int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {   //遍历行
////                    Row row = sheet.getRow(rIndex);
////                    if (row != null) {
////                        int firstCellIndex = row.getFirstCellNum();
////                        int lastCellIndex = row.getLastCellNum();
////                        for (int cIndex = firstCellIndex; cIndex < lastCellIndex; cIndex++) {   //遍历列
////                            Cell cell = row.getCell(cIndex);
////                            if (cell != null) {
////                                System.out.println(cell.toString());
////                            }
////                        }
////                    }
////                }
////            } else {
////                System.out.println("找不到指定的文件");
////            }
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////        TestnewsBen testnewsBen = Excel.GetTest(new File("E:\\test\\file\\Test\\Test\\Test.xls"),
////                new File("E:\\test\\file\\Test\\Test\\ExamPaper.xls"));
////        System.out.println(testnewsBen.getTestClass());
////        System.out.println("TestSubjectNum:"+testnewsBen.getTestSubject().size());
////        System.out.println("StudentNum:"+testnewsBen.getStudentBens().size());
////        Map<String,String> map = new HashMap<String,String>();
////        map.put("A","B");
////        map.put("N","E");
////        System.out.println(JSON.toJSONString(map));
//        Timer timer = new Timer(true);
//        TimerTask timerTask = new TimerTask() {
//            @Override
//            public void run() {
//                System.out.println("执行了定时器内容");
//            }
//        };
//        timer.schedule(timerTask,0,1000);
//
//    }

    public static void main(String[] args) {
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                System.out.println("执行了定时器内容");
//
//            }
//        },0,1000);
////        System.out.println("执行了外面的内容");
//
//        Map<String,Integer> map = new HashMap<String,Integer>();
//        map.put("1",100);
//        map.put("2",200);
////        map.remove(100);
//        removeNullKey(map);
//        for(String kay:map.keySet()){
//            int u = map.get(kay);
//            if(u == 100){
//                map.remove(kay);
//                break;
//            }
//        }
////        System.out.println(map.get("2"));
//        for(String kay:map.keySet()){
//            System.out.println(kay);
//        }
//        String i = "1";
//

        String i = "1";
        int o =  (int)(Object)i+1;
    }
    public static void removeNullKey(Map map) {
        Set set = map.keySet();
        for (Iterator iterator = set.iterator(); iterator.hasNext(); ) {
            Object obj = (Object) iterator.next();
            remove(obj, iterator);
        }
    }
    private static void remove(Object obj, Iterator iterator) {
        if (obj instanceof String) {
            String str = (String) obj;
            if (str == null || str.trim().isEmpty()) {
                iterator.remove();
            }
        } else if (obj instanceof Collection) {
            Collection col = (Collection) obj;
            if (col == null || col.isEmpty()) {
                iterator.remove();
            }

        } else if (obj instanceof Map) {
            Map temp = (Map) obj;
            if (temp == null || temp.isEmpty()) {
                iterator.remove();
            }

        } else if (obj instanceof Object[]) {
            Object[] array = (Object[]) obj;
            if (array == null || array.length <= 0) {
                iterator.remove();
            }
        } else {
            if (obj == null) {
                iterator.remove();
            }
        }
    }
}
