import com.sun.scenario.effect.ImageData;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;



public class interfaceTest implements ListSelectionListener , ActionListener
{
    JFrame f;
    int port=81;
    String ip="49.158.107.110";//連線ip
    String uploadip="49.158.107.110";
    String net="";//檢查網路回傳訊息
    boolean checknet=false;//檢查網路判斷參數
    String[] _fileName=new String[100];
    String[] _filePath=new String[100];
    String upfile="";
    String theStrDestDir = "D:\\";//儲存位置
    JList<String> list;

     
    public static void main(String [] args) throws IOException
    {
         //LoadData img = new LoadData();
        interfaceTest fa =new interfaceTest();
        fa.loading();
        
        fa.checkNet("http://"+fa.ip+"/Uploads/");      //檢查連線
            if(fa.checknet==true)
            {
                JOptionPane.showMessageDialog(null,fa.net, "連線狀態提示", JOptionPane.PLAIN_MESSAGE );
                fa.getnetFile(fa.ip);    //抓遠端檔案名稱 下載連結
                try
                {
                    fa.setGUI();        //設定介面  download.upload refresh
                }
                catch(Exception fe)
                {
                    JOptionPane.showMessageDialog(null,"產生介面失敗"+fe.getMessage(), "介面產生提示", JOptionPane.PLAIN_MESSAGE );
                }
                
                
            }
            else
            {
                JOptionPane.showMessageDialog(null,fa.net, "連線狀態提示", JOptionPane.PLAIN_MESSAGE );
                
            }
        
        
    }
    static void pr(String input)
    {
        System.out.println(input);
    }
    public void reGUI(JFrame f) throws IOException
    {
        f.dispose();
        interfaceTest fa=new interfaceTest();
        fa.getnetFile(fa.ip);
        fa.setGUI();
    }
    
    public void loading()
    {
         JFrame mainFrame;
    JLabel icon;
    Container contentPane;
     mainFrame = new JFrame();
     contentPane = mainFrame.getContentPane();
     icon = new JLabel(new ImageIcon("loading.jpg"));
     contentPane.add(icon);
     //將Frame置於螢幕中間
     Dimension dim = mainFrame.getToolkit().getScreenSize();
     Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
     mainFrame.setLocation(screenSize.width/3  , screenSize.height/3 );
     mainFrame.setSize(400, 300);
     //取消標題列
     mainFrame.setUndecorated(true);
     mainFrame.setVisible(true);
     
     try 
     {
        Thread.sleep(5000);
        mainFrame.dispose();
     } 
     catch (InterruptedException e) 
     {
        e.printStackTrace();
     }
    }
    
    public void setGUI() throws UnknownHostException
    {
        java.net.URL imgURL1 = interfaceTest.class.getResource("/images/refresh.png");
        java.net.URL imgURL2 = interfaceTest.class.getResource("/images/set.png");
        java.net.URL imgURL3 = interfaceTest.class.getResource("/images/upload.png");
        java.net.URL imgURL4 = interfaceTest.class.getResource("/images/checked.png");
        ImageIcon im1 = new ImageIcon(imgURL1);
        ImageIcon im2 = new ImageIcon(imgURL2);
        ImageIcon im3 = new ImageIcon(imgURL3);
        ImageIcon im4 = new ImageIcon(imgURL4);
        File f1=new File("set.png");
        File f2=new File("upload.png");
        File f3=new File("refresh.png");
    
       
        
        Label lb=new Label();

        JLabel imageLabel = new JLabel(im4); 
        InetAddress myComputer = InetAddress.getLocalHost() ;
        lb.setText(myComputer+" is connecting...");
        
        lb.setForeground(Color.blue);
        f =new JFrame("yee");
        f. setResizable(false);
        JScrollPane jsp=new JScrollPane();
            list=new JList<String>(_fileName);
        JButton bt_refresh=new JButton("更新",im1);
        JButton bt_set=new JButton("設定",im2);
        JButton bt_upload=new JButton("上傳",im3);
        bt_refresh.setBackground(new Color(0,150,0));
        bt_set.setBackground(new Color(0,150,0));
        bt_upload.setBackground(new Color(0,150,0));
        
        //宣告
        
        f.getContentPane().setLayout(null);
        bt_set.addActionListener(this);
        //bt_set.addActionListener(new interfaceTest(1));
        bt_refresh.addActionListener(this);
        //bt_refresh.addActionListener(new interfaceTest(2));
        bt_upload.addActionListener(this);
        //bt_upload.addActionListener(new interfaceTest(3));
        list.addListSelectionListener(this);
        
        jsp.setViewportView(list);
        bt_refresh.setBounds(15,5, 100, 50);
        bt_set.setBounds(145,5, 100, 50);
        bt_upload.setBounds(275,5, 100, 50);
        jsp.setBounds(0, 60, 400, 360);
        f.setBounds(100, 100, 400, 500);
        //設定屬性
        lb.setBounds(30, 445, 210, 30);
        imageLabel.setBounds(5, 450, 20, 20);
        f.add(imageLabel);
        f.add(lb);
        f.add(jsp);
        f.add(bt_refresh);
        f.add(bt_set);
        f.add(bt_upload);
        //加入元件
        
        f.setVisible(true);
        
        f.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            //主視窗增加到視窗監聽器中
            f.addWindowListener(new WindowAdapter() 
            {//視窗關閉執行的動作
              public void windowClosing(WindowEvent e) 
              {//宣告整數result存放視窗執行的動作,line:147~151設定關閉視窗時 詢問是否關閉視窗的樣式
                int result=JOptionPane.showConfirmDialog
                            (f,
                            "確定要結束程式嗎?",
                            "確認訊息",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE);
                //確定關閉視窗
                    if (result==JOptionPane.YES_OPTION)
                        {//結束程式
                            System.exit(0);
                        }
                }    
            });
    }
    public void checkNet(String input)
    {
         try {
            java.net.URL url = new java.net.URL(input);
            java.net.HttpURLConnection uc = (java.net.HttpURLConnection) url
                    .openConnection();
            uc.setRequestProperty("User-agent", "IE/6.0");
            uc.setReadTimeout(10000);// 設定timeout時間
            uc.connect();// 連線
            System.out.println("網址/ip位置 : "+java.net.Inet4Address.getByName(url.getHost()));
            int status = uc.getResponseCode();
            System.out.println(status);
            
            switch (status) {
            case java.net.HttpURLConnection.HTTP_GATEWAY_TIMEOUT://504
                System.out.println("連線網址逾時!");
                net="連線網址逾時!";
                break;
            case java.net.HttpURLConnection.HTTP_FORBIDDEN://403
                System.out.println("連線網址禁止!");
                net="連線網址禁止!";
                break;
            case java.net.HttpURLConnection.HTTP_INTERNAL_ERROR://500
                System.out.println("連線網址錯誤或不存在!");
                net="連線網址錯誤或不存在!";
                break;
            case java.net.HttpURLConnection.HTTP_NOT_FOUND://404
                System.out.println("連線網址不存在!");
                net="連線網址不存在!";
                break;
            case java.net.HttpURLConnection.HTTP_OK:
                
                System.out.println("OK!");
                net="連線成功";
                checknet=true;
                break;
 
            }
 
        } catch (java.net.MalformedURLException e) {
            System.out.println("網址格式錯誤!!!");
            net="網址格式錯誤!";
            e.printStackTrace();
        } catch (java.io.IOException e) {
            System.out.println("連線有問題!!!!!!");
            net="\n連線失敗,請主機狀態!\n如果不確定如何設定網路及權限,請聯絡您的網路管理者\n\n";
            e.printStackTrace();
        }
    }
    public void getnetFile(String ip) throws MalformedURLException, IOException
    {
        int length_count=0;
        String temp;
        URL url = new URL("http://"+ip+"/Uploads/");
        Document xmlDoc =  Jsoup.parse(url, 3000); //使用Jsoup jar 去解析網頁
        Elements netPath = xmlDoc.select("a");  //要解析的tag元素為td
        for(int i=1;i<30000;i++)
        {
            try
            {
                temp=netPath.get(i).toString().substring(netPath.get(i).toString().indexOf(">")+1,
                        netPath.get(i).toString().lastIndexOf("<"));
                _fileName[length_count]=temp;//檔名存到陣列
                temp="";
                _filePath[length_count]= netPath.get(i).toString().substring(18, netPath.get(i).toString().indexOf(">")-1);//路徑存到陣列
                length_count++;
            }
            catch(IndexOutOfBoundsException ie)
            {
                break;
            }
        }
    }
    
    public void Upload(String uploadfile)
    {
            this.connectServer(uploadip, 81, upfile);
            
    }
    public void Download(String file,String saveName,String ip)
    {
         String strUrl = "http://"+ip+"/Uploads/";//下載位置
        strUrl+=file;
        System.out.println(strUrl);
            try
            {
                //URL source = new URL(strUrl);
               URL source = new URL(strUrl);
               File theStockDest = new File(theStrDestDir);
               FileUtils.forceMkdir(theStockDest);
               File destination = new File(theStrDestDir+ saveName);
               FileUtils.copyURLToFile(source, destination);
               //File file = new File(".");
               System.out.println("File Downloaded!");
            } 
            catch (MalformedURLException e)
            {
               e.printStackTrace();
            } 
            catch (IOException e)
            {
                e.printStackTrace();
            }
    }
    public void reFresh() throws IOException
    {
        for(int i=0;i<_fileName.length;i++)
        {
            _fileName[i]="";
            _filePath[i]="";
        }
        this.getnetFile(this.ip);
        //list.validate();
        
        list.repaint();
    }
    public void connectServer(String ip,int port,String fileName)
    {
         try { 
            System.out.println("簡易檔案傳送..."); 
            String remoteHost = ip;
            File file = new File(fileName); 
            System.out.printf("遠端主機: %s%n", remoteHost); 
            System.out.printf("遠端主機連接埠: %d%n", port);
            System.out.printf("傳送檔案: %s%n", file.getName());
            Socket skt = new Socket(remoteHost, port); 
            System.out.println("連線成功！嘗試傳送檔案....");
            PrintStream printStream = 
                new PrintStream(skt.getOutputStream()); 
            printStream.println(file.getName()); 
            System.out.print("OK! 傳送檔案...."); 
            BufferedInputStream inputStream = 
                new BufferedInputStream( 
                        new FileInputStream(file)); 
            int readin; 
            while((readin = inputStream.read()) != -1) { 
                 printStream.write(readin); 
                 Thread.yield();
            } 

            printStream.flush();
            printStream.close();
            inputStream.close(); 
            
            skt.close();
            
            System.out.println("\n檔案傳送完畢！"); 
        } 
        catch(Exception e) { 
            e.printStackTrace(); 
        } 
    }
    @Override
    public void actionPerformed(ActionEvent al) 
    {
        if(al.getActionCommand().startsWith("上"))//上傳
        {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
                if(returnValue == JFileChooser.APPROVE_OPTION)
                {
                    JOptionPane.showMessageDialog(null, fileChooser.getSelectedFile().getAbsoluteFile().toString(),"選擇檔案", JOptionPane.PLAIN_MESSAGE );//提示選取的檔案
                    this.upfile=fileChooser.getSelectedFile().getAbsoluteFile().toString();//選檔案
                    try
                    {
                        this.Upload(this.upfile);
                    }
                    catch(Exception ie)
                    {
                        JOptionPane.showMessageDialog(null, "上傳檔案 "+fileChooser.getSelectedFile().getAbsoluteFile().toString()+
                                "時發生錯誤,請確定檔案是否超過伺服器接收最大值\n\n錯誤訊息 : "+ie.getMessage(),"選擇檔案", JOptionPane.PLAIN_MESSAGE );//提示選取的檔案
                    }
                    
                    //上傳
                }
        }
        else if(al.getActionCommand().startsWith("設"))//設定下載位置
        {
            JFileChooser file = new JFileChooser (".");
            file.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);//只能选择目录
            int returnValue = file.showOpenDialog(null);
            if(returnValue == JFileChooser.APPROVE_OPTION)
            {
                theStrDestDir=file.getSelectedFile().getAbsolutePath();//設定路徑
                JOptionPane.showMessageDialog(null, "\n新的下載路徑 : "+theStrDestDir,"設定路徑", JOptionPane.PLAIN_MESSAGE );//顯示路徑
            }
                
            
            
        }
        else if(al.getActionCommand().startsWith("更"))//畫面更新
        {
            //this.setGUI();
            //this.reFresh();
            try
            {               this.reFresh();
                 this.reGUI(f);
            }
            catch(Exception re)
            {
                pr(re.getMessage());
            }
           
            
            
            
            JOptionPane.showMessageDialog(null, "畫面更新","更新提示", JOptionPane.PLAIN_MESSAGE );
        }
    }
    @Override
    public void valueChanged(ListSelectionEvent e) 
    {
        Object file=((JList)e.getSource()).getSelectedValue();//人看得懂的檔名
        Object obj=((JList)e.getSource()).getSelectedIndex();//位置檔名對應list的選項位置
        int temp = Integer.valueOf(obj.toString());
        try
        {
            Download(_filePath[temp],file.toString(),ip);
            JOptionPane.showMessageDialog(null, file.toString()+"   已經被下載至 "+theStrDestDir , "下載提示", JOptionPane.PLAIN_MESSAGE );
        }
        catch(Exception ae)
        {
            JOptionPane.showMessageDialog(null, "空的選項" , "下載提示", JOptionPane.PLAIN_MESSAGE );
        }
        
    }
    
}