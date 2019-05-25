import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class test {

	protected static void setClipboardText(String writeMe) {
		Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();//获取系统剪贴板
        Transferable tText = new StringSelection(writeMe);
        clip.setContents(tText, null);
    }
	
	public static String txt2String(File file){
        StringBuilder result = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                result.append(System.lineSeparator()+s);
            }
            br.close();    
        }catch(Exception e){
            e.printStackTrace();
        }
        return result.toString();
    }
	
	public static void main(String[] args) throws InterruptedException, IOException {
			
		  //String path="D:/1.txt";
		  //File file = new File(path);
		  //file.mkdir();
		  //File outputFile = new File("D:/", "2");
		
		  /*
          Process  pro = Runtime.getRuntime()
                  .exec(new String[]{"D:/Program Files (x86)/Tesseract-OCR/tesseract.exe", 
                                     "D:/1.jpg", 
                                     "D:/1"});
          */
		Process  pro = Runtime.getRuntime()
                .exec(new String[]{System.getProperty("user.dir")+"\\lib\\tesseract.exe", 
                                   "D:/1.jpg", 
                                   "D:/1"});
          	pro.waitFor();
		    //File test=new File("D:/1.txt");
		    //String content=txt2String(test);
		    //setClipboardText(content);
	        //new File("D:/1.txt").delete();
	        
		//String classPath = test.class.getResource("/").getPath();              
		//System.out.println("项目路径：" + classPath);   
		System.out.println(System.getProperty("user.dir"));
	}

}
