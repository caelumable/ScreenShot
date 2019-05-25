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
     * �÷����������ע���ϵͳ�ȼ��¼� 
     * 
     * @param key:�������ȼ���ʶ 
     */  
    public void onHotKey(int key) {  
        switch (key) {  
            case KEY_1:  
            System.out.println("ctrl+alt+Q����........."); 
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
            	System.out.println("ճ������"); 
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
            	
            	System.out.println("ϵͳ�˳�..........");  
                destroy();  
                
        }  
  
    }  
  
  
    /** 
     * ���ע�Ტ�˳� 
     */  
    void destroy() {  
        JIntellitype.getInstance().unregisterHotKey(KEY_1);  
        JIntellitype.getInstance().unregisterHotKey(KEY_2);  
        JIntellitype.getInstance().unregisterHotKey(KEY_3);  
        System.exit(0);  
    }  
  
    /** 
     * ��ʼ���ȼ���ע������¼� 
     */  
    void initHotkey() {  
        //����KEY_1��ʾ�����ȼ���ϵı�ʶ���ڶ���������ʾ��ϼ������û����Ϊ0�����ȼ���Ӧctrl+alt+I  
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
  
        //����ģ�ⳤʱ��ִ�е�����  
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
       

//�������ڣ��̳�jwindow����һ���ޱ������Ĵ���
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
           //��ȡ��Ļ�ߴ�  
           Dimension d = Toolkit.getDefaultToolkit().getScreenSize();  
           this.setBounds(0, 0, d.width, d.height);  

           //��ȡ��Ļ  
           Robot robot = new Robot();  
           image = robot.createScreenCapture(new Rectangle(0, 0, d.width,d.height));  

           this.addMouseListener(new MouseAdapter() {  
            @Override  
          //��갴�µ��¼�����
           public void mousePressed(MouseEvent e) {  
                     orgx = e.getX();  
                     orgy = e.getY();  

                     if(tools!=null){  
                      tools.setVisible(false);  
                     }  
           }  
           @Override  
			//���̧����¼�����
           public void mouseReleased(MouseEvent e) {    
            if(tools==null){  
             //̧���ʱ�򴴽���������
             tools=new ToolsWindow(ScreenShotWindow.this,e.getX(),e.getY());  
            }else{  
             tools.setLocation(e.getX(),e.getY());  
            }  
            tools.setVisible(true);  
            tools.toFront();  
           }  
          });  

   		//��������ƶ��ļ���
           this.addMouseMotionListener(new MouseMotionAdapter() {  

   		   //��껬���ļ���
   		   //�ڻ��������лᱻ��������
           @Override  
           public void mouseDragged(MouseEvent e) {  
                        endx = e.getX();  
                        endy = e.getY();  

                        //�½�һ��ͼ�񻺴�ռ䣬��ʱͼ�����ڻ�����Ļ���������Ļ��˸ 
                        Image tempImage2=createImage(ScreenShotWindow.this.getWidth(),ScreenShotWindow.this.getHeight()); 
        				//�����Ļ����ù���,��gImage���棬���ڻ�ͼ
                        Graphics g =tempImage2.getGraphics();                               
                        //Ȼ����ʾ����
                        g.drawImage(tempImage, 0, 0, null);  
                        int x = Math.min(orgx, endx);  
                        int y = Math.min(orgy, endy);  
                        int width = Math.abs(endx - orgx)+1;  
                        int height = Math.abs(endy - orgy)+1;  
                        // ����1��ֹwidth��heightΪ0  
                        g.setColor(Color.BLUE);  
                        g.drawRect(x-1, y-1, width+1, height+1);  
                        //��1��1���˷�ֹͼƬ���ο򸲸ǵ�  
                        
        				//getSubimage(dint x,dint y,dint w,dint h)���ڷ��ع涨λ���еľ���ͼ��BufferedImag������
                        saveImage = image.getSubimage(x, y, width, height);
        				//���ڻ���ǰͼ���еĿ���ͼ��
                        g.drawImage(saveImage, x, y, null);  
                        ScreenShotWindow.this.getGraphics().drawImage(tempImage2,0,0,ScreenShotWindow.this);  
           }  
          });  
         }  
     	
         //��д�˻滭�ķ���
            @Override  
            public void paint(Graphics g) {  
        		//new RescaleOp(float[] scaleFactors, float[] offsets, RenderingHints hints)
        		//����һ��������ϣ�����������Ӻ�ƫ�������� RescaleOp��
        		//RescaleOp ���й�ͼ�����ŵ���
        		//RescaleOp.filter(BufferedImage ,BufferedImage )
        		//���ڶ�Դͼ��image��������
                RescaleOp ro = new RescaleOp(0.8f, 0, null);  
                tempImage = ro.filter(image, null);  
                g.drawImage(tempImage, 0, 0, this);  
            }  
            
            //��ͼƬ���Ƶ�������
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
            
        	//������ճ����������
        	protected static void setClipboardText(String writeMe) {
        		Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();//��ȡϵͳ������
                Transferable tText = new StringSelection(writeMe);
                clip.setContents(tText, null);
            }
        	
        	
        	//���ļ��е����ݸ��Ƶ�string��
        	public static String txt2String(File file){
                StringBuilder result = new StringBuilder();
                try{
                    BufferedReader br = new BufferedReader(new FileReader(file));//����һ��BufferedReader������ȡ�ļ�
                    String s = null;
                    while((s = br.readLine())!=null){//ʹ��readLine������һ�ζ�һ��
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
           jfc.setDialogTitle("����");  
           //�ļ����������û����˿�ѡ���ļ�  
           FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG", "jpg");//�����ļ�����ֻ��ʾjpg��ʽ�ļ�
           jfc.setFileFilter(filter);  //���ļ����������뵽�ļ�ѡ����

           //��ʼ��һ��Ĭ���ļ������ļ������ɵ������ϣ�  
           SimpleDateFormat sdf = new SimpleDateFormat("yyyymmddHHmmss");  
           String fileName = sdf.format(new Date());  //��ʱ����ΪͼƬ������ֹͼƬ���ظ�������֮ǰ��ͼƬ���ǣ�
           File filePath = FileSystemView.getFileSystemView().getHomeDirectory();  //��ȡϵͳ�����·��
           File defaultFile = new File(filePath + File.separator + fileName + ".jpg");  
           jfc.setSelectedFile(defaultFile);  

           int flag = jfc.showSaveDialog(this);  
           if(flag==JFileChooser.APPROVE_OPTION){  
            File file=jfc.getSelectedFile();  
            String path=file.getPath();  
            //����ļ���׺�������û����������׺�������벻��ȷ�ĺ�׺  
            if(!(path.endsWith(".jpg")||path.endsWith(".JPG"))){  
             path+=".jpg";  
            }  
            //д���ļ�  
            ImageIO.write(saveImage,"jpg",new File(path)); 
           }
       }
        	
        	     	
    //����ͼ���ļ� ,�����ܲ��洢ͼƬ��ֱ��ճ��ͼƬ�е����֣���ΪͼƬ�е�������Ҫ����ȡͼƬ��Ϣ��õģ����Ա����ȴ�ͼƬ 
         public void saveImage() throws IOException, InterruptedException {
        //����Ǽ򵥸��ƣ��Ͱ�ͼƬ�ŵ�����������,�����ļ�����ϵͳ
          if(hotkey.k==88)
     	  	{
        	choose();  
     	   setClipboardImage(saveImage);
     	   }
          else {//��Ҫ����ʶ��
       	   try {
       		   //�����ǻ�ȡ��Ŀ·�����Ӷ���ͼƬ������Ŀ·���£�����ȥѡ��·��
       		String classPath = ScreenShot.class.getResource("/").getPath();  
       		classPath=classPath.substring(1);
       		ImageIO.write(saveImage,"jpg",new File(classPath+"1.jpg"));
       		  //ͨ��Tesseract-OCR/tesseract��ͼ��ת��������
  	            Process  pro = Runtime.getRuntime()
  	                           .exec(new String[]{System.getProperty("user.dir")+"\\lib\\tesseract.exe", 
  	                        		   			  classPath+"1.jpg",
  	                        		   			classPath+"1"});
  	         System.out.println(classPath+"1.jpg");
  	         System.out.println(classPath+"1");
  	        //����Ǳ���ӵģ���Ϊ�������������仰������Ļ�û��ִ����ϣ�1.txt��û�б������������Ǵ򲻿��ģ��ᱨ��
  	         pro.waitFor();
		    File test=new File(classPath+"1.txt");
		    //��������д��string����
		    String content=txt2String(test);
		    //��string����д�������
		    setClipboardText(content);
		    //ɾ������ļ�,��ͼƬ
	        new File(classPath+"1.txt").delete();
	        new File(classPath+"1.jpg").delete();
  	            
  	        } catch (IOException e) {
  	            e.printStackTrace();
  	        }
          }
          
          
         }  
}//ScreenShotWindow�����ĵط�  
        
        
//�����ȡ���Ĵ���
class ToolsWindow extends JWindow implements ActionListener{  
         /**
			 * 
			 */
		private static final long serialVersionUID = 1L;
		  private ScreenShotWindow parent;  
		  JButton closeButton=null;
		  JButton saveButton=null;
		  
		  private void setproper(int x,int y){
			  //todo ��tool bar�����ں��ʵ�λ��
		  }
		  
          public ToolsWindow(ScreenShotWindow parent,int x,int y) {  
          this.parent=parent;  
          this.init();  
  		//������Ƶ�(x,y)��λ��
          this.setLocation(x, y);  
  		//�������ڵĴ�С����Ӧ�ؼ�
          this.pack();  
          this.setVisible(true);  
         }  

         private void init(){  
          
          //���ò���
          this.setLayout(new BorderLayout());  
          JToolBar toolBar=new JToolBar("Java ��ͼ");  

          //���水ť  
          saveButton=new JButton(new ImageIcon());
          saveButton.addActionListener(this);
          toolBar.add(saveButton);   

          //�رհ�ť  
          closeButton=new JButton(new ImageIcon());
          closeButton.addActionListener(this);
          toolBar.add(closeButton);  

          this.add(toolBar,BorderLayout.NORTH);  
         }

         //������������Ӧ����Ϊ���������������Ժ󣬾ͱ�ʾһ�ν����Ѿ���ɣ���Ҫ�ر�������������
         //�������Ҫ�ͷŵ��������ڵ���Դ
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			//����ǰ���savebutton�Ļ�
			if(e.getSource()==saveButton) {
				try {
					//��ʼ�洢ͼƬ
					parent.saveImage();
					//�������Ժ���ͷŵ��������ں�tool�Ĵ���
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
			//�����ȡ���Ĵ��ڵİ���
			else if(e.getSource()==closeButton) {
				//���ͷŵ��������ں�tool�Ĵ���
				parent.dispose();//�ѽ�������ȡ����
				this.dispose();
			}
			
			
		}

		 

}//ToolsWindow�����ط�