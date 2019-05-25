import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;  
import java.text.SimpleDateFormat;  
import java.util.Date;   

import javax.imageio.ImageIO;  
import javax.swing.ImageIcon;  
import javax.swing.JButton;  
import javax.swing.JFileChooser;  
import javax.swing.JToolBar;  
import javax.swing.JWindow;  
import javax.swing.filechooser.FileNameExtensionFilter;  
import javax.swing.filechooser.FileSystemView;
import com.melloware.jintellitype.HotkeyListener;  
import com.melloware.jintellitype.JIntellitype; 


class hotkey implements HotkeyListener {  
    static final int KEY_1 = 88;  
    static final int KEY_2 = 89;  
    static final int KEY_3 = 90;  
  
    public static int k=0;
    /** 
     * 该方法负责监听注册的系统热键事件 
     * 
     * @param key:触发的热键标识 
     */  
    public void onHotKey(int key) {  
        switch (key) {  
            case KEY_1:  
            System.out.println("ctrl+alt+Q按下........."); 
            k=KEY_1;
			try {
			ScreenShotWindow ssw = new ScreenShotWindow();
			ssw.setVisible(true); 
			} catch (AWTException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
                break; 
            case KEY_2:  
            	System.out.println("粘贴文字"); 
            	k=KEY_2;
            	try {
        			ScreenShotWindow ssw = new ScreenShotWindow();
        			ssw.setVisible(true); 
        			} catch (AWTException e) {
        				// TODO Auto-generated catch block
        				e.printStackTrace();
        			}
               break;
            case KEY_3:  
            	
            	System.out.println("系统退出..........");  
                destroy();  
                
        }  
  
    }  
  
  
    /** 
     * 解除注册并退出 
     */  
    void destroy() {  
        JIntellitype.getInstance().unregisterHotKey(KEY_1);  
        JIntellitype.getInstance().unregisterHotKey(KEY_2);  
        JIntellitype.getInstance().unregisterHotKey(KEY_3);  
        System.exit(0);  
    }  
  
    /** 
     * 初始化热键并注册监听事件 
     */  
    void initHotkey() {  
        //参数KEY_1表示改组热键组合的标识，第二个参数表示组合键，如果没有则为0，该热键对应ctrl+alt+I  
        JIntellitype.getInstance().registerHotKey(KEY_1, JIntellitype.MOD_CONTROL + JIntellitype.MOD_ALT,  
                (int) 'Q');  
        JIntellitype.getInstance().registerHotKey(KEY_2, JIntellitype.MOD_CONTROL + JIntellitype.MOD_ALT,  
                (int) 'S');  
        JIntellitype.getInstance().registerHotKey(KEY_3, JIntellitype.MOD_CONTROL + JIntellitype.MOD_ALT,  
                (int) 'X');  
        JIntellitype.getInstance().addHotKeyListener(this);  
    }  
    /*
    public static void main(String[] args) {  
        test key = new test();  
        key.initHotkey();  
  
        //下面模拟长时间执行的任务  
        while (true) {  
            try {  
                Thread.sleep(10000);  
            } catch (Exception ex) {  
                break;  
            }  
        }  
    }  */
}  

public class ScreenShot {
    public static void main(String[] args) throws AWTException {  	
    		hotkey key=new hotkey();
    		key.initHotkey();
    	 //ScreenShotWindow ssw=new ScreenShotWindow(); 
    	 //ssw.setVisible(true);
         }   
}  
       

//截屏窗口，继承jwindow，是一个无标题栏的窗口
 class ScreenShotWindow extends JWindow {   
         /**
			 * 
			 */
			private static final long serialVersionUID = 1L;
		    private int orgx, orgy, endx, endy;  
            private BufferedImage image=null;  
            private BufferedImage tempImage=null;  
            private BufferedImage saveImage=null;  

            private ToolsWindow tools=null;  

         public ScreenShotWindow() throws AWTException{  
           //获取屏幕尺寸  
           Dimension d = Toolkit.getDefaultToolkit().getScreenSize();  
           this.setBounds(0, 0, d.width, d.height);  

           //截取屏幕  
           Robot robot = new Robot();  
           image = robot.createScreenCapture(new Rectangle(0, 0, d.width,d.height));  

           this.addMouseListener(new MouseAdapter() {  
            @Override  
          //鼠标按下的事件监听
           public void mousePressed(MouseEvent e) {  
                     orgx = e.getX();  
                     orgy = e.getY();  

                     if(tools!=null){  
                      tools.setVisible(false);  
                     }  
           }  
           @Override  
			//鼠标抬起的事件监听
           public void mouseReleased(MouseEvent e) {    
            if(tools==null){  
             //抬起的时候创建操作按键
             tools=new ToolsWindow(ScreenShotWindow.this,e.getX(),e.getY());  
            }else{  
             tools.setLocation(e.getX(),e.getY());  
            }  
            tools.setVisible(true);  
            tools.toFront();  
           }  
          });  

   		//对于鼠标移动的监听
           this.addMouseMotionListener(new MouseMotionAdapter() {  

   		   //鼠标滑动的监听
   		   //在滑动过程中会被反复调用
           @Override  
           public void mouseDragged(MouseEvent e) {  
                        endx = e.getX();  
                        endy = e.getY();  

                        //新建一个图像缓存空间，临时图像，用于缓冲屏幕区域放置屏幕闪烁 
                        Image tempImage2=createImage(ScreenShotWindow.this.getWidth(),ScreenShotWindow.this.getHeight()); 
        				//把它的画笔拿过来,给gImage保存，用于绘图
                        Graphics g =tempImage2.getGraphics();                               
                        //然后显示出来
                        g.drawImage(tempImage, 0, 0, null);  
                        int x = Math.min(orgx, endx);  
                        int y = Math.min(orgy, endy);  
                        int width = Math.abs(endx - orgx)+1;  
                        int height = Math.abs(endy - orgy)+1;  
                        // 加上1防止width或height为0  
                        g.setColor(Color.BLUE);  
                        g.drawRect(x-1, y-1, width+1, height+1);  
                        //减1加1都了防止图片矩形框覆盖掉  
                        
        				//getSubimage(dint x,dint y,dint w,dint h)用于返回规定位置中的矩形图像到BufferedImag对象中
                        saveImage = image.getSubimage(x, y, width, height);
        				//用于画当前图像中的可用图像
                        g.drawImage(saveImage, x, y, null);  
                        ScreenShotWindow.this.getGraphics().drawImage(tempImage2,0,0,ScreenShotWindow.this);  
           }  
          });  
         }  
     	
         //重写了绘画的方法
            @Override  
            public void paint(Graphics g) {  
        		//new RescaleOp(float[] scaleFactors, float[] offsets, RenderingHints hints)
        		//构造一个具有所希望的缩放因子和偏移量的新 RescaleOp。
        		//RescaleOp 是有关图像缩放的类
        		//RescaleOp.filter(BufferedImage ,BufferedImage )
        		//用于对源图像image进行缩放
                RescaleOp ro = new RescaleOp(0.8f, 0, null);  
                tempImage = ro.filter(image, null);  
                g.drawImage(tempImage, 0, 0, this);  
            }  
            
            //把图片复制到剪贴板
        	public static void setClipboardImage(final Image image) {
        		Transferable trans = new Transferable() {
        			public DataFlavor[] getTransferDataFlavors() {
        				return new DataFlavor[] { DataFlavor.imageFlavor };
        			}
         
        			public boolean isDataFlavorSupported(DataFlavor flavor) {
        				return DataFlavor.imageFlavor.equals(flavor);
        			}
         
        			public Object getTransferData(DataFlavor flavor)
        					throws UnsupportedFlavorException, IOException {
        				if (isDataFlavorSupported(flavor))
        					return image;
        				throw new UnsupportedFlavorException(flavor);
        			}
         
        		};
        		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(trans,
        				null);
        	}
            
        	//把文字粘贴到剪贴板
        	protected static void setClipboardText(String writeMe) {
        		Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();//获取系统剪贴板
                Transferable tText = new StringSelection(writeMe);
                clip.setContents(tText, null);
            }
        	
        	
        	//把文件中的内容复制到string中
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
     
       public void choose() throws IOException
       {
    	   JFileChooser jfc=new JFileChooser();  
           jfc.setDialogTitle("保存");  
           //文件过滤器，用户过滤可选择文件  
           FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG", "jpg");//过滤文件名，只显示jpg格式文件
           jfc.setFileFilter(filter);  //将文件过滤器加入到文件选择中

           //初始化一个默认文件（此文件会生成到桌面上）  
           SimpleDateFormat sdf = new SimpleDateFormat("yyyymmddHHmmss");  
           String fileName = sdf.format(new Date());  //把时间作为图片名（防止图片名重复，而把之前的图片覆盖）
           File filePath = FileSystemView.getFileSystemView().getHomeDirectory();  //获取系统桌面的路径
           File defaultFile = new File(filePath + File.separator + fileName + ".jpg");  
           jfc.setSelectedFile(defaultFile);  

           int flag = jfc.showSaveDialog(this);  
           if(flag==JFileChooser.APPROVE_OPTION){  
            File file=jfc.getSelectedFile();  
            String path=file.getPath();  
            //检查文件后缀，放置用户忘记输入后缀或者输入不正确的后缀  
            if(!(path.endsWith(".jpg")||path.endsWith(".JPG"))){  
             path+=".jpg";  
            }  
            //写入文件  
            ImageIO.write(saveImage,"jpg",new File(path)); 
           }
       }
        	
        	     	
    //保存图像到文件 ,不可能不存储图片，直接粘贴图片中的文字，因为图片中的文字是要靠读取图片信息获得的，所以必须先存图片 
         public void saveImage() throws IOException, InterruptedException {
        //如果是简单复制，就把图片放到剪贴板里面,并打开文件管理系统
          if(hotkey.k==88)
     	  	{
        	choose();  
     	   setClipboardImage(saveImage);
     	   }
          else {//需要文字识别
       	   try {
       		   //这里是获取项目路径，从而把图片放在项目路径下，不必去选择路径
       		String classPath = ScreenShot.class.getResource("/").getPath();  
       		classPath=classPath.substring(1);
       		ImageIO.write(saveImage,"jpg",new File(classPath+"1.jpg"));
       		  //通过Tesseract-OCR/tesseract把图像转换成文字
  	            Process  pro = Runtime.getRuntime()
  	                           .exec(new String[]{System.getProperty("user.dir")+"\\lib\\tesseract.exe", 
  	                        		   			  classPath+"1.jpg",
  	                        		   			classPath+"1"});
  	         System.out.println(classPath+"1.jpg");
  	         System.out.println(classPath+"1");
  	        //这个是必须加的，因为如果不加下面这句话，上面的还没有执行完毕，1.txt还没有被创建，下面是打不开的，会报错
  	         pro.waitFor();
		    File test=new File(classPath+"1.txt");
		    //文字内容写到string当中
		    String content=txt2String(test);
		    //把string内容写入剪贴板
		    setClipboardText(content);
		    //删除这个文件,和图片
	        new File(classPath+"1.txt").delete();
	        new File(classPath+"1.jpg").delete();
  	            
  	        } catch (IOException e) {
  	            e.printStackTrace();
  	        }
          }
          
          
         }  
}//ScreenShotWindow结束的地方  
        
        
//保存和取消的窗口
class ToolsWindow extends JWindow implements ActionListener{  
         /**
			 * 
			 */
		private static final long serialVersionUID = 1L;
		  private ScreenShotWindow parent;  
		  JButton closeButton=null;
		  JButton saveButton=null;
		  
		  private void setproper(int x,int y){
			  //todo 让tool bar放置在合适的位置
		  }
		  
          public ToolsWindow(ScreenShotWindow parent,int x,int y) {  
          this.parent=parent;  
          this.init();  
  		//将组件移到(x,y)的位置
          this.setLocation(x, y);  
  		//调整窗口的大小来适应控件
          this.pack();  
          this.setVisible(true);  
         }  

         private void init(){  
          
          //设置布局
          this.setLayout(new BorderLayout());  
          JToolBar toolBar=new JToolBar("Java 截图");  

          //保存按钮  
          saveButton=new JButton(new ImageIcon());
          saveButton.addActionListener(this);
          toolBar.add(saveButton);   

          //关闭按钮  
          closeButton=new JButton(new ImageIcon());
          closeButton.addActionListener(this);
          toolBar.add(closeButton);  

          this.add(toolBar,BorderLayout.NORTH);  
         }

         //两个按键的响应，因为这两个按键按下以后，就表示一次截屏已经完成，就要关闭那两个窗口了
         //所以最后要释放掉两个窗口的资源
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			//如果是按下savebutton的话
			if(e.getSource()==saveButton) {
				try {
					//开始存储图片
					parent.saveImage();
					//保存完以后就释放掉截屏窗口和tool的窗口
					parent.dispose();
					this.dispose();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
	             
			}
			//如果是取消的窗口的按键
			else if(e.getSource()==closeButton) {
				//就释放掉截屏窗口和tool的窗口
				parent.dispose();//把截屏窗口取消掉
				this.dispose();
			}
			
			
		}

		 

}//ToolsWindow结束地方