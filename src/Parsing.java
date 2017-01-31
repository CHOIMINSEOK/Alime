

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.*;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.win32.W32APIOptions;

public class Parsing {
	final static int WM_SETTEXT = 0x000C;
	final static int VK_ENTER	= 0x0D;
	
	public static void main(String[] args)throws Exception{

		Document doc = Jsoup.connect("http://electronic.hanyang.ac.kr/notice/list_hi.php").get();
		Elements notices = doc.select("a[href*=javascript]");

		//ī�� �����ִ��� Ȯ��
		HWND hKakao = User32.INSTANCE.FindWindow(null, "īī����");
		HWND hChat;
		
		if(hKakao == null){
			//īī���� ����
			Runtime.getRuntime().exec("C:\\Program Files (x86)\\Kakao\\KakaoTalk\\KakaoTalk.exe");
		}
		
		//EVA_VH_ListControl-EVA_CustomScrollCtrl ī�� �� ������ ȭ��
		hKakao = User32.INSTANCE.FindWindow(null, "īī����");

		User32.INSTANCE.ShowWindow(hKakao, 9 );        // SW_RESTORE
		User32.INSTANCE.SetForegroundWindow(hKakao);   // bring to front
		
		
		//ä�ù� Ȯ��
		hKakao = User32.INSTANCE.FindWindow(null, "����"); // window title
		
		if (hKakao == null) {
			System.out.println("ī���� Ű����");
			return ;
		}
		
		hChat = User32.INSTANCE.FindWindowEx(hKakao, null, "RichEdit20W", null);
		User32.INSTANCE.ShowWindow(hKakao, 9 );        // SW_RESTORE
		User32.INSTANCE.SetForegroundWindow(hKakao);   // bring to front
		
		for(Element e: notices){
			SendText(hChat, e.text());
			System.out.println( e.text());
		}

			
	}
	
	public static void SendText(HWND hEdit, String notice) {		
	    MyUser32.INSTANCE.SendMessage(hEdit, WM_SETTEXT, 0, notice);
	   
	    try{
	    	Thread.sleep(500);
	    } catch(InterruptedException e){
	    	e.getMessage();
	    }
	    
	    MyUser32.INSTANCE.PostMessage(hEdit, User32.WM_KEYDOWN, VK_ENTER, 0);
	    MyUser32.INSTANCE.PostMessage(hEdit, User32.WM_KEYUP, VK_ENTER, 0);
	}
	public interface MyUser32 extends User32 {
	    MyUser32 INSTANCE = (MyUser32)Native.loadLibrary("user32", MyUser32.class, W32APIOptions.DEFAULT_OPTIONS);
	    LRESULT SendMessage(HWND hWnd, int Msg, int wParam, String lParam);
	    int PostMessage(HWND hWnd, int msg, int wParam, int lParam); 
	    
	}

}

